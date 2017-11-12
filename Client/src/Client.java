import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Client {
    private static final int PORT = 8080;

    private ObjectInputStream receiver;
    private ObjectOutputStream writer;
    private Fenetre fenetre;
    private HashMap<Character, List<Boolean>> tableCodage;
    private ArrayList<Boolean> crypted;
    private File file;
    private Socket socket;

    private Client() {
        fenetre = new Fenetre(this);
        fenetre.setVisible(true);
       /* JFrame jFrame = new JFrame("Etienne");
        jFrame.getContentPane().add(new Etienne().panel);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setPreferredSize(new Dimension(300, 400));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);*/
    }

    public static void main(String[] arg) {
        Client client = new Client();
        client.exec();
    }


    void chooseFile(String fileName) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("client"));
        chooser.setApproveButtonText("Valider");
        chooser.setSelectedFile(new File(fileName));
        chooser.setDialogTitle("Choisir la location du fichier");
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.file = chooser.getSelectedFile();
            try {
                writer.writeObject(fileName);
            } catch (IOException e) {
                fenetre.showMessage("Erreur lors du choix du fichier");
            }
        } else {
            fenetre.showMessage("Opération annulé");
        }

    }

    private void exec() {
        try {
            socket = new Socket("127.0.0.1", PORT);
            writer = new ObjectOutputStream(socket.getOutputStream());
            writer.flush();
            receiver = new ObjectInputStream(socket.getInputStream());

            while (socket.isConnected()) {
                Object object = receiver.readObject();
                if(object instanceof String[]) {
                    this.fenetre.setListFiles((String[])object);
                } else if(object instanceof HashMap) {
                    this.tableCodage = (HashMap<Character, List<Boolean>>) object;
                } else if(object instanceof ArrayList) {
                    this.crypted = (ArrayList<Boolean>) object;
                    Decompression decompression = new Decompression(tableCodage, crypted);
                    saveFile(decompression.exec());
                    fenetre.showMessage("Sauvegarde avec succès de "+file.getName());
                }
            }

        } catch (UnknownHostException e) {
            fenetre.showError("Destinataire inconnu");
        } catch (IOException exc) {
            fenetre.showError("Serveur arrêté");
        } catch (Exception ignored) {}
    }

    private void saveFile(String data) throws IOException {
        BufferedWriter writer=new BufferedWriter(new FileWriter(file));
        writer.write(data);
        writer.close();
    }

    void disconnect() {
        try {
            writer.close();
            receiver.close();
            socket.close();
            System.exit(0);
        } catch (IOException ignored) { }

    }


}

