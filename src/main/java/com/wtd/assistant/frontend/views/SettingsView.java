package com.wtd.assistant.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DropEffect;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import com.wtd.assistant.frontend.domain.FileEntity;
import com.wtd.assistant.frontend.exception.FileSizeExceededException;

import java.io.*;

@Route(value = "Settings", layout = AssistantAppLayout.class)
public class SettingsView extends VerticalLayout {

    private Button importDataBtn;

    private Button uploadDataBtn;
    private Dialog dialog;


    //layout
    //users


    public SettingsView() {
        this.importDataBtn = new Button("Import data", VaadinIcon.UPLOAD.create());
        this.uploadDataBtn = new Button("Upload data", VaadinIcon.UPLOAD.create());
        this.dialog = new Dialog();


        add(uploadDataBtn);

        initialize();
    }

    private void initialize() {
        uploadDataBtn.addClickListener(e -> dialog.setOpened(true));


        MemoryBuffer buffer = new MemoryBuffer ();
        Upload upload = new Upload(buffer);
        dialog.add(upload);

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream fileDataStream = buffer.getInputStream();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();

            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            //fileEntity.setData(fileData);
            try {
                // Read the InputStream into a byte array
                byte[] byteBuffer = new byte[1024];
                int bytesRead;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                while ((bytesRead = fileDataStream.read(byteBuffer)) != -1) {
                    byteArrayOutputStream.write(byteBuffer, 0, bytesRead);
                }

                // Create a File and write the content to it
                //File outputFile = new File(outputFilePath);

                try (FileOutputStream fos = new FileOutputStream()) {
                    fos.write(byteArrayOutputStream.toByteArray());
                    fileEntity.setData();
                }

                System.out.println("File created at: " + outputFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });



    }
}
