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
    }
}
