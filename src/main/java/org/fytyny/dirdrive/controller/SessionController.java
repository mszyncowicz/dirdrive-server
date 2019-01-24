package org.fytyny.dirdrive.controller;

import org.fytyny.dirdrive.dto.ApiKeyDTO;
import org.fytyny.dirdrive.dto.GeneralResponseDTO;
import org.fytyny.dirdrive.model.ApiKey;
import org.fytyny.dirdrive.service.ApiKeyService;
import org.fytyny.dirdrive.service.ResponseService;
import org.fytyny.dirdrive.service.SessionService;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.io.File;

@Path("/session")
public class SessionController {

    @Any
    @Inject
    SessionService sessionService;

    @Any
    @Inject
    ResponseService responseService;

    @Any
    @Inject
    ApiKeyService apiKeyService;

    @Inject
    UserTransaction userTransaction;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSession(@HeaderParam(DirectoryController.X_API_KEY) String apiKey){
        ApiKey byToken = apiKeyService.getByToken(apiKey);
        if (byToken == null){
            return responseService.error(GeneralResponseDTO.authenticationFailed(),401);
        }
       ;
        return responseService.success(sessionService.createSession(byToken));
    }

}
