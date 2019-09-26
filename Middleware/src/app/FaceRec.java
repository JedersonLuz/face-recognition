//package app;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.Base64;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class FaceRec implements Runnable {

    private Socket client;
    private String img;
    public String response;
    public String[] uris;
    public boolean[] urisOn;
    public URL[] urls;
    public HttpURLConnection[] cons;

    public FaceRec(Socket client, String img){
        this.client = client;
        this.img = img;
        this.uris = new String[] {
            "http://localhost:8080/Server0/webresources/server0", 
            "http://10.180.84.139:8080/Server1/webresources/server1",
            "http://10.180.84.140:8080/Server2/webresources/server2"
        };
        this.urisOn = new boolean[this.uris.length];
        this.urls = new URL[this.uris.length];
        this.cons = new HttpURLConnection[this.uris.length];
    }

    public FaceRec () {
        this.uris = new String[] {
            "http://localhost:8080/Server0/webresources/server0", 
            "http://10.180.84.139:8080/Server1/webresources/server1",
            "http://10.180.84.140:8080/Server2/webresources/server2"
        };
        this.urisOn = new boolean[this.uris.length];
        this.urls = new URL[this.uris.length];
        this.cons = new HttpURLConnection[this.uris.length];
    }

    public void run() {
        System.out.println("Send data for FaceRecServer");
        getFaceRec();
    }

    /* public static void main(String[] args) {
        new FaceRec().getFaceRec();
    } */

    public void getFaceRec(){
        try {
            int matterI = 0;
            double matterCPU = 99999999.0;
            long matterLatence = 99999999;
            int responseCode;
            for (int i = 0; i < uris.length; i++) {
                try {
                    urisOn[i] = false;
                    urls[i] = new URL(uris[i] + "/cpu");
                    cons[i] = (HttpURLConnection) urls[i].openConnection();
                    cons[i].setRequestMethod("GET");
                    cons[i].setConnectTimeout(5000);
                    responseCode = cons[i].getResponseCode();
                    System.out.println(responseCode);
                    if (responseCode == 200) {
                        urisOn[i] = true;
                        BufferedReader in = new BufferedReader(new InputStreamReader(cons[i].getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        
                        long start = System.currentTimeMillis();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        long end = System.currentTimeMillis();
    
                        long latence = end - start;
                        
                        if ((matterCPU >= Double.parseDouble(response.toString())) && (matterLatence >= latence)) {
                            matterCPU = Double.parseDouble(response.toString());
                            matterLatence = latence;
                            matterI = i;
                            /* Mandar imagem */
                        }
    
                        in.close();
                        System.out.println(response.toString() + " " + (latence));
                        this.response = response.toString();
                    } else {
                        System.out.println("Servidor indisponivel");
                    }
                } catch (Exception e) {
                    //TODO: handle exception
                    System.out.println(e.getMessage());
                }
            }
            
            System.out.println("Melhor servidor: " + matterI);
            System.out.println("Enviando imagem para o melhor servidor");
            
            File myFile = new File (img);
            byte[] mybytearray  = new byte [(int)myFile.length()];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            String s = Base64.getEncoder().encodeToString(mybytearray);
            //System.out.println(s);
            
            URL matterServer = new URL(uris[matterI] + "/upload/" + s);
            HttpURLConnection con = (HttpURLConnection) matterServer.openConnection();
            con.setRequestMethod("GET");
            responseCode = con.getResponseCode();
            System.out.println(responseCode);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            //System.out.println(response.toString());
            this.response = response.toString();
            System.out.println(this.response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
