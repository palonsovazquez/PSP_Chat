/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;



/**
 *
 * @author usuario1
 */
public class ServerChat {
    private static Integer portSend = 5556;
    private static Integer portReceive = 5555;
    private static String ipLocal;
    
    ServerSend envios;
    ServerReceive recepcion;

    public ServerChat(String ipLocal) {
        this.ipLocal = ipLocal;
        envios = new ServerSend(ipLocal,portSend);
        recepcion = new ServerReceive(ipLocal,portReceive);
        envios.start();
        recepcion.start();
        
        
        
    }
    
   
    
    

  

    public ServerSend getEnvios() {
        return envios;
    }

 

    public ServerReceive getRecepcion() {
        return recepcion;
    }



  
    
    
}
