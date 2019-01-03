package org.fytyny.dirdrive.dto;

import lombok.Data;

@Data
public class FileDTO {

    String name;

    String modifyDate;

    public FileDTO(String name, String modifyDate){
     this.name= name;
     this.modifyDate = modifyDate;
    }
}
