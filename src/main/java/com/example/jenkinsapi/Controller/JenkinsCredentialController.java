package com.example.jenkinsapi.Controller;

import com.example.jenkinsapi.service.JenkinsApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/credential")
public class JenkinsCredentialController {

    JenkinsApiService jenkinsApiService = new JenkinsApiService();

    @PostMapping("/add")
    public String credentialAdd(String username, String password, String id){
        String[] accessToken = jenkinsApiService.getCrumb("http://100.0.0.159:8080/crumbIssuer/api/json");
        String result = jenkinsApiService.AddCreden(
                "http://100.0.0.159:8080/credentials/store/system/domain/_/createCredentials", username, password, id, accessToken);
        return result;
    }
}
