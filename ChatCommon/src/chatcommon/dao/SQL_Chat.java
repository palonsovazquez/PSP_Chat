/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcommon.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import chatcommon.model.Chat;
import chatcommon.model.Chat;
import chatcommon.model.Usuario;
import chatcommon.model.Usuario;

/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 *
 *
 *
 */
public class SQL_Chat {

    /**
     * Este metodo devuelve la chat con el id mas alto, que al tener configurada
     * la tabla como autoincrement tambien es la ultima chat introducida.
     *
     * @param Id
     * @return
     */
//     public static Chat getUltimaChat() {
//        ArrayList<Chat> auxArrlist = new ArrayList<Chat>();
//        Chat etiqaux;
//        // consulta base
//        try{
//            Connection connection = SQLConnection.getInstance().getConexion();
//            String auxConsulta = "SELECT " + Chat.getAlias_idChat() + "," + Chat.getAlias_nombre()
//                    + "," + Chat.getAlias_ejemplo() + "," + Chat.getAlias_descripcion() + " FROM "
//                    + Chat.getAlias_tabla() + " WHERE "+ Chat.getAlias_idChat()+ " = (SELECT MAX( "+Chat.getAlias_idChat()+ " ) FROM "+Chat.getAlias_tabla()+" );";
//            
//            
//              
//
//                
//        
//            Statement sta = connection.createStatement();
//       
//
//          
//            ResultSet rs = sta.executeQuery(auxConsulta);
//
//            while (rs.next()) {
//
//                Integer idEtiqueta = rs.getInt(Chat.getAlias_idChat());
//                String nombre = rs.getString(Chat.getAlias_nombre());
//                String ejemplo = rs.getString(Chat.getAlias_ejemplo());
//                String descripcion = rs.getString(Chat.getAlias_descripcion());
//
//                etiqaux = new Chat(idEtiqueta, nombre, ejemplo, descripcion);
//                auxArrlist.add(etiqaux);
//
//            }
//            rs.close();
//            sta.close();
//            connection.close();
//
//        } catch (SQLException ex) {
//            //System.out.println(ex);
//
//        }
//        return auxArrlist.get(0);
//
//    }
    /**
     * Metodo para agregar una chat a la tabla chats
     *
     * @param chat recive una chat
     * @return devuelve un true si lo ha hecho correctamente
     */
    public static boolean addChat(Chat chat)  {
        boolean hecho = false;
        

        try (Connection connection = SQLConnection.getInstance().getConexion()) {
            PreparedStatement preSt = connection.prepareStatement("INSERT INTO " + Chat.getAlias_tabla() + " ( " + Chat.getAlias_idChat() + "," + Chat.getAlias_nombre() + ") VALUES(?,?);");

            preSt.setString(1, chat.getIdChat().toString());
            preSt.setString(2, chat.getNombre());

           // //System.out.println("prueba rs = " + preSt.toString());
            preSt.executeUpdate();

            // //System.out.println(proy.toString());
            preSt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL_Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
            SQL_ChatsUsuarios.añadirUsuariosdeChat(chat);
            
            hecho = true;

       

        return hecho;
    }

    /**
     * Metodo para actualizar una chat a la tabla chats
     *
     * @param chat recive una chat
     * @return un true si lo ha hecho correctamente
     */
    public static boolean updateNombreChat(Chat chat) {
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("UPDATE " + Chat.getAlias_tabla() + " SET   " + Chat.getAlias_nombre() + " = ? WHERE " + Chat.getAlias_idChat() + " = ?");

           
            preSt.setString(1, chat.getNombre());
             preSt.setString(2, chat.getIdChat().toString());

            //System.out.println("prueba rs = " + preSt.toString());
            preSt.executeUpdate();

            // //System.out.println(proy.toString());
            preSt.close();
            connection.close();
            hecho = true;

        } catch (SQLException ex) {
            //System.out.println(ex);
            hecho = false;
        }

        return hecho;
    }
    
    public static boolean updateChat(Chat chat){
        
        updateNombreChat(chat);
        
        SQL_ChatsUsuarios.añadirUsuariosdeChat(chat);
        
    
    
    
    
    return true;}

    /**
     * Metodo para eliminar de la base de datos una chat
     *
     * @param chat
     * @return un true si lo ha hecho correctamente
     */
    public static boolean deleteChat(Chat chat) {

        // falta gestionar las etiquetas asociadas a esta chat en etiquetasDeChats para que tambien las elimine. 
        boolean hecho = false;
        for(Usuario user: SQL_ChatsUsuarios.getUsuariosChat(chat)){
        SQL_ChatsUsuarios.deleteChatDeUsuario(chat, user);
        
        }
        
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("DELETE FROM  " + Chat.getAlias_tabla() + " WHERE " + Chat.getAlias_idChat() + " = ?");

            preSt.setString(1, chat.getIdChat().toString());
            //System.out.println("prueba rs = " + preSt.toString());
            preSt.executeUpdate();

            preSt.close();
            connection.close();
            hecho = true;

        } catch (SQLException ex) {
            //System.out.println(ex);
            hecho = false;
        }

        return hecho;
    }

    public static Chat getChat(String idChat) {

        Chat etiqaux = null;
        try {
            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("SELECT " + Chat.getAlias_idChat() + "," + Chat.getAlias_nombre() + " FROM " + Chat.getAlias_tabla() + " WHERE " + Chat.getAlias_idChat() + "= ? ");
            preSt.setString(1, idChat);

            //System.out.println("prueba rs = " + preSt.toString());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {

                String idCh = rs.getString(Chat.getAlias_idChat());
                String nombre = rs.getString(Chat.getAlias_nombre());
                ArrayList<Usuario> arrUsuarios = SQL_ChatsUsuarios.getUsuariosChat(idCh);

                etiqaux = new Chat (idCh, nombre, arrUsuarios);

            }
            rs.close();
            preSt.close();
            connection.close();
            
            

        } catch (SQLException ex) {
            //System.out.println(ex);

        }
        return etiqaux;

    }

    /**
     * Metodo que añade una etiqueta a la tabla etiqueta
     *
     * @param etiqueta recive una etiqueta
     * @return devuelve true si ha sido correcto.
     */
//    public static boolean añadirEtiqueta(Etiqueta etiqueta) {
//        boolean hecho = false;
//        try {
//
//            Connection connection = SQLConnection.getInstance().getConexion();
//
//            PreparedStatement preSt = connection.prepareStatement("INSERT INTO " + Etiqueta.getAlias_tabla() + " ( " + Etiqueta.getAlias_nombre() + "," + Chat.getAlias_descripcion() + ")VALUES(?,?);");
//
//            preSt.setString(1, etiqueta.getNombre());
//            preSt.setString(2, etiqueta.getDescripcion());
//
//            //System.out.println("prueba rs = " + preSt.toString());
//            preSt.executeUpdate();
//
//            // //System.out.println(proy.toString());
//            preSt.close();
//            connection.close();
//            hecho = true;
//
//        } catch (SQLException ex) {
//            //System.out.println(ex);
//            hecho = false;
//        }
//
//        return hecho;
//    }
    /**
     * Metodo que devuelve las Chats que estan asociadas con las etiquetas de la
     * lista que se le envian, ademas de filtrar por el nombre de la chat.
     *
     * @param BusquedaNombre en el programa por ahora no se usa nunca.
     * @param arrListEtiquetas
     * @return
     */
//    public static ArrayList<Chat> getChatsFiltradas(String BusquedaNombre, List<Etiqueta> arrListEtiquetas) {
//        ArrayList<Chat> auxArrlist = new ArrayList<Chat>();
//        Chat etiqaux;
//        // consulta base
//        try {
//            Connection connection = SQLConnection.getInstance().getConexion();
//            String auxConsulta = "SELECT " + Chat.getAlias_idChat() + "," + Chat.getAlias_nombre()
//                    + "," + Chat.getAlias_ejemplo() + "," + Chat.getAlias_descripcion() + " FROM "
//                    + Chat.getAlias_tabla() + " WHERE " + Chat.getAlias_nombre() + " LIKE ? ";
//            // si la lista no es nula añado un limitante mas a la consulta para que filtre por las etiquetas.
//            if (arrListEtiquetas != null) {
//                for (int i = 0; i < arrListEtiquetas.size(); i++) {
//                    if (i == 0) {
//                        auxConsulta = auxConsulta + " AND ( " + Chat.getAlias_idChat() + " IN (SELECT " + Chat.getAlias_idChat()
//                                + " FROM etiquetasDeChats  WHERE " + Etiqueta.getAlias_idEtiqueta() + "  = ? ";
//                    }
//                    if (i > 0) {
//
//                        auxConsulta = auxConsulta + " OR " + Etiqueta.getAlias_idEtiqueta() + " = ? ";
//
//                    }
//                    if (i == arrListEtiquetas.size() - 1) {
//                        auxConsulta = auxConsulta + "))";
//                    }
//
//                }
//            }
//            //System.out.println("auxC" + auxConsulta);
//            PreparedStatement preSt = connection.prepareStatement(auxConsulta);
//            preSt.setString(1, "%" + BusquedaNombre + "%");
//            if (arrListEtiquetas != null) {
//                for (int i = 0; i < arrListEtiquetas.size(); i++) {
//                    preSt.setInt(i + 2, arrListEtiquetas.get(i).getIdEtiqueta());
//
//                }
//            }
//
//            //System.out.println("prueba rs = " + preSt);
//            ResultSet rs = preSt.executeQuery();
//
//            while (rs.next()) {
//
//                Integer idEtiqueta = rs.getInt(Chat.getAlias_idChat());
//                String nombre = rs.getString(Chat.getAlias_nombre());
//                String ejemplo = rs.getString(Chat.getAlias_ejemplo());
//                String descripcion = rs.getString(Chat.getAlias_descripcion());
//
//                etiqaux = new Chat(idEtiqueta, nombre, ejemplo, descripcion);
//                auxArrlist.add(etiqaux);
//
//            }
//            rs.close();
//            preSt.close();
//            connection.close();
//
//        } catch (SQLException ex) {
//            //System.out.println(ex);
//
//        }
//        return auxArrlist;
//
//    }
    /**
     * Metodo que devuelve las chats que coinciden con una buasqueda
     *
     * @param BusquedaNombre
     * @return
     */
//    public static ArrayList<Chat> getChats(String BusquedaNombre) {
//        return getChatsFiltradas(BusquedaNombre, null);
//
//    }
//    public static ArrayList<Chat> getChats() {
//        return getChats("");
//
//    }
public static ArrayList<Chat> getChats() {
        ArrayList<Chat> auxArrlist = new ArrayList<Chat>();

        Chat aux;
        try {
            Connection connection = SQLConnection.getInstance().getConexion();
            String prestatexto = "SELECT " + Chat.getAlias_idChat() + ","
                    + Chat.getAlias_nombre()  + " FROM " + Chat.getAlias_tabla()
                    + ";";
            //System.out.println(prestatexto);
            //PreparedStatement preSt = connection.prepareStatement(prestatexto);
            Statement sta = connection.createStatement();

            //System.out.println("prueba rs = " + prestatexto);
            ResultSet rs = sta.executeQuery(prestatexto);

            while (rs.next()) {
                //System.out.println("------");
                String idChat = rs.getString(Chat.getAlias_idChat());
                String nombre = rs.getString(Chat.getAlias_nombre());
               

                aux = new Chat(idChat,nombre,SQL_ChatsUsuarios.getUsuariosChat(idChat));
                //System.out.println(aux);
                auxArrlist.add(aux);

            }
            rs.close();
            sta.close();
            connection.close();

        } catch (SQLException ex) {
            //System.out.println(ex);

        }
        return auxArrlist;

    }



}
