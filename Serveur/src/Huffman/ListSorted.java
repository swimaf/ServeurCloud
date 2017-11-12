package Huffman;

import java.util.ArrayList;

class ListSorted extends ArrayList<Element> {

    void sort() {
        sort((o1, o2) -> o2.occurence - o1.occurence);
    }

    Element getLastAndRemove() {
        Element e = this.get(this.size()-1);
        this.remove(this.size()-1);
        return e;
    }

}
