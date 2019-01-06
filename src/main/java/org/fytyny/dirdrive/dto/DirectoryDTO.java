package org.fytyny.dirdrive.dto;

import lombok.Data;
import org.fytyny.dirdrive.model.Directory;

@Data
public class DirectoryDTO {
    private String label;
    private String path;

    public static DirectoryDTO getFrom(Directory directory){
        DirectoryDTO directoryDTO = new DirectoryDTO();
        directory.setPath(directory.getPath());
        directory.setLabel(directory.getLabel());
        return directoryDTO;
    }
}
