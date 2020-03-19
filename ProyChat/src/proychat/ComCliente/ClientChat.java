/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proychat.ComCliente;

import chatcommon.dao.SQL_Chat;
import chatcommon.dao.SQL_Usuario;
import chatcommon.json.ComunicationJsonParser;
import chatcommon.model.Mensaje;
import chatcommon.model.Usuario;
import java.util.ArrayList;

/**
 *
 * @author usuario1
 */
public class ClientChat {

    private Integer portSend = 5555;
    private Integer portReceive = 5556;
    private String ip;
    private Usuario user;
    ClientSend envios;
    ClientReceive recepcion;

    public ClientChat(Usuario user, String ip) {
        this.ip = ip;
        this.user = user;
        envios = new ClientSend(user, portSend, ip);
        recepcion = new ClientReceive(user, portReceive, ip);
        envios.start();
        recepcion.start();

    }

    public void agregarMensaje(String json) {

        envios.añadirMensaje(json);

    }

}
