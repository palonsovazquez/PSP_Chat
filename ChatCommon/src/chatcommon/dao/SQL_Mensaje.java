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
import chatcommon.dao.SQLConnection;
import chatcommon.model.Chat;
import chatcommon.model.Chat;
import chatcommon.model.Mensaje;
import chatcommon.model.Mensaje;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;



/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 *
 *
 *
 */
public class SQL_Mensaje {
   
   
    /**
     * Metodo para agregar una mensaje a la tabla mensajes
     *
     * @param mensaje recive una mensaje
     * @return devuelve un true si lo ha hecho correctamente
     */
    public static boolean añadirMensaje(Mensaje mensaje) {
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("INSERT INTO " + Mensaje.getAlias_tabla() + " ( " + Mensaje.getAlias_idMensaje()+ "," + Mensaje.getAlias_idUsuario()+"," + Mensaje.getAlias_idChat()+"," + Mensaje.getAlias_texto()+"," + Mensaje.getAlias_Fecha()+ ")VALUES(?,?,?,?,?);");

            preSt.setString(1, mensaje.getIdMensaje().toString());
            preSt.setString(2, mensaje.getEmisor().getIdUsuario().toString());
            preSt.setString(3, mensaje.getReceptor().getIdChat().toString());
            preSt.setString(4, mensaje.getTexto());
            preSt.setLong(5, mensaje.getFecha().getTimeInMillis()); 
            //System.out.println();
           
    
            
      

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
     * Metodo para eliminar de la base de datos un mensaje
     *
     * @param mensaje
     * @return un true si lo ha hecho correctamente
     */
    public static boolean deleteMensaje(Mensaje mensaje) {

        // falta gestionar las etiquetas asociadas a esta mensaje en etiquetasDeMensajes para que tambien las elimine. 
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("DELETE FROM  " + Mensaje.getAlias_tabla() + " WHERE " + Mensaje.getAlias_idMensaje() + " = ?");

            preSt.setString(1, mensaje.getIdMensaje().toString());
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
     * Metodo que devuelve las Mensajes que estan asociadas con las etiquetas de
     * la lista que se le envian, ademas de filtrar por el nombre de la mensaje.
     *
     * @param BusquedaNombre en el programa por ahora no se usa nunca.
     * @param arrListEtiquetas
     * @return
     */
    public static ArrayList<Mensaje> getMensajesDeChat(Chat chat, Boolean orderbyFechaAsc) {
        ArrayList<Mensaje> auxArrlist = new ArrayList<Mensaje>();
        Mensaje etiqaux;
        // consulta base
        try {
            Connection connection = SQLConnection.getInstance().getConexion();
            String auxConsulta = "SELECT " + Mensaje.getAlias_idMensaje() + "," + Mensaje.getAlias_idUsuario()
                    + "," + Mensaje.getAlias_idChat()+ "," + Mensaje.getAlias_texto() +"," + Mensaje.getAlias_Fecha() + " FROM "
                    + Mensaje.getAlias_tabla() + " WHERE " + Mensaje.getAlias_idChat() + " = ? ORDER BY "+ Mensaje.getAlias_Fecha() +" "+ ((orderbyFechaAsc)?"ASC":"DESC")+";";
         
       
            PreparedStatement preSt = connection.prepareStatement(auxConsulta);
            preSt.setString(1, chat.getIdChat().toString());

            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {

                String idMensaje = rs.getString(Mensaje.getAlias_idMensaje());
                String idusuario = rs.getString(Mensaje.getAlias_idUsuario());
                String idchat = rs.getString(Mensaje.getAlias_idChat());
                String texto = rs.getString(Mensaje.getAlias_texto());
               GregorianCalendar fecha =new GregorianCalendar();
               fecha.setTimeInMillis(rs.getLong(Mensaje.getAlias_Fecha()));
                 
                
                etiqaux = new Mensaje(idMensaje,idusuario, idchat, texto,fecha);
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


public static Mensaje getMensaje(String idMensaje) {
        
        Mensaje aux = null;
        try {
            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("SELECT " + Mensaje.getAlias_idMensaje() + "," + Mensaje.getAlias_idUsuario()+ "," + Mensaje.getAlias_idChat()+ "," + Mensaje.getAlias_texto()+ "," + Mensaje.getAlias_Fecha()+ " FROM " + Mensaje.getAlias_tabla() + " WHERE " + Mensaje.getAlias_idMensaje()+ "= ? ");
            preSt.setString(1,idMensaje );

            //System.out.println("prueba rs = " + preSt.toString());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {

                String idEtiqueta = rs.getString(Mensaje.getAlias_idMensaje());
                String idusuario = rs.getString(Mensaje.getAlias_idUsuario());
                String idchat = rs.getString(Mensaje.getAlias_idChat());
                String texto = rs.getString(Mensaje.getAlias_texto());
                 Date fecha = rs.getDate(Mensaje.getAlias_Fecha());

                aux = new Mensaje(idEtiqueta,idusuario, idchat, texto,fecha);
               

            }
            rs.close();
            preSt.close();
            connection.close();

        } catch (SQLException ex) {
            //System.out.println(ex);

        }
        return aux;

    }







}
