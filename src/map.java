import java.util.*;

public class map {
    public static void main(String[] args) {
        Map<String, List<Integer>> pu = new HashMap<>();
        pu.put("patate", Arrays.asList(2,1,98,1));
        pu.put("domate", Arrays.asList(2,2,2,1));
        pu.put("rrush", Arrays.asList(2,1,2,4));
        pu.put("kola", Arrays.asList(99,5,2,1));

        for (String key: pu.keySet()){
            List<Integer> list = pu.get(key);
            int to = 0;
            for (Integer i: list){
                to += i;
            }
            if(to >100){
                System.out.println(key +" ka me shume se 100 shitje 🌪️ "+to);
            }
        }
    }
}