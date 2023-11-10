package com.synpulse8.pulse8.core.yournamebackend.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/")
public class DefaultController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String index() {
        return "Pulse8 YourName Backend";
    }

}
