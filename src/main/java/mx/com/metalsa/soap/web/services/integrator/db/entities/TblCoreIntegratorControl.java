package mx.com.metalsa.soap.web.services.integrator.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
//@Table(name = "TBL_CORE_INTEGRATOR_CONTROL")
@IdClass(TblCoreIntegratorControlId.class)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@NamedStoredProcedureQuery(
        name = "mx.com.metalsa.jpa.repository.TblCoreIntegratorControlRepository.genericIntegrator",
        procedureName = "INTEGRATION_CORE_PKG.GENERIC_INTEGRATOR",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "I_MODULE", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "I_TABLE_NAME", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "I_DATA", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "O_RESPONSE_CODE", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "O_RESPONSE_MESSAGE", type = String.class)
        }
)
public class TblCoreIntegratorControl implements Serializable {

    @Id
    private String module;
    @Id
    private String tableName;
    @Column
    private String tableOwner;
    @Column
    private String mergeKeyColumns;
    @Column
    private String xmlDataPath;
    @Column
    private Integer deleteRequired;
    @Column
    private Integer active;

}
