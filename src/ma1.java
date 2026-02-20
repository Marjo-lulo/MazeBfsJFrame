import java.util.ArrayList;

public class ma1 {
    public static void main(String[] args){
        ArrayList<String> a = new ArrayList<>();
        a.add("Marjo");
        a.add("C++");
        a.add("Java");
        a.add("Python");
        a.remove(0);
        System.out.println(a);
    }
}
