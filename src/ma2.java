// perdorim klasen ma qe i kemi krijuar metodat
public class ma2 {
    public static void main(String[] args) {
        ma<String> li = new ma<>();
        li.push("Algjebra");
        li.push("C++");
        li.push("Data Structures in Java");
        li.push("Kodi i Da Vincit");

        ma<Integer> na = new ma<>();
        na.push(10);
        na.push(11);
        na.push(110);
        na.push(20);
        na.push(10);
        zbrasStiven(li);
        zbrasStiven(na);
        zbrasStiven2(na);
    }

    public static <E> void zbrasStiven(ma<E> stiva) {
        System.out.print("Elementet e stives: ");
        while(!stiva.isEmpty())
            System.out.print(stiva.pop()+", ");
        System.out.println();

    }
    public static void zbrasStiven2(ma<? extends Number> stiva) {
        System.out.print("Elementet e stives: ");
        while(!stiva.isEmpty())
            System.out.print(stiva.pop()+", ");
        System.out.println();

    }
}

