# bamoe9-oidc-authentication-authorization

PoT for authentication and autorization of BAMOE v9 processes using Keycloak OIDC and JWT tokens

## Test commands
<pre>
curl -v -X 'GET' 'http://localhost:8080/hiring' -H 'accept: application/json' -H '_PRIVATE_SRV_ID: U0lELTAxMjM0NTY3ODktNDIwYWE3OWUtYzQ1ZC00NjZmLWI3ZjYtOGQzZTA2ZTZjYmE4'

# from external VM
curl -v -X 'GET' 'http://192.168.200.137:8080/hiring' -H 'accept: application/json' -H '_PRIVATE_SRV_ID: U0lELTAxMjM0NTY3ODktNDIwYWE3OWUtYzQ1ZC00NjZmLWI3ZjYtOGQzZTA2ZTZjYmE4'
</pre>