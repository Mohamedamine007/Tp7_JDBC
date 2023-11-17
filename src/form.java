import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class form extends JFrame{

 JPanel p1 = new JPanel();
 JPanel p2 = new JPanel();
 JPanel p3 = new JPanel();

 JButton ajouter = new JButton("Ajouter");
 JButton supprimer = new JButton("Supprimer");
 JButton afficher = new JButton("Afficher");

 JTextField nomT = new JTextField();
 JTextField prenomT = new JTextField();
 JTextField ageT = new JTextField();

 JOptionPane jOptionPane = new JOptionPane();

 public form() {

     setTitle("form");
     setDefaultCloseOperation(this.EXIT_ON_CLOSE);
     setSize(500, 400);
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
                     } catch(SQLException sqle) {
                         sqle.printStackTrace();
                     }finally {
                         if(db != null) {
                             try {
                                 db.closeConnection();
                             } catch(SQLException sqle) {
                                 sqle.printStackTrace();
                             }
                         }
                     }
                 }
             }
     );

     add(p1);
     p1.add(p2);
     p1.add(p3);
     setVisible(true);
 }

}
