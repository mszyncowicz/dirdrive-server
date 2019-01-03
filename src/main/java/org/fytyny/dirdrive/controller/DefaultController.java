package org.fytyny.dirdrive.controller;


import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/test")
public class DefaultController {

    @GET
    @Path("/say")
    public String sayHello(){
        return "Hello Wordl!";
    }


}
