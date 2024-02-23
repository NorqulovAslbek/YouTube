package com.example;

import com.example.service.HttpTestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class YoutubeApplicationTests {

    @Autowired
    private HttpTestService httpTestService;

    @Test
    void contextLoads() {
//        httpTestService.createTask();
//        httpTestService.taskGetAll();
//        httpTestService.executeGet("081e1add-7231-4a64-93cc-51eea54f92f2c");
        httpTestService.updateTask("081e1adefefd-7231-4a64-93cc-5eea54f92f2c");
//        httpTestService.getTaskById("kjdfhgkdjhfgkdjf");
    }


}
