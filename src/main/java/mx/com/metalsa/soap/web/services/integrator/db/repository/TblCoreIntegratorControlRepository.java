package mx.com.metalsa.soap.web.services.integrator.db.repository;

import mx.com.metalsa.soap.web.services.integrator.db.entities.TblCoreIntegratorControl;
import mx.com.metalsa.soap.web.services.integrator.db.entities.TblCoreIntegratorControlId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Map;

@Repository
public interface TblCoreIntegratorControlRepository extends JpaRepository<TblCoreIntegratorControl, TblCoreIntegratorControlId> {

    @Transactional
    @Procedure(name = "mx.com.metalsa.jpa.repository.TblCoreIntegratorControlRepository.genericIntegrator")
    Map<String, Object> genericIntegrator(@Param("I_MODULE") String module,
                                          @Param("I_TABLE_NAME") String tableName,
                                          @Param("I_DATA") String data);
}
