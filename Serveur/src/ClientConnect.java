import Huffman.Compression;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientConnect implements Runnable{

    private File dir;
    private Socket socket;
    private Serveur serveur;
    private ObjectInputStream receiver;
    private ObjectOutputStream writer;

    ClientConnect(Socket socket, Serveur serveur) {
        this.socket = socket;
        this.serveur = serveur;
        this.dir = new File("serveur").getAbsoluteFile();
    }


    @Override
    public void run() {
        try {
            writer = new ObjectOutputStream(socket.getOutputStream());
            writer.flush();
            receiver = new ObjectInputStream(socket.getInputStream());
            while (socket.isConnected()) {
                writer.writeObject(dir.list());
                Compression compression = new Compression(dir + "/" + receiver.readObject());
                compression.exec();

                writer.writeObject(compression.getTableCodage());
                writer.writeObject(compression.getCrypted());
            }
        } catch (Exception e) {
            serveur.print("Le client s'est déconnecté");
            try {
                socket.close();
            } catch (IOException ignored) { }
        }
    }
}
