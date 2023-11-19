import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

//Inheriting from the JFrame class, so we use its method directly
public class form extends JFrame {

    // Declaring the panels needed to achieve the desired layout in the Tp
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    JPanel mainPanel = new JPanel();

    // Instantiating the buttons from the JButton class
    JButton ajouter = new JButton("Ajouter");
    JButton supprimer = new JButton("Supprimer");
    JButton afficher = new JButton("Afficher");

    // Instantiating the Text Fields that we need
    JTextField nomT = new JTextField();
    JTextField prenomT = new JTextField();
    JTextField ageT = new JTextField();

    // Instantiating from the JOptionPane so that we use it to display explanatory messages
    JOptionPane jOptionPane = new JOptionPane();

    // Instantiating the classes needed to create the table (where we'll display the data)
    DefaultTableModel tableModel;
    JTable table;

    // Declaring a unidimensional array for the table column's name
    String columns[] = {"NOM", "PRENOM", "AGE"};

    public form() {
        // setting the essentials settings for the frame
        setTitle("form");
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setSize(700, 240);
        setLocationRelativeTo(null);

        // Setting the layout
        p1.setLayout(new GridLayout(2, 1));

        //Setting the second panel with its layout components
        p2.setLayout(new GridLayout(3, 2));
        p2.add(new JLabel("nom"));
        p2.add(nomT);
        p2.add(new JLabel("Prenom"));
        p2.add(prenomT);
        p2.add(new JLabel("age"));
        p2.add(ageT);

        //Setting the third panel with its layout and components
        p3.setLayout(new FlowLayout());
        p3.add(ajouter);
        p3.add(supprimer);
        p3.add(afficher);

        // Adding an action listener to the "ajouter" button
        ajouter.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Creating a person object so we can use its attributes
                        Person P = new Person();
                        // Assigning the values gotten from the inputs(textfields) to the person attributes
                        P.nom = nomT.getText();
                        P.prenom = prenomT.getText();
                        // We convert the age from a string to an integer
                        P.age = Integer.parseInt(ageT.getText());

                        // Instantiating the DbConnection class, so we can interact with the database
                        DbConnection db = new DbConnection();
                        // Because the insert method throws an exception, we've to handel it here
                        try {
                            //Calling the insert method(to insert data)
                            db.insert(P);
                            //If data inserted displaying an explanatory message
                            jOptionPane.showMessageDialog(form.this, "Record added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            //If we catch an exception
                        } catch (SQLException sqle) {
                            // displaying a message for the user, you know to make things more interactive
                            jOptionPane.showMessageDialog(form.this, "Record addition FAILED", "Error", JOptionPane.ERROR_MESSAGE);
                            // Printing the stack trace information to the error output
                            sqle.printStackTrace();
                            // Adding this finally, so we can execute it however the case
                        } finally {
                            // Checking if the db object is created successfully(is not null), before start closing
                            if (db != null) {
                                try {
                                    // Closing the connection by calling the closeConnection() method
                                    db.closeConnection();
                                    // because the closeConnection method throws an exception we've to handel it here
                                } catch (SQLException sqle) {
                                    sqle.printStackTrace();
                                }
                            }
                        }
                    }
                }
        );

        // The same comments that we add for the "ajouter" buttons are almost the same,
        // so we won't duplicate the comments,
        // but we'll put a comment if there's something that needs an explanation

        supprimer.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Person P = new Person();
                        P.nom = nomT.getText();

                        DbConnection db = new DbConnection();

                        try {
                            // The delete method returns an integer that representes the number of rows affected by the query
                            // so to make sure that the record is deleted successfully we'll use that variable
                            int rowsAffected = db.delete(P);
                            // Verifying if the rowsAffected > 0(which means that there's some affected rows by the deletion operation)
                            if (rowsAffected > 0) {

                                jOptionPane.showMessageDialog(form.this, "Record deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                jOptionPane.showMessageDialog(form.this, "Record deletion FAILED", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            // Catching the exception that the delete method throws
                        } catch (SQLException sqle) {
                            sqle.printStackTrace();
                        } finally {
                            if (db != null) {
                                try {
                                    db.closeConnection();
                                } catch (SQLException sqle) {
                                    sqle.printStackTrace();
                                }
                            }
                        }
                    }
                }
        );

        afficher.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DbConnection db = new DbConnection();
                        try {
                            // Assigning the data returned by the afficher() method to the selectData variable
                            // now we can use the selectData variable to display the data
                            ResultSet selectedData = db.afficher();
                            tableModel.setRowCount(0); // Clear existing data in the table
                            // Start a while loop to loop over the data on the selectedData variable using the next() method
                            while (selectedData.next()) {
                                // Assigning the values that we get from the selectedData variable
                                String selectedNom = selectedData.getString("Nom");
                                String selectedPrenom = selectedData.getString("Prenom");
                                // Converting the age from the text field from a string to an integer
                                int selectedAge = selectedData.getInt("Age");
                                //declaring an array of objects so we can store different data types
                                Object[] rowData = {selectedNom, selectedPrenom, selectedAge};
                                // Adding that data as a row to the tableModel
                                tableModel.addRow(rowData);
                            }
                        } catch (SQLException sqle) {
                            sqle.printStackTrace();
                        }
                    }
                }
        );

        // Create an initially empty table model with column names
        tableModel = new DefaultTableModel(null, columns);
        table = new JTable(tableModel);
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        p4.add(new JScrollPane(table));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(p1, BorderLayout.NORTH);
        mainPanel.add(p4, BorderLayout.CENTER);
        add(mainPanel);
        p1.add(p2);
        p1.add(p3);
        setVisible(true);
    }

}
