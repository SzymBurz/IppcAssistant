package com.wtd.assistant.frontend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EnterpriseDto {

    String ippcCode;
    String name;
    LocalDate expiryDate;
}
