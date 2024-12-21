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


```
quarkus dev
```

## Get access token

For HR role users: alice, mary

For IT role users: john, marco

```
#---------------------------------
# token for HR user

KC_PORT=44444
KC_REALM=my-realm-1
KC_CLIENT_USER=my-client-bpm
KC_CLIENT_SECRET=my-secret-bpm
USER_NAME=alice
USER_PWD=alice
KC_TOKEN_EXPIRATION=""
KC_TOKEN_SCOPE=""
KC_FULL_TOKEN=$(curl -sk -X POST http://localhost:${KC_PORT}/realms/${KC_REALM}/protocol/openid-connect/token \
  --user ${KC_CLIENT_USER}:${KC_CLIENT_SECRET} -H 'content-type: application/x-www-form-urlencoded' \
  -d 'username='${USER_NAME}'&password='${USER_PWD}'&grant_type=password&scope=openid')
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then echo "Logged in"; else echo "Not logged in"; fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN=$(echo "${KC_FULL_TOKEN}" | jq '.access_token' | sed 's/"//g'); else KC_TOKEN=""; fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN_EXPIRATION=$(echo $KC_FULL_TOKEN | jq .expires_in | sed 's/"//g'); fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN_SCOPE=$(echo $KC_FULL_TOKEN | jq .scope | sed 's/"//g'); fi
echo "Token expires in: ${KC_TOKEN_EXPIRATION}"
echo "Token scopes: ${KC_TOKEN_SCOPE}"

_PROCESS_NAME=hiring

# avvia istanza
INSTANCE_RESULT=$(curl -s -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN} \
  -X POST http://localhost:8880/bamoe/process-instances/${_PROCESS_NAME} \
    -d '{"candidateData": { "name": "Jon", "lastName": "Snow", "email": "jon@snow.org", "experience": 5, "skills": ["Java", "Kogito", "Fencing"]}}' )
    
echo ${INSTANCE_RESULT} | jq .
_PROC_ID=$(echo ${INSTANCE_RESULT} | jq .id | sed 's/"//g')
echo "new instance id: "${_PROC_ID}

# legge istanze processi
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/process-instances/${_PROCESS_NAME} | jq .

# dettaglio istanza processo
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/process-data/${_PROCESS_NAME}/${_PROC_ID} | jq .

# lista task della istanza
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/task-list/${_PROCESS_NAME}/${_PROC_ID} | jq .

# legge dettaglio istanza task
TASK_NAME=HRInterview
TASK_ID=748ec1f8-3d72-410a-a54b-309d0a5704cd

curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/task-instance/${_PROCESS_NAME}/${_PROC_ID}/${TASK_NAME}/${TASK_ID} | jq .

# claim task
curl -s -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN} -X POST http://localhost:8880/bamoe/task-claim/${_PROCESS_NAME}/${_PROC_ID}/${TASK_NAME}/${TASK_ID} | jq .


# aggiorna dettaglio istanza task
curl -s -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN} -X PUT http://localhost:8880/bamoe/task-instance/${_PROCESS_NAME}/${_PROC_ID}/${TASK_NAME}/${TASK_ID} \
-d '{
  "offer": {
    "category": "Very Old Senior Software Engineer",
    "salary": 99999
  },
  "candidate": {
    "name": "Jon",
    "lastName": "Snow",
    "email": "jon@snow.org",
    "experience": 5,
    "skills": [
      "Java",
      "Kogito",
      "Fencing"
    ]
  },
  "approve": false
}' | jq .

# complete task
curl -s -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN} -X POST http://localhost:8880/bamoe/task-complete/${_PROCESS_NAME}/${_PROC_ID}/${TASK_NAME}/${TASK_ID} \
-d '{
  "offer": {
    "category": "Very Old Senior Software Engineer",
    "salary": 99999
  },
  "candidate": {
    "name": "Jon",
    "lastName": "Snow",
    "email": "jon@snow.org",
    "experience": 5,
    "skills": [
      "Java",
      "Kogito",
      "Fencing"
    ]
  },
  "approve": true
}' | jq .


#---------------------------------
# token for IT user

KC_PORT=44444
KC_REALM=my-realm-1
KC_CLIENT_USER=my-client-bpm
KC_CLIENT_SECRET=my-secret-bpm
USER_NAME=john
USER_PWD=john
KC_TOKEN_EXPIRATION=""
KC_TOKEN_SCOPE=""
KC_FULL_TOKEN=$(curl -sk -X POST http://localhost:${KC_PORT}/realms/${KC_REALM}/protocol/openid-connect/token \
  --user ${KC_CLIENT_USER}:${KC_CLIENT_SECRET} -H 'content-type: application/x-www-form-urlencoded' \
  -d 'username='${USER_NAME}'&password='${USER_PWD}'&grant_type=password&scope=openid')
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then echo "Logged in"; else echo "Not logged in"; fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN=$(echo "${KC_FULL_TOKEN}" | jq '.access_token' | sed 's/"//g'); else KC_TOKEN=""; fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN_EXPIRATION=$(echo $KC_FULL_TOKEN | jq .expires_in | sed 's/"//g'); fi
if ([[ ! -z "${KC_FULL_TOKEN}" ]] && [[ "${KC_FULL_TOKEN}" != "null" ]]) then KC_TOKEN_SCOPE=$(echo $KC_FULL_TOKEN | jq .scope | sed 's/"//g'); fi
echo "Token expires in: ${KC_TOKEN_EXPIRATION}"
echo "Token scopes: ${KC_TOKEN_SCOPE}"

# lista task della istanza
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/task-list/${_PROCESS_NAME}/${_PROC_ID} | jq .

TASK_NAME=ITInterview
TASK_ID=0dbf03de-7a77-4916-843c-0560d0988abb

curl -s -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN} -X POST http://localhost:8880/bamoe/task-complete/${_PROCESS_NAME}/${_PROC_ID}/${TASK_NAME}/${TASK_ID} \
-d '{
  "approve": true
}' | jq .

#---------------------------------------------
# graphql
# NOT YET IMPLEMENTED

curl -v -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN}   -X POST http://localhost:8880/bamoe/graphql -d '{}' | jq .


```
