package com.synpulse8.pulse8.core.accesscontrolsvc.dto;

import com.authzed.api.v1.PermissionService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@Hidden
public class ReadRelationshipRequestDto extends RelationshipRequestDto {

    public PermissionService.ReadRelationshipsRequest toReadRelationshipsRequest() {
        PermissionService.Consistency consistency = PermissionService.Consistency.newBuilder()
                .setMinimizeLatency(true)
                .build();

        PermissionService.ReadRelationshipsRequest request = PermissionService.ReadRelationshipsRequest.newBuilder()
                .setConsistency(consistency)
                .setRelationshipFilter(buildRelationshipFilter())
                .build();
        return request;
    }

}
