package jdev.dto.repo;

//import org.springframework.data.jpa.repository.JpaRepository;
import javafx.collections.transformation.SortedList;
import jdev.dto.PointDTO;
import org.h2.index.PageBtreeLeaf;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Repository
//@org.springframework.transaction.annotation.Transactional
public interface PointsDbRepository extends CrudRepository  <PointDTO, Long> {

    PointDTO findFirstById(Long id);

    List<PointDTO> findAllByAutoIdOrderByTimeStampDesc(String autoId);

    PointDTO findFirstByAutoIdAndTimeStamp(String autoId, Long timeStamp);
}
