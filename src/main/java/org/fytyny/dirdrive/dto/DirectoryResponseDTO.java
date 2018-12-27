package org.fytyny.dirdrive.dto;


import lombok.Data;

import java.util.List;

@Data
public class DirectoryResponseDTO {
    List<FileDTO> fileDTOList;
}
