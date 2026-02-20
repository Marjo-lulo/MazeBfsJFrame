import java.util.Stack;

public class b3 {
    public static void main(String[] args) {
        String ma = "Marjo lulo";
        String[] fa = ma.split(" ");
        StringBuilder re = new StringBuilder();
        Stack<Character> s = new Stack<>();

        for (String f : fa) {
            for (char c : f.toCharArray()) {
                s.push(c);
            }
            while (!s.isEmpty()) {
                re.append(s.pop());
            }
            re.append(" ");
        }

        System.out.println(ma);
        System.out.println(re.toString().trim());
    }
}