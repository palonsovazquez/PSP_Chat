/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.logging.Logger.global;
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import jdk.nashorn.internal.parser.JSONParser;
import socketLibraries.LibreriaSockets;


/**
 *
 * @author usuario1
 */
public class ServerReceive extends Thread {

    private static HashMap<String, serverTrheatReceive> serverThreatReceive;
    Integer puertoRecepcion = 5555;
    private String ipLocal;
    private OutputStream os;
    private InputStream is;

    public ServerReceive(String ipLocal, Integer puerto) {
        this.ipLocal = ipLocal;
        this.puertoRecepcion = puerto;
        serverThreatReceive = new HashMap<String, serverTrheatReceive>();
    }

    public void run() {
        Boolean continuar = true;
        ServerSocket serverSocket = null;

        try {

            System.out.println("Creando socket servidor Recepcion");

            serverSocket = new ServerSocket();

            System.out.println("Realizando el bind");

            
            InetSocketAddress addr = new InetSocketAddress(puertoRecepcion);

            System.out.println("ip: " + addr);

            serverSocket.bind(addr);
            do {
                System.out.println("Aceptando conexiones Recepcion");
              
                Socket newSocket = serverSocket.accept();
                System.out.println("Aceptada conexion Recepcion");
               
                os = newSocket.getOutputStream();
            is = newSocket.getInputStream();
                String UsuarioJson = LibreriaSockets.leerMensaje(is);
                System.out.println("Recepcion:" + UsuarioJson);


                serverThreatReceive.put(UsuarioJson, new serverTrheatReceive(newSocket,is));
                serverThreatReceive.get(UsuarioJson).start();


            } while (continuar);

            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
