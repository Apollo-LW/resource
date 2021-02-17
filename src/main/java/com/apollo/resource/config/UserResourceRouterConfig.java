package com.apollo.resource.config;

import com.apollo.resource.constant.RoutingConstant;
import com.apollo.resource.handler.UserResourceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserResourceRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routeUserResource(final UserResourceHandler resourceHandler) {
        return RouterFunctions
                .route()
                .path(RoutingConstant.RESOURCE_USER_PATH , routeFunctionBuilder ->
                        routeFunctionBuilder.nest(RequestPredicates.accept(MediaType.APPLICATION_JSON) ,
                                builder -> builder.GET(RoutingConstant.USER_ID_PATH , resourceHandler::getUserResources)))
                .build();
    }

}
