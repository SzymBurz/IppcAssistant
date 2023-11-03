package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.FileDao;
import com.wtd.assistant.frontend.domain.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
    private final FileDao fileDao;

    @Autowired
    public FileService(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public void saveFile(FileEntity fileEntity) {
        fileDao.saveFile(fileEntity);
    }

    public FileEntity getFileById(int id) {
        return fileDao.getFileById(id);
    }
}