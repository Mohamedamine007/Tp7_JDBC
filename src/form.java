import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class form extends JFrame {

    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    JPanel mainPanel = new JPanel();

    JButton ajouter = new JButton("Ajouter");
    JButton supprimer = new JButton("Supprimer");
    JButton afficher = new JButton("Afficher");

    JTextField nomT = new JTextField();
    JTextField prenomT = new JTextField();
    JTextField ageT = new JTextField();

    JOptionPane jOptionPane = new JOptionPane();

    DefaultTableModel tableModel;
    JTable table;

    String columns[] = {"NOM", "PRENOM", "AGE"};

    public form() {
        setTitle("form");
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setSize(700, 240);
        setLocationRelativeTo(null);

        p1.setLayout(new GridLayout(2, 1));

        p2.setLayout(new GridLayout(3, 2));
        p2.add(new JLabel("nom"));
        p2.add(nomT);
        p2.add(new JLabel("Prenom"));
        p2.add(prenomT);
        p2.add(new JLabel("age"));
        p2.add(ageT);

        p3.setLayout(new FlowLayout());
        p3.add(ajouter);
        p3.add(supprimer);
        p3.add(afficher);

        ajouter.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Person P = new Person();
                        P.nom = nomT.getText();
                        P.prenom = prenomT.getText();
                        P.age = Integer.parseInt(ageT.getText());

                        DbConnection db = new DbConnection();

                        try {
                            db.insert(P);
                            jOptionPane.showMessageDialog(form.this, "Record added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (SQLException sqle) {
                            jOptionPane.showMessageDialog(form.this, "Record addition FAILED", "Error", JOptionPane.ERROR_MESSAGE);
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

        supprimer.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Person P = new Person();
                        P.nom = nomT.getText();

                        DbConnection db = new DbConnection();

                        try {
                            int rowsAffected = db.delete(P);
                            if (rowsAffected > 0) {
                                jOptionPane.showMessageDialog(form.this, "Record deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                jOptionPane.showMessageDialog(form.this, "Record deletion FAILED", "Error", JOptionPane.ERROR_MESSAGE);
                            }
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
                            ResultSet selectedData = db.afficher();
                            tableModel.setRowCount(0); // Clear existing data in the table
                            while (selectedData.next()) {
                                String selectedNom = selectedData.getString("Nom");
                                String selectedPrenom = selectedData.getString("Prenom");
                                int selectedAge = selectedData.getInt("Age");

                                Object[] rowData = {selectedNom, selectedPrenom, selectedAge};
                                tableModel.addRow(rowData);
                            }
                        } catch (SQLException sqle) {
                            sqle.printStackTrace();
                        }
                    }
                }
        );

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
