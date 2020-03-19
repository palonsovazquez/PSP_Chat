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
public class serverTrheatSend extends Thread {
     private  ArrayList<String> mensajesPendientes = new ArrayList<>();
    Socket newSocket;
    OutputStream os;
    public serverTrheatSend(Socket newSocket, OutputStream os) throws IOException {
        this.newSocket = newSocket;
                         this.os = os;
          

    }
 public synchronized void getLastMessaje(){
 if(mensajesPendientes.size() != 0){
                   
                        System.out.println("reenviado"+mensajesPendientes.get(0));
                        LibreriaSockets.escribirMensaje(os,mensajesPendientes.get(0));
                        
                        mensajesPendientes.remove(0);
                        
                    
            
                }
 
 }
    public synchronized void addText(String json){
        
        mensajesPendientes.add(json);
        System.out.println("-+-+"+mensajesPendientes.get(0));
        
    }
    

    @Override
    public void run() {
        do {
            getLastMessaje();
                
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(serverTrheatSend.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);

    }

}
