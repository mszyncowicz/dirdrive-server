package org.fytyny.dirdrive.dto;

import lombok.Data;
import org.fytyny.dirdrive.model.Directory;

@Data
public class DirectoryDTO {
    private String label;
    private String path;

    public static DirectoryDTO getFrom(Directory directory){
        DirectoryDTO directoryDTO = new DirectoryDTO();
        directoryDTO.setPath(directory.getPath());
        directoryDTO.setLabel(directory.getLabel());
        return directoryDTO;
    }
}
