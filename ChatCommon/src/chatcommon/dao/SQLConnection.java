/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcommon.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

/**
 *
 * @author Pablo Alonso Vazquez <pav.vigo@gmail.com>
 *
 *
 *
 */
public class SQLConnection {

    private static SQLConnection INSTANCE = null;
    private File url = new File("data" + File.separator + "BD.db");
    private String preurl = "jdbc:sqlite://";
    private String username = "";
    private String password = "";

    public File getUrl() {
        return url;
    }

    /**
     * devuelve una nueva conexion con los parametros cargados.
     *
     * @return
     * @throws SQLException
     */
    private void crearDefaults() {
        FileWriter filWri = null;
        File archivo = new File("data" + File.separator + "creacionTablas.sql");
        if (!archivo.canRead()) {
            if (!archivo.getParentFile().exists()) {

                archivo.getParentFile().mkdir();

            }
            try {
                //System.out.println("creado fichero sql");
                archivo.createNewFile();
                filWri = new FileWriter(archivo);
                filWri.write("DROP TABLE  if exists UsuariosChat;\n" +
"DROP TABLE  if exists Mensaje;\n" +
"DROP TABLE  if exists Chat;\n" +
"DROP TABLE  if exists Usuario;\n" +
"create table Usuario(\n" +
"idUsuario varchar(36) primary key NOT NULL ,\n" +
"nombre varchar(30),\n" +
"secLevel integer\n" +
");\n" +
"CREATE TABLE Chat(\n" +
"idChat varchar(36) primary key NOT NULL ,\n" +
"nombre varchar(30)\n" +
");\n" +
"create table Mensaje(\n" +
"idMensaje varchar(36) primary key NOT NULL ,\n" +
"idUsuario varchar(36) NOT NULL ,\n" +
"idChat varchar(36) NOT NULL,\n" +
"texto varchar(200) NOT NULL,\n" +
"fecha Long ,\n" +
"foreign key (idUsuario) references Usuario(idUsuario),\n" +
"foreign key (idChat) references Chat(idChat)\n" +
");\n" +
"\n" +
"CREATE TABLE UsuariosChat(\n" +
"idUsuario varchar(36) NOT NULL ,\n" +
"idChat varchar(36) NOT NULL,\n" +
"foreign key (idUsuario) references Usuario(idUsuario),\n" +
"foreign key (idChat) references Chat(idChat)\n" +
"primary key (idUsuario,idChat)\n" +
");\n" +
"INSERT INTO Usuario(idUsuario,nombre,secLevel) values ('87d5564e-51e1-4fb2-ab25-760818521ea6','jose',0);\n" +
"\n" +
"INSERT INTO Usuario(idUsuario,nombre,secLevel) values ('87d5564e-51e1-4fb2-ab25-760818521ea9','maria',0);\n" +
"\n" +
"\n" +
"INSERT INTO Chat(idChat,nombre) values ('00000000-0000-0000-0000-000000000000','GrupalGeneral');\n" +
"\n" +
"\n" +
"INSERT INTO Mensaje(idMensaje,idUsuario,idChat,fecha,texto) values ('8eecd038-d181-4346-a3a8-abcdc1351a55','87d5564e-51e1-4fb2-ab25-760818521ea6','00000000-0000-0000-0000-000000000000','15102','hola');\n" +
"\n" +
"\n" +
"\n" +
"INSERT INTO UsuariosChat(idChat,idUsuario) values ('00000000-0000-0000-0000-000000000000','87d5564e-51e1-4fb2-ab25-760818521ea6');\n" +
"INSERT INTO UsuariosChat(idChat,idUsuario) values ('00000000-0000-0000-0000-000000000000','87d5564e-51e1-4fb2-ab25-760818521ea9');");

            } catch (IOException ex) {
                Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    filWri.close();
                } catch (IOException ex) {
                    Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        //System.out.println(archivo.getAbsolutePath());
        EjecutarSQLFile(archivo);
    }

    public void setUrl(File url) {
        this.url = url;
        testTablas();

        EjecutarSQLFile(new File("data" + File.separator + "creacionTablas.sql"));

    }

    public Connection getConexion() throws SQLException {
        Connection connection = DriverManager.getConnection(preurl + url.getAbsolutePath(), username, password);

        return connection;
    }

    /**
     * Constructor privado para el SQLConnection
     */
    private SQLConnection() {
        if (null == url || !url.canRead()) {
            //System.out.println("Error, fichero no encontrado.");
            crearDefaults();

        } else {
            try {
                Class.forName("org.sqlite.JDBC");
                java.sql.Connection conexion = getConexion();
                conexion.close();
                //System.out.println("exito");
            } catch (SQLException | ClassNotFoundException ex) {
                //System.out.println("Error en la conexión de la base de datos");
            }

        }
    }

    /**
     * Metodo para construir el unico el unico objeto de este Singleton.
     *
     * @return devuelve el objeto SQLConnection
     */
    public static SQLConnection getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new SQLConnection();

        }
        return INSTANCE;

    }

    /**
     * Metodo para generar la tabla a partir de un fichero sql, parte el fichero
     * en consultas y los va agregando a un batch, luego lo ejecuta.
     *
     * @param file
     */
    private void EjecutarSQLFile(File file) {
        //System.out.println(url.getAbsolutePath());
        //System.out.println("file-" + file.getAbsolutePath());
        ArrayList<String> consultas = new ArrayList<>();
        boolean ok = true;
        if (file.canRead()) {

            // compruebo si es fichero es valido ( termina en sql)
            if (file.isFile() && file.getName() != "" && (file.getName().endsWith(".sql") || file.getName().endsWith(".SQL"))) {

                try {
                    FileReader filread = new FileReader(file);
                    Scanner sc = new Scanner(filread).useDelimiter(";");
                    while (sc.hasNext()) {
                        consultas.add(sc.next() + ";");
                        //System.out.println(consultas.get(consultas.size() - 1));

                    }

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SQLConnection.class.getName()).log(Level.SEVERE, null, ex);
                    ok = false;
                }

            } else {
                //System.out.println("El fichero no es valido.");
                ok = false;
            }

        } else {
            //System.out.println("No se encuentra el archivo SQL");
            ok = false;
        }
        if (ok) {
            try {
                Connection connection = getConexion();

                Statement sta = connection.createStatement();
                for (String aux : consultas) {
                    sta.addBatch(aux);
                }
                sta.executeBatch();

                sta.close();

                connection.close();

            } catch (SQLException ex) {
                //System.out.println(ex);

            }
        }
    }

    /**
     * Test de conexion para verificar que funciona bien la base de datos.
     *
     * @return
     */
    public boolean PruebaConexion() {
        boolean prueba = false;
        try {

            Connection connection = getConexion();
            prueba = true;
            connection.close();
            JOptionPane.showMessageDialog(null, "Conexion exitosa");
        } catch (SQLException ex) {
            prueba = false;
            JOptionPane.showMessageDialog(null, "Error de conexion, conecte la base de datos, antes de iniciar.");
        }
        return prueba;
    }

    /**
     * comprueba si el esquema de tablas es correcto en el fichero
     *
     * @param fichero
     * @return devuelve true si es correcto, false si no es correcto
     */
    public void testTablas() throws CorruptDatabase {
        boolean correcto = true;
        ArrayList<String> auxArrlist = new ArrayList<String>();
        String name = "";
        try {
            Connection connection = getConexion();

            Statement sta = connection.createStatement();
            ResultSet rs = sta.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' order by name;");

            while (rs.next()) {

                name = rs.getString("name");
                //System.out.println(name);
                auxArrlist.add(name);
            }
            rs.close();
            sta.close();
            connection.close();

        } catch (SQLException ex) {
            //System.out.println(ex);

        }
//        if (auxArrlist.size() != 4) {
//            correcto = false;
//        } else {
//            if (auxArrlist.get(0).compareToIgnoreCase(Etiqueta.getAlias_tabla()) != 0) {
//                correcto = false;
//            }
//            if (auxArrlist.get(1).compareToIgnoreCase("etiquetasDeTarjetas") != 0) {
//                correcto = false;
//            }
//            if (auxArrlist.get(2).compareToIgnoreCase("sqlite_sequence") != 0) {
//                correcto = false;
//            }
//            if (auxArrlist.get(3).compareToIgnoreCase(Tarjeta.getAlias_tabla()) != 0) {
//                correcto = false;
//            }
//        }
        if (correcto = false) {
            throw new CorruptDatabase();
        }

    }

    public class CorruptDatabase extends ArithmeticException {

        public CorruptDatabase() {
            super("Error, base de datos no completa.");
        }

        public CorruptDatabase(String e) {
            super(e);
        }
    }

}
