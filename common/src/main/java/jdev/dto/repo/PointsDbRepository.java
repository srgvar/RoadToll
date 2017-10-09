package jdev.dto.repo;

//import org.springframework.data.jpa.repository.JpaRepository;
import jdev.dto.PointDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@org.springframework.transaction.annotation.Transactional
public interface PointsDbRepository extends CrudRepository<PointDTO, Long> {


    List<PointDTO> findAllByAutoIdOrderByTimeDesc(String autoId);

}
