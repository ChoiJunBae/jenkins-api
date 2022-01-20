package com.example.jenkinsapi.service;

import org.apache.http.cookie.Cookie;
import org.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;


import static java.lang.System.out;
@Service
public class JenkinsApiService {

    @Autowired
    public static void getCookie(String targetUrl){
        try {
            URL url = new URL(targetUrl);
            URLConnection connection = url.openConnection();
            /*
             * Cookie getting
             * */
            Map<String, List<String>> headerFields = connection.getHeaderFields();

            Set<String> headerFieldsSet = headerFields.keySet();
            Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();

            while (hearerFieldsIter.hasNext()) {
                String headerFieldKey = hearerFieldsIter.next();

                if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
                    List<String> headerFieldValue = headerFields.get(headerFieldKey);

                    for (String headerValue : headerFieldValue) {
                        System.out.println("Cookie Found...");

                        String[] fields = headerValue.split(";\\s*");

                        String cookieValue = fields[0];
                        String expires = null;
                        String path = null;
                        String domain = null;
                        boolean secure = false;

                        // Parse each field
                        for (int j = 1; j < fields.length; j++) {
                            if ("secure".equalsIgnoreCase(fields[j])) {
                                secure = true;
                            }
                            else if (fields[j].indexOf('=') > 0) {
                                String[] f = fields[j].split("=");
                                if ("expires".equalsIgnoreCase(f[0])) {
                                    expires = f[1];
                                }
                                else if ("domain".equalsIgnoreCase(f[0])) {
                                    domain = f[1];
                                }
                                else if ("path".equalsIgnoreCase(f[0])) {
                                    path = f[1];
                                }
                            }
                        }

                        System.out.println("cookieValue:" + cookieValue);
                        System.out.println("expires:" + expires);
                        System.out.println("path:" + path);
                        System.out.println("domain:" + domain);
                        System.out.println("secure:" + secure);

                        System.out.println("*****************************************");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    * crumb 값이 있어야지만 JenkinsApi에 Post요청을 보낼 수 있음.
    * */
    @Autowired
    public static String getCrumb(String targetURL){
        String crumb = null;
        String cookie = null;

        try {
            //Connection을 진행할 URL선언부
            URL url = new URL (targetURL);
            //Basic Auth Incoding작업
            String encoding = Base64.getEncoder().encodeToString(("admin:okestro2018").getBytes("UTF-8"));

            //위에서 선언한 url을 가지고 httprurl케넥션을 연결
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //httpUrl커넥션의 방식 결정
            connection.setRequestMethod("GET");



            //항상 갱신된 내용을 가져옴.
            connection.setDoOutput(true);
            //Request Header에 어떤 값을 담을지 선택한다.
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            //데이터가 전송될 통로를 InputStream으로 열어준다.
            InputStream content = (InputStream)connection.getInputStream();
            //응답 받은 값을 버퍼리더로 읽어와서 In에 담음
            BufferedReader in = new BufferedReader (new InputStreamReader (content));
            String line;
            //line에는 in에 담긴 값을 값이 없을때까지 읽어서 담아준다.
            while ((line = in.readLine()) != null) {
                out.println("response json ---> "+line);
                //한줄로 반환되기 때문에 JSONObject로 내가 필요한 값만 추출해낸다.
                JSONObject jsonObject = new JSONObject(line);
                crumb = jsonObject.getString("crumb");
                out.println("crumb token ---> "+crumb);
            }



        } catch(Exception e) {
            e.printStackTrace();
        }
        return crumb;
    }

    public String buildTest(String buildUrl, String token){
        URL url = null;
        String result = null;

        try{
            //String encoding = Base64.getEncoder().encodeToString(("admin:okestro2018").getBytes("UTF-8"));

            url = new URL(buildUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            //connection.setRequestProperty("Authorization", "Basic "+encoding);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(5000);
            //connection.setRequestProperty("Jenkins-Crumb", token);
            connection.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setDoOutput(true);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Jenkins-Crumb=").append(token);
            out.println(stringBuffer);

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "euc-kr"));
            printWriter.write(stringBuffer.toString());
            printWriter.flush();

            int responseCode = 0;
            responseCode = connection.getResponseCode();
            out.println(responseCode);

            out.println(connection.getErrorStream().toString());

            StringBuffer sb = new StringBuffer();
            if(responseCode < 400){
                String line;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "euc-kr"));
                while ((line = bufferedReader.readLine())!=null){
                    out.println(line);
                    stringBuffer.append(line);
                }

                printWriter.close();
                bufferedReader.close();
                result="성공";
            }
            else{
                result="에러";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            out.println(e.getMessage());
        }
        return result;
    }
}
