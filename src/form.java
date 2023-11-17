import javax.swing.*;
import java.awt.*;

public class form extends JFrame{

 JPanel p1 = new JPanel();
 JPanel p2 = new JPanel();
 JPanel p3 = new JPanel();

 public form() {

     setTitle("form");
     setDefaultCloseOperation(this.EXIT_ON_CLOSE);
     setSize(500, 400);
     setLocationRelativeTo(null);

     p1.setLayout(new GridLayout(2, 1));

     p2.setLayout(new GridLayout(3, 2));
     p2.add(new JLabel("nom"));
     p2.add(new JTextField());
     p2.add(new JLabel("Prenom"));
     p2.add(new JTextField());
     p2.add(new JLabel("age"));
     p2.add(new JTextField());

     p3.setLayout(new FlowLayout());
     p3.add(new JButton("Ajouter"));
     p3.add(new JButton("Supprimer"));
     p3.add(new JButton("Afficher"));

     add(p1);
     p1.add(p2);
     p1.add(p3);
     setVisible(true);
 }

}
