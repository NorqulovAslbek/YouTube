package com.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Channel Api list", description = "Api list for Channel")
@RestController
@RequestMapping("/channel")
@Slf4j
public class ChannelController {
}
