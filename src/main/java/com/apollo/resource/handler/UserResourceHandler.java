package com.apollo.resource.handler;

import com.apollo.resource.constant.RoutingConstant;
import com.apollo.resource.model.Resource;
import com.apollo.resource.service.ResourceUserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserResourceHandler {

    private final ResourceUserService resourceUserService;

    public @NotNull Mono<ServerResponse> getUserResources(ServerRequest request) {
        final String userId = request.pathVariable(RoutingConstant.USER_ID);
        final Flux<Resource> userResourcesFlux = this.resourceUserService.getUserResources(userId);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userResourcesFlux , Resource.class);
    }
}
