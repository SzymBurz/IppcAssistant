package com.wtd.assistant.frontend.generator;

import com.wtd.assistant.frontend.domain.Enterprise;

import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class EnterpriseSummaryGenerator {

    public void generateSummary(List<Enterprise> enterprises, TextArea textArea) {
        StringBuilder builder = new StringBuilder();
            for(Enterprise e : enterprises) {
                builder.append("\n Name: " + e.getName());
                builder.append("\n IPPC code: " + e.getIppcCode());
                builder.append("\n Name: " + e.getExpiryDate());
                builder.append("\n ");
            }
            textArea.setValue(builder.toString());
    }
}
