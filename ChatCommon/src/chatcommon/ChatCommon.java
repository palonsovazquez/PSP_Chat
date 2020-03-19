/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcommon;

import chatcommon.dao.SQLConnection;
import chatcommon.dao.SQL_Chat;
import chatcommon.dao.SQL_Usuario;
import chatcommon.model.Chat;
import chatcommon.model.Mensaje;
import chatcommon.model.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author kazuo
 */
public class ChatCommon {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        //  SQLConnection.getInstance().getConexion();
//         for(Usuario us:SQL_Usuario.getUsuarios()){
//        us.toString();
//        
//        }
//        System.out.println(socketLibraries.LibreriaSockets.preparar(30, 32));
//        Calendar x = GregorianCalendar.getInstance();
//        System.out.println(x.getTime());
//        System.out.println(x.getTimeInMillis());
//        x.setTimeInMillis(287789224l);
//        System.out.println(x.getTime());
//        Mensaje mensaje = new Mensaje(new Usuario(), new Chat(), "dafsadfasdf", GregorianCalendar.getInstance());
        Mensaje mensaje = new Mensaje(new Usuario(), new Chat(), "dafsadfasdf", GregorianCalendar.getInstance());
        ArrayList<Mensaje> mensajes = new ArrayList<Mensaje>();
        mensajes.add(mensaje);
        SQL_Chat.getChats();
        String json = chatcommon.json.ComunicationJsonParser.generarJson(null, SQL_Chat.getChats(), mensajes);

        chatcommon.json.ComunicationJsonParser.Procesar(json);
//        Usuario x = new Usuario("pedro",0);
//        String s = chatcommon.json.ComunicationJsonParser.exportUsuario(x);
//        System.out.println("s= "+s);
//        Usuario y = chatcommon.json.ComunicationJsonParser.importUser(s);
//        System.out.println("y="+ y);
    }

}
