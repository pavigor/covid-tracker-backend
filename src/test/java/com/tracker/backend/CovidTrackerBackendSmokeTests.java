package com.tracker.backend;

import com.tracker.backend.data.DataController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CovidTrackerBackendSmokeTests {
    @Autowired
    private DataController dataController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(dataController).isNotNull();
    }

    @Test
    public void updateTest() throws Exception {

    }
}
