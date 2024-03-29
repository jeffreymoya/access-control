package com.synpulse8.pulse8.core.accesscontrolsvc.controller;

import com.authzed.api.v1.SchemaServiceOuterClass;
import com.synpulse8.pulse8.core.accesscontrolsvc.dto.WriteSchemaRequestDto;
import com.synpulse8.pulse8.core.accesscontrolsvc.service.SchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/v1")
public class SchemaController {

    private final SchemaService schemaService;

    @Autowired
    public SchemaController(SchemaService schemaService) {
        this.schemaService = schemaService;
    }

    @GetMapping("/schema")
    public CompletableFuture<ResponseEntity<String>> readSchema() {
        SchemaServiceOuterClass.ReadSchemaRequest requestBody = SchemaServiceOuterClass.ReadSchemaRequest
                .newBuilder()
                .build();
        return schemaService.readSchema(requestBody)
                .thenApply(x -> ResponseEntity.ok(x.getSchemaText()));
    }
    @PostMapping("/schema")
    public CompletableFuture<ResponseEntity<String>> writeSchema(@RequestBody String requestBody) {
        return schemaService.writeSchema(requestBody)
                .thenApply(x -> ResponseEntity.ok(x.getWrittenAt().getToken()));
    }
}