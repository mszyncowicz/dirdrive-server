package org.fytyny.dirdrive.repository;

import org.fytyny.dirdrive.model.ApiKey;
import org.fytyny.dirdrive.model.Session;

import java.util.LinkedList;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ApiKeyRepositoryIT {

    @Rule
    public SessionFactoryRule sessionFactoryRule = new SessionFactoryRule();
    ApiKeyRepositoryImpl apiKeyRepository;

    @Before
    public void init(){
        apiKeyRepository = new ApiKeyRepositoryImpl();
        sessionFactoryRule.injectManager(apiKeyRepository);
    }

    @Test
    public void saveTest(){
        ApiKey apiKey = generateRandomApiKey();
        apiKeyRepository.save(apiKey);
        Assert.assertEquals(apiKey,apiKeyRepository.getById(apiKey.getId()));
    }

    ApiKey generateRandomApiKey(){
        ApiKey apiKey = new ApiKey();
        apiKey.setId(UUID.randomUUID());
        apiKey.setToken(Session.generateRandom(10));
        apiKey.setDirectoryList(new LinkedList<>());
        return apiKey;
    }
}
