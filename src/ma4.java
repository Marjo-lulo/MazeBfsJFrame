public class ma4 {
    public static void main(String[] args) {
        ma<Integer> li = new ma<>();
        li.push(10);
        li.push(100);
        li.push(0);
        while(!li.isEmpty()){
            int e = li.pop();
            System.out.println(e);
        }
    }
}
