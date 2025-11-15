package VersãoCorrigida;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; // Usado para prevenir SQL Injection
import java.sql.ResultSet;
import java.sql.SQLException; // Importação específica para exceções de SQL

/**
 * Classe de usuário que lida com a conexão e autenticação no banco de dados.
 *
 * NOTA: Esta classe foi refatorada para corrigir falhas graves de segurança
 * e de gerenciamento de recursos.
 */
public class User {

    // A URL de conexão agora é uma constante privada.
    // Senhas NUNCA devem estar no código-fonte, mas mantido para o exercício.
    private static final String DB_URL = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";

    /**
     * Conecta ao banco de dados.
     * @return um objeto Connection.
     * @throws SQLException se a conexão falhar.
     */
    public Connection conectarBD() throws SQLException {
        /* * O registro do Driver (Class.forName) não é mais necessário
         * em drivers JDBC 4.0+ (a maioria).
         * O DriverManager lida com isso automaticamente.
         */
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Verifica as credenciais do usuário no banco de dados de forma segura.
     * @param login O nome de usuário.
     * @param senha A senha do usuário.
     * @return true se o usuário for válido, false caso contrário.
     */
    public boolean verificarUsuario(String login, String senha) {
        // A query SQL agora usa placeholders (?) para segurança.
        String sql = "select nome from usuarios Where login = ? and senha = ?";
        
        // Usar "try-with-resources" garante que Connection, PreparedStatement,
        // e ResultSet sejam FECHADOS automaticamente, mesmo se ocorrer um erro.
        // Isso previne o vazamento de recursos.
        try (Connection conn = conectarBD();
             PreparedStatement st = conn.prepareStatement(sql)) {

            // 1. Prevenção de SQL Injection:
            // Os valores são inseridos nos placeholders (?) de forma segura.
            st.setString(1, login);
            st.setString(2, senha); // NOTA: Senhas devem ser armazenadas com HASH

            try (ResultSet rs = st.executeQuery()) {
                
                // Se rs.next() for true, significa que o SELECT encontrou
                // uma linha com o login E senha correspondentes.
                if (rs.next()) {
                    // Opcional: Se quiséssemos armazenar o nome do usuário logado.
                    // String nome = rs.getString("nome"); 
                    return true; // Usuário encontrado e válido.
                } else {
                    return false; // Usuário não encontrado (login/senha errados).
                }
            }

        } catch (SQLException e) {
            // Tratamento de exceção adequado:
            // Em vez de um catch vazio, registramos o erro.
            // Em um app real, isso iria para um sistema de logs.
            System.err.println("Erro ao verificar usuário: " + e.getMessage());
            return false; // Se houver um erro de banco, o login falha.
        }
    }
}