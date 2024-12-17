package marco.policies;

import java.security.Principal;
import java.util.Base64;
import java.util.Set;
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

@ApplicationScoped
@Unremovable
public class MyBackendHttpSecPolicy implements HttpSecurityPolicy {

  @ConfigProperty(name = "quarkus.http.auth.permission.backend.policy")
  String _policyName;

  @Inject
  SecurityIdentityAssociation identity;

  @Override
  public String name() {
    Log.trace("===>>> MyBackendHttpSecPolicy POLICY NAME: "+_policyName);
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
        Log.trace("===> MyBackendHttpSecPolicy header value decoded: "+_decoded);

        // unsecure token validation, just to test the code  
        if ( _decoded.startsWith("SID-0123456789")) {
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

  private static boolean _checkPermission(RoutingContext routingContext) {
    boolean permit = false;
    boolean _validHost = false;
    boolean _validSvcHeader = false;
    boolean _validIdentity = false;
    String _user = "";
    String _path = routingContext.request().path();

    _validHost = _isValidRemoteHost(routingContext);
    if (_validHost) {
      _validSvcHeader = _isServiceHeaderPresent(routingContext);                
      SecurityIdentity secIdentity = QuarkusHttpUser.getSecurityIdentityBlocking(routingContext, null);
      if (secIdentity != null) {
        if (!secIdentity.isAnonymous()) {
          Principal principal = secIdentity.getPrincipal();
          if (principal != null) {
            _user = principal.getName();
            if (_user.length() > 0) {
              _validIdentity = true;
              if (Log.isTraceEnabled()) {
                Log.trace("===>>> MyBackendHttpSecPolicy invoked path: " + _path);
                Log.trace("===>>> MyBackendHttpSecPolicy ide: " + _user);
                Set<String> roles = secIdentity.getRoles();
                for (String _role : roles) {
                  Log.trace("===>>> MyBackendHttpSecPolicy ide roles: " + _role);
                }
              }
            }
          }
        }
      }
    }
    if ( _validHost && _validSvcHeader && _validIdentity ) {
      permit = true;
    }
    Log.trace("===>>> MyBackendHttpSecPolicy _checkPermission [" + permit + "] validHost["+_validHost+"] validSvcHeader["+_validSvcHeader+"] validIdentity["+_validIdentity+"] user [" + _user + "] path[" + _path + "]");

    return permit;
  }

  @Override
  public Uni<CheckResult> checkPermission(RoutingContext routingContext, Uni<SecurityIdentity> identity, AuthorizationRequestContext requestContext) {
    if (_checkPermission(routingContext)) {
      return Uni.createFrom().item(CheckResult.PERMIT);
    } else {
      return Uni.createFrom().item(CheckResult.DENY);
    }
  }

}
