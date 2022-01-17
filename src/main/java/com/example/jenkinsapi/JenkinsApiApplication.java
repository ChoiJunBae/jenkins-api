package com.example.jenkinsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.offbytwo.jenkins.JenkinsServer;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@SpringBootApplication
public class JenkinsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JenkinsApiApplication.class, args);


	}
}
