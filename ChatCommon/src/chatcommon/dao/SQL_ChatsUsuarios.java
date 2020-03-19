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
public class SQL_ChatsUsuarios {

    /**
     * Metodo para eliminar de la base de datos una asociacion de Chat y Usuario
     *
     * @param chat
     * @param usuario
     *
     * @return un true si lo ha hecho correctamente
     */
    public static boolean deleteChatDeUsuario(Chat chat, Usuario usuario) {

        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();
            String auxString = "DELETE FROM  UsuariosChat WHERE " + Usuario.getAlias_idUsuario() + " = ? AND " + Chat.getAlias_idChat() + " = ? ;";
            //System.out.println("auxe-" + auxString);
            PreparedStatement preSt = connection.prepareStatement(auxString);

            preSt.setString(1, usuario.getIdUsuario().toString());
            preSt.setString(2, chat.getIdChat().toString());
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

    /**
     * Metodo que añade una chat a la tabla chat
     *
     * @param chat recive una chat
     * @return devuelve true si ha sido correcto.
     */
    public static boolean añadirUsuariosdeChat(Chat chat) {
        for (Usuario us : chat.getUsuarios()) {
            SQL_Usuario.añadirUsuario(us);
            añadirChatAUsuario(us, chat);

        }

        return true;

    }

    public static boolean añadirChatAUsuario(Usuario usuario, Chat chat) {
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("INSERT INTO UsuariosChat ( " + Chat.getAlias_idChat() + "," + Usuario.getAlias_idUsuario() + ")VALUES(?,?);");
            preSt.setString(1, chat.getIdChat().toString());
            preSt.setString(2, usuario.getIdUsuario().toString());

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

    /**
     * Metodo para conseguir las chats asociadas a una usuario concreta, esta
     * relacion se encuentra en la tabla UsuariosChat.
     *
     * @param usuario recibe una usuario de la cual extrae el id.
     * @return devuelve un arraylist de chats.
     */
    public static ArrayList<Usuario> getUsuariosChat(String idchat) {

        return getUsuariosChat(new Chat(idchat, "", null));
    }

    public static Boolean updateChats() {

        return true;
    }

    public static ArrayList<Usuario> getUsuariosChat(Chat chat) {
        ArrayList<Usuario> auxArrlist = new ArrayList<Usuario>();

        Usuario etiqaux;
        try {
            Connection connection = SQLConnection.getInstance().getConexion();
            String prestatexto = "SELECT " + Usuario.getAlias_idUsuario() + ","
                    + Usuario.getAlias_nombre() + "," + Usuario.getAlias_secLevel() + " FROM " + Usuario.getAlias_tabla()
                    + " WHERE " + Usuario.getAlias_idUsuario() + " IN (SELECT " + Usuario.getAlias_idUsuario() + " FROM UsuariosChat WHERE "
                    + Chat.getAlias_idChat() + " = ? );";
            //System.out.println(prestatexto);
            PreparedStatement preSt = connection.prepareStatement(prestatexto);
            //System.out.println(chat.getIdChat().toString());
            preSt.setString(1, chat.getIdChat().toString());

            //System.out.println("prueba rs = " + preSt.toString());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {
                //System.out.println("------");
                String idUsuario = rs.getString(Usuario.getAlias_idUsuario());
                String nombre = rs.getString(Usuario.getAlias_nombre());
                Integer secLevel = rs.getInt(Usuario.getAlias_secLevel());

                etiqaux = new Usuario(idUsuario, nombre, secLevel);
                //System.out.println(etiqaux);
                auxArrlist.add(etiqaux);

            }
            rs.close();
            preSt.close();
            connection.close();

        } catch (SQLException ex) {
            //System.out.println(ex);

        }
        return auxArrlist;

    }

    public static ArrayList<Chat> getChatsdeUsuarios(Usuario usuario) {
        ArrayList<Chat> auxArrlist = new ArrayList<Chat>();

        Chat chataux = null;
        try {
            Connection connection = SQLConnection.getInstance().getConexion();
            String prestatexto = "SELECT " + Chat.getAlias_idChat() + ","
                    + Chat.getAlias_nombre() + " FROM " + Chat.getAlias_tabla()
                    + " WHERE " + Usuario.getAlias_idUsuario() + " IN (SELECT " + Usuario.getAlias_idUsuario() + " FROM UsuariosChat WHERE "
                    + Usuario.getAlias_idUsuario() + " = ? );";
            //System.out.println(prestatexto);
            PreparedStatement preSt = connection.prepareStatement(prestatexto);
            //System.out.println(usuario.getIdUsuario().toString());
            preSt.setString(1, usuario.getIdUsuario().toString());

            //System.out.println("prueba rs = " + preSt.toString());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {
                //System.out.println("------");
                String idChat = rs.getString(Chat.getAlias_idChat());
                String nombre = rs.getString(Chat.getAlias_nombre());

                chataux = new Chat(idChat, getUsuariosChat(chataux));
                //System.out.println(chataux);
                auxArrlist.add(chataux);

            }
            rs.close();
            preSt.close();
            connection.close();

        } catch (SQLException ex) {
            //System.out.println(ex);

        }
        return auxArrlist;

    }

}
