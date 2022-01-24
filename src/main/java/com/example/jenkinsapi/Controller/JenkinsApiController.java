package com.example.jenkinsapi.Controller;

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
    String[] token, list;
    String jobParam, runningStatus;

    @GetMapping("/crumb")
    public String[] getCrumb(){
        token = jenkinsApiService.getCrumb("http://100.0.0.159:8080/crumbIssuer/api/json");
        return token;
    }

    @GetMapping("/list")
    public String[] getList(){
        list = jenkinsApiService.getList("http://100.0.0.159:8080/api/json?tree=jobs[name,color]");
        return list;
    }

    @GetMapping("/status")
    public String getStatus(String jobName){
        jobParam = jobName;
        runningStatus = jenkinsApiService.getLastStatus("http://100.0.0.159:8080/job/"+jobParam+"/lastBuild/wfapi/describe");
        return runningStatus;
    }

    @PostMapping("/build")
    public String buildTest(String jobName){
        jobParam = jobName;
        String[] token = jenkinsApiService.getCrumb("http://100.0.0.159:8080/crumbIssuer/api/json");
        String result = jenkinsApiService.buildTest("http://admin:okestro2018@100.0.0.159:8080/job/"+jobParam+"/build", token);
        return result;
    }
}