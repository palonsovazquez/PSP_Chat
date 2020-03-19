/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcommon.model;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;
import chatcommon.dao.SQL_Chat;
import chatcommon.dao.SQL_ChatsUsuarios;
import chatcommon.dao.SQL_Usuario;

/**
 *
 * @author kazuo
 */
public class Mensaje {
        private static String alias_tabla = "Mensaje";
    private static String alias_idMensaje = "idMensaje";
    private static String alias_idUsuario = "idUsuario";
    private static String alias_idChat = "idChat";
    private static String alias_texto = "texto";
    private static String alias_Fecha = "fecha";
    
    private UUID idMensaje;
    private Usuario emisor;
    private Chat receptor;
    private String texto;
    private Calendar fecha;
    public static Calendar sqlDatetoCalendar(Date fecha){
    Calendar x = GregorianCalendar.getInstance();
        x.setTimeInMillis(fecha.getTime());
        return x;
    
    
    }
    public static Calendar utilDatetoCalendar(java.util.Date fecha){
    Calendar x = GregorianCalendar.getInstance();
        x.setTimeInMillis(fecha.getTime());
        return x;
    
    
    }
    
    public Mensaje(String idMensaje, String emisor, String receptor, String texto, java.util.Date fecha) {
               
        this(idMensaje,emisor,receptor,texto,utilDatetoCalendar(fecha));
        
    }
    
    
    public Mensaje(String idMensaje, String emisor, String receptor, String texto, java.sql.Date fecha) {
               
        this(idMensaje,emisor,receptor,texto,sqlDatetoCalendar(fecha));
        
    }
     public Mensaje(String idMensaje, String emisor, String receptor, String texto, Calendar fecha) {
        this.idMensaje = UUID.fromString(idMensaje);
        this.emisor = SQL_Usuario.getUsuario(emisor);
        this.receptor = SQL_Chat.getChat(receptor);
        this.texto = texto;
       
        this.fecha = fecha;
    }
    
    public Mensaje(String idMensaje, Usuario emisor, Chat receptor, String texto, Calendar fecha) {
        this.idMensaje = UUID.fromString(idMensaje);
        this.emisor = emisor;
        this.receptor = receptor;
        this.texto = texto;
        this.fecha = fecha;
    }
    
    
    public Mensaje(UUID idMensaje, Usuario emisor, Chat receptor, String texto, Calendar fecha) {
        this.idMensaje = idMensaje;
        this.emisor = emisor;
        this.receptor = receptor;
        this.texto = texto;
        this.fecha = fecha;
    }

    public Mensaje(Usuario emisor, Chat receptor, String texto, Calendar fecha) {
       
        this.emisor = emisor;
        this.receptor = receptor;
        this.texto = texto;
        this.fecha = fecha;
         this.idMensaje = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "Mensaje{" + "idMensaje=" + idMensaje + ", emisor=" + emisor + ", receptor=" + receptor + ", texto=" + texto + ", fecha=" + fecha + '}';
    }

    public static String getAlias_tabla() {
        return alias_tabla;
    }

    public static void setAlias_tabla(String alias_tabla) {
        Mensaje.alias_tabla = alias_tabla;
    }

    public static String getAlias_idMensaje() {
        return alias_idMensaje;
    }

    public static void setAlias_idMensaje(String alias_idMensaje) {
        Mensaje.alias_idMensaje = alias_idMensaje;
    }

    public static String getAlias_idUsuario() {
        return alias_idUsuario;
    }

    public static void setAlias_idUsuario(String alias_idUsuario) {
        Mensaje.alias_idUsuario = alias_idUsuario;
    }

    public static String getAlias_idChat() {
        return alias_idChat;
    }

    public static void setAlias_idChat(String alias_idChat) {
        Mensaje.alias_idChat = alias_idChat;
    }

    public static String getAlias_texto() {
        return alias_texto;
    }

    public static void setAlias_texto(String alias_texto) {
        Mensaje.alias_texto = alias_texto;
    }

    public static String getAlias_Fecha() {
        return alias_Fecha;
    }

    public static void setAlias_Fecha(String alias_Fecha) {
        Mensaje.alias_Fecha = alias_Fecha;
    }

    public UUID getIdMensaje() {
        return idMensaje;
    }

    public void setIdMensaje(UUID idMensaje) {
        this.idMensaje = idMensaje;
    }

    public Usuario getEmisor() {
        return emisor;
    }

    public void setEmisor(Usuario emisor) {
        this.emisor = emisor;
    }

    public Chat getReceptor() {
        return receptor;
    }

    public void setReceptor(Chat receptor) {
        this.receptor = receptor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    
    
    
    
}
