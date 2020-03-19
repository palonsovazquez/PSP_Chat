/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;


import chatcommon.dao.SQL_Usuario;
import chatcommon.model.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import socketLibraries.LibreriaSockets;


/**
 *
 * @author usuario1
 */
public class ServerSend extends Thread {
    private static HashMap<String,serverTrheatSend> serverThreatSends;
    Integer puertoEnvio = 5555;
    private String ipLocal;
    private InputStream is;
    private OutputStream os;

    public ServerSend(String ipLocal,Integer puerto) {
        this.ipLocal= ipLocal;
        this.puertoEnvio = puerto;
          serverThreatSends= new HashMap<String,serverTrheatSend>();

    }

    public static void broadcast(String mens) {
        for(serverTrheatSend sts: serverThreatSends.values()) {
            System.out.println("key");
        sts.addText(mens);}
    }

    

     public void run() {
        Boolean continuar = true;
        ServerSocket serverSocket = null;

        try {

            
                System.out.println("Creando socket servidor Send");

                serverSocket = new ServerSocket();

                System.out.println("Realizando el bind");// InetAddress.getLocalHost()
               
                InetSocketAddress addr = new InetSocketAddress( puertoEnvio);
                System.out.println("ip: " + addr);

                serverSocket.bind(addr);

               
                do {
                     System.out.println("Aceptando conexiones Send");
           
                Socket newSocket = serverSocket.accept();
                 os = newSocket.getOutputStream();
            is = newSocket.getInputStream();
                String usuarioJson = LibreriaSockets.leerMensaje(is);
                System.out.println("Send:" + usuarioJson);
              
                
                serverThreatSends.put(usuarioJson, new serverTrheatSend(newSocket,os));
                serverThreatSends.get(usuarioJson).start();


            } while (continuar);

            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
