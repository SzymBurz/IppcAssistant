package com.wtd.assistant.frontend.dataimport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class InputFileConverter {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HttpHeaders headers;

    public boolean convertPdfToXlsxWithExternalApi() {

        /*
        https://pdftables.com/pdf-to-excel-api
        It's paid service, and key is already not working
        For purpose of this program, we just use older file
         */

        String pdfFile = ".src/main/resources/ippcpdf.pdf";
        String apiKey = "5gkhx4629mkp";

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("f", new File(pdfFile));

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);
        String url = "https://pdftables.com/api?key=" + apiKey + "&format=xlsx-single";

        byte[] response = restTemplate.postForObject(url, request, byte[].class);

        File xlsxFile = new File("src/main/resources");
        try (FileOutputStream fos = new FileOutputStream(xlsxFile)) {
            fos.write(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (xlsxFile.length() > 0) {
            return true;
        } else {
            return false;
        }

    }
}
