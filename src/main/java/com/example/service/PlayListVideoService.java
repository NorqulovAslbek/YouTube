package com.example.service;

import com.example.repository.PlayListVideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlayListVideoService {

    @Autowired
    private PlayListVideoRepository playListVideoRepository;
}
