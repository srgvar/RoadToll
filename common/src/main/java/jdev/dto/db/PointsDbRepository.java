package jdev.dto.db;

//import org.springframework.data.jpa.repository.JpaRepository;
import jdev.dto.PointDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.JpaParameters;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
//@org.springframework.transaction.annotation.Transactional
public interface PointsDbRepository extends CrudRepository<PointDTO, Long> {

}
