import java.util.ArrayList;

public class ma<E> {
    ArrayList<E> li = new ArrayList<>();

    public int getSize(){
        return li.size();
    }
    public E peek(){
        return li.get(getSize()-1);
    }
    public E pop(){
        E o = li.get(getSize()-1);
        li.remove(getSize()-1);
        return o;
    }
    public  void  push(E o){
        li.add(o);
    }
    public boolean isEmpty(){
        return li.isEmpty();
    }
    @Override
    public String toString(){
        return "stack: " + li.toString();
    }
}
