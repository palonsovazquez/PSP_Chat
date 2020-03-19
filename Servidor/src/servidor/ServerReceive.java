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

            System.out.println("Realizando el bind");// InetAddress.getLocalHost()

            // InetSocketAddress addr = new InetSocketAddress(ipLocal, puertoRecepcion);
            InetSocketAddress addr = new InetSocketAddress(puertoRecepcion);

            System.out.println("ip: " + addr);

            serverSocket.bind(addr);
            do {
                System.out.println("Aceptando conexiones Recepcion");
                // conexion Aceptada
                Socket newSocket = serverSocket.accept();
                System.out.println("Aceptada conexion Recepcion");
                // Leo el mensaje que contiene el usuario
                os = newSocket.getOutputStream();
            is = newSocket.getInputStream();
                String UsuarioJson = LibreriaSockets.leerMensaje(is);
                System.out.println("Recepcion:" + UsuarioJson);
//                Usuario usuario = chatcommon.json.ComunicationJsonParser.importUser(UsuarioJson);
//
//                Usuario usersql = SQL_Usuario.getUsuario(usuario.getIdUsuario().toString());
//                if (usersql == null) {
//                    SQL_Usuario.añadirUsuario(usuario);
//                    SQL_ChatsUsuarios.añadirChatAUsuario(usuario, new Chat());
//                    usersql = usuario;
//                }

                // devuelvo los datos para el usuario incluyendo chats y usuarios en los mismos chats
                //LibreriaSockets.escribirMensaje(newSocket, chatcommon.json.ComunicationJsonParser.exportUsuario(usersql));

                serverThreatReceive.put(UsuarioJson, new serverTrheatReceive(newSocket,is));
                serverThreatReceive.get(UsuarioJson).start();
//                do {
//                    String mn = LibreriaSockets.leerMensaje(newSocket);
//                    System.out.println(mn);
//                    LibreriaSockets.escribirMensaje(newSocket, "de vuelta " + mn);
//
//                } while (true);

            } while (continuar);

            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
