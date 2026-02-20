public class Bi {

    public static int ba(int[] arr, int t){
     int le = 0;
     int ri = arr.length;

     while(le < ri){
         int mid = le + (ri-le) / 2;
         if(arr[mid] == t){
             return mid;
         }else if (arr[mid] < t){
             le = mid + 1;
         }else{
             ri = mid - 1;
         }
     }
     return -1;
    }
    public static void main(String[] args){
        int[] arr = {2, 5, 8, 12, 16, 23, 38, 56};
        int t = 233;
        int index = ba(arr, t);

        if (index != -1){
            System.out.println("Found at index: "+ index);
        }else {
            System.out.println("Not found!");
        }
    }
}
