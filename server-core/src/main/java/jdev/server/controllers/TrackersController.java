package jdev.server.controllers;

/**
 * Created by srgva on 02.08.2017.
 */
import jdev.dto.PointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileWriter;


@RestController
public class TrackersController {
    private static final Logger log = LoggerFactory.getLogger(TrackersController.class);
    ResponseEntity<String> ret;

    @Value("${fileToSave}")
    String fileToSave;
    File storeFile;

    @RequestMapping(value = "/tracker", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getPoint(@RequestBody PointDTO point) {

        try{ //Проверка наличия пути/файла для вывода
            File file = new File(".");
            String fullPathToFileSave = file.getCanonicalPath().toString() + fileToSave;
            storeFile = new File(fullPathToFileSave);
            if (!storeFile.exists()) { // Если нет файла для сохранения
                String storePath = storeFile.getParent();
                File storeDir = new File(storePath); //Путь к файлу
                storeDir.mkdirs(); //создать путь
            }
        }catch(Exception e){
            log.error(" failure get: " + e.getMessage());
            e.printStackTrace();
            /** Ошибка - для передачи треккеру */
            ret = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Пишем данные в файл
        try(FileWriter fw = new FileWriter(storeFile.getAbsoluteFile(), true)) {
            fw.write(point.toJson()+"\n");
            fw.flush();
            log.info(" success get and save: " + point.toString()); // пишем в лог
            /** Успешно - для передачи треккеру */
            ret = new ResponseEntity<String>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(" failure get: " + e.getMessage());
            e.printStackTrace();
            /** Ошибка - для передачи треккеру */
            ret = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }

}

