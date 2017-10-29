package jdev.server.services;

import jdev.dto.PointDTO;
import jdev.dto.repo.PointsDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RoutesService {

    private PointsDbRepository pointsDbRepository;

    public RoutesService(@Autowired PointsDbRepository pointsDbRepository){
       this.pointsDbRepository = pointsDbRepository;
    }

    public ArrayList<PointDTO> getScopeByAutoId(String autoId, int scope ) {
        ArrayList<PointDTO> all; ArrayList retList;
        if ("".equals(autoId) || autoId == null) all = (ArrayList<PointDTO>) pointsDbRepository.findAll();
        else all = (ArrayList<PointDTO>) pointsDbRepository.findAllByAutoIdOrderByTimeStampDesc(autoId);

        if (all.size() > 2) {
            all.sort((o1, o2) -> Long.compare(o2.getTimeStamp() - o1.getTimeStamp(), 0L));
        }

        if (all.size() >= 0) {
            if (scope == 0 || all.size() < scope) {
                retList = all;
                // У нас уже всё есть :)
            } else {
                retList = new ArrayList( all.subList(0, scope));
            }
        } else {
            retList = null;
        }
            return retList;
    }

    public PointDTO getPoint(long id){
        return pointsDbRepository.findOne(id);
    }

    public void delete(long id){
        PointDTO point = pointsDbRepository.findOne(id);
        pointsDbRepository.delete(point);
    }

    public boolean exist(PointDTO point){
       boolean result;
        return (pointsDbRepository
                .findFirstByAutoIdAndTimeStamp(point.getAutoId(),
                        point.getTimeStamp()) == null) ? false : true;
    }

    public PointDTO save( PointDTO point){
        return pointsDbRepository.save(point);
    }

}
