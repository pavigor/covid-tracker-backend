package com.tracker.backend.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/data")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    @Autowired
    private DataService service;

    @GetMapping(path="/update")
    @ResponseBody
    public Integer update() {
        logger.info("Request for update data");
        int updated = service.updateData();
        logger.info("Updated " + updated);
        return updated;
    }
}
