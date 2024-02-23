package com.example.service;

import com.example.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class HttpTestService {
    @Autowired
    private RestTemplate restTemplate;

    public void createTask() {
        TaskDTO dto = new TaskDTO();
        dto.setTitle("Test title 1");
        dto.setContent("Salom...");

        HttpEntity<TaskDTO> request = new HttpEntity<TaskDTO>(dto);

        String response = restTemplate.postForObject("http://localhost:8081/task", request, String.class);
        System.out.println(response);
    }

    public void taskGetAll() {
        List<?> result = restTemplate.getForObject("http://localhost:8081/task/getAll", List.class);
        System.out.println(result);
    }

    public void getTaskById(String id) {
        String url = "http://localhost:8081/task/" + id;
        ResponseEntity<TaskDTO> responseEntity = restTemplate.getForEntity(url, TaskDTO.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println(responseEntity.getBody());
        }
    }

    public void updateTask(String id) {
        String url = "http://localhost:8081/task/" + id;

        TaskDTO dto = new TaskDTO();
        dto.setTitle("Test title 11");
        dto.setContent("Salom11...");

        restTemplate.put(url, dto);
    }

    public void executeGet(String id) {
        String url = "http://localhost:8081/task/" + id;
        try {
            RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(url));
            ResponseEntity<TaskDTO> responseEntity = restTemplate.exchange(requestEntity, TaskDTO.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println(responseEntity.getBody());
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


}
