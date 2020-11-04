package org.acme.filters;

import io.vertx.core.http.HttpServerRequest;
import org.acme.ApplicationConstants;
import org.jboss.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Priority(100)
@Provider
public class TokenValidationFilter implements ContainerRequestFilter {
    private static final Logger LOG = Logger.getLogger(TokenValidationFilter.class);

    @Context
    HttpServerRequest request;

    @Override
    public void filter(ContainerRequestContext context) {
        if (!isValid(request)) {
            context.abortWith(Response
                    .status(Response.Status.FORBIDDEN)
                    .entity("No access")
                    .build());
            LOG.warnf("Bad token in request from %s.",
                    AddressResolver.resolve(request));
        }
    }

    private boolean isValid(HttpServerRequest request) {
        final String token = request.getHeader(ApplicationConstants.TOKEN_KEY);
        return token != null
                && token.equals(System.getenv(ApplicationConstants.TOKEN_KEY));
    }
}
