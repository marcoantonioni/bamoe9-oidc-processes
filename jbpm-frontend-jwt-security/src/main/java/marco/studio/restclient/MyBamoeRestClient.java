package marco.studio.restclient;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import io.quarkus.oidc.token.propagation.AccessToken;
import io.smallrye.mutiny.Uni;
import jakarta.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * vedi marco.studio.restclient.{*}/mp-rest/url=
 */

/*
/hiring
/hiring/HRInterview/schema
/hiring/ITInterview/schema
/hiring/schema
/hiring/{id}
/hiring/{id}/HRInterview/{taskId}
/hiring/{id}/HRInterview/{taskId}/attachments
/hiring/{id}/HRInterview/{taskId}/attachments/{attachmentId}
/hiring/{id}/HRInterview/{taskId}/comments
/hiring/{id}/HRInterview/{taskId}/comments/{commentId}
/hiring/{id}/HRInterview/{taskId}/phases/{phase}
/hiring/{id}/HRInterview/{taskId}/schema
/hiring/{id}/ITInterview/{taskId}
/hiring/{id}/ITInterview/{taskId}/attachments
/hiring/{id}/ITInterview/{taskId}/attachments/{attachmentId}
/hiring/{id}/ITInterview/{taskId}/comments
/hiring/{id}/ITInterview/{taskId}/comments/{commentId}
/hiring/{id}/ITInterview/{taskId}/phases/{phase}
/hiring/{id}/ITInterview/{taskId}/schema
/hiring/{id}/tasks
*/

@RegisterRestClient //(baseUri = "/aaa")
@AccessToken
//@Path("/")
public interface MyBamoeRestClient extends RestClientConstants {
/*
    //-----------------------------------------------
    // GraphQL
    // Methods: POST

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("graphql")
    Uni<Object> graphql(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, 
                                        Object payload);
 */
    //-----------------------------------------------
    // Process

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}")
    Uni<JsonObject> getProcessInstancesList(@HeaderParam(RestClientConstants._keyHeaderServiceID) String privateServiceId, 
                                                @PathParam(value = "processName") String processName);
                                            
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{processName}/{processId}")
    Uni<JsonObject> getProcessInstanceData(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, 
                                        String processName, String processId);

/*
    //-----------------------------------------------
    // Task

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tasks/{processName}/{processId}/tasks?user={userId}&group={groupNames}")
    Uni<List<Object>> getTaskList(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, 
                                        String processName, String processId, 
                                        String userId, Collection<String> groupNames);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tasks/{processName}/{processId}/{taskName}/{taskId}")
    Uni<Object> getTaskInstanceData(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, 
                                        String processName, String processId, 
                                        String taskName, String taskId);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tasks/{processName}/{processId}/{taskName}/{taskId}?phase=claim&user={userId}&group={groupNames}")
    Uni<Object> claimTask(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, 
                                        String processName, String processId, 
                                        String taskName, String taskId,
                                        Object payload);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tasks/{processName}/{processId}/{taskName}/{taskId}?phase=complete&user={userId}&group={groupNames}")
    Uni<Object> completeTask(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, 
                                        String processName, String processId, 
                                        String taskName, String taskId,
                                        Object payload);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tasks/{processName}/{processId}/{taskName}/{taskId}")
    Uni<Object> setTaskInstanceData(@HeaderParam("_PRIVATE_SRV_ID") String privateServiceId, 
                                        String processName, String processId, 
                                        String taskName, String taskId,
                                        Object payload);
 */


}
