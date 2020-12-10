package cz.janakdom.backend.service;

import cz.janakdom.backend.dao.FileDao;
import cz.janakdom.backend.model.database.Building;
import cz.janakdom.backend.model.database.FileDB;
import cz.janakdom.backend.model.database.Hydrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service(value = "fileService")
public class FileService {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private BuildingService buildingService;

    public FileDB save(MultipartFile file, Integer building_id) throws IOException, SQLException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        FileDB fileDB = new FileDB();
        fileDB.setFilename(fileName);
        fileDB.setBuilding(buildingService.findById(building_id));
        fileDB.setContent(file.getBytes());
        fileDB.setContentType(file.getContentType());

        return fileDao.save(fileDB);
    }

    public FileDB getFile(Integer id) {
        Optional<FileDB> file =  fileDao.findById(id);
        return file.orElse(null);
    }

    public List<FileDB> getAllFiles() {
        return fileDao.findAll();
    }

    public List<FileDB> getAllFilesByBuildingId(Integer building_id) {
        return fileDao.findAllByBuildingId(building_id);
    }

    public Stream<FileDB> getAllFilesByBuildingIdStream(Integer building_id) {
        return fileDao.findAllByBuildingId(building_id).stream();
    }

    public FileDB findById(Integer id){
        Optional<FileDB> fileDB = fileDao.findById(id);
        return fileDB.orElse(null);
    }

    public boolean delete(Integer id) {
        FileDB fileDB = this.findById(id);
        fileDao.delete(fileDB);
        return true;
    }

}
