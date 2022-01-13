package com.example.jenkinsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.offbytwo.jenkins.JenkinsServer;

import java.net.URI;

@SpringBootApplication
public class JenkinsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JenkinsApiApplication.class, args);

		String jenkinsUri = "http://100.0.0.159";
		String username = "admin";
		String passwordOrToken = "114122cca5cd350667557b80b22dc2d501";
		String password = "okestro2018";

		//JenkinsServer jenkinsServer = new JenkinsServer(new URI(jenkinsUri), username, passwordOrToken);
	}
}
