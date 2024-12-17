package marco.studio.utils;

import org.eclipse.microprofile.jwt.JsonWebToken;
import io.quarkus.logging.Log;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

public class TokenUtils {

  public static String getRoles(JsonWebToken principal) {
    String _rolesData = "";
    if (principal != null) {
      JsonObject realmAccess = (JsonObject) principal.getClaim("realm_access");
      Log.trace("===>>> TokenUtils realmAccess: " + realmAccess);
      if (realmAccess != null) {
        JsonArray _arrRoles = (JsonArray) realmAccess.get("roles");
        if (_arrRoles != null) {
          int maxRoles = _arrRoles.size();
          StringBuffer sb = new StringBuffer();
          int idx = 0;
          for (JsonValue jsonValue : _arrRoles) {
            sb.append(new String(jsonValue.toString()).replace("\"", ""));
            if (++idx < (maxRoles)) {
              sb.append(",");
            }
          }
          _rolesData = sb.toString();
          Log.trace("===>>> TokenUtils user[" + principal.getName() + "] roles: " + _rolesData);
        }
      }
    }
    return _rolesData;
  }

}
