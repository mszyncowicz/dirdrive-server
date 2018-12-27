package org.fytyny.dirdrive.controller;

import org.fytyny.dirdrive.dto.DirectoryDTO;
import org.fytyny.dirdrive.dto.DirectoryResponseDTO;
import org.fytyny.dirdrive.dto.FileDTO;
import org.fytyny.dirdrive.model.Directory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Path("/dir")
public class DirectoryController {

    public static final String X_SESSION_TOKEN = "X-session=token";

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDir(@HeaderParam(X_SESSION_TOKEN) String sessionToken, DirectoryDTO directory){
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
        DirectoryResponseDTO directoryResponseDTO = new DirectoryResponseDTO();
        return Response.ok().entity(directoryDTO).build();
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
