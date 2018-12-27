package org.fytyny.dirdrive.service;

import org.fytyny.dirdrive.model.ApiKey;
import org.fytyny.dirdrive.model.Directory;
import org.fytyny.dirdrive.model.Session;

import java.util.List;
import java.util.Optional;

public interface DirectoryService {

    List<String> getAllAvaiblePaths(Session session);

    Optional<Directory> getDirByLabel(String label, Session session);

    boolean addDirectoryToApiKey(Directory directory, Session session);
}
