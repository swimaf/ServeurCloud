import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Serveur {

    private final static int PORT = 8080;

    private Fenetre fenetre;
    private List<ClientConnect> clients = new ArrayList<>();
    private ServerSocket socket;


    public static void main(String[] args) {
        Serveur serveur = new Serveur();
        serveur.show();
    }

    private Serveur() {
        this.fenetre = new Fenetre(this);
    }

    private void show() {
        this.fenetre.setVisible(true);
    }

    void exec() {

        try {
            fenetre.print("Le serveur est lancé sur 127.0.0.1:"+PORT);
            fenetre.print("Attente d'un nouvel utilisateur...");
            fenetre.setServeurButton("Arrêter serveur");
            socket = new ServerSocket(PORT);
            new Thread(() -> {
                while (!socket.isClosed()) {
                    try {
                        addClient(new ClientConnect(socket.accept(), this));
                    } catch (Exception e) {
                        fenetre.print("Un client déconnecté");
                    }
                }
            }).start();


        } catch (IOException exc) {
            fenetre.print("Le serveur n'arrive pas à démarrer ");
        }
    }

    private void addClient(ClientConnect client) {
        clients.add(client);
        new Thread(client).start();
    }

    public void print(String text) {
        fenetre.print(text);
    }

    void toggleServeur() {
        if(socket == null || socket.isClosed()) {
            exec();
        } else {
            try {
                socket.close();
                fenetre.print("Arrêt du serveur");
                fenetre.setServeurButton("Démarrer serveur");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


  