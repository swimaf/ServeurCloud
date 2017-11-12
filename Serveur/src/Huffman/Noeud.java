package Huffman;

public class Noeud extends Element {

    private Element droite;
    private Element gauche;

    public Noeud(Integer occurence, Element droite, Element gauche ) {
        super(occurence);
        this.droite = droite;
        this.gauche = gauche;
    }

    public Element getDroite() {
        return droite;
    }

    public Element getGauche() {
        return gauche;
    }

    @Override
    public Boolean isFeuille() {
        return false;
    }
}
