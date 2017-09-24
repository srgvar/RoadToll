package jdev.dto.db;

//import org.springframework.data.jpa.repository.JpaRepository;
import jdev.dto.PointDTO;
import org.springframework.data.repository.CrudRepository;

public interface PointsDbRepository extends CrudRepository<PointDTO, Long> {

}
