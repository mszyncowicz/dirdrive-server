package org.fytyny.dirdrive.controller;

import org.fytyny.dirdrive.dto.DirectoryDTO;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RSApplication extends Application {
    private Set<Object> singletons = new HashSet<>();

    public RSApplication(){


        singletons.add(new SessionController());
        singletons.add(new DirectoryController());

    }

    public Set<Class<?>> getClasses()
    {

        return getRestClasses();
    }

    public Set<Class<?>> getRestClasses()
    {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(DirectoryDTO.class);
        return s;
    }


    public Set<Object> getSingletons()
    {
        return this.singletons;
    }
}
