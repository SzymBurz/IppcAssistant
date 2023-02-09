package com.wtd.assistant.frontend;

import com.wtd.assistant.frontend.data.InputFileDownloader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class InputFileDownloaderTestSuite {

    InputFileDownloader inputFileDownloader = new InputFileDownloader();

    @Test
    void fileDownloadingTest() {

        inputFileDownloader.downloadFile();

        Path path = FileSystems.getDefault().getPath("./src/main/resources/ippcpdf.pdf");

        boolean deleted = false;

        try {
            deleted = Files.deleteIfExists(path);
        } catch (NoSuchFileException e) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException e) {
            System.err.println(e);
        }

        Assertions.assertTrue(deleted);
    }


}
