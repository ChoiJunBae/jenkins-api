package com.example.jenkinsapi.service;


import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static java.lang.System.out;
@Service
public class JenkinsApiService {

    @Autowired
    public static String getCrumb(String targetURL){
        String crumb = null;

        try {
            URL url = new URL (targetURL);
            String encoding = Base64.getEncoder().encodeToString(("admin:okestro2018").getBytes("UTF-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in = new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
                out.println("response json ---> "+line);

                JSONObject jsonObject = new JSONObject(line);
                crumb = jsonObject.getString("crumb");
                out.println("crumb token ---> "+crumb);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return crumb;
    }

    public void buildTest(String token){

    }
}
