package org.fytyny.dirdrive.service;

import lombok.Setter;
import org.fytyny.dirdrive.model.ApiKey;
import org.fytyny.dirdrive.model.Session;
import org.fytyny.dirdrive.repository.ApiKeyRepository;
import org.fytyny.dirdrive.repository.SessionRepository;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Stateless
public class ApiKeyServiceImpl implements  ApiKeyService{

    @Any
    @Inject
    SessionRepository sessionRepository;

    @Any
    @Inject
    ApiKeyRepository apiKeyRepository;

    @Override
    public ApiKey getApiKeyBySession(String sessionToken) {
        Session byToken = sessionRepository.getByToken(sessionToken);
        if (byToken != null){
            return byToken.getApiKey();
        }
        return null;
    }

    @Override
    public ApiKey save(ApiKey apiKey) {
        return apiKeyRepository.save(apiKey);
    }
}
