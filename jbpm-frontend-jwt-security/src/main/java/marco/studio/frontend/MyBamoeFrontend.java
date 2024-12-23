package marco.studio.frontend;

import java.io.StringReader;
import java.util.Map;
import java.util.StringTokenizer;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.common.NotImplementedYet;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import marco.studio.cache.MyCachedServiceId;
import marco.studio.restclient.MyBamoeRestClient;
import marco.studio.utils.TokenUtils;

@Authenticated
@Path("/bamoe")
public class MyBamoeFrontend {

  static String _starterRolesPrefix = "marco.bamoe.process-starter-roles.";

  @Inject
  JsonWebToken principal;

  @Inject
  MyCachedServiceId cacheIds;

  @Inject
  @RestClient
  MyBamoeRestClient myRCBamoe;

  private boolean _roleEnabled(String processName, Map<String, String> _roles) {
    boolean _enabled = true;
    String _configuredRoles = ConfigProvider.getConfig().getValue(_starterRolesPrefix + processName, String.class);
    if (_configuredRoles != null && _configuredRoles.length() > 0) {
      _enabled = false;
      StringTokenizer st = new StringTokenizer(_configuredRoles, ",");
      boolean roleFound = false;
      while (st.hasMoreElements()) {
        String _token = st.nextToken().trim();
        roleFound = _roles.get(_token) != null;
        if (roleFound) {
          _enabled = true;
          break;
        }
      }

    }
    return _enabled;
  }

  @POST
  @Path("process-instances/{processName}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> startProcessInstance(@PathParam(value = "processName") String processName, JsonObject payload) {
    Map<String, String> _roles = TokenUtils.getRolesAsMap(principal);
    if (_roleEnabled(processName, _roles)) {
      Log.info("===>>> MyBamoeFrontend startProcessInstance, user[" + principal.getName() + "] with roles[" + _roles.keySet() + "] calling backend service...");
      return myRCBamoe.startProcessInstance(cacheIds.generateServiceId("BAMOE"), processName, payload);
    } else {      
      throw new NotAllowedException(Response.status(405).build());
    }
  }

  @GET
  @Path("process-instances/{processName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getProcessInstancesList(@PathParam(value = "processName") String processName) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend getProcessInstancesList, user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return myRCBamoe.getProcessInstancesList(cacheIds.generateServiceId("BAMOE"), processName);
  }

  @GET
  @Path("process-data/{processName}/{processId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getProcessInstanceData(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend getProcessInstanceData, user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return myRCBamoe.getProcessInstanceData(cacheIds.generateServiceId("BAMOE"), processName, processId);
  }

  @GET
  @Path("task-list/{processName}/{processId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getTaskList(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend getTaskList, processName[" + processName + "] processId[" + processId + "] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return myRCBamoe.getTaskList(cacheIds.generateServiceId("BAMOE"), processName, processId, principal.getName(), roles);
  }

  @GET
  @Path("task-instance/{processName}/{processId}/{taskName}/{taskId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getTaskInstance(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId, @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend getTaskInstance, processName[" + processName + "] processId[" + processId + "] taskName[" + taskName + "] taskId[" + taskId + "] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return myRCBamoe.getTaskInstance(cacheIds.generateServiceId("BAMOE"), processName, processId, taskName, taskId, principal.getName(), roles);
  }

  @PUT
  @Path("task-instance/{processName}/{processId}/{taskName}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> setTaskInstance(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId, @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId, JsonObject payload) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend setTaskInstance, processName[" + processName + "] processId[" + processId + "] taskName[" + taskName + "] taskId[" + taskId + "] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return myRCBamoe.setTaskInstance(cacheIds.generateServiceId("BAMOE"), processName, processId, taskName, taskId, principal.getName(), roles, payload);
  }

  @POST
  @Path("task-claim/{processName}/{processId}/{taskName}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> claimTaskInstance(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId, @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend claimTaskInstance, processName[" + processName + "] processId[" + processId + "] taskName[" + taskName + "] taskId[" + taskId + "] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return myRCBamoe.claimTaskInstance(cacheIds.generateServiceId("BAMOE"), processName, processId, taskName, taskId, principal.getName(), roles, null);
  }

  @POST
  @Path("task-complete/{processName}/{processId}/{taskName}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> completeTaskInstance(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId, @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId, JsonObject payload) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend completeTaskInstance, processName[" + processName + "] processId[" + processId + "] taskName[" + taskName + "] taskId[" + taskId + "] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return myRCBamoe.completeTaskInstance(cacheIds.generateServiceId("BAMOE"), processName, processId, taskName, taskId, principal.getName(), roles, payload);
  }

  @POST
  @Path("list-all-processes")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> graphqlProcesses(JsonObject payload) {
    // this graphql query is in a "horrible" format :) but it's just for demonstration
    String query = "{\"operationName\":\"getProcessInstances\",\"variables\":{\"where\":{\"parentProcessInstanceId\":{\"isNull\":true},\"state\":{\"in\":[\"ACTIVE\"]}},\"offset\":0,\"limit\":10,\"orderBy\":{\"lastUpdate\":\"DESC\"} },\"query\":\"query getProcessInstances($where: ProcessInstanceArgument, $offset: Int, $limit: Int, $orderBy: ProcessInstanceOrderBy) { ProcessInstances(where: $where, pagination: {offset: $offset, limit: $limit}, orderBy: $orderBy) { id processId processName parentProcessInstanceId rootProcessInstanceId roles state start lastUpdate addons businessKey serviceUrl error { nodeDefinitionId message __typename } __typename }}\" } }";
    JsonReader reader = Json.createReader(new StringReader(query));
    JsonObject queryPayload = reader.readObject();

    Log.info("===>>> MyBamoeFrontend graphql list-all-processes, calling backend service...");
    return myRCBamoe.graphql(cacheIds.generateServiceId("BAMOE"), queryPayload);
  }

  @POST
  @Path("list-all-tasks")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> graphqlTasks(JsonObject payload) {
    String roles = TokenUtils.getRolesAsGQLQ(principal);
    // this graphql query is in a "horrible" format :) but it's just for demonstration
    String query = "{ \"operationName\":\"getTasksForUser\", \"variables\":{ \"whereArgument\":{ \"and\":[ {\"or\": [ {\"actualOwner\":{\"equal\":\""+principal.getName()+"\"}}, {\"and\": [ {\"actualOwner\":{\"isNull\":true}}, {\"not\":{\"excludedUsers\":{\"contains\":\""+principal.getName()+"\"}}}, {\"or\":[ {\"potentialUsers\":{\"contains\":\""+principal.getName()+"\"}}, {\"potentialGroups\":{\"containsAny\":["+roles+"]}} ] } ] } ] }, { \"and\":[{\"state\":{\"in\":[\"Ready\",\"Reserved\"]}}] } ] }, \"offset\":0,\"limit\":10,\"orderBy\":{\"lastUpdate\":\"DESC\"} }, \"query\":\"query getTasksForUser($whereArgument: UserTaskInstanceArgument, $offset: Int, $limit: Int, $orderBy: UserTaskInstanceOrderBy) { UserTaskInstances(where: $whereArgument, pagination: {offset: $offset, limit: $limit}, orderBy: $orderBy) { id name referenceName description priority processInstanceId processId rootProcessInstanceId rootProcessId state actualOwner adminGroups adminUsers completed started excludedUsers potentialGroups potentialUsers inputs outputs lastUpdate endpoint __typename }}\"}";
    JsonReader reader = Json.createReader(new StringReader(query));
    JsonObject queryPayload = reader.readObject();

    Log.info("===>>> MyBamoeFrontend graphql list-all-tasks, calling backend service...");
    return myRCBamoe.graphql(cacheIds.generateServiceId("BAMOE"), queryPayload);
  }

  @ServerExceptionMapper(NotAllowedException.class)
  public Response notAuthorized() {
    return Response.status(403).build();
  }

  @ServerExceptionMapper(NotImplementedYet.class)
  public Response notImplemented() {
    return Response.status(501).build();
  }
}
