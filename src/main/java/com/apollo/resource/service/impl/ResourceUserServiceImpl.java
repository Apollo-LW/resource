package com.apollo.resource.service.impl;

import com.apollo.resource.model.Resource;
import com.apollo.resource.model.ResourceUser;
import com.apollo.resource.service.ResourceUserService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResourceUserServiceImpl implements ResourceUserService {

    @Value("${user.kafka.store}")
    private String resourceUserStateStoreName;
    private final InteractiveQueryService interactiveQueryService;
    private ReadOnlyKeyValueStore<String, ResourceUser> resourceUserStateStore;

    private ReadOnlyKeyValueStore<String, ResourceUser> getResourceUserStateStore() {
        if (this.resourceUserStateStore == null)
            this.resourceUserStateStore = this.interactiveQueryService.getQueryableStore(this.resourceUserStateStoreName , QueryableStoreTypes.keyValueStore());
        return this.resourceUserStateStore;
    }

    @Override
    public Flux<Resource> getUserResources(final String userId) {
        Optional<ResourceUser> resourceUserOptional = Optional.ofNullable(this.getResourceUserStateStore().get(userId));
        if (resourceUserOptional.isEmpty()) return Flux.empty();
        return Flux.fromIterable(resourceUserOptional.get().getUserResources());
    }

}
