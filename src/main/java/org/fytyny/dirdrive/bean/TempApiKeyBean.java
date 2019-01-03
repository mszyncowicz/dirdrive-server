package org.fytyny.dirdrive.bean;

import org.fytyny.dirdrive.model.ApiKey;
import org.fytyny.dirdrive.service.ApiKeyService;
import org.slf4j.Logger;

import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Singleton
@Startup
public class TempApiKeyBean {

    private static final String TEMP_TOKEN = "superapikey";

    @Any
    @Inject
    ApiKeyService apiKeyService;

    @Inject
    Logger logger;

    @PostConstruct
    public void init(){
        if (!apiKeyService.existByToken(TEMP_TOKEN)){
            getTempApiKey();
            logger.info("Key created");
        } else {
            logger.info("Key exists");
        }
    }

    private ApiKey getTempApiKey(){
        ApiKey apiKey = new ApiKey();
        apiKey.setToken(TEMP_TOKEN);
        apiKey.setId(UUID.randomUUID());
        return apiKeyService.save(apiKey);
    }

}
