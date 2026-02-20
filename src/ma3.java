public class ma3 {
    public static void main(String[] args) {
        ma<String> li = new ma<>();
        li.push("Marjo");
        li.push("Matematika 2");
        li.push("Fizika 2");
        System.out.println(li);
        System.out.println(li.peek());
        li.pop();
        System.out.println(li.peek());
    }
}
