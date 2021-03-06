package com.example.jenkinsapi.service;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.juli.logging.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;


import static java.lang.System.out;
@Service
public class JenkinsApiService {

    /*
    * -------------getCrumb------------------
    * crumb와 cookie값이 있어야지만 JenkinsApi에 Post요청을 보낼 수 있음.
    * 이 2개의 값을 배열로 얻어내는 함수
    * */
    @Autowired
    public static String[] getCrumb(String targetURL){
        //crumb 객체 생성
        String crumb = "";
        String cookies = "";

        try {
            //Connection을 진행할 URL선언부
            URL url = new URL (targetURL);
            //Basic Auth Incoding작업
            String encoding = Base64.getEncoder().encodeToString(("admin:okestro2018").getBytes("UTF-8"));

            //위에서 선언한 url을 가지고 httprurl케넥션을 연결
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //httpUrl커넥션의 방식 결정
            connection.setRequestMethod("GET");

            //항상 갱신된 내용을 가져옴
            connection.setDoOutput(true);

            //Request Header에 어떤 값을 담을지 선택한다.
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            //데이터가 전송될 통로를 InputStream으로 열어준다.
            InputStream content = (InputStream)connection.getInputStream();
            //응답 받은 값을 버퍼리더로 읽어와서 In에 담음
            BufferedReader in = new BufferedReader (new InputStreamReader (content));
            out.println("\n\n[ Get request to Jenkins API server... ]\n----------------------------------------");
            /*
            * Cookie 값 획득 부
            * */
            Map<String, List<String>> imap = connection.getHeaderFields( ) ;
            if( imap.containsKey( "Set-Cookie" ) )
            {
                List<String> lString = imap.get( "Set-Cookie" ) ;
                for( int i = 0 ; i < lString.size() ; i++ ) {
                    cookies += lString.get( i ) ;
                }
                out.println("Cookie 획득 --> "+cookies);
            } else {
                out.println("쿠키 획득에 실패했습니다.");
            }

            /*
            * crumb 값 획득 부
            * */
            String line;
            //line에는 in에 담긴 값을 값이 없을때까지 읽어서 담아준다.
            while ((line = in.readLine()) != null) {
                out.println("응답 값 ---> "+line);
                //한줄로 반환되기 때문에 JSONObject로 내가 필요한 값만 추출해낸다.
                JSONObject jsonObject = new JSONObject(line);
                crumb = jsonObject.getString("crumb");
                out.println("crumb 값 추출 ---> "+crumb);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        out.println("다음 2개의 값 획득 성공\ncrumb = "+crumb+"\ncookie = "+cookies);
        return new String[]{crumb, cookies};
    }

    /*
    * pipeline의 리스트를 출력해주는 함수
    * */
    public String[] getList(String targetURL){
        String line = null;
        Integer length = 0;
        String[] name = new String[0];
        try {
            URL url = new URL (targetURL);

            String encoding = Base64.getEncoder().encodeToString(("admin:okestro2018").getBytes("UTF-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in = new BufferedReader (new InputStreamReader (content));

            //line에는 in에 담긴 값을 값이 없을때까지 읽어서 담아준다.
            while ((line = in.readLine()) != null) {
                //out.println("응답 값 ---> "+line);
                //한줄로 반환되기 때문에 JSONObject로 내가 필요한 값만 추출해낸다.
                JSONObject jsonObject = new JSONObject(line);
                JSONArray jsonArray = jsonObject.getJSONArray("jobs");
                length=jsonArray.length();
                out.println("Jenkins pipeline을 반환합니다...\n총 pipeline --> "+length+"개");
                name = new String[length];
                for(int i =0; i<jsonArray.length(); i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    name[i] = obj.getString("name");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /*
     * 현재 수행한 JOB의 수행 결과를 확인하기 위해 가장 최근에 실행한 JOB의 수행 번호 확인
     * * */
    public String getLastStatus(String targetURL){
        String line = null;
        String status = null;
        try {
            URL url = new URL (targetURL);
            String encoding = Base64.getEncoder().encodeToString(("admin:okestro2018").getBytes("UTF-8"));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);

            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in = new BufferedReader (new InputStreamReader (content));

            while ((line = in.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(line);
                status = jsonObject.getString("status");
                out.println("빌드 결과 ---> "+status);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return status;
    }


    /*
    * Post를 통한 젠킨스 프로젝트 빌드
    * */
    public String buildTest(String buildUrl, String[] token){
        String result = null;
        try{
            String encoding = Base64.getEncoder().encodeToString(("admin:okestro2018").getBytes("UTF-8"));

            URL url = new URL(buildUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            out.println("\n\n빌드에 대한 POST요청이 성공했습니다...");

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic "+encoding);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Jenkins-Crumb", token[0]);
            connection.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Cookie", token[1]);
            connection.setDoOutput(true);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Jenkins-Crumb=").append(token);

            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "euc-kr"));
            printWriter.write(stringBuffer.toString());
            printWriter.flush();

            int responseCode = 0;
            responseCode = connection.getResponseCode();
            out.println("http response code: "+responseCode);

            result="Success for build job";
        }
        catch (Exception e){
            e.printStackTrace();
            out.println(e.getMessage());
        }
        return result;
    }

    /*
     * Post를 통한 젠킨스 프로젝트 빌드
     * */
    public String AddCreden(String targetUrl, String username, String password, String id, String[] accessToken){

        String result = null;
        String wwwJson = "{\"\": \"0\", \"credentials\": {\"scope\": \"GLOBAL\", \"username\": \""+username+"\", " +
                "\"usernameSecret\": false, \"password\": \""+password+"\", \"$redact\": \"password\", \"id\": \""+id+"\", " +
                "\"description\": \"\", \"stapler-class\": \"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\", " +
                "\"$class\": \"com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl\"}, " +
                "\"Jenkins-Crumb\": \""+accessToken[0]+"\"}";

//        JSONParser parser = new JSONParser();
//        JSONObject jsonObject = (JSONObject) parser.parse(wwwJson);

        try{
            String encoding = Base64.getEncoder().encodeToString(("admin:okestro2018").getBytes("UTF-8"));

            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            out.println("\n\nCredential 생성 요청에 성공했습니다...");

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic "+encoding);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Cookie", accessToken[1]);
            connection.setRequestProperty("json", wwwJson);
            connection.setDoOutput(true);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Jenkins-Crumb=").append(accessToken[0]);


            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "euc-kr"));
            printWriter.write(stringBuffer.toString());

            printWriter.flush();

            int responseCode = 0;
            responseCode = connection.getResponseCode();
            out.println(responseCode);
            if (responseCode<400){
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
