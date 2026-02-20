import java.util.Scanner;

public class Ush5Rekusiviteti {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Jepni numrat: ");
        int a = sc.nextInt();
        System.out.println("Shuma e termave eshte: "+ sh(a));
    }
    public static double sh(int a){
        if(a == 0) throw  new IllegalArgumentException("Parametri nuk duhet te jete negativ.");
        if(a == 1) return 1.0;
        return 1.0/a + sh(a-1);
    }
}