package com.example.jenkinsapi.Controller;

import com.example.jenkinsapi.JenkinsApi;
import com.example.jenkinsapi.service.JenkinsApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/pipeline")
public class JenkinsApiController {

    private final JenkinsApiService jenkinsApiService;
    private String token;

    @GetMapping("/crumb")
    public String getCrumb(){
        token = jenkinsApiService.getCrumb("http://100.0.0.159:8080/crumbIssuer/api/json");
        return token;
    }

    @PostMapping("/build")
    public String buildTest(){
        String crumb = jenkinsApiService.getCrumb("http://100.0.0.159:8080/crumbIssuer/api/json");
        String result = jenkinsApiService.buildTest("http://admin:okestro2018@100.0.0.159:8080/job/test/build",crumb);
        return result;
        // 참고 cookie = https://caileb.tistory.com/146
    }
}
