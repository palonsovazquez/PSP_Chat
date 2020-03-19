/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketLibraries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kazuo
 */
public class LibreriaSockets {
   private static Integer tamanhoToken = 32;

    public static String leerMensaje(InputStream io) {

        String data = null;
        try {

            byte[] tamanho = new byte[tamanhoToken];

            int leido = io.read(tamanho);

            if (leido != -1) {
                String ss = new String(tamanho);
         
                int x = Integer.parseInt(ss);
                

                byte[] messaje = new byte[x];
                io.read(messaje);
                data = new String(messaje);
       
            }

        } catch (Exception ex) {
            Logger.getLogger(LibreriaSockets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public static Boolean escribirMensaje(OutputStream os, String data) {
        Boolean hecho = false;

        try {



            Integer s = data.length();

            os.write(preparar(s,tamanhoToken).getBytes());
            os.write(data.getBytes());
            hecho = true;
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LibreriaSockets.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hecho;
    }

    public static String preparar(Integer num, int tamanho) {
        String data = num.toString();
        if(data.length() <tamanho){
        do{
        data= "0"+data;
        }while(data.length()<tamanho);
        
        
        }



return data;
    }
}
