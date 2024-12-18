package marco.studio.frontend;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import marco.studio.cache.MyCachedServiceId;
import marco.studio.restclient.MyBamoeRestClient;
import marco.studio.utils.TokenUtils;

@Path("/bamoe")
public class MyBamoeFrontend {

  @ConfigProperty(name = "marco.studio.restclient.mode", defaultValue = "TP")
  String tokenMode;

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

  // ---------------------------------------------

  // @Authenticated
  @POST
  @Path("process-instances/{processName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> startProcessInstance(@PathParam(value = "processName") String processName, JsonObject payload) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend startProcessInstance, user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _startProcessInstance(processName, payload);
  }

  // @Authenticated
  @GET
  @Path("process-instances/{processName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getProcessInstancesList(@PathParam(value = "processName") String processName) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend getProcessInstancesList, user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _getProcessInstancesList(processName);
  }

  // @Authenticated
  @GET
  @Path("process-data/{processName}/{processId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getProcessInstanceData(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId) {
    String roles = TokenUtils.getRoles(principal);
    Log.info("===>>> MyBamoeFrontend getProcessInstanceData, user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _getProcessInstanceData(processName, processId);
  }

  // @Authenticated
  @GET
  @Path("task-list/{processName}/{processId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Uni<JsonObject> getTaskList(@PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId) {
    String roles = TokenUtils.getRoles(principal);    
    Log.info("===>>> MyBamoeFrontend getTaskList, user[" + principal.getName() + "] with roles[" + roles + "] calling backend service...");
    return _getTaskList(processName, processId, principal.getName(), roles);
  }

}
