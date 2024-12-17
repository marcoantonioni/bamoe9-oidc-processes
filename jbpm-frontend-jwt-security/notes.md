# Notes / Commands

## References
<pre>
https://quarkus.io/guides/security-openid-connect-client

</pre>

## Addons
<pre>
✬ ArtifactId                                         Extension Name
✬ quarkus-cache                                      Cache
✬ quarkus-oidc                                       OpenID Connect
✬ quarkus-rest                                       REST
✬ quarkus-rest-client-oidc-filter                    REST Client - OpenID Connect Filter
✬ quarkus-rest-client-oidc-token-propagation         REST Client - OpenID Connect Token Propagation
</pre>


<pre>
    /*
    if (MyBackendHttpSecPolicy._trace) {
      System.out.println("===>>> MyBackendHttpSecPolicy headerFound: " + headerFound);
      Iterator<Entry<String, String>> _headers = routingContext.request().headers().iterator();
      _headers.forEachRemaining(
          fieldName -> {
            if (fieldName.getKey().equals("_PRIVATE_SRV_ID")) {
              System.out.println("===>>> _header: " + fieldName.getValue());
            }
          });
    }
    */

</pre>
```
quarkus run
```

## Get access token
```
KC_PORT=44444
KC_REALM=my-realm-1
USER_NAME=requestor1
USER_PWD=requestor1
KC_TOKEN_EXPIRATION=""
KC_TOKEN_SCOPE=""
KC_FULL_TOKEN=$(curl -sk -X POST http://localhost:${KC_PORT}/realms/${KC_REALM}/protocol/openid-connect/token \
  --user my-client-bpm:my-secret-bpm -H 'content-type: application/x-www-form-urlencoded' \
  -d 'username='${USER_NAME}'&password='${USER_PWD}'&grant_type=password&scope=openid')
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then echo "Logged in"; else echo "Not logged in"; fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN=$(echo "${KC_FULL_TOKEN}" | jq '.access_token' | sed 's/"//g'); else KC_TOKEN=""; fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN_EXPIRATION=$(echo $KC_FULL_TOKEN | jq .expires_in | sed 's/"//g'); fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN_SCOPE=$(echo $KC_FULL_TOKEN | jq .scope | sed 's/"//g'); fi
echo "Token expires in: ${KC_TOKEN_EXPIRATION}"
echo "Token scopes: ${KC_TOKEN_SCOPE}"
```

## Access protected resource (any authenticated)
```
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/protected/username
```

## Access protected resource (requestors and admins)
```
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/protected/requestors

KC_PORT=44444
KC_REALM=my-realm-1
USER_NAME=requestor1
USER_PWD=requestor1
KC_TOKEN_EXPIRATION=""
KC_TOKEN_SCOPE=""
KC_FULL_TOKEN=$(curl -sk -X POST http://localhost:${KC_PORT}/realms/${KC_REALM}/protocol/openid-connect/token \
  --user my-client-bpm:my-secret-bpm -H 'content-type: application/x-www-form-urlencoded' \
  -d 'username='${USER_NAME}'&password='${USER_PWD}'&grant_type=password&scope=openid')
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then echo "Logged in"; else echo "Not logged in"; fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN=$(echo "${KC_FULL_TOKEN}" | jq '.access_token' | sed 's/"//g'); else KC_TOKEN=""; fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN_EXPIRATION=$(echo $KC_FULL_TOKEN | jq .expires_in | sed 's/"//g'); fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN_SCOPE=$(echo $KC_FULL_TOKEN | jq .scope | sed 's/"//g'); fi
echo "Token expires in: ${KC_TOKEN_EXPIRATION}"
echo "Token scopes: ${KC_TOKEN_SCOPE}"

#------------------------
# /protected is PROTECTED with custom header

# 403 Forbidden
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/protected/requestors

# 403 Forbidden
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/protected/validators

# 403 Forbidden
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/protected/username-roles

# 403 header counterfeit
curl -w '\n' -i -H "_PRIVATE_SRV_ID: xyz" -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/protected/requestors

#------------------------
# /frontend-requestors and /frontend-validators

# 200
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/frontend-requestors/requestor

# 403 if token propagation
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/frontend-validators/validator

# token absent 401 Unauthorized
curl -w '\n' -i -X GET http://localhost:8080/frontend-requestors/requestor

# token absent 401 Unauthorized
curl -w '\n' -i -X GET http://localhost:8080/frontend-validators/validator

#------------------------
# /frontend-global

# 200
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/frontend-global/requestor

# 403 if token propagation
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/frontend-global/validator

# 200 propagate roles toward backend (get from jwt claims and send as service parameters)
curl -w '\n' -i -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8080/frontend-global/username

# token absent 401 Unauthorized
curl -w '\n' -i -X GET http://localhost:8080/frontend-global/requestor

# token absent 401 Unauthorized
curl -w '\n' -i -X GET http://localhost:8080/frontend-global/validator

#------------------------------------


curl -s -H "Authorization: Bearer "${KC_TOKEN} -X 'GET' 'http://localhost:8880/bamoe/process-instances/hiring' | jq .

_PROC_ID=24519b79-f405-40cf-8698-abe4c1e61b5b
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X 'GET' 'http://localhost:8880/bamoe/process-data/hiring/'${_PROC_ID} | jq .

 
```
