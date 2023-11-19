import java.sql.DriverManager;
import java.sql.*;
public class DbConnection {

    // Declaring the conn attribute that we'll use a lot using the Connection interface
    private Connection conn;
    // The url
    private String url = "jdbc:mysql://localhost:3306/bdtest";

    public DbConnection() {

        try {
            // Charging the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //making the connection using the getConnection()
            conn = DriverManager.getConnection(url, "root", "");
        }catch(Exception e) {
            System.out.println("Fix your bugs, and errors: " + e);
        }
        }

        // The insert method that we'll call on the "ajouter" button
        public void insert(Person P) throws SQLException{
            // The MySQL query
            String query = "INSERT INTO options VALUES('" + P.nom + "', '" + P.prenom + "', '" + P.age + "')";
            // creating the statement
            Statement stmt = conn.createStatement();
            // calling the executeUpdate() method from the stmt object (because we'll modify something)
            // which is the database table in this case
            stmt.executeUpdate(query);
        }

        // The delete method that we'll call on the "supprimer" button
        public int delete(Person P) throws SQLException {

            // Deletion query
            String query = "DELETE FROM options WHERE nom='"+P.nom+"'";
            Statement stmt = conn.createStatement();
            // Assigning the value that the executeUpdate() method returns to the rowsAffected variable
            int rowsAffected = stmt.executeUpdate(query);

            // Returning this variable will help us on verifying the success of the deletion
            return rowsAffected;
        }

        // The afficher() method that we'll use inside a table
        public ResultSet afficher() throws SQLException{

        // The selection query
            String query = "SELECT * FROM options";
            Statement stmt = conn.createStatement();
            // Declaring a ResultSet object, so we can use it to display the data on the table
            // Because we've a select, this time we use the executeQuery() method.
            ResultSet result = stmt.executeQuery(query);
            // returning the result object
            return result;
        }

        // the closing connection method
        public void closeConnection() throws SQLException {
            // we've to make sure first if the conn is established, and it's not yet closed before closing it
            if(conn != null && !conn.isClosed()) {
                // The close() method will close the connection (we close the connection, so we stop the program from taking resources
                conn.close();
                System.out.println("Connection closed");
            }
        }

}


