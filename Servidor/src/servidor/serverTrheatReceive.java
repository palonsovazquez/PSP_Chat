/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import socketLibraries.LibreriaSockets;



/**
 *
 * @author usuario1
 */
public class serverTrheatReceive extends Thread{
Socket newSocket ;
InputStream is;
    public serverTrheatReceive(Socket newSocket, InputStream is) throws IOException  {
        this.newSocket = newSocket;
                        
            this.is = is;   
        
        
        
        
    }

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        
        
        do {
                    String mn = LibreriaSockets.leerMensaje(is);
                   if(mn != null){
                    System.out.println("mensaje recibido"+mn);
                    
//                      ArrayList<Usuario> arrUsua = SQL_Usuario.getUsuarios();
//                      ArrayList<Chat> arrChat = SQL_Chat.getChats();
//                     ArrayList<Mensaje> arrMens = SQL_Mensaje.getMensajesDeChat(new Chat(), true);
                        
                    ServerSend.broadcast(mn);}
            try {
                sleep(1000);
                
//                       
            } catch (InterruptedException ex) {
                Logger.getLogger(serverTrheatReceive.class.getName()).log(Level.SEVERE, null, ex);
            }

                } while (true);
        
        
    }
    
    
    
}
