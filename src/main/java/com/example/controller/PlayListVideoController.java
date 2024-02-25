package com.example.controller;

import com.example.service.PlayListVideoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/video_tag")
@Tag(name = "Play List for api",description = "This api shows all method about play list video")
public class PlayListVideoController {

    @Autowired
    private PlayListVideoService playListVideoService;

}
