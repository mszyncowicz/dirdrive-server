package org.fytyny.dirdrive.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Data
@Entity
public class Directory {

    @Id
    UUID id;

    String path;

    String label;

}
