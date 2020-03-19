/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcommon.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author kazuo
 */
public class Chat {
    //alias
   private static String alias_tabla = "Chat";
   private static String alias_idChat = "idChat";
    private static String alias_nombre = "nombre";
    private UUID IdChat;
    private String nombre;
    private ArrayList<Usuario> usuarios;

    public static String getAlias_tabla() {
        return alias_tabla;
    }

    public static void setAlias_tabla(String alias_tabla) {
        Chat.alias_tabla = alias_tabla;
    }

    public static String getAlias_idChat() {
        return alias_idChat;
    }

    public static void setAlias_idChat(String alias_idChat) {
        Chat.alias_idChat = alias_idChat;
    }

    public static String getAlias_nombre() {
        return alias_nombre;
    }

    public static void setAlias_nombre(String alias_nombre) {
        Chat.alias_nombre = alias_nombre;
    }

    
    
    

    

    public Chat(String IdChat,String nombre, ArrayList<Usuario> usuarios) {
        this.IdChat = UUID.fromString(IdChat);
        this.nombre = nombre;
        this.usuarios = usuarios;
    }
    
    public Chat(UUID IdChat,String nombre, ArrayList<Usuario> usuarios) {
        this.IdChat = IdChat;
         this.nombre = nombre;
        this.usuarios = usuarios;
    }

    public Chat(String nombre,ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
         this.nombre = nombre;
        this.IdChat = UUID.randomUUID();
    }

    public Chat() {
        this("00000000-0000-0000-0000-000000000000","chatGeneral",new ArrayList<Usuario>());
    }
     public Chat(ArrayList<Usuario> usuarios) {
        this("",usuarios);
    }

    public UUID getIdChat() {
        return IdChat;
    }

    public void setIdChat(UUID IdChat) {
        this.IdChat = IdChat;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
