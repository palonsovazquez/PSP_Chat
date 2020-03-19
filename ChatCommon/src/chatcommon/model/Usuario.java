/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcommon.model;

import java.util.UUID;

/**
 *
 * @author kazuo
 */
public class Usuario {

    //alias
    private static String alias_tabla = "Usuario";
    private static String alias_idUsuario = "idUsuario";
    private static String alias_nombre = "nombre";
    private static String alias_secLevel = "secLevel";
    private UUID IdUsuario;
    private String nombre;
    private Integer securityLevel;

    public Usuario(String IdUsuario, String nombre, Integer securityLevel) {
        this.IdUsuario = UUID.fromString(IdUsuario);
        this.nombre = nombre;
        this.securityLevel = securityLevel;
    }

    public Usuario(String nombre, Integer securityLevel) {

        this.nombre = nombre;
        this.securityLevel = securityLevel;
        this.IdUsuario = UUID.randomUUID();
    }

    public Usuario() {

        this("87d5564e-51e1-4fb2-ab25-760818521ea6","paco", new Integer(0));
    }

    public static String getAlias_idUsuario() {
        return alias_idUsuario;
    }

    public static void setAlias_idUsuario(String alias_idUsuario) {
        Usuario.alias_idUsuario = alias_idUsuario;
    }

    public static String getAlias_tabla() {
        return alias_tabla;
    }

    public static void setAlias_tabla(String alias_tabla) {
        Usuario.alias_tabla = alias_tabla;
    }

    public static String getAlias_nombre() {
        return alias_nombre;
    }

    public static void setAlias_nombre(String alias_nombre) {
        Usuario.alias_nombre = alias_nombre;
    }

    public static String getAlias_secLevel() {
        return alias_secLevel;
    }

    public static void setAlias_secLevel(String alias_secLevel) {
        Usuario.alias_secLevel = alias_secLevel;
    }

    public UUID getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(UUID IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(Integer securityLevel) {
        this.securityLevel = securityLevel;
    }

    @Override
    public String toString() {
        return  nombre ;
    }

}
