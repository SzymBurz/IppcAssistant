package com.wtd.assistant.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.wtd.assistant.frontend.dao.FileDao;
import com.wtd.assistant.frontend.domain.FileEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

@Route(value = "Settings", layout = AssistantAppLayout.class)
public class SettingsView extends VerticalLayout {

    private Button uploadDataBtn;
    private Dialog dialog;

    @Autowired
    FileDao fileDao;

    //layout
    //users


    public SettingsView() {
        this.uploadDataBtn = new Button("Upload data", VaadinIcon.UPLOAD.create());
        this.dialog = new Dialog();


        add(uploadDataBtn);

        initialize();
    }

    private void initialize() {
        MemoryBuffer buffer = new MemoryBuffer ();
        Upload upload = new Upload(buffer);
        dialog.add(upload);

        uploadDataBtn.addClickListener(e -> dialog.setOpened(true));




        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            //fileEntity.setData(fileData);
            try(InputStream fileDataStream = buffer.getInputStream()
            ) {
                byte[] byteBuffer = new byte[1024];
                int bytesRead;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                while ((bytesRead = fileDataStream.read(byteBuffer)) != -1) {
                    byteArrayOutputStream.write(byteBuffer, 0, bytesRead);
                }

                fileEntity.setData(byteArrayOutputStream.toByteArray());
                fileDao.save(fileEntity);

                dialog.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

}
