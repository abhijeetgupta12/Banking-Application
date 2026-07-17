package com.banking.gateway.config;

import java.util.function.Function;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {
	
	private Function<ServerRequest, ServerRequest> addAuthUserHeader() {
        return request -> {
            Object user = request.servletRequest().getAttribute("authenticatedUser");
            if (user != null) {
                return ServerRequest.from(request)
                        .header("X-Auth-User", user.toString())
                        .build();
            }
            return request;
        };
    }

    @Bean
    public RouterFunction<ServerResponse> customerServiceRoute() {
        return GatewayRouterFunctions.route("customer-service")
                .route(RequestPredicates.path("/customer/**"),
                        HandlerFunctions.http())
                .before(addAuthUserHeader())
                .before(BeforeFilterFunctions.uri("http://localhost:9092"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> accountServiceRoute() {
        return GatewayRouterFunctions.route("account-service")
                .route(RequestPredicates.path("/accounts/**"),
                        HandlerFunctions.http())
                .before(addAuthUserHeader())
                .before(BeforeFilterFunctions.uri("http://localhost:9091"))
                .build();
    }
}
