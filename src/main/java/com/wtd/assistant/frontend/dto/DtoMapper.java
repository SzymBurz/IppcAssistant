package com.wtd.assistant.frontend.dto;

import com.wtd.assistant.frontend.domain.Enterprise;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public List<EnterpriseDto> enterprisesToDto(List<Enterprise> input) {

        return input.stream()
                .map(this::enterpriseToDto)
                .collect(Collectors.toList());
    }

    public EnterpriseDto enterpriseToDto(Enterprise e) {
        return new EnterpriseDto(e.getIppcCode(), e.getName(), e.getExpiryDate());
    }
}
