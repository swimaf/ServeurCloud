package Huffman;

public class Feuille extends Element {

    private Character character;

    public Feuille(Character character) {
        super(1);
        this.character = character;
    }

    @Override
    public String toString() {
        return character+ "  " + occurence;
    }

    @Override
    public boolean equals(Object obj) {
        Feuille feuille = (Feuille) obj;
        return character.equals(feuille.character);
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public Boolean isFeuille() {
        return true;
    }
}
