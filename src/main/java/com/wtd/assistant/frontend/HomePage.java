package com.wtd.assistant.frontend;

import com.helger.commons.io.file.FileOperations;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;

@Route(value = "Home", layout = AssistantAppLayout.class)
public class HomePage extends VerticalLayout{

    StreamResource imageResource1 = new StreamResource("7.jpg", () -> getClass().getResourceAsStream("/images/7.jpg"));
    StreamResource imageResource2 = new StreamResource("16.jpg", () -> getClass().getResourceAsStream("/images/16.jpg"));
    StreamResource imageResource3 = new StreamResource("18.jpg", () -> getClass().getResourceAsStream("/images/18.jpg"));
    StreamResource imageResource4 = new StreamResource("19.jpg", () -> getClass().getResourceAsStream("/images/19.jpg"));
    StreamResource imageResource5 = new StreamResource("12.jpg", () -> getClass().getResourceAsStream("/images/12.jpg"));
    StreamResource imageResource6 = new StreamResource("6.jpg", () -> getClass().getResourceAsStream("/images/6.jpg"));

    List<Image> imageList = new ArrayList<>();
    Image image1 = new Image(imageResource1, "myImg");
    Image image2 = new Image(imageResource2, "myImg");
    Image image3 = new Image(imageResource3, "myImg");
    Image image4 = new Image(imageResource4, "myImg");
    Image image5 = new Image(imageResource5, "myImg");
    Image image6 = new Image(imageResource6, "myImg");


    public HomePage() {

        imageList.add(image1);
        imageList.add(image2);
        imageList.add(image3);
        imageList.add(image4);
        imageList.add(image5);
        imageList.add(image6);

        Label label1 = new Label();
        InputStream is1 = getClass().getClassLoader().getResourceAsStream("text/text3.txt");
        String text1 = new BufferedReader(
                new InputStreamReader(is1, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        Label label2 = new Label();
        InputStream is2 = getClass().getClassLoader().getResourceAsStream("text/text2.txt");
        String text2 = new BufferedReader(
                new InputStreamReader(is2, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));


        label1.getElement().setProperty("innerHTML", text1);
        label2.getElement().setProperty("innerHTML", text2);

        configureImages();
        HorizontalLayout imageLayout1 = new HorizontalLayout(image1, image2, image3);
        imageLayout1.setAlignItems(Alignment.STRETCH);

        HorizontalLayout imageLayout2 = new HorizontalLayout(image4, image5, image6);
        imageLayout2.setAlignItems(Alignment.STRETCH);

        add(label1, imageLayout1, label2, imageLayout2);


    }

    private void configureImages() {
        for (Image i: imageList) {
            i.setWidth(400, Unit.PIXELS);
            i.setHeight(300, Unit.PIXELS);
        }

    }
}
