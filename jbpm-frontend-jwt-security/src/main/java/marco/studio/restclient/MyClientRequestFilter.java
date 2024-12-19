package marco.studio.restclient;

import java.net.URI;
import java.net.URISyntaxException;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestContext;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestFilter;

public class MyClientRequestFilter implements ResteasyReactiveClientRequestFilter   {

  @Override
  public void filter(ResteasyReactiveClientRequestContext requestContext) {
    String pathInfo = requestContext.getUri().toString();
    URI myUri = null;
    try {
        myUri = new URI(pathInfo.replace("%3F", "?"));
    } catch (URISyntaxException e) {
        throw new RuntimeException(e);
    }
    requestContext.setUri(myUri);
  }
  
}
