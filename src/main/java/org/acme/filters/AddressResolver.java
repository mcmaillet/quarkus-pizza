package org.acme.filters;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.net.SocketAddress;

class AddressResolver {
    static String resolve(HttpServerRequest request) {
        final SocketAddress socketAddress = request.remoteAddress();
        if (socketAddress != null) {
            return socketAddress.toString();
        }
        return request.getHeader("x-forwarded-for");
    }
}
