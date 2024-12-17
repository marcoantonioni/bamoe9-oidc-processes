package marco.studio.securitypolicies;

import static org.eclipse.microprofile.jwt.Claims.iss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.arc.Unremovable;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.jwt.consumer.Validator;

@SuppressWarnings("static-access")
@Unremovable
@ApplicationScoped
public class MyIssuerValidator implements Validator {

  @ConfigProperty(name = "quarkus.oidc.auth-server-url")
  String authServerUrl;

  @ConfigProperty(name = "quarkus.oidc.client-id")
  String oidcClientId;

  @ConfigProperty(name = "marco.studio.validate.token.scope")
  String tokenScope;

  @Override
  public String validate(JwtContext jwtContext) throws MalformedClaimException {
    String _subj = jwtContext.getJwtClaims().getSubject();
    String _issuer = jwtContext.getJwtClaims().getIssuer();
    String _clientId = jwtContext.getJwtClaims().getClaimValueAsString(iss.azp.name());
    String _claims = jwtContext.getJwtClaims().getClaimNames().toString();
    String _audience = jwtContext.getJwtClaims().getAudience().toString();
    String _scopes = jwtContext.getJwtClaims().getStringClaimValue("scope");
    Map<String, Object> _mapClaims = jwtContext.getJwtClaims().getClaimsMap();
    try {
      String _userName = (String) _mapClaims.get("preferred_username");
      Log.trace("===>>> MyIssuerValidator userName: " + _userName);

      @SuppressWarnings("unchecked")
      HashMap<String, Object> realmAccess = (HashMap<String, Object>) _mapClaims.get("realm_access");

      if (realmAccess != null) {
        Log.trace("===>>> MyIssuerValidator realmAccess: " + realmAccess);

        @SuppressWarnings("unchecked")
        ArrayList<String> roles = (ArrayList<String>) realmAccess.get("roles");

        for (int i = 0; i < roles.size(); i++) {
          Log.trace("===>>> MyIssuerValidator role: " + roles.get(i));
        }
      }
    } catch (Throwable t) {
      Log.error("===>>> MyIssuerValidator validate", t);
    }

    StringTokenizer st = new StringTokenizer(_scopes);
    boolean scopeFound = false;

    while (st.hasMoreElements()) {
      String _token = st.nextToken(" ");
      Log.trace("===>>> MyIssuerValidator validate received scope: [" + _token + "]");
      scopeFound = tokenScope.equals(_token);
      if (scopeFound)
        break;
    }
    if (Log.isTraceEnabled()) {
      Log.trace("===>>> RAW: " + jwtContext.getJwtClaims().getRawJson());
      Log.trace("===>>> MyIssuerValidator subject: " + _subj);
      Log.trace("===>>> MyIssuerValidator validate issuer[" + _issuer + "] authServerUrl[" + authServerUrl + "]");
      Log.trace("===>>> MyIssuerValidator validate clientId[" + _clientId + "] oidcClientId[" + oidcClientId + "]");
      Log.trace("===>>> MyIssuerValidator validate claims: [" + _claims + "]");
      Log.trace("===>>> MyIssuerValidator validate audience: [" + _audience + "]");
      Log.trace("===>>> MyIssuerValidator validate scope: [" + _scopes + "] tokenScope[" + tokenScope + "] found[" + scopeFound + "]");
    }
    if (_issuer.equals(authServerUrl) && _clientId.equals(oidcClientId) && scopeFound) {
      Log.trace("===>>> MyIssuerValidator validate OK for subject [" + _subj + "]");
      return null;
    }
    String _errMsg = "wrong issuer data in token";
    Log.trace("===>>> MyIssuerValidator validate ERROR: [" + _errMsg + "] authServerUrl[" + authServerUrl + "] oidcClientId[" + oidcClientId + "] scope [" + tokenScope + "] found[" + scopeFound + "] for subject [" + _subj + "]");
    return _errMsg;
  }
}
