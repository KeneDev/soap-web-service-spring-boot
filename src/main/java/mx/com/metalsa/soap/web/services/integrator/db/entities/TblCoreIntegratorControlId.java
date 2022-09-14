package mx.com.metalsa.soap.web.services.integrator.db.entities;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class TblCoreIntegratorControlId implements Serializable {

    private String module;
    private String tableName;

}
