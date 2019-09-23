package app;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class FaceRec extends Thread {

    private Socket client;
    private String numbers;
    private CountDownLatch countDownLatch;
    public String response;

    public FaceRec(Socket client, String numbers, CountDownLatch countDownLatch){
        this.client = client;
        this.numbers = numbers;
        this.countDownLatch = countDownLatch;
    }

    public FaceRec () {}

    public void run() {
        System.out.println("Send data for MeanServer");
        getFaceRec();
        countDownLatch.countDown();
    }

    public static void main(String[] args) {
        new FaceRec().getFaceRec();
    }

    public void getFaceRec(){
        try {
            String uri = "http://localhost:8080/Server0/webresources/server0/cpu";
            String uri2 = "http://10.180.84.139:8080/Server1/webresources/server1/cpu";
            URL obj = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            System.out.println(responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            System.out.println(response.toString());
            this.response = response.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}