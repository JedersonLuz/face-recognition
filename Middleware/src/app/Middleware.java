package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch; 


public class Middleware{

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            ServerSocket server = new ServerSocket(12345);
            System.out.println("server opened on port 12345");

            while(true){
                CountDownLatch latch = new CountDownLatch(2);

                Socket client = server.accept();
                System.out.println("IP " + client.getInetAddress().getHostAddress() + " connected");

                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String a = br.readLine();
                System.out.println("Mensage from client: " + a);

                MeanCalculator meanCalculator = new MeanCalculator(client, a, latch);

                Thread mean = new Thread(meanCalculator);
                
                System.out.println("Média: " + meanCalculator.response + "\n" + "Mediana: " + medianCalculator.response);

                OutputStream os = client.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                bw.newLine();
                bw.flush();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}