package mx.com.metalsa.soap.web.services.integrator.db.repository;

import mx.com.metalsa.soap.web.services.integrator.db.entities.TblControl;
import mx.com.metalsa.soap.web.services.integrator.db.entities.TblControlId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TblControlRepository extends CrudRepository<TblControl, TblControlId> {

    public Optional<TblControl> findOneByTableName(String tableName);
}