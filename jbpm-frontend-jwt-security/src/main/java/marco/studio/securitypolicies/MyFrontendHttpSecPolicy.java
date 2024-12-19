package marco.studio.securitypolicies;

import java.security.Principal;
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

/**
 * The purpose of this policy is to protect all URIs configured in the application.properties file (see: security policies configuration)
 */
@ApplicationScoped
@Unremovable
public class MyFrontendHttpSecPolicy implements HttpSecurityPolicy {
  
  @ConfigProperty(name = "quarkus.http.auth.permission.bamoe.policy")
  String _policyName;

  @Inject
  SecurityIdentityAssociation identity;

  @Override
  public String name() {
    return _policyName;
  }

  @Override
  public Uni<CheckResult> checkPermission(RoutingContext routingContext, Uni<SecurityIdentity> identity, AuthorizationRequestContext requestContext) {

    if (_checkPermission(routingContext)) {
      return Uni.createFrom().item(CheckResult.PERMIT);
    } else {
      return Uni.createFrom().item(CheckResult.DENY);
    }
  }

  private static boolean _checkPermission(RoutingContext routingContext) {
    boolean permit = false;
    String _user = "";
    String _path = routingContext.request().path();

    SecurityIdentity secIdentity = QuarkusHttpUser.getSecurityIdentityBlocking(routingContext, null);
    if (secIdentity != null) {
      Principal principal = secIdentity.getPrincipal();
      if (principal != null) {
        _user = principal.getName();
        if (Log.isTraceEnabled()) {
          Log.trace("===>>> MyFrontendHttpSecPolicy invoked path: " + _path);
          Log.trace("===>>> MyFrontendHttpSecPolicy ide: " + _user);
          Set<String> roles = secIdentity.getRoles();
          for (String _role : roles) {
            Log.trace("===>>> MyFrontendHttpSecPolicy ide roles: " + _role);
          }
        }
        permit = true;
      }
    }
    Log.trace("===>>> MyFrontendHttpSecPolicy _checkPermission [" + permit + "] user [" + _user + "] path[" + _path + "]");
    return permit;
  }

}
