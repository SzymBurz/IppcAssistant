package com.wtd.assistant.frontend.dataimport;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@NoArgsConstructor
@Component
public class InputFileDownloader {

    static String url = "https://pit.lukasiewicz.gov.pl/wp-content/uploads/2023/01/KRAJOWY-WYKAZ-KODOW-IPPC-09.12.2022.pdf";
    public void downloadFile() {
        try {
            downloadUsingNIO(url, "src/main/resources/ippcpdf.pdf");
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file path. Try updating it");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
}
