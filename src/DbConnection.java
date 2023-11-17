import java.sql.DriverManager;
import java.sql.*;
public class DbConnection {

    private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/bdtest";

    public DbConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, "root", "");
        }catch(Exception e) {
            System.out.println("Fixer a ben 3emi: " + e);
        }
        }

        public void insert(Person P) throws SQLException{

            String query = "INSERT INTO options VALUES('" + P.nom + "', '" + P.prenom + "', '" + P.age + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        }

        public void delete(Person P) throws SQLException {

            String query = "DELETE FROM options WHERE nom='"+P.nom+"'";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        }

        public void closeConnection() throws SQLException {

            if(conn != null && !conn.isClosed()) {

                conn.close();
                System.out.println("Connection closed");
            }
        }

}

