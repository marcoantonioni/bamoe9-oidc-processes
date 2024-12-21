# bamoe9-oidc-authentication-authorization

PoT for client authentication and autorization for BAMOE v9 processes/tasks using Keycloak OIDC and JWT tokens

## PoT goals

The goal of this repository is to present a possible functional and architectural blueprint for the definition of a security layer dedicated to applications based on Kogito BPM.

In this scenario we will use the IBM BAMOE v9.1.1 distribution with the technology preview of BPMN processes.


## Security scenario

Kogito BPM and its APIs for interactions with human processes and tasks adopt an extremely permissive model, that is, they are based on the concept of self-declaration of the requester.

In technical terms, the APIs for interaction with human tasks accept a pair of parameters with which the requester declares the user-id and the list of roles to which the user is associated.

The architectural choice of the process and task management framework to externalize the security issue is not questioned and can be justified by the fact that in a cloud-native world, the implementations of security systems are multiple and based on various technologies that are not always interoperable; this means that the security layer for authentication and authorization is delegated externally and consequently each application solution will have to implement its own strategies and implementations.

## Architectural blueprint

In an architectural scenario based on micro-services concepts, the choice made for the security blueprint is to be as less invasive as possible with respect to the BPMN application that is to be created.

To keep the BPMN application as free as possible from external dependencies (other Quarkus extensions and any prerequisites of runtime versions) it was decided to externalize the standard REST transport protection layer on another container, making the most of the security potential applicable to the API and integration with external OIDC systems; in this example a Keycloak server will be used with a realm customized specifically for this scenario.

The blueprint defines the presence of two containers defined for the same pod. The communication between the two containers is based on the ip address 127.0.0.1. The 'backend' container that will contain the BPMN application will not have http ports exposed to the Service but can only be reached by internal calls or by the 'frontend' container via ip 127.0.0.1. The 'frontend' container exposes custom and protected REST APIs that allow requests to be mediated towards the 'backend' container.

![Architectural overview](./docs/BAMOE9-SecurityBlueprint.png "Architectural overview")


### BPMN application, 'backend' container (jbpm-compact-architecture-security)

<i>
NOTE:
This application is a copy of the 'jbpm-compact-architecture-example' example present in the 'bamoe-9.1.1-examples.zip' archive of the IBM BAMOE v9 distribution.
The 'HRInterview' and 'ITInterview' tasks have been adapted with the configuration of the user roles authorized to interact with these human-tasks.
</i>

![Hiring process](./docs/BAMOE9-HiringProcess.png "Hiring process")


Taking into account that the executable Java application code is automatically generated during the build phase starting from the BPMN model and that this must not be manually modified in any way, both to avoid blocking the CI/CD pipelines and to avoid losing the changes at each build, we chose to adopt a 'protection' approach of the BPMN application core.

A Java class '<b>MyBackendHttpSecPolicy</b>' that implements the interface is added to the BPMN application in a separate package

<b><code>io.quarkus.vertx.http.runtime.security.HttpSecurityPolicy</code></b>

This Java class will need to implement the method

<b>
<code>
@Override
public Uni<CheckResult> checkPermission(RoutingContext routingContext, Uni<SecurityIdentity> identity, AuthorizationRequestContext requestContext)
</code>
</b>

This method will perform a series of custom checks to authorize every single request that will be received for one or more BPMN processes; in this example for the process exposed on REST transport with the URI <b>"/hiring"</b>.

The file '<b>application.properties</b>' will contain a set of properties related to the http policy named '<b>backend</b>'

<b><code>quarkus.http.auth.permission.backend.{*}'</code></b>

These properties define the behavior of the custom policy.

In this example the policy performs (based on configuration):

1. checking the IP of origin of the request
2. checking the presence and verification of an http header that identifies a service-key
3. checking the presence of the http header 'Authorization' with JWT Bearer token on which both the client-id with which the OIDC server created the token in question and the scope associated with the client-id are necessarily verified.

These three checks are a simple example of how to create a security enforcement for request authorization.

### BPMN security enforcement, 'frontend' container (jbpm-frontend-jwt-security)

This application defines a set of REST APIs protected with JWT tokens.

The example scenario does not implement an exhaustive set of process and task management APIs, it is limited to a fully functional subset that allows you to start processes and interact with human-tasks.

These APIs simplify the integration towards the 'backend' layer regarding the authentication and authorization theme both technologically and applicatively.

These APIs are associated with a custom http authorization policy '<b>MyFrontendHttpSecPolicy</b>' implementing the interface

<b><code>io.quarkus.vertx.http.runtime.security.HttpSecurityPolicy</code></b>

and a custom JWT issuer validator '<b>MyIssuerValidator</b>'  implementing the interface

<b><code>org.jose4j.jwt.consumer.Validator</code></b>

The Java code in the '<b>frontend</b>' application is process-agnostic and can be reused for any BPMN process. All key elements are identified by rest-query parameters.

The main purpose of this layer, in addition to implementing OIDC and JWT token-based security, is to extract from the JWT token the user-name and the roles associated with it in the realm on which the user was authenticated and for which the token was issued.

With these automatic user and role mapping features, a serious and solid security layer is created that identifies the user and his roles in a guaranteed way.

### Customized Keycloak realm

In the application 'jbpm-frontend-jwt-security' there is a json archive 'my-realm-1-realm.json' for a custom realm named <b>'my-realm-1'</b>.

In this realm the following were defined:

1. A client dedicated to the BPM scenario
2. Two HR and IT roles for human-tasks
3. Two user groups HRGroup and ITGroup to register the users, each group is associated with the relative role

#### Client Configuration

Client name: <b>my-client-bpm</b>

<img src="./docs/Keycloak-Client.png" width="50%" height="50%">

Client scope: <b>my-bpm-scope</b>

<img src="./docs/Keycloak-Client-Scope.png" width="50%" height="50%">


#### Realm Roles Configuration

Realm Roles: <b>HR</b>, <b>IT</b>

<img src="./docs/Keycloak-Roles.png" width="50%" height="50%">


#### Group Configuration

Groups: <b>HRGroup</b>, <b>ITGroup</b>

<img src="./docs/Keycloak-Groups.png" width="50%" height="50%">

Groups to Roles mapping: <b>HRGroup</b>, <b>ITGroup</b>

<img src="./docs/Keycloak-Groups-RoleMapping.png" width="70%" height="70%">

Users in group HRGroup (role HR): <b>alice</b>, <b>mary</b>

Users in group ITGroup (role IT): <b>john</b>, <b>marco</b>


## Blueprint solution known limits

This repository should be considered an example from which to start to create proprietary solutions that will implement all the security policies that are required in your environments.

No performance analysis has been performed.

No scalability analysis of the solution has been performed.

<b><i>
The application code present in this repository is purely for example purposes and SHOULD NOT be used in production environments.

Any use in whole or in part is at your own risk.
</i></b>

## Run commands
<pre>
</pre>