import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        map.put(1, "一");
        map.put(2, "二");
        map.put(3, "三");
        map2.put(1, 100);
        map2.put(2, 200);
        Map<String, Integer> map3 = new HashMap<>();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            Integer it = map2.get(key);
            map3.put(value, it);
        }
        for (Map.Entry<String, Integer> entry : map3.entrySet()) {
            System.out.println(entry.getKey() + "---" + entry.getValue());
        }
    }
}
