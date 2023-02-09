package com.wtd.assistant.frontend.Controller;

import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.dto.DtoMapper;
import com.wtd.assistant.frontend.dto.EnterpriseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@CrossOrigin("*")
@RestController
public class AssistantController {

    @Autowired
    EnterpriseDao ed;
    @Autowired
    DtoMapper dm;

    @GetMapping(value = "/ippccodes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<EnterpriseDto> getEnterprises() {
        return dm.enterprisesToDto((List<Enterprise>) ed.findAll());
    }


}
