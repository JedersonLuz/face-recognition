package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientTest {
    public static void main(String[] args) {
        try {
            InetAddress iAddress = InetAddress.getByName("127.0.0.1");
            Socket client = new Socket(iAddress, 12345);
            OutputStream os = client.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("imagem_fake\n");
            bw.flush();

            /* InputStream is = client.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String response = br.readLine();
            String mean = response.split("-")[0];
            String median = response.split("-")[1];
            System.out.println(mean);
            System.out.println(median); */
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
