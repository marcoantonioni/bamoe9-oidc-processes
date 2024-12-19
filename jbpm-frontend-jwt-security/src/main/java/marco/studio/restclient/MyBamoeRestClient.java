package marco.studio.restclient;

import java.util.Collection;
import java.util.Map;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;
import io.quarkus.oidc.token.propagation.AccessToken;
import io.smallrye.mutiny.Uni;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Encoded;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * see property: marco.studio.restclient.{*}/mp-rest/url=
 */

/**
 * List of URI for 'hiring' process /hiring /hiring/HRInterview/schema /hiring/ITInterview/schema /hiring/schema /hiring/{id} /hiring/{id}/HRInterview/{taskId} /hiring/{id}/HRInterview/{taskId}/attachments /hiring/{id}/HRInterview/{taskId}/attachments/{attachmentId} /hiring/{id}/HRInterview/{taskId}/comments /hiring/{id}/HRInterview/{taskId}/comments/{commentId} /hiring/{id}/HRInterview/{taskId}/phases/{phase} /hiring/{id}/HRInterview/{taskId}/schema /hiring/{id}/ITInterview/{taskId}
 * /hiring/{id}/ITInterview/{taskId}/attachments /hiring/{id}/ITInterview/{taskId}/attachments/{attachmentId} /hiring/{id}/ITInterview/{taskId}/comments /hiring/{id}/ITInterview/{taskId}/comments/{commentId} /hiring/{id}/ITInterview/{taskId}/phases/{phase} /hiring/{id}/ITInterview/{taskId}/schema /hiring/{id}/tasks
 */

@RegisterRestClient
@AccessToken
@RegisterProvider(MyClientRequestFilter.class)
public interface MyBamoeRestClient extends RestClientConstants {

    //----------------------------------------------- 
    // GraphQL APIs 
    //-----------------------------------------------

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("graphql") 
    Uni<JsonObject> graphql(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, Object payload);

    // -----------------------------------------------
    // Process APIs
    // -----------------------------------------------

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}")
    Uni<JsonObject> startProcessInstance(@HeaderParam(RestClientConstants._keyHeaderServiceID) String privateServiceId, @PathParam(value = "processName") String processName, JsonObject payload);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}")
    Uni<JsonObject> getProcessInstancesList(@HeaderParam(RestClientConstants._keyHeaderServiceID) String privateServiceId, @PathParam(value = "processName") String processName);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}/{processId}")
    Uni<JsonObject> getProcessInstanceData(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, @PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId);

    // -----------------------------------------------
    // Task APIs
    // -----------------------------------------------

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}/{processId}/tasks?user={userId}&{groupNames}")
    Uni<JsonObject> getTaskList(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, @PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId, @PathParam(value = "userId") String userId, @PathParam(value = "groupNames") String groupNames);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}/{processId}/{taskName}/{taskId}?user={userId}&{groupNames}")
    Uni<JsonObject> getTaskInstance(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, @PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId, @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId, @PathParam(value = "userId") String userId, @PathParam(value = "groupNames") String groupNames);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}/{processId}/{taskName}/{taskId}?user={userId}&{groupNames}")
    Uni<JsonObject> setTaskInstance(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, @PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId, @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId, @PathParam(value = "userId") String userId, @PathParam(value = "groupNames") String groupNames, Object payload);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}/{processId}/{taskName}/{taskId}?phase=claim&user={userId}&{groupNames}")
    Uni<JsonObject> claimTaskInstance(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, @PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId, @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId, @PathParam(value = "userId") String userId, @PathParam(value = "groupNames") String groupNames, JsonObject payload);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}/{processId}/{taskName}/{taskId}?phase=complete&user={userId}&{groupNames}")
    Uni<JsonObject> completeTaskInstance(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, @PathParam(value = "processName") String processName, @PathParam(value = "processId") String processId, @PathParam(value = "taskName") String taskName, @PathParam(value = "taskId") String taskId, @PathParam(value = "userId") String userId, @PathParam(value = "groupNames") String groupNames, JsonObject payload);



}
