package com.tracker.backend.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService service;

    @GetMapping(path="/update")
    @ResponseBody
    public Integer update() {
        int updated = service.updateData();
        return updated;
    }
}
