package versãoOriginal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
    public Connection conectarBD() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance(); 
            String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=123";
            conn = DriverManager.getConnection(url);
        } catch (Exception e) { }
        return conn;
    }
    public String nome="";
    public boolean result = false;
    public boolean verificarUsuario(String login, String senha) {
        String sql = "";
        Connection conn = conectarBD();
        //INSTRUÇÃO SQL
        sql = "select nome from usuarios ";
        sql += "Where login = '" + login + "'";
        sql += " and senha = '" + senha + "';";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()) {
                result = true;
                nome = rs.getString("nome");
            }
        } catch (Exception e) { }
        return result;
    }
}