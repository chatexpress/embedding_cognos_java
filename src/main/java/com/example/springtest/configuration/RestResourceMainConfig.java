package com.example.springtest.configuration;

import com.example.springtest.flows.HelloWebRestService;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class RestResourceMainConfig extends ResourceConfig {
    public RestResourceMainConfig() {
        System.out.println("Registering Rest Configuration...");

        register(HelloWebRestService.class);
        register(CORSResponseFilter.class);
    }
}
