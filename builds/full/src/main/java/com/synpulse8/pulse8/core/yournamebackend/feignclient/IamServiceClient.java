package com.synpulse8.pulse8.core.yournamebackend.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="iamServiceClient", url="${iam.service.baseUrl}")
public interface IamServiceClient {

    @PostMapping(value = "${iam.service.endpoints.createTenant}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void createTenant(@PathVariable String correlationId);

    @PostMapping(value = "${iam.service.endpoints.createRestAdminClientForTenant}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void createRestAdminClientForTenant(@PathVariable String correlationId);

    @PostMapping(value = "${iam.service.endpoints.createTenantRole}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void createTenantRole(@PathVariable String correlationId);

    @PostMapping(value = "${iam.service.endpoints.assignCompositeRole}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void assignCompositeRole(@PathVariable String correlationId);

    @PostMapping(value = "${iam.service.endpoints.assignTenantRole}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void assignTenantRole(@PathVariable String correlationId);
}
