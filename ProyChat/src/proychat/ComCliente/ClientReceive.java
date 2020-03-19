/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proychat.ComCliente;



import chatcommon.dao.SQL_Usuario;
import chatcommon.json.ComunicationJsonParser;
import chatcommon.model.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
public class ClientReceive extends Thread {

    private Integer puerto ;
     private String ip ;
     private Usuario user;
     private OutputStream os;
     private InputStream is;

    public ClientReceive(Usuario user,Integer puerto,String ip) {
        this.user = user;
        this.puerto = puerto;
        this.ip = ip;
        
    }

    public void run() { 
        try {
            Boolean continuar = true;
            
            
            
            
            
            
            System.out.println("Creando socket cliente");
            Socket clienteSocket=new Socket();
            System.out.println("Estableciendo la conexi�n"+ip+":"+puerto);
            
            InetSocketAddress addr= new InetSocketAddress(ip,puerto);
            System.out.println(addr);
            clienteSocket.connect(addr);
            
            
            
            
            System.out.println("Enviando id Receive");
            
            
            String mensa= user.getIdUsuario().toString();
            os = clienteSocket.getOutputStream();
            is = clienteSocket.getInputStream();
            System.out.println(mensa+ "\nres1"+LibreriaSockets.escribirMensaje(os, mensa));
//            Usuario x = ComunicationJsonParser.importUser(LibreriaSockets.leerMensaje(clienteSocket));
//            System.out.println("x" +x);
//            System.out.println("Mensaje enviado");
//            SQL_Usuario.añadirUsuario(x);
            
            do{
               
                //LibreriaSockets.escribirMensaje(clienteSocket, "adfasdfs");
                String json = LibreriaSockets.leerMensaje(is);
               
                if (json != null) {
                   
                    System.out.println("recibido"+json);
                    chatcommon.json.ComunicationJsonParser.Procesar(json);}
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientReceive.class.getName()).log(Level.SEVERE, null, ex);
                }
                
               
                
                
                
            }while(continuar);
            
            
            
            System.out.println("Cerrando el socket cliente");
            
            clienteSocket.close();
            
            System.out.println("Terminado");
        } catch (IOException ex) {
            Logger.getLogger(ClientSend.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            
            
            
           
        }

}
