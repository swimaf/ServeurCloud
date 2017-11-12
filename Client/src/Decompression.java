import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Decompression {
    private Map<List<Boolean>, Character> tableCodage;
    private ArrayList<Boolean> crypted;


    Decompression(HashMap<Character, List<Boolean>> tableCodage, ArrayList<Boolean> crypted) {
        this.crypted = crypted;
        this.tableCodage = new HashMap<>();
        for(Map.Entry<Character, List<Boolean>> entry : tableCodage.entrySet()){
            this.tableCodage.put(entry.getValue(), entry.getKey());
        }
    }

    String exec() {
        StringBuilder decrypted = new StringBuilder();
        List<Boolean> code = new ArrayList<>();
        while (crypted.size() > 0) {
            code.add(crypted.get(0));
            crypted.remove(0);
            if(tableCodage.containsKey(code)) {
                decrypted.append(tableCodage.get(code));
                code = new ArrayList<>();
            }
        }
        return decrypted.toString();
    }
}
