package com.apollo.resource.config;

import com.apollo.resource.constant.RoutingConstant;
import com.apollo.resource.handler.ResourceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ResourceRouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routeResource(final ResourceHandler resourceHandler) {
        return RouterFunctions
                .route()
                .path(RoutingConstant.RESOURCE_PATH , routeFunctionBuilder ->
                        routeFunctionBuilder.nest(RequestPredicates.accept(MediaType.APPLICATION_JSON) ,
                                builder -> builder
                                        .GET(RoutingConstant.RESOURCE_ID_PATH , resourceHandler::getResourceById)
                                        .POST(resourceHandler::createResource)
                                        .PUT(RoutingConstant.RESOURCE_OWNER_ID , resourceHandler::updateResource)
                                        .PATCH(RoutingConstant.SHARE_FLAG_PATH , resourceHandler::shareResource)
                                        .DELETE(RoutingConstant.RESOURCE_OWNER_ID , resourceHandler::deleteResource)))
                .build();
    }

}
