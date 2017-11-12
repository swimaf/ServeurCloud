package Huffman;

abstract public class Element {
    protected Integer occurence;
    public Element(Integer occurence) {
        this.occurence = occurence;
    }

    public Integer getOccurence() {
        return occurence;
    }

    public void setOccurence(Integer occurence) {
        this.occurence = occurence;
    }

    public void incremente() {
        occurence++;
    }

    abstract public Boolean isFeuille();
}
