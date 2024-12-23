package marco.studio.utils;

import java.util.HashMap;
import java.util.Map;
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
      Log.trace("===>>> TokenUtils getRoles realmAccess: " + realmAccess);
      if (realmAccess != null) {
        JsonArray _arrRoles = (JsonArray) realmAccess.get("roles");
        if (_arrRoles != null) {
          int maxRoles = _arrRoles.size();
          StringBuffer sb = new StringBuffer();
          int idx = 0;
          for (JsonValue jsonValue : _arrRoles) {
            sb.append("group=").append(new String(jsonValue.toString()).replace("\"", "").trim());
            if (++idx < (maxRoles)) {
              sb.append("&");
            }
          }
          _rolesData = sb.toString();
          Log.trace("===>>> TokenUtils getRoles user[" + principal.getName() + "] roles: " + _rolesData);
        }
      }
    }
    return _rolesData;
  }

  public static Map<String, String> getRolesAsMap(JsonWebToken principal) {
    HashMap<String,String> _rolesData = new HashMap<>();
    if (principal != null) {
      JsonObject realmAccess = (JsonObject) principal.getClaim("realm_access");
      Log.trace("===>>> TokenUtils getRolesAsMap realmAccess: " + realmAccess);
      if (realmAccess != null) {
        JsonArray _arrRoles = (JsonArray) realmAccess.get("roles");
        if (_arrRoles != null) {
          for (JsonValue jsonValue : _arrRoles) {
            String _r = new String(jsonValue.toString()).replace("\"", "").trim();
            _rolesData.put(_r, _r);
          }
          Log.trace("===>>> TokenUtils getRolesAsMap user[" + principal.getName() + "] roles: " + _rolesData.keySet());
        }
      }
    }
    return _rolesData;
  }

  public static String getRolesAsGQLQ(JsonWebToken principal) {
    StringBuffer _rolesData = new StringBuffer();
    if (principal != null) {
      JsonObject realmAccess = (JsonObject) principal.getClaim("realm_access");
      Log.trace("===>>> TokenUtils getRolesAsMap realmAccess: " + realmAccess);
      if (realmAccess != null) {
        JsonArray _arrRoles = (JsonArray) realmAccess.get("roles");
        if (_arrRoles != null) {
          int maxRoles = _arrRoles.size();
          int idx = 0;
          for (JsonValue jsonValue : _arrRoles) {
            String _r = new String(jsonValue.toString()).trim();
            _rolesData.append(_r);
            if (++idx < (maxRoles)) {
              _rolesData.append(",");
            }
          }
          Log.trace("===>>> TokenUtils getRolesAsGQLQ user[" + principal.getName() + "] roles: " + _rolesData.toString());
        }
      }
    }
    return _rolesData.toString();
  }

}
