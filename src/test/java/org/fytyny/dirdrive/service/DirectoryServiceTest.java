package org.fytyny.dirdrive.service;

import org.fytyny.dirdrive.model.ApiKey;
import org.fytyny.dirdrive.model.Directory;
import org.fytyny.dirdrive.model.Session;
import org.fytyny.dirdrive.repository.DirectoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DirectoryServiceTest {

    @Mock
    ApiKeyService apiKeyService;

    @Mock
    DirectoryRepository directoryRepository;

    DirectoryServiceImpl directoryService;

    @Before
    public void init(){
        directoryService = new DirectoryServiceImpl();
        directoryService.apiKeyService = apiKeyService;
        directoryService.directoryRepository = directoryRepository;

        ApiKey apiKey = new ApiKey();
        apiKey.setDirectoryList(randomDirs());

        ApiKey secondApiKey = new ApiKey();
        secondApiKey.setDirectoryList(randomDirs());

        when(apiKeyService.getApiKeyBySession("session1")).thenReturn(apiKey);

        when(apiKeyService.getApiKeyBySession("session2")).thenReturn(secondApiKey);


    }

    @Test
    public void cantAddTwoSameLabelsForOneApiKey(){
        Directory dir = apiKeyService.getApiKeyBySession("session1").getDirectoryList().get(0);
        Session session = new Session();
        session.setApiKey(apiKeyService.getApiKeyBySession("session1"));
        session.setToken("session1");
        Session session2 = new Session();
        ApiKey apiKey2 = apiKeyService.getApiKeyBySession("session2");
        session2.setApiKey(apiKey2);
        session2.setToken("session2");
        int size = apiKey2.getDirectoryList().size();
        Assert.assertFalse(directoryService.addDirectoryToApiKey(dir,session));
        Assert.assertTrue(directoryService.addDirectoryToApiKey(dir,session2));
        Assert.assertTrue(apiKey2.getDirectoryList().size() > size);

        verify(apiKeyService).save(apiKey2);
    }

    List<Directory> randomDirs(){
       Random random = new Random();
       int num = random.nextInt(10)+5;
       List<Directory> directoryList = new LinkedList<>();
       for (int i = 0; i< num; i++){
           Directory directory = new Directory();
           directory.setPath(Session.generateRandom(10));
           directory.setLabel(Session.generateRandom(10));
           directoryList.add(directory);
       }
       return directoryList;
    }
}
