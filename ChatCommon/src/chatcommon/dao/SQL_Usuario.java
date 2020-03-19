package chatcommon.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import chatcommon.model.Usuario;
import chatcommon.model.Usuario;

/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 *
 *
 *
 */
public class SQL_Usuario {

    /**
     * Metodo que añade una etiqueta a la tabla etiqueta
     *
     * @param usuario recive una etiqueta
     * @return devuelve true si ha sido correcto.
     */
    public static boolean añadirUsuario(Usuario usuario) {
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();
            String stateString ="INSERT INTO " + Usuario.getAlias_tabla() + " ( " + Usuario.getAlias_idUsuario() + "," + Usuario.getAlias_nombre() + "," + Usuario.getAlias_secLevel() + ") VALUES (?,?,?);";
            //System.out.println(stateString);
            PreparedStatement preSt = connection.prepareStatement(stateString);

            preSt.setString(1, usuario.getIdUsuario().toString());
            preSt.setString(2, usuario.getNombre());
            preSt.setInt(3, usuario.getSecurityLevel());

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

    public static boolean updateUsuario(Usuario usuario) {
        boolean hecho = false;
        try {

            Connection connection = SQLConnection.getInstance().getConexion();
            String staString ="UPDATE " + Usuario.getAlias_tabla() + " SET   " + Usuario.getAlias_nombre() + " = ? , " + Usuario.getAlias_secLevel()+ " = ?  " + " WHERE " + Usuario.getAlias_idUsuario() + " = ?";
            //System.out.println(staString);
            PreparedStatement preSt = connection.prepareStatement(staString);
              preSt.setString(1, usuario.getNombre());
            preSt.setInt(2, usuario.getSecurityLevel());
          
            preSt.setString(3, usuario.getIdUsuario().toString());

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

///**
// * Metodo para conseguir las etiquetas asociadas a una tarjeta concreta, esta relacion se encuentra en la tabla etiquetasDeTarjetas.
// * @param tarjeta recibe una tarjeta de la cual extrae el id.
// * @return devuelve un arraylist de etiquetas.
// */
//    public static ArrayList<Usuario> getUsuariosdeTarjeta(Tarjeta tarjeta) {
//        ArrayList<Usuario> auxArrlist = new ArrayList<Usuario>();
//        
//        Usuario etiqaux;
//        try {
//            Connection connection = SQLConnection.getInstance().getConexion();
//
//            PreparedStatement preSt = connection.prepareStatement("SELECT " + Usuario.getAlias_idUsuario() + "," 
//                    + Usuario.getAlias_nombre() + "," + Usuario.getAlias_secLevel() + " FROM " + Usuario.getAlias_tabla() +
//                    " WHERE " + Usuario.getAlias_idUsuario() + " IN (SELECT " + Usuario.getAlias_idUsuario() + " FROM etiquetasDeTarjetas WHERE " +
//                    Tarjeta.getAlias_idTarjeta() + " = ?)");
//            preSt.setInt(1, tarjeta.getIdTarjeta());                                                                                                
//
//            //System.out.println("prueba rs = " + preSt.toString());
//            ResultSet rs = preSt.executeQuery();
//
//            while (rs.next()) {
//
//                Integer idUsuario = rs.getInt(Usuario.getAlias_idUsuario());
//                String nombre = rs.getString(Usuario.getAlias_nombre());
//                String descripcion = rs.getString(Usuario.getAlias_secLevel());
//
//                etiqaux = new Usuario(idUsuario, nombre, descripcion);
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
     * Metodo que devuelve las etiquetas que coinciden con una busqueda
     *
     * @param BusquedaNombre
     * @return
     */
    public static ArrayList<Usuario> getUsuariosChat(Chat chat) {
        ArrayList<Usuario> auxArrlist = new ArrayList<Usuario>();
        Usuario etiqaux;
        try {
            Connection connection = SQLConnection.getInstance().getConexion();

            PreparedStatement preSt = connection.prepareStatement("SELECT " + Usuario.getAlias_idUsuario() + "," + Usuario.getAlias_nombre() + "," + Usuario.getAlias_secLevel() + " FROM " + Usuario.getAlias_tabla() + " WHERE " + Chat.getAlias_idChat() + "= ? ");
            preSt.setString(1, chat.getIdChat().toString());

            //System.out.println("prueba rs = " + preSt.toString());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {

                String idUsuario = rs.getString(Usuario.getAlias_idUsuario());
                String nombre = rs.getString(Usuario.getAlias_nombre());
                Integer secLevel = rs.getInt(Usuario.getAlias_secLevel());

                etiqaux = new Usuario(idUsuario, nombre, secLevel);
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

    public static Usuario getUsuario(String idUsuario) {

        Usuario etiqaux = null;
        try {
            Connection connection = SQLConnection.getInstance().getConexion();
            String stateStrn ="SELECT " + Usuario.getAlias_idUsuario() + " ," + Usuario.getAlias_nombre() + "," + Usuario.getAlias_secLevel() + " FROM " + Usuario.getAlias_tabla() + " WHERE " + Usuario.getAlias_idUsuario() + " = ? ";
            //System.out.println(stateStrn);
            PreparedStatement preSt = connection.prepareStatement(stateStrn);
            preSt.setString(1, idUsuario);

            ////System.out.println("prueba rs = " + preSt.toString());
            ResultSet rs = preSt.executeQuery();

            while (rs.next()) {

                String idUsu = rs.getString(Usuario.getAlias_idUsuario());
                String nombre = rs.getString(Usuario.getAlias_nombre());
                Integer secLevel = rs.getInt(Usuario.getAlias_secLevel());

                etiqaux = new Usuario(idUsu, nombre, secLevel);

            }
            rs.close();
            preSt.close();
            connection.close();

        } catch (SQLException ex) {
            //System.out.println(ex);

        }
        return etiqaux;

    }

    public static ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> auxArrlist = new ArrayList<Usuario>();

        Usuario etiqaux;
        try {
            Connection connection = SQLConnection.getInstance().getConexion();
            String prestatexto = "SELECT " + Usuario.getAlias_idUsuario() + ","
                    + Usuario.getAlias_nombre() + "," + Usuario.getAlias_secLevel() + " FROM " + Usuario.getAlias_tabla()
                    + ";";
            //System.out.println(prestatexto);
            //PreparedStatement preSt = connection.prepareStatement(prestatexto);
            Statement sta = connection.createStatement();

            //System.out.println("prueba rs = " + prestatexto);
            ResultSet rs = sta.executeQuery(prestatexto);

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
            sta.close();
            connection.close();

        } catch (SQLException ex) {
            //System.out.println(ex);

        }
        return auxArrlist;

    }

}
