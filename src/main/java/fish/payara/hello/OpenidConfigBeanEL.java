package fish.payara.hello;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.Config;

import javax.servlet.http.HttpServletRequest;

@Named
public class OpenidConfigBeanEL {

    @Inject
    HttpServletRequest request;

    @Inject
   Config config;

    private static final String BASE_OPENID_KEY = "payara.security.openid";

    public String getTokenEndpointURL() {
        String tenant = getTenant(request);  // a custom method to decide which tenant to use
        return config
                .getOptionalValue(BASE_OPENID_KEY + "." + tenant + ".providerURI", String.class)
                // e.g. payara.security.openid.tenant1.providerURI for "tenant1" tenant
                .orElseGet(() -> {
                    // read config for the "tenant1" tenant by default
                    return config.getValue(BASE_OPENID_KEY + ".tenant1.providerURI", String.class);
                });
    }

    private String getTenant(HttpServletRequest request) {
        return request.getParameter("tenant"); // resolves the tenant name from a query parameter
    }

}