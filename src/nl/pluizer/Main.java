package nl.pluizer;

public class Main {

    public static void main(String[] args) {
        // Ook geen testframeworks gebruiken? dan maar zo ...
        HashMap<String, String> map = new HashMap<>();

        map.insert("Richard", "van Roy");
        map.insert("Jan", "Jaap");
        map.insert("Kees", "de Hond");
        map.insert("Kees", "de Kat", true);
        map.insert("Berend", "de Boer");

        System.out.println(map.get("Richard")); // Should print "van Roy"
        System.out.println(map.get("Jan"));     // Should print "Jaap"
        System.out.println(map.get("Kees"));    // Should print "de Kat"
        System.out.println(map.get("Berend"));  // Should print "de Boer"
        System.out.println(map.get("Piet"));    // Should print "null"

        HashMap<String, String> map2 = new HashMap<>(object -> {
            int hash = 0;
            String string = object.toString();
            for (int i=0; i<string.length(); i++) {
                char ch = string.charAt(i);
                hash ^= ch << ((7*i) % 32);
            }
            return hash;
        });

        System.out.println();

        map2.insert("Richard", "van Roy");
        map2.insert("Jan", "Jaap");
        map2.insert("Kees", "de Hond");
        map2.insert("Kees", "de Kat", true);
        map2.insert("Berend", "de Boer");

        System.out.println(map2.get("Richard")); // Should print "van Roy"
        System.out.println(map2.get("Jan"));     // Should print "Jaap"
        System.out.println(map2.get("Kees"));    // Should print "de Kat"
        System.out.println(map2.get("Berend"));  // Should print "de Boer"
        System.out.println(map2.get("Piet"));    // Should print "null"

    }
}
