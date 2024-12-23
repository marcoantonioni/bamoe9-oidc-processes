# Notes / Commands


Run backend in a dedicate shell
```
cd ./jbpm-compact-architecture-security
quarkus dev
```

Run frontend in a dedicate shell
```
cd ./jbpm-frontend-jwt-security
quarkus dev
```

## Get access token

For HR role users: alice, mary

For IT role users: john, marco

```
#---------------------------------
# token for HR user

USER_NAME=alice
USER_PWD=alice

KC_PORT=44444
KC_REALM=my-realm-1
KC_CLIENT_USER=my-client-bpm
KC_CLIENT_SECRET=my-secret-bpm
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

# start process instance
INSTANCE_RESULT=$(curl -s -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN} \
  -X POST http://localhost:8880/bamoe/process-instances/${_PROCESS_NAME} \
    -d '{"candidateData": { "name": "Jon", "lastName": "Snow", "email": "jon@snow.org", "experience": 5, "skills": ["Java", "Kogito", "Fencing"]}}' )
    
echo ${INSTANCE_RESULT} | jq .
_PROC_ID=$(echo ${INSTANCE_RESULT} | jq .id | sed 's/"//g')
echo "new instance id: "${_PROC_ID}

# get process instances
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/process-instances/${_PROCESS_NAME} | jq .

# get a single process instance
_PROC_ID=8d21b9b1-9287-47e4-82a1-432f202daa53
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/process-data/${_PROCESS_NAME}/${_PROC_ID} | jq .

# get task list for a process instance
_PROC_ID=8d21b9b1-9287-47e4-82a1-432f202daa53
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/task-list/${_PROCESS_NAME}/${_PROC_ID} | jq .

# get task data
TASK_NAME=HRInterview
TASK_ID=5a90dff2-1125-40e9-b3df-ed64ecb4d4fd
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/task-instance/${_PROCESS_NAME}/${_PROC_ID}/${TASK_NAME}/${TASK_ID} | jq .

# claim task
curl -s -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN} -X POST http://localhost:8880/bamoe/task-claim/${_PROCESS_NAME}/${_PROC_ID}/${TASK_NAME}/${TASK_ID} | jq .


# update task
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

USER_NAME=john
USER_PWD=john

KC_PORT=44444
KC_REALM=my-realm-1
KC_CLIENT_USER=my-client-bpm
KC_CLIENT_SECRET=my-secret-bpm
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

# get task list for a process instance
curl -s -H "Authorization: Bearer "${KC_TOKEN} -X GET http://localhost:8880/bamoe/task-list/${_PROCESS_NAME}/${_PROC_ID} | jq .

TASK_NAME=ITInterview
TASK_ID=636b75d5-01ab-4f8e-8a99-8603e6511110

# complete task
curl -s -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN} -X POST http://localhost:8880/bamoe/task-complete/${_PROCESS_NAME}/${_PROC_ID}/${TASK_NAME}/${TASK_ID} \
-d '{
  "approve": true
}' | jq .

#---------------------------------------------
# graphql

# list all active process instances
curl -v -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN}   -X POST http://localhost:8880/bamoe/list-all-processes -d '{}' | jq .

# list all active tasks instances for user roles
curl -v -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer "${KC_TOKEN}   -X POST http://localhost:8880/bamoe/list-all-tasks -d '{}' | jq .


```
