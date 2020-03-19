/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proychat.ComCliente;

import chatcommon.json.ComunicationJsonParser;
import socketLibraries.LibreriaSockets;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import chatcommon.dao.SQL_Usuario;
import chatcommon.model.Usuario;

/**
 *
 * @author usuario1
 */
public  class ClientSend extends Thread {

    public Integer puerto;
    public String ip;
    public Boolean continuar = true;
    public Usuario user;
    private ArrayList<String> mensajesPendientes = new ArrayList<String>();
    private InputStream is;
    private OutputStream os;

    public ClientSend(Usuario user, Integer puerto, String ip) {
        this.user = user;
        this.puerto = puerto;
        this.ip = ip;
        

    }
    
    
    
public synchronized void añadirMensaje(String json){
    
        mensajesPendientes.add(json);
        //System.out.println("mens:"+mensajesPendientes.get(mensajesPendientes.size()-1));
        
        
    }
    public void run() {
        try {

            //System.out.println("Creando socket cliente");
            Socket clienteSocket = new Socket();
            //System.out.println("Estableciendo la conexi�n"+ip+":"+puerto);

            InetSocketAddress addr = new InetSocketAddress(ip, puerto);
            //System.out.println(addr);
            clienteSocket.connect(addr);

            //System.out.println("Enviando id");

            String mensa = user.getIdUsuario().toString();
            //System.out.println("id = "+mensa);
            os = clienteSocket.getOutputStream();
            is = clienteSocket.getInputStream();

            //System.out.println(mensa + "\nres0" + LibreriaSockets.escribirMensaje(os, mensa));
            //Usuario x = ComunicationJsonParser.importUser(LibreriaSockets.leerMensaje(clienteSocket));
//            //System.out.println("x" + x);
//            //System.out.println("Mensaje enviado");
//            SQL_Usuario.añadirUsuario(x);
                LibreriaSockets.escribirMensaje(os,mensa);
            do {
                
                if(mensajesPendientes.size() != 0){
                  //System.out.println("mens2:"+mensajesPendientes.get(0));  
                        
                LibreriaSockets.escribirMensaje(os,mensajesPendientes.get(0) );
                if(mensajesPendientes.get(0).compareTo("bye")==0){
                continuar= false;
                }    
                mensajesPendientes.remove(0);
                
                }
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientSend.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                

            } while (continuar);

            //System.out.println("Cerrando el socket cliente");

            clienteSocket.close();

            //System.out.println("Terminado");
        } catch (IOException ex) {
            Logger.getLogger(ClientSend.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}



