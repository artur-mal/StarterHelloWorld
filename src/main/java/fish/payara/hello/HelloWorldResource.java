package fish.payara.hello;

import fish.payara.security.annotations.OpenIdAuthenticationDefinition;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Arrays;

@Path("/api")


public class HelloWorldResource {


        @GET
        @Path("/config")
        @Produces(MediaType.TEXT_PLAIN)
        public String getConfig() {
            OpenIdAuthenticationDefinition openIdAuthDef = OpenidConfigBeanEL.class.getAnnotation(OpenIdAuthenticationDefinition.class);

            if (openIdAuthDef != null) {
                return "Provider URI: " + openIdAuthDef.providerURI() + "\n" +
                        "Client ID: " + openIdAuthDef.clientId() + "\n" +
                        "Client Secret: " + openIdAuthDef.clientSecret() + "\n" +
                        "Redirect URI: " + openIdAuthDef.redirectURI() + "\n" +
                        "Extra Parameters: " + Arrays.toString(openIdAuthDef.extraParameters());
            } else {
                return "OpenIdAuthenticationDefinition annotation not found.";
            }
        }


    @GET
    @Path("/hello")
    public Response hello(@QueryParam("name") String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "world";
        }
        return Response
                .ok(name)
                .build();
    }
        
}