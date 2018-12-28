package org.fytyny.dirdrive.bean;

import org.fytyny.dirdrive.model.ApiKey;
import org.fytyny.dirdrive.service.ApiKeyService;
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

    @PostConstruct
    public void init(){
        if (!apiKeyService.existByToken(TEMP_TOKEN)){
            getTempApiKey();
        }
    }

    private ApiKey getTempApiKey(){
        ApiKey apiKey = new ApiKey();
        apiKey.setToken(TEMP_TOKEN);
        apiKey.setId(UUID.randomUUID());
        return apiKeyService.save(apiKey);
    }

}
