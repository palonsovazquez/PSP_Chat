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
    * Este metodo devuelve la mensaje con el id mas alto, que al tener configurada la tabla como autoincrement tambien es la ultima mensaje introducida.
    * @param Id
    * @return 
    */
    
    
    
    
    
    
    
    
    
    
    
    
    
//     public static Mensaje getUltimaMensaje() {
//        ArrayList<Mensaje> auxArrlist = new ArrayList<Mensaje>();
//        Mensaje etiqaux;
//        // consulta base
//        try{
//            Connection connection = SQLConnection.getInstance().getConexion();
//            String auxConsulta = "SELECT " + Mensaje.getAlias_idMensaje() + "," + Mensaje.getAlias_nombre()
//                    + "," + Mensaje.getAlias_ejemplo() + "," + Mensaje.getAlias_descripcion() + " FROM "
//                    + Mensaje.getAlias_tabla() + " WHERE "+ Mensaje.getAlias_idMensaje()+ " = (SELECT MAX( "+Mensaje.getAlias_idMensaje()+ " ) FROM "+Mensaje.getAlias_tabla()+" );";
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
//                Integer idEtiqueta = rs.getInt(Mensaje.getAlias_idMensaje());
//                String nombre = rs.getString(Mensaje.getAlias_nombre());
//                String ejemplo = rs.getString(Mensaje.getAlias_ejemplo());
//                String descripcion = rs.getString(Mensaje.getAlias_descripcion());
//
//                etiqaux = new Mensaje(idEtiqueta, nombre, ejemplo, descripcion);
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
//__-----------------------------------------------------------------------------------------------------------------------------------------------------
//    /**
//     * Metodo para actualizar una mensaje a la tabla mensajes
//     *
//     * @param mensaje recive una mensaje
//     * @return un true si lo ha hecho correctamente
//     */
//    public static boolean updateNombreMensaje(Mensaje mensaje) {
//        boolean hecho = false;
//        try {
//
//            Connection connection = SQLConnection.getInstance().getConexion();
//
//            PreparedStatement preSt = connection.prepareStatement("UPDATE " + Mensaje.getAlias_tabla() + " SET   " + Mensaje.getAlias_nombre() + " = ? WHERE " + Mensaje.getAlias_idMensaje() + " = ?");
//
//            preSt.setString(1, mensaje.getIdMensaje().toString());
//            preSt.setString(2, mensaje.getNombre());
//           
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
//            PreparedStatement preSt = connection.prepareStatement("INSERT INTO " + Etiqueta.getAlias_tabla() + " ( " + Etiqueta.getAlias_nombre() + "," + Mensaje.getAlias_descripcion() + ")VALUES(?,?);");
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
            // si la lista no es nula añado un limitante mas a la consulta para que filtre por las etiquetas.
//            if (arrListEtiquetas != null) {
//                for (int i = 0; i < arrListEtiquetas.size(); i++) {
//                    if (i == 0) {
//                        auxConsulta = auxConsulta + " AND ( " + Mensaje.getAlias_idMensaje() + " IN (SELECT " + Mensaje.getAlias_idMensaje()
//                                + " FROM etiquetasDeMensajes  WHERE " + Etiqueta.getAlias_idEtiqueta() + "  = ? ";
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
            //System.out.println("auxC" + auxConsulta);
            PreparedStatement preSt = connection.prepareStatement(auxConsulta);
            preSt.setString(1, chat.getIdChat().toString());
//            if (arrListEtiquetas != null) {
//                for (int i = 0; i < arrListEtiquetas.size(); i++) {
//                    preSt.setInt(i + 2, arrListEtiquetas.get(i).getIdEtiqueta());
//
//                }
//            }

            //System.out.println("prueba rs = " + preSt);
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

    /**
     * Metodo que devuelve las mensajes que coinciden con una buasqueda
     *
     * @param BusquedaNombre
     * @return
     */
//    public static ArrayList<Mensaje> getMensajes(String BusquedaNombre) {
//        return getMensajesFiltradas(BusquedaNombre, null);
//
//    }
//    public static ArrayList<Mensaje> getMensajes() {
//        return getMensajes("");
//
//    }
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
