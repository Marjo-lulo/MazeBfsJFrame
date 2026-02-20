import java.util.ArrayList;
import java.util.Random;

public class ml {
    public static void main(String[] args) {
        ArrayList<Integer> nm = new ArrayList<>();
        nm.add(10);
        nm.add(-13);
        nm.add(1);
        nm.add(0);

        System.out.println("The numbers before the shuffling: "+ nm);
        shuffle(nm);
        System.out.println("The numbers after the shuffling: "+ nm);
    }
    public static <E> void shuffle(ArrayList<E> nm){
        Random ra = new Random();
        for(int i = 0; i < nm.size(); i++){
            int Rindex = ra.nextInt(nm.size());

            E tmp = nm.get(i);
            nm.set(i, nm.get(Rindex));
            nm.set(Rindex, tmp);
        }
    }
}
