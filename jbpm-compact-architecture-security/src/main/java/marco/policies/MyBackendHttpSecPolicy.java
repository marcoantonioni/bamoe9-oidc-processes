package marco.policies;

import java.io.StringReader;
import java.security.Principal;
import java.util.Base64;
import java.util.Set;
import java.util.StringTokenizer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import io.quarkus.arc.Unremovable;
import io.quarkus.logging.Log;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.SecurityIdentityAssociation;
import io.quarkus.vertx.http.runtime.security.HttpSecurityPolicy;
import io.quarkus.vertx.http.runtime.security.QuarkusHttpUser;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

/**
 * The purpose of this policy is to protect all URIs configured in the application.properties file (see: security policies configuration)
 */
@ApplicationScoped
@Unremovable
public class MyBackendHttpSecPolicy implements HttpSecurityPolicy {

  @ConfigProperty(name = "quarkus.http.auth.permission.backend.policy")
  String _policyName;

  @ConfigProperty(name = "quarkus.http.auth.permission.backend.only-authenticated")
  boolean onlyAuthenticated = true;

  @ConfigProperty(name = "quarkus.http.auth.permission.backend.oidc-client-id")
  String requiredClientId;

  @ConfigProperty(name = "quarkus.http.auth.permission.backend.oidc-client-scope")
  String requiredClientScope;

  @Inject
  SecurityIdentityAssociation identity;

  @Override
  public String name() {
    Log.trace("===>>> MyBackendHttpSecPolicy POLICY NAME: " + _policyName + ", onlyAuthenticated[" + onlyAuthenticated + "]");
    return _policyName;
  }

  private static boolean _isValidRemoteHost(RoutingContext routingContext) {
    boolean validHost = false;

    String _hostAddress = routingContext.request().remoteAddress().hostAddress();
    if ("127.0.0.1".equals(_hostAddress)) {
      validHost = true;
      Log.trace("===>>> MyBackendHttpSecPolicy _isValidRemoteHost: true");
    } else {
      String _hostName = routingContext.request().authority().host();
      Log.trace("===>>> MyBackendHttpSecPolicy _isValidRemoteHost: false _hostAddress[" + _hostAddress + "] _hostName[" + _hostName + "]");
    }

    return validHost;
  }

  private static boolean _isServiceHeaderPresent(RoutingContext routingContext) {
    boolean headerFound = false;
    String srvId = routingContext.request().headers().get("_PRIVATE_SRV_ID");
    Log.trace("===>>> MyBackendHttpSecPolicy header key: " + srvId);
    try {
      if (srvId != null) {
        String _decoded = new String(Base64.getDecoder().decode(srvId));
        Log.trace("===> MyBackendHttpSecPolicy header value decoded: " + _decoded);

        // unsecure token validation, just to run a demo
        if (_decoded.startsWith("SID-BAMOE")) {
          headerFound = true;
        }

      } else {
        Log.trace("===>>> MyBackendHttpSecPolicy service header key not found");
      }
    } catch (Throwable t) {
      Log.trace("===>>> MyBackendHttpSecPolicy _isServiceHeaderPresent", t);
    }

    return headerFound;
  }

  private static boolean _isAuthenticated(boolean onlyAuthenticated, String requiredScope, String requiredCliId, RoutingContext routingContext) {
    boolean valid = true;
    if (onlyAuthenticated) {
      valid = false;

      SecurityIdentity secIdentity = QuarkusHttpUser.getSecurityIdentityBlocking(routingContext, null);
      if (secIdentity != null) {
        Log.trace("===>>> MyBackendHttpSecPolicy identity anonymous: " + secIdentity.isAnonymous());
        if (!secIdentity.isAnonymous()) {
          Principal principal = secIdentity.getPrincipal();
          if (principal != null) {
            String _user = principal.getName();
            String _path = routingContext.request().path();
            if (_user.length() > 0) {
              valid = true;
              if (Log.isTraceEnabled()) {
                Log.trace("===>>> MyBackendHttpSecPolicy invoked path: " + _path);
                Log.trace("===>>> MyBackendHttpSecPolicy user: " + _user);
                Set<String> roles = secIdentity.getRoles();
                for (String _role : roles) {
                  Log.trace("===>>> MyBackendHttpSecPolicy user roles: " + _role);
                }
              }
            }
          }
        }
      }

      if (!valid) {
        String headerAuth = routingContext.request().getHeader("Authorization");
        Log.trace("===>>> MyBackendHttpSecPolicy Authorization header: " + headerAuth);
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
          String token = headerAuth.replace("Bearer ", "");
          String[] chunks = token.split("\\.");
          if (chunks.length > 1) {
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String decodedChunk = new String(decoder.decode(chunks[1]));
            JsonReader reader = Json.createReader(new StringReader(decodedChunk));
            JsonObject _json = reader.readObject();
            JsonValue _urlIssuer = _json.get("iss");
            JsonValue _clientName = _json.get("azp");
            JsonValue _realmAccess = _json.get("realm_access");
            JsonValue _clientScope = _json.get("scope");
            String urlIssuer = _urlIssuer.toString().replaceAll("\"", "");
            String clientName = _clientName.toString().replaceAll("\"", "");
            String clientScope = _clientScope.toString().replaceAll("\"", "");
            String realmAccess = _realmAccess.toString().replaceAll("\"", "");

            Log.trace("===>>> MyBackendHttpSecPolicy JWT FIELD URL: " + urlIssuer);
            Log.trace("===>>> MyBackendHttpSecPolicy JWT FIELD CLIENT NAME: " + clientName);
            Log.trace("===>>> MyBackendHttpSecPolicy JWT FIELD SCOPE: " + clientScope);
            Log.trace("===>>> MyBackendHttpSecPolicy JWT FIELD REALM: " + realmAccess);

            JsonValue _roles = _realmAccess.asJsonObject().get("roles");
            JsonArray _rolesArray = _roles.asJsonArray();
            for (int i = 0; i < _rolesArray.size(); i++) {
              Log.trace("===>>> MyBackendHttpSecPolicy JWT ROLE: " + _rolesArray.get(i).toString());
            }

            StringTokenizer st = new StringTokenizer(clientScope, " ");
            boolean scopeFound = false;
            while (st.hasMoreElements()) {
              String _token = st.nextToken();
              scopeFound = requiredScope.equals(_token);
              if (scopeFound) {
                break;
              }
            }

            if (scopeFound && requiredCliId.equals(clientName)) {
              valid = true;
            }

          }
        }
      }

    }
    return valid;

  }

  private static boolean _checkPermission(boolean onlyAuthenticated, String requiredScope, String requiredCliId, RoutingContext routingContext) {
    boolean permit = false;
    boolean _validHost = false;
    boolean _validSvcHeader = false;
    boolean _validIdentity = false;

    _validHost = _isValidRemoteHost(routingContext);
    if (_validHost) {
      _validSvcHeader = _isServiceHeaderPresent(routingContext);
      if (_validSvcHeader) {
        _validIdentity = _isAuthenticated(onlyAuthenticated, requiredScope, requiredCliId, routingContext);
      }
    }
    if (_validHost && _validSvcHeader && _validIdentity) {
      permit = true;
    }
    Log.trace("===>>> MyBackendHttpSecPolicy _checkPermission [" + permit + "] validHost[" + _validHost + "] validSvcHeader[" + _validSvcHeader + "] validIdentity[" + _validIdentity + "]");

    return permit;
  }

  @Override
  public Uni<CheckResult> checkPermission(RoutingContext routingContext, Uni<SecurityIdentity> identity, AuthorizationRequestContext requestContext) {
    if (_checkPermission(onlyAuthenticated, requiredClientScope, requiredClientId, routingContext)) {
      return Uni.createFrom().item(CheckResult.PERMIT);
    } else {
      return Uni.createFrom().item(CheckResult.DENY);
    }
  }

}
