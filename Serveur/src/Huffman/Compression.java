package Huffman;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Compression implements Serializable{

    private HashMap<Character, List<Boolean>> tableCodage;
    private ArrayList<Boolean> crypted;
    private String fileName;

    public Compression(String fileName) {
        this.fileName = fileName;
        crypted = new ArrayList<>();
        tableCodage = new HashMap<>();

    }

    private String readFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private ListSorted createList(String fichier) {
        ListSorted occurences = new ListSorted();
        Element temp;
        for (int i = 0; i < fichier.length(); ++i) {
            temp = new Feuille(fichier.charAt(i));

            if (occurences.contains(temp)) {
                occurences.get(occurences.indexOf(temp)).incremente();
            } else {
                occurences.add(temp);
            }
        }
        occurences.sort();
        return occurences;
    }

    private Element buildArbre(ListSorted occurences) {
        while (occurences.size() > 1) {
            Element last1 = occurences.getLastAndRemove();
            Element last2 = occurences.getLastAndRemove();
            Element element = new Noeud(last1.getOccurence() + last2.getOccurence(), last2, last1);
            occurences.add(element);
            occurences.sort();
        }
        return occurences.get(0);
    }

    private void buildCodage(Element racine, List<Boolean> bitArray) {
        if (racine.isFeuille()) {
            tableCodage.put(((Feuille) racine).getCharacter(), new ArrayList<>(bitArray));
        } else {
            bitArray.add(false);
            buildCodage(((Noeud) racine).getGauche(), bitArray);
            bitArray.remove(bitArray.size() - 1);
            bitArray.add(true);
            buildCodage(((Noeud) racine).getDroite(), bitArray);
            bitArray.remove(bitArray.size() - 1);

        }
    }

    private List<Boolean> encryptFile(String file) {
        Character temp;
        for (int i = 0; i < file.length(); ++i) {
            temp = file.charAt(i);
            crypted.addAll(tableCodage.get(temp));
        }
        return crypted;
    }

    public List<Boolean> exec() throws IOException {
        String fichier = readFile();
        ListSorted occurences = createList(fichier);
        Element racine = buildArbre(occurences);
        buildCodage(racine, new ArrayList<>());
        return encryptFile(fichier);
    }


    public HashMap<Character, List<Boolean>> getTableCodage() {
        return tableCodage;
    }

    public ArrayList<Boolean> getCrypted() {
        return crypted;
    }
    
}
