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
