package com.wtd.assistant.frontend.controller;

import com.wtd.assistant.frontend.domain.FileEntity;
import com.wtd.assistant.frontend.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

//@RestController
//@RequestMapping("/files")
@Controller
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //@PostMapping("/upload")
    //public String uploadFile(@RequestParam("file") MultipartFile file) {
    /*
    public String uploadFile(MultipartFile file) {
        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setContentType(file.getContentType());
            fileEntity.setData(file.getBytes());

            fileService.saveFile(fileEntity);

            return "File uploaded successfully!";
        } catch (Exception e) {
            return "Error uploading file: " + e.getMessage();
        }
    }
     */
}
