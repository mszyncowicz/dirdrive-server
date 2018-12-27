package org.fytyny.dirdrive.service;

import org.fytyny.dirdrive.model.ApiKey;
import org.fytyny.dirdrive.model.Directory;
import org.fytyny.dirdrive.model.Session;
import org.fytyny.dirdrive.repository.DirectoryRepository;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class DirectoryServiceImpl implements DirectoryService {

    @Any
    @Inject
    ApiKeyService apiKeyService;

    @Any
    @Inject
    DirectoryRepository directoryRepository;

    @Override
    public List<String> getAllAvaiblePaths(Session session) {
        ApiKey apiKeyBySession = getBySessionToken(session.getToken());
        return apiKeyBySession.getDirectoryList().stream()
                .map(d -> d.getPath()).collect(Collectors.toList());
    }

    @Override
    public Optional<Directory> getDirByLabel(String label, Session session) {
        ApiKey bySessionToken = getBySessionToken(session.getToken());
        return bySessionToken.getDirectoryList().stream()
            .filter(d -> d.getLabel().equals(label)).findAny();
    }

    ApiKey getBySessionToken(String token){
        return apiKeyService.getApiKeyBySession(token);
    }

    @Override
    public boolean addDirectoryToApiKey(Directory directory, Session session) {
        ApiKey bySessionToken = getBySessionToken(session.getToken());
        boolean result = !bySessionToken.getDirectoryList().stream().map(d -> d.getLabel()).collect(Collectors.toList()).contains(directory.getLabel());
        if (result){
            directoryRepository.save(directory);
            bySessionToken.getDirectoryList().add(directory);
            apiKeyService.save(bySessionToken);
        }
        return result;
    }
}
