package mx.com.metalsa.soap.web.services.integrator.db.entities;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TblControlId implements Serializable {

    private String module;
    private String tableName;
}
