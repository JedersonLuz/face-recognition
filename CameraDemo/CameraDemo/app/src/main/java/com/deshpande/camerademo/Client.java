package com.deshpande.camerademo;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class Client extends AsyncTask<Void, Void, Void> {

    String host= "10.180.14.64";
    int port = 12345;
    File file;
    public static String FILE_TO_SEND;

    public Client (File file) {
        this.file = file;
    }
    public Client () { }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        FILE_TO_SEND = mediaStorageDir.getPath() + File.separator + file.getName();

        try {
            //Log.d("Mensagem de conexão", "Conectando ao host " + host);
            //Socket cliente = new Socket(host, port);
            //Log.d("Mensagem de conexão", "Conectado com sucesso!");
            //PrintStream mensagem = new PrintStream(cliente.getOutputStream());
            //mensagem.println(String.format("%s", file.toString()));
            //mensagem.println(String.format("%s", "foto_fake"));

            InetAddress iAddress = InetAddress.getByName(host);
            Log.d("Mensagem de conexão", "Conectando ao host " + host);
            Socket client = new Socket(iAddress, 12345);
            Log.d("Mensagem de conexão", "Conectado com sucesso!");

            // send file
            File myFile = new File (FILE_TO_SEND);
            byte [] mybytearray  = new byte [(int)myFile.length()];
            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            os = client.getOutputStream();
            Log.d("Mensagem de conexão", "Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            Log.d("Mensagem de conexão", "Done!");
        } catch (Exception e) {
            Log.d("Mensagem de conexão", "Conexão falhou!!");
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) bis.close();
                if (os != null) os.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

}