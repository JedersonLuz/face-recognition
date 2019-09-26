//package app;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;


public class Middleware {

    // you may change this, I give a
    // different name because i don't want to
    // overwrite the one used by server...
    public final static String FILE_TO_RECEIVED = "./image-downloaded.png";

    // file size temporary hard coded
    // should bigger than the file to be downloaded
    public final static int FILE_SIZE = 6022386;

    public static void main(String[] args) throws IOException, InterruptedException {

        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            ServerSocket server = new ServerSocket(12345);
            System.out.println("server opened on port 12345");

            while(true){
                Socket client = server.accept();
                System.out.println("IP " + client.getInetAddress().getHostAddress() + " connected");

                
                // receive file
                byte [] mybytearray  = new byte [FILE_SIZE];
                InputStream is = client.getInputStream();
                fos = new FileOutputStream(FILE_TO_RECEIVED);
                bos = new BufferedOutputStream(fos);
                bytesRead = is.read(mybytearray,0,mybytearray.length);
                current = bytesRead;
                
                System.out.println("Começando a receber iamgem");
                do {
                    bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                    if(bytesRead >= 0) current += bytesRead;
                    //System.out.println("Recebendo iamgem bytes: " + current);
                } while(bytesRead > 0);

                System.out.println("Imagem recebia");
                bos.write(mybytearray, 0 , current);
                bos.flush();
                System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");


                /* BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String msg = br.readLine();
                System.out.println("Mensage from client: " + msg); */

                Runnable faceRec = new FaceRec(client, FILE_TO_RECEIVED);

                Thread faceRecT = new Thread(faceRec);
		        faceRecT.start();

                /* OutputStream os = client.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                bw.newLine();
                bw.flush(); */
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
