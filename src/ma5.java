import java.util.ArrayList;

public class ma5 {
    public static void main(String[] args) {
        ma<Object> m = new ma();
        m.push("Marjo");
        m.push(20);
        m.push('M');
        m.push(true);
        ArrayList<String> l = new ArrayList<>();
        l.add("Test1");
        l.add("Test2");
        m.push(l);
        System.out.println(m);
    }
}
