import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    private JTextArea jtaConsole;
    private Serveur serveur;
    private JButton btnStart;

    public Fenetre(Serveur serveur) {
        this.serveur = serveur;
        this.setTitle("Serveur TCP");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(300, 300));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.createUI();
    }

    private void createUI() {
        jtaConsole = new JTextArea();
        jtaConsole.setEditable(false);
        btnStart = new JButton("Démarrer serveur");
        btnStart.addActionListener(e -> toggleServeur());
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(jtaConsole, BorderLayout.CENTER);
        this.getContentPane().add(btnStart, BorderLayout.SOUTH);
    }

    private void toggleServeur() {
       serveur.toggleServeur();
    }

    void print(String text) {
        this.jtaConsole.append(text+"\n");
    }

    public void setServeurButton(String text) {
        btnStart.setText(text);
    }
}
