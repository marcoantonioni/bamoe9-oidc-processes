package marco.studio.utils;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@Provider
public class MyExceptionMapper implements ExceptionMapper<ClientWebApplicationException> {

	@ConfigProperty(name = "marco.studio.log", defaultValue = "false")
	boolean _log;

	@Override
	public Response toResponse(ClientWebApplicationException t) {
		int status = t.getResponse().getStatus();
		if ( _log )
			System.out.println("===>>> MyExceptionMapper:toResponse status[" + status + "]");
		return Response.status(status).build();
	}

}
