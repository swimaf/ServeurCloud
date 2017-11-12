import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fenetre extends JFrame implements ActionListener {

    private JList<String> jlFichiers;
    private Client client;
    private JButton btnSave;

    Fenetre(Client client) {
        this.client = client;
        this.setTitle("Client TCP");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(300, 400));
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.createUI();
    }

    private void createUI() {
        JLabel jLabel = new JLabel("Liste des fichiers");
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setFont(new Font("Arial", Font.BOLD, 20));
        jlFichiers = new JList<>();
        DefaultListModel<String> modele = new DefaultListModel<>();
        modele.addElement("Aucun element");
        jlFichiers.setModel(modele);
        jlFichiers.setEnabled(false);
        JButton btnQuit = new JButton("Quitter");
        btnQuit.setPreferredSize(new Dimension(145, 40));
        btnSave = new JButton("Enregistrer");
        btnQuit.addActionListener(e -> client.disconnect());
        btnSave.addActionListener(this);
        btnSave.setEnabled(false);
        btnSave.setPreferredSize(new Dimension(145, 40));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(btnSave, BorderLayout.WEST);
        panel.add(btnQuit, BorderLayout.EAST);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(jLabel, BorderLayout.NORTH);
        this.getContentPane().add(jlFichiers, BorderLayout.CENTER);
        this.getContentPane().add(panel, BorderLayout.SOUTH);
    }

    void setListFiles(String[] listFiles) {
        DefaultListModel<String> modele = new DefaultListModel<>();
        for (String listFile : listFiles) {
            modele.addElement(listFile);
        }
        jlFichiers.setModel(modele);
        jlFichiers.setEnabled(true);
        btnSave.setEnabled(listFiles.length != 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(jlFichiers.getSelectedIndex() == -1) {
            showMessage("Aucun fichier sélectionné");
        }else {
            client.chooseFile(jlFichiers.getSelectedValue());
        }
    }

    void showError(String text) {
        this.getContentPane().removeAll();
        JLabel jLabel = new JLabel(text);
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        this.getContentPane().add(jLabel, BorderLayout.CENTER);

    }

    void showMessage(String text) {
        Toast toast = new Toast(text, 2000);
        toast.setVisible(true);
    }
}
