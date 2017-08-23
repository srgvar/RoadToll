package jdev.server.controllers;

/*
 * Created by srgva on 02.08.2017.
 */
import jdev.dto.PointDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.FileWriter;


@RestController
class TrackersController {
    private static final Logger log = LoggerFactory.getLogger(TrackersController.class);

    @Value("${fileToSave}")
    private String fileToSave;

    @RequestMapping(value = "/tracker", method = RequestMethod.POST,
         produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> getPoint(@RequestBody PointDTO point) {
        File storeFile;
        try{ //Проверка наличия пути/файла для вывода
            File file = new File(".");
            String fullPathToFileSave = file.getCanonicalPath() + fileToSave;
            storeFile = new File(fullPathToFileSave);
            if (!storeFile.exists()) { // Если нет файла для сохранения
                String storePath = storeFile.getParent();
                File storeDir = new File(storePath); //Путь к файлу
                storeDir.mkdirs(); //создать путь
            }
        }catch(Exception e){
            log.error(" failure get: " + e.getMessage());
            e.printStackTrace();
            /* Ошибка - для передачи треккеру */
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // Пишем данные в файл
        try(FileWriter fw = new FileWriter(storeFile.getAbsoluteFile(), true)) {
            fw.write(point.toJson()+"\n");
            fw.flush();
            log.info(" success get and save: " + point.toString()); // пишем в лог
            /* Успешно - для передачи треккеру */
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(" failure get: " + e.getMessage());
            e.printStackTrace();
            /* Ошибка - для передачи треккеру */
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

