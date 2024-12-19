package marco.studio.frontend;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.quarkus.logging.Log;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import marco.studio.cache.MyCachedServiceId;
import marco.studio.restclient.MyBamoeRestClient;
import marco.studio.utils.TokenUtils;

/*
 * TBD: token propagation
 * @HeaderParam("Authorization") String authorization
 */

@Authenticated
@Path("/bamoe")
public class MyBamoeFrontend {

  @Inject
  JsonWebToken principal;

  @Inject
  MyCachedServiceId cacheIds;

  // ---------------------------------------------
  @Inject
  @RestClient
  MyBamoeRestClient myRCBamoe;

  // ---------------------------------------------
  private Uni<JsonObject> _startProcessInstance(String processName, JsonObject payload) {
    return myRCBamoe.startProcessInstance(cacheIds.generateServiceId("BAMOE"), processName, payload);
  }

  private Uni<JsonObject> _getProcessInstancesList(String processName) {
    return myRCBamoe.getProcessInstancesList(cacheIds.generateServiceId("BAMOE"), processName);
  }

  private Uni<JsonObject> _getProcessInstanceData(String processName, String processId) {
    return myRCBamoe.getProcessInstanceData(cacheIds.generateServiceId("BAMOE"), processName, processId);
  }

  private Uni<JsonObject> _getTaskList(String processName, String processId, String user, String roles) {
    return myRCBamoe.getTaskList(cacheIds.generateServiceId("BAMOE"), processName, processId, user, roles);
  }

  private Uni<JsonObject> _getTaskInstance(String processName, String processId, String taskName, String taskId, String user, String roles) {
    return myRCBamoe.getTaskInstance(cacheIds.generateServiceId("BAMOE"), processName, processId, taskName, taskId, user, roles);
  }

  private Uni<JsonObject> _setTaskInstance(String processName, String processId, String taskName, String taskId, String user, String roles, JsonObject payload) {
    return myRCBamoe.setTaskInstance(cacheIds.generateServiceId("BAMOE"), processName, processId, taskName, taskId, user, roles, payload);
  }

  private Uni<JsonObject> _claimTaskInstance(String processName, String processId, String taskName, String taskId, String user, String roles) {
    return myRCBamoe.claimTaskInstance(cacheIds.generateServiceId("BAMOE"), processName, processId, taskName, taskId, user, roles, null);
  }

  private Uni<JsonObject> _completeTaskInstance(String processName, String processId, String taskName, String taskId, String user, String roles, JsonObject payload) {
    return myRCBamoe.completeTaskInstance(cacheIds.generateServiceId("BAMOE"), processName, processId, taskName, taskId, user, roles, payload);
  }

  // ---------------------------------------------

  @POST
  @Path("process-instances/{processName}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> startProcessInstance(@PathParam(value = "processName") String processName, JsonObject payload) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend startProcessInstance, user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _startProcessInstance(processName, payload);
  }

  @GET
  @Path("process-instances/{processName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getProcessInstancesList(@PathParam(value = "processName") String processName) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend getProcessInstancesList, user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _getProcessInstancesList(processName);
  }

  @GET
  @Path("process-data/{processName}/{processId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getProcessInstanceData(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend getProcessInstanceData, user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _getProcessInstanceData(processName, processId);
  }

  @GET
  @Path("task-list/{processName}/{processId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getTaskList(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId) {
    String roles = TokenUtils.getRoles(principal);    
    Log.info("===>>> MyBamoeFrontend getTaskList, processName["+processName+"] processId["+processId+"] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _getTaskList(processName, processId, principal.getName(), roles);
  }

  @GET
  @Path("task-instance/{processName}/{processId}/{taskName}/{taskId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getTaskInstance(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId,
                                          @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId) {
    String roles = TokenUtils.getRoles(principal);    
    Log.info("===>>> MyBamoeFrontend getTaskInstance, processName["+processName+"] processId["+processId+"] taskName["+taskName+"] taskId["+taskId+"] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _getTaskInstance(processName, processId, taskName, taskId, principal.getName(), roles);
  }

  @PUT
  @Path("task-instance/{processName}/{processId}/{taskName}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> setTaskInstance(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId,
                                          @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId, JsonObject payload) {
    String roles = TokenUtils.getRoles(principal);    
    Log.info("===>>> MyBamoeFrontend setTaskInstance, processName["+processName+"] processId["+processId+"] taskName["+taskName+"] taskId["+taskId+"] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _setTaskInstance(processName, processId, taskName, taskId, principal.getName(), roles, payload);
  }

  @POST
  @Path("task-claim/{processName}/{processId}/{taskName}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> claimTaskInstance(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId,
                                          @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId) {
    String roles = TokenUtils.getRoles(principal);    
    Log.info("===>>> MyBamoeFrontend claimTaskInstance, processName["+processName+"] processId["+processId+"] taskName["+taskName+"] taskId["+taskId+"] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _claimTaskInstance(processName, processId, taskName, taskId, principal.getName(), roles);
  }

  @POST
  @Path("task-complete/{processName}/{processId}/{taskName}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> completeTaskInstance(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId,
                                          @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId, JsonObject payload) {
    String roles = TokenUtils.getRoles(principal);    
    Log.info("===>>> MyBamoeFrontend completeTaskInstance, processName["+processName+"] processId["+processId+"] taskName["+taskName+"] taskId["+taskId+"] user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _completeTaskInstance(processName, processId, taskName, taskId, principal.getName(), roles, payload);
  }


}
