package com.apollo.resource.handler;

import com.apollo.resource.constant.RoutingConstant;
import com.apollo.resource.model.Resource;
import com.apollo.resource.model.SharableResource;
import com.apollo.resource.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ResourceHandler {

    private final ResourceService resourceService;

    public @NotNull Mono<ServerResponse> getResourceById(final ServerRequest request) {
        final String resourceId = request.pathVariable(RoutingConstant.RESOURCE_ID);
        final Mono<Resource> resourceMono = this.resourceService.getResourceById(resourceId).flatMap(Mono::justOrEmpty);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resourceMono , Resource.class);
    }

    public @NotNull Mono<ServerResponse> createResource(final ServerRequest request) {
        final Mono<Resource> resourceMono = request.bodyToMono(Resource.class);
        final Mono<Resource> createdResourceMono = this.resourceService.saveResource(resourceMono).flatMap(Mono::justOrEmpty);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdResourceMono , Resource.class);
    }

    public @NotNull Mono<ServerResponse> updateResource(final ServerRequest request) {
        final Mono<Resource> resourceMono = request.bodyToMono(Resource.class);
        final String ownerId = request.pathVariable(RoutingConstant.OWNER_ID);
        final Mono<Boolean> isUpdated = this.resourceService.updateResource(resourceMono , ownerId);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(isUpdated , Boolean.class);
    }

    public @NotNull Mono<ServerResponse> shareResource(final ServerRequest request) {
        final Mono<SharableResource> sharableResourceMono = request.bodyToMono(SharableResource.class);
        final Boolean isAdd = Boolean.valueOf(request.pathVariable(RoutingConstant.FLAG));
        final Mono<Boolean> isShared = this.resourceService.shareResource(sharableResourceMono , isAdd);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(isShared , Boolean.class);
    }

    public @NotNull Mono<ServerResponse> deleteResource(final ServerRequest request) {
        final String resourceId = request.pathVariable(RoutingConstant.RESOURCE_ID);
        final String ownerId = request.pathVariable(RoutingConstant.OWNER_ID);
        final Mono<Boolean> isDeleted = this.resourceService.deleteResource(resourceId , ownerId);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(isDeleted , Boolean.class);
    }

}
