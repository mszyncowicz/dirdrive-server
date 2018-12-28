package org.fytyny.dirdrive.service;

import org.fytyny.dirdrive.model.ApiKey;

public interface ApiKeyService {

    ApiKey getApiKeyBySession(String sessionToken);

    ApiKey save(ApiKey apiKey);

    boolean existByToken(String token);
}
