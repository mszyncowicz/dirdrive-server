package org.fytyny.dirdrive.controller;

import org.fytyny.dirdrive.dto.ApiKeyDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.File;

@Path("/session")
public class SessionController {


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSession(@HeaderParam(DirectoryController.X_SESSION_TOKEN) String sessionToken, ApiKeyDTO apiKey){
        return Response.ok().build();
    }

}
