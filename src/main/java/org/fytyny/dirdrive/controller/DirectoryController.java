package org.fytyny.dirdrive.controller;

import org.fytyny.dirdrive.dto.DirectoryDTO;
import org.fytyny.dirdrive.dto.FileDTO;
import org.fytyny.dirdrive.dto.FileListResponseDTO;
import org.fytyny.dirdrive.model.Directory;
import org.fytyny.dirdrive.service.ApiKeyService;
import org.fytyny.dirdrive.service.DirectoryService;
import org.fytyny.dirdrive.service.ResponseService;
import org.fytyny.dirdrive.service.SessionService;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Path("/dir")
public class DirectoryController {

    @Any
    @Inject
    ResponseService responseService;

    @Any
    @Inject
    SessionService sessionService;

    @Any
    @Inject
    ApiKeyService apiKeyService;

    @Any
    @Inject
    DirectoryService directoryService;

    public static final String X_SESSION_TOKEN = "X-session=token";
    private static final String X_API_KEY = "X-api-key" ;

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDir(@HeaderParam(X_API_KEY) String apiKeyToken, @HeaderParam(X_SESSION_TOKEN) String sessionToken, DirectoryDTO directory){
        return Response.ok().build();
    }

    @GET
    @Path("/listAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDirs(@HeaderParam(X_SESSION_TOKEN) String sessionToken){
        List<DirectoryDTO> directoryDTOList = new ArrayList<DirectoryDTO>();
        return Response.ok().entity(directoryDTOList).build();
    }

    @POST
    @Path("/get")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDir(@HeaderParam(X_SESSION_TOKEN) String sessionToken, DirectoryDTO directoryDTO){
        FileListResponseDTO fileListResponseDTO = new FileListResponseDTO();
        return Response.ok().entity(fileListResponseDTO).build();
    }

    @POST
    @Path("/get/file")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getDirFile(@HeaderParam(X_SESSION_TOKEN) String sessionToken, DirectoryDTO directoryDTO, FileDTO fileDTO){
        File file = new File("bla");
        return Response.ok().entity(file)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ).build();
    }

}
