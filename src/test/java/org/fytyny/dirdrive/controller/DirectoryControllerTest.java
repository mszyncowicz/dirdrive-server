package org.fytyny.dirdrive.controller;

import org.apache.commons.lang.RandomStringUtils;
import org.fytyny.dirdrive.dto.DirectoryDTO;
import org.fytyny.dirdrive.dto.DirectoryListDTO;
import org.fytyny.dirdrive.dto.GeneralResponseDTO;
import org.fytyny.dirdrive.model.ApiKey;
import org.fytyny.dirdrive.model.Directory;
import org.fytyny.dirdrive.model.Session;
import org.fytyny.dirdrive.service.ApiKeyService;
import org.fytyny.dirdrive.service.DirectoryService;
import org.fytyny.dirdrive.service.ResponseService;
import org.fytyny.dirdrive.service.SessionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.ws.rs.core.Response;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DirectoryControllerTest {

    private final static String SESSION_TOKEN = "Working token";
    private final static String API_TOKEN = "Working api token";

    ResponseService responseService;

    @Mock
    SessionService sessionService;

    @Mock
    ApiKeyService apiKeyService;

    @Mock
    DirectoryService directoryService;

    @Spy
    DirectoryController directoryController;

    @Before
    public void before(){
        directoryController.sessionService =sessionService;
        directoryController.apiKeyService =apiKeyService;
        directoryController.directoryService = directoryService;
        ApiKey apiKey = new ApiKey();
        apiKey.setToken(API_TOKEN);
        when(apiKeyService.getApiKeyBySession(SESSION_TOKEN)).thenReturn(apiKey);

        Session session = getSession(SESSION_TOKEN, apiKeyService.getApiKeyBySession(SESSION_TOKEN));

        when(sessionService.getSessionByToken(SESSION_TOKEN)).thenReturn(session);
        directoryController.responseService = mock(ResponseService.class, (Answer)  a ->{
            throw new IllegalStateException();
        });

    }

    @Test
    public void shouldNotAddDirectoryWhenWrongSession(){
        doAnswer(a ->{
            Assert.assertTrue(a.getArguments()[0] instanceof GeneralResponseDTO);
            return null;
        }).when(responseService).error(any(),401);

        responseService.error(null);
        DirectoryDTO directoryDTO = new DirectoryDTO();
        directoryDTO.setPath("c:/windows");

        Response wrong = directoryController.addDir(API_TOKEN, "wrong", directoryDTO);
        Assert.assertNull(wrong);

    }

    @Test
    public void shouldNotAddDirectoryWhenNullSession(){
        doAnswer(a ->{
            Assert.assertTrue(a.getArguments()[0] instanceof GeneralResponseDTO);
            return null;
        }).when(responseService).error(any(),401);

        responseService.error(null);
        DirectoryDTO directoryDTO = new DirectoryDTO();
        directoryDTO.setPath("c:/windows");

        Response wrong = directoryController.addDir(API_TOKEN, null, directoryDTO);
        Assert.assertNull(wrong);
    }

    @Test
    public void shouldNotAddDirectoryWhenWrongApiKey(){
        doAnswer(a ->{
            Assert.assertTrue(a.getArguments()[0] instanceof GeneralResponseDTO);
            return null;
        }).when(responseService).error(any(),401);

        responseService.error(null);
        DirectoryDTO directoryDTO = new DirectoryDTO();
        directoryDTO.setPath("c:/windows");

        Response wrong = directoryController.addDir("wrong", SESSION_TOKEN, directoryDTO);
        Assert.assertNull(wrong);
    }
    @Test
    public void shouldNotAddDirectoryWhenNullApiKey(){
        doAnswer(a ->{
            Assert.assertTrue(a.getArguments()[0] instanceof GeneralResponseDTO);
            return null;
        }).when(responseService).error(any(),401);

        responseService.error(null);
        DirectoryDTO directoryDTO = new DirectoryDTO();
        directoryDTO.setPath("c:/windows");

        Response wrong = directoryController.addDir(null, SESSION_TOKEN, directoryDTO);
        Assert.assertNull(wrong);
    }

    @Test
    public void shouldAddDitectoryWhenSessionAndKeyOk(){
        doAnswer(a ->{
            Assert.assertTrue(a.getArguments()[0] instanceof GeneralResponseDTO);
            return null;
        }).when(responseService).success(any());

        responseService.error(null);
        DirectoryDTO directoryDTO = new DirectoryDTO();
        directoryDTO.setPath("c:/windows");

        Response right = directoryController.addDir(API_TOKEN, SESSION_TOKEN, directoryDTO);
        Assert.assertNull(right);

        verify(directoryService).addDirectoryToApiKey(argThat(new ArgumentMatcher<Directory>() {
            @Override
            public boolean matches(Object o){
                if (!(o instanceof Directory)) return false;
                return ((Directory) o).getPath().equals(directoryDTO.getPath());
            }
        }),any());
    }

    @Test
    public void shouldReturnDirectoryList(){
        ApiKey apiKeyBySession = apiKeyService.getApiKeyBySession(SESSION_TOKEN);
        List<Directory> directoryList = generateRandomDirList();
        Assert.assertFalse(directoryList.isEmpty());
        apiKeyBySession.setDirectoryList(directoryList);

        doAnswer(a ->{
            Assert.assertTrue(a.getArguments()[0] instanceof DirectoryListDTO);
            DirectoryListDTO directoryListDTO = (DirectoryListDTO) a.getArguments()[0];
            List<DirectoryDTO> collect = directoryList.stream().map(d -> {
                DirectoryDTO directoryDTO = new DirectoryDTO();
                directoryDTO.setPath(d.getPath());
                directoryDTO.setLabel(d.getLabel());
                return directoryDTO;
            }).collect(Collectors.toList());
            Assert.assertTrue(directoryListDTO.getDirectoryList().containsAll(collect));
            return null;
        }).when(responseService).success(any());

        Assert.assertNull(directoryController.getAllDirs(SESSION_TOKEN));
    }

    @Test
    public void shouldNotReturnDirectoryList(){
        doAnswer(a ->{
            Assert.assertTrue(a.getArguments()[0] instanceof GeneralResponseDTO);
            return null;
        }).when(responseService).error(any(),401);

        Assert.assertNull(directoryController.getAllDirs(API_TOKEN));
    }

    @Test
    public void shoulrdReturnReturnFileListResponse(){

    }

    @Test
    public void shoulrdNotReturnReturnFileListWhenDirectoryIsNotInApiKey(){

    }

    @Test
    public void shouldNotReturnFileListSessionWrong(){

    }

    @Test
    public void shouldGetSingleFile(){

    }

    @Test
    public void shouldNotGetSingleFileWhenDirNotPartOfApiKey(){

    }

    @Test
    void shouldNotGetSingleFileSessionWrong(){

    }

    private List<Directory> generateRandomDirList(){
        List<Directory> directoryList = new LinkedList<>();
        Random random = new Random();
        int count = random.nextInt(5);
        count += 10;
        IntStream.range(0,count).peek( p ->{
            Directory directory = new Directory();
            directory.setPath(RandomStringUtils.randomAlphanumeric(random.nextInt(5) + 10));
            directory.setLabel(RandomStringUtils.randomAlphabetic(random.nextInt(5) + 10));
            directoryList.add(directory);
        }).count();
        return directoryList;
    }
    private Session getSession(String token, ApiKey apiKey){
        Session session = new Session();
        session.setToken(token);
        session.setApiKey(apiKey);
        return session;
    }

}
