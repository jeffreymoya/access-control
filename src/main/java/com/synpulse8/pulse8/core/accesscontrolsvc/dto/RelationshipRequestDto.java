package com.synpulse8.pulse8.core.accesscontrolsvc.dto;

import com.authzed.api.v1.PermissionService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.synpulse8.pulse8.core.accesscontrolsvc.exception.P8CException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.stream.Stream;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "DTO for representing a relationship request", subTypes = {
        WriteRelationshipRequestDto.class,
        ReadRelationshipRequestDto.class,
        DeleteRelationshipRequestDto.class
})
public class RelationshipRequestDto {

    @NotBlank(message = "Object type cannot be null")
    @Schema(description = "The type of resource that is requested", example = "policy")
    protected String objectType;

    @Schema(description = "The ID of the resource that is requested", example = "doc001")
    protected String objectId;

    @Schema(description = "The relation of the subject to the resource", example = "doc001")
    protected String relation;

    @Schema(description = "Type of the subject reference", example ="user")
    protected String subjRefObjType;

    @Schema(description = "ID of the subject reference", example = "john01")
    protected String subjRefObjId;

    @Schema(description = "Subject relation", example = "employee")
    protected String subjRelation;

    @Schema(description = "Caveat name", example = "has_valid_ip")
    protected String caveatName;

    @Schema(description = "Context for caveat", example = "{}")
    protected Map<String, Object> context;

    protected PermissionService.RelationshipFilter buildRelationshipFilter() {
        PermissionService.RelationshipFilter.Builder relationshipFilterBuilder = PermissionService.RelationshipFilter.newBuilder()
                .setResourceType(objectType);

        if (StringUtils.isNotEmpty(objectId)) relationshipFilterBuilder.setOptionalResourceId(objectId);

        if (StringUtils.isNotEmpty(relation)) relationshipFilterBuilder.setOptionalRelation(relation);

        if (Stream.of(subjRefObjType, subjRefObjId, subjRelation).anyMatch(StringUtils::isNotEmpty)) {
            if (StringUtils.isEmpty(subjRefObjType)) {
                throw new P8CException("Subject reference object type cannot be null if either subject reference object id or subject relation is present");
            }
            PermissionService.SubjectFilter.Builder subjectBuilder = PermissionService.SubjectFilter.newBuilder()
                    .setSubjectType(subjRefObjType);

            if (StringUtils.isNotEmpty(subjRefObjId)) subjectBuilder.setOptionalSubjectId(subjRefObjId);

            if (StringUtils.isNotEmpty(subjRelation)) {
                subjectBuilder.setOptionalRelation(
                        PermissionService.SubjectFilter.RelationFilter.newBuilder()
                                .setRelation(subjRelation)
                                .build()
                );
            }

            relationshipFilterBuilder.setOptionalSubjectFilter(subjectBuilder.build());
        }

        return relationshipFilterBuilder.build();
    }
}
