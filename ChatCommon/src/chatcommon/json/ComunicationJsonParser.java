/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcommon.json;

import chatcommon.dao.SQL_Chat;
import chatcommon.dao.SQL_ChatsUsuarios;
import chatcommon.dao.SQL_Mensaje;
import chatcommon.dao.SQL_Usuario;
import chatcommon.model.Chat;
import chatcommon.model.Mensaje;
import chatcommon.model.Usuario;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author usuario1
 */
public class ComunicationJsonParser {

    public final static String ETIQUETA_MENSAJES = "messajes";
    public final static String ETIQUETA_MENSAJE = "messaje";
    public final static String ETIQUETA_MENSAJE_ID = "idMensaje";
    public final static String ETIQUETA_MENSAJE_TEXT = "text";
    public final static String ETIQUETA_MENSAJE_DATE = "date";
    public final static String ETIQUETA_MENSAJE_DATE_ANHO = "year";
    public final static String ETIQUETA_MENSAJE_DATE_MES = "month";
    public final static String ETIQUETA_MENSAJE_DATE_DIA = "day";
    public final static String ETIQUETA_MENSAJE_DATE_hora = "hour";
    public final static String ETIQUETA_MENSAJE_DATE_min = "minute";
    public final static String ETIQUETA_MENSAJE_DATE_sec = "seconds";
    public final static String ETIQUETA_USERS = "Users";
    public final static String ETIQUETA_USER = "User";
    public final static String ETIQUETA_USER_ID = "UserId";
    public final static String ETIQUETA_USER_Name = "UserName";
    public final static String ETIQUETA_USER_NEW = "newUser";
    public final static String ETIQUETA_CHATS = "Chats";
    public final static String ETIQUETA_CHAT_ID = "ChatId";
    public final static String ETIQUETA_CHAT_Name = "ChatName";
    public final static String ETIQUETA_CHAT = "new-chat";

    public static HashMap jsonToMap(String json) {

        HashMap<String, Object> retMap = new Gson().
                fromJson(json,
                        new TypeToken<HashMap<String, Object>>() {
                        }.getType());
        return retMap;
    }

    public static String exportCalendar(Calendar calendar) {
        String data = "{ \"" + ETIQUETA_MENSAJE_DATE_ANHO
                + "\" : " + calendar.get(Calendar.YEAR) + ",\""
                + ETIQUETA_MENSAJE_DATE_MES
                + "\" : " + calendar.get(Calendar.MONTH) + ",\""
                + ETIQUETA_MENSAJE_DATE_DIA
                + "\" : " + calendar.get(Calendar.DAY_OF_MONTH) + ",\""
                + ETIQUETA_MENSAJE_DATE_hora
                + "\" : " + calendar.get(Calendar.HOUR_OF_DAY) + ", \""
                + ETIQUETA_MENSAJE_DATE_min
                + "\" : " + calendar.get(Calendar.MINUTE) + ", \""
                + ETIQUETA_MENSAJE_DATE_sec
                + "\" : " + calendar.get(Calendar.SECOND) + "}";
        // year month day hora minuto segundos

        return data;

    }

    public static GregorianCalendar importCalendar(LinkedTreeMap calendar) {

        Double anho = (Double) calendar.get(ETIQUETA_MENSAJE_DATE_ANHO);
        Double mes = (Double) calendar.get(ETIQUETA_MENSAJE_DATE_MES);
        Double dia = (Double) calendar.get(ETIQUETA_MENSAJE_DATE_DIA);
        Double hora = (Double) calendar.get(ETIQUETA_MENSAJE_DATE_hora);
        Double minuto = (Double) calendar.get(ETIQUETA_MENSAJE_DATE_min);
        Double segundo = (Double) calendar.get(ETIQUETA_MENSAJE_DATE_sec);

        return new GregorianCalendar(anho.intValue(), mes.intValue(), dia.intValue(), hora.intValue(), minuto.intValue(), segundo.intValue());
    }

    public static Usuario importUser(String json) {

        return importUser(jsonToMap(json));
    }

    public static Usuario importUser(Map mapaUsuario) {
        String id = (String) mapaUsuario.get(ETIQUETA_USER_ID);
        String name = (String) mapaUsuario.get(ETIQUETA_USER_Name);
        Usuario us =new Usuario(id, name, 0);
        return us;

    }

    public static String exportUsuario(Usuario usuario) {
        System.out.println(usuario.getIdUsuario()+
                usuario.getNombre()+
                usuario.getSecurityLevel());
        String data = "{\"" + ETIQUETA_USER_ID
                + "\":\"" + usuario.getIdUsuario() + "\",\""
                + ETIQUETA_USER_Name + "\":\"" + usuario.getNombre()
                + "\"}";

        return data;

    }

    public static ArrayList<Usuario> importUsuarios(ArrayList<Map<String, Object>> usuariosMap) {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        for (Map mapuser : usuariosMap) {
            Usuario us = ComunicationJsonParser.importUser((Map<String, Object>) mapuser);
            usuarios.add(us);
            if (SQL_Usuario.getUsuario(us.getIdUsuario().toString()) == null) {
                SQL_Usuario.añadirUsuario(us);

            }
        }
        return usuarios;
    }

    public static String addUsuarios(ArrayList<Usuario> usuarios) {
        String data = " \"" + ETIQUETA_USERS + "\" :[ \n";
        for (int i = 0; i < usuarios.size(); i++) {
            data += exportUsuario(usuarios.get(i)) + ((i < usuarios.size() - 1) ? ",\n" : "]");

        }

        return data;
    }

    public static String exportMessaje(Mensaje messaje) {
        String data = "{ \"" + ETIQUETA_MENSAJE_ID
                + "\" : \"" + messaje.getIdMensaje() + "\", \"" + ETIQUETA_USER_ID
                + "\" : \"" + messaje.getEmisor().getIdUsuario() + "\", \""
                + ETIQUETA_MENSAJE_TEXT + "\" :\"" + messaje.getTexto()
                + "\",\"" + ETIQUETA_CHAT_ID + "\": \"" + messaje.getReceptor().getIdChat()
                + "\",\"" + ETIQUETA_MENSAJE_DATE + "\":" + exportCalendar(messaje.getFecha()) + "}";

        return data;

    }

    public static Mensaje importMensaje(Map mapaMensaje) {
        String idMensaje = (String) mapaMensaje.get(ETIQUETA_MENSAJE_ID);
        String idUsuario = (String) mapaMensaje.get(ETIQUETA_USER_ID);
        System.out.println(idUsuario);
        String receiverChat = (String) mapaMensaje.get(ETIQUETA_CHAT_ID);
        String text = (String) mapaMensaje.get(ETIQUETA_MENSAJE_TEXT);
        System.out.println(text);

        System.out.println(receiverChat);
        System.out.println(mapaMensaje.get(ETIQUETA_MENSAJE_DATE).getClass());
        Calendar date = importCalendar((LinkedTreeMap) mapaMensaje.get(ETIQUETA_MENSAJE_DATE));
        System.out.println(date);
        Mensaje m = new Mensaje(idMensaje, idUsuario, receiverChat, text, date);
        System.out.println("mensaje = "+m.toString());
        return m;

    }

    public static String addMessajes(ArrayList<Mensaje> mensajes) {
        String data = " \"" + ETIQUETA_MENSAJES + "\" :[ \n";
        for (int i = 0; i < mensajes.size(); i++) {
            data += exportMessaje(mensajes.get(i)) + ((i < mensajes.size() - 1) ? ",\n" : "]");

        }

        return data;
    }

    public static ArrayList<Mensaje> importMensajes(ArrayList<Map<String, Object>> mensajes) {
        ArrayList<Mensaje> mensajesarr = new ArrayList<Mensaje>();
        
        for (Map<String,Object> mapuser : mensajes) {
            Mensaje mens = ComunicationJsonParser.importMensaje((Map<String, Object>) mapuser);

            if (SQL_Mensaje.getMensaje(mens.getIdMensaje().toString()) == null) {
                mensajesarr.add(mens);

            }
        }
        return mensajesarr;
    }

    public static String exportChat(Chat chat) {
        String data = "{\"" + ETIQUETA_CHAT_ID
                + "\":\"" + chat.getIdChat() + "\",\""
                + ETIQUETA_CHAT_Name + "\":\"" + chat.getNombre() + "\"," + addUsuarios(SQL_ChatsUsuarios.getUsuariosChat(chat))
                + "}";

        return data;

    }

    public static String addChats(ArrayList<Chat> chats) {

        String data = " \"" + ETIQUETA_CHATS + "\" :[ \n";
        for (int i = 0; i < chats.size(); i++) {
            data += exportChat(chats.get(i)) + ((i < chats.size() - 1) ? ",\n" : "]");

        }

        return data;
    }

    public static Chat importChat(Map mapaChat) {
        String idChat = (String) mapaChat.get(ETIQUETA_CHAT_ID);
        String name = (String) mapaChat.get(ETIQUETA_CHAT_Name);
        System.out.println(name);
        ArrayList<Usuario> usuarios = null;
        if (mapaChat.get(ComunicationJsonParser.ETIQUETA_USERS).getClass() == ArrayList.class) {
            ArrayList<Map<String, Object>> usuariosOb = (ArrayList<Map<String, Object>>) mapaChat.get(ComunicationJsonParser.ETIQUETA_USERS);
            usuarios = importUsuarios(usuariosOb);

        }

        return new Chat(idChat, name, usuarios);

    }

    public static ArrayList<Chat> importChats(ArrayList<Map<String, Object>> Chats) {
        ArrayList<Chat> chatarr = new ArrayList<Chat>();

        for (Map<String, Object> mapchat : Chats) {
            Chat cht = ComunicationJsonParser.importChat(mapchat);

            chatarr.add(cht);

        }
        return chatarr;
    }

    public static void Procesar(String json) {
        System.out.println("procesar "+json);
        HashMap<String, Object> mapa = ComunicationJsonParser.jsonToMap(json);
        // usuarios
        int contador = 0;
        if (mapa.containsKey(ETIQUETA_USERS)) {
            ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
            
            if (mapa.get(ETIQUETA_USERS).getClass() == ArrayList.class) {
                ArrayList<Map<String, Object>> mapusuarios = (ArrayList<Map<String, Object>>) mapa.get(ETIQUETA_USERS);
                usuarios = importUsuarios(mapusuarios);
                System.out.println(usuarios.size());
                for (Usuario us : usuarios) {
                    contador++;
                    System.out.println(SQL_Usuario.getUsuario(us.getIdUsuario().toString()));
                    if (SQL_Usuario.getUsuario(us.getIdUsuario().toString()) == null) {
                        SQL_Usuario.añadirUsuario(us);
                    } else {
                        SQL_Usuario.updateUsuario(us);

                    }

                }

            }
        }
        System.out.println("usuarios superados"+contador);
        contador = 0;
        // chats
        if (mapa.containsKey(ETIQUETA_CHATS)) {
            //System.out.println("cha");
            ArrayList<Chat> chats = new ArrayList<Chat>();
            if (mapa.get(ETIQUETA_CHATS).getClass() == ArrayList.class) {
                ArrayList<Map<String, Object>> mapchats = (ArrayList<Map<String, Object>>) mapa.get(ETIQUETA_CHATS);
                chats = importChats(mapchats);
                for (Chat cha : chats) {
                    contador++;
                    if (SQL_Chat.getChat(cha.getIdChat().toString()) == null) {
                        SQL_Chat.addChat(cha);
                    } else {
                        SQL_Chat.updateChat(cha);

                    }

                }

            }
        }
        System.out.println("chats Procesados"+contador);
        contador =0;
        // mensajes
        if (mapa.containsKey(ComunicationJsonParser.ETIQUETA_MENSAJES)) {
            ArrayList<Mensaje> arrMensajes = new ArrayList<Mensaje>();
            if (mapa.get(ComunicationJsonParser.ETIQUETA_MENSAJES).getClass() == ArrayList.class) {
                ArrayList<Map<String, Object>> mapchats = (ArrayList<Map<String, Object>>) mapa.get(
                        ETIQUETA_MENSAJES);
                
                arrMensajes = importMensajes(mapchats);
                for (Mensaje mens : arrMensajes) {
                    contador++;
                    if (SQL_Mensaje.getMensaje(mens.getIdMensaje().toString()) == null) {
                        SQL_Mensaje.añadirMensaje(mens);
                    } else {
                        System.out.println("Mensaje Existente");
//SQL_Usuario.updateMen(mens);

                    }

                }

            }
        }
          System.out.println("mensajes Procesados"+contador);

    }

    public static String generarJson(Mensaje mens) {
        ArrayList<Mensaje> arrMens = new ArrayList<Mensaje>();
        arrMens.add(mens);
        return generarJson( arrMens);

    }

    public static String generarJson(ArrayList<Mensaje> arrMens) {

        return generarJson(SQL_Usuario.getUsuarios(), SQL_Chat.getChats(), arrMens);

    }

    public static String generarJson(ArrayList<Usuario> arrUsua, ArrayList<Chat> arrChat, ArrayList<Mensaje> arrMens) {
        String data = "{";

        if (arrUsua != null) {
            data += addUsuarios(arrUsua);
        }
        data += (arrUsua != null && arrChat != null) ? "," : "";

        if (arrChat != null) {
            data += addChats(arrChat);
        }
        data += (arrMens != null && arrChat != null) ? "," : "";
        if (arrMens != null) {
            data += addMessajes(arrMens);
        }
        data += "}";
        System.out.println(data);

        return data;
    }

}

//    public static String createNewUsuario(String usuario) {
//       Usuario user = new   Usuario(usuario,0);
//      
//        
//        String data = "{ \"" + ETIQUETA_USER_NEW+ "\" : \n";
//        
//            data += exportUsuario(SQL_Usuario.) +  "]}";
//
//        
//
//        return data;
//    }
//    public static Object jsonToObject(String json) {
//        String jsonString = json;
//        Object returnObject = null;
//        Map<String, Object> retMap = new Gson().fromJson(jsonString, new TypeToken<HashMap<String, Object>>() {
//        }.getType());
//
//        if (retMap.containsKey(ETIQUETA_MENSAJES)) {
//            System.out.println(retMap.get(ETIQUETA_MENSAJES).getClass());
//            if (retMap.get(ETIQUETA_MENSAJES).getClass() == ArrayList.class) {
//                ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) retMap.get(ETIQUETA_MENSAJES);
//                for (Mensaje mens : mensajes) {
//                    Chat.getChatPorID(mens.getChatReceiver().getId()).addMessaje(mens);
//
//                }
//            }
//
//        }
//
//        if (retMap.containsKey(ETIQUETA_CHAT)) {
//
//            if (retMap.get(ETIQUETA_CHAT).getClass() == LinkedTreeMap.class) {
//                LinkedTreeMap x = (LinkedTreeMap) retMap.get(ETIQUETA_CHAT);
//                
//                Double userId = (Double) x.get(ETIQUETA_USER_ID);
//                ArrayList<Usuario> usuarios =(ArrayList<Usuario>) x.get(ETIQUETA_CHAT_ID);
//                
//                returnObject = importMensaje((Map) retMap.get("messaje"));
//
//            }
//
//        } 
//        if (retMap.containsKey(ETIQUETA_USER)) {
//            if (retMap.get(ETIQUETA_USER).getClass() == LinkedTreeMap.class) {
//                LinkedTreeMap x = (LinkedTreeMap) retMap.get("messaje");
//                System.out.print(x.get(ETIQUETA_USER_ID));
//                System.out.print(x.get(ETIQUETA_USER_Name));
//
//                returnObject = importMensaje((Map) retMap.get("usuario"));
//
//            }
//        }
//        return returnObject;
//    }
//   public static Map<String, Object> MapToJson(Map<String,String> mapa) {
//   
//   
//   
//   return new Map<String,Object>();}}

