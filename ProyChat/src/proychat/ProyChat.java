/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proychat;


import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import chatcommon.dao.SQLConnection;
import chatcommon.dao.SQL_ChatsUsuarios;
import chatcommon.model.Chat;
import chatcommon.model.Mensaje;
import chatcommon.model.Usuario;
import chatcommon.dao.SQL_Usuario;
import chatcommon.json.ComunicationJsonParser;
import interfaz.chatcliente.Login;
import proychat.ComCliente.ClientChat;
/**
 *
 * @author kazuo
 */
public class ProyChat {
public static String idUsuario = "87d5564e-51e1-4fb2-ab25-760818521ea7";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        
       // SQLConnection.getInstance().crearDefaults();
        //SQLConnection.getInstance().EjecutarSQLFile(new File("data/creacionTablas.sql"));
        
      
        new Login().setVisible(true);
        
        
//       Mensaje x = new Mensaje(new Usuario(), new Chat(), "dasfsaedfasdfasdf",GregorianCalendar.getInstance());
//        System.out.println(x);
//        Chat chat = new Chat("87d5564e-51e1-4fb2-ab25-760818521ea6",new ArrayList<Usuario>());
//        System.out.println("chat"+chat);
//        ArrayList<Usuario> s =SQL_ChatsUsuarios.getUsuariosChat(chat);
//        System.out.println("sss"+s.size());
//        for(Usuario us : s){
//           System.out.println(us);
//       
//       }
    }
    
}
