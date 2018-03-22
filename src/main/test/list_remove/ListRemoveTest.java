package list_remove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ListRemoveTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "d", "r"));

        /*会报java.lang.UnsupportedOperationException*/
        /*for (String e:list){
           if(e.equals("d")){
               list.remove(e);
           }
        }*/
        /*正常删除，每次调用size方法，损耗性能，不推荐*/
      /*  for(int i =0;i<list.size();i++){
            String s = list.get(i);
            if(s.contains("d")){
                list.remove(i);
            }
        }*/
        /*IndexOutOfBoundsException*/
      /*  int size = list.size();
        for(int i =0;i<size;i++){
            String s = list.get(i);
            if(s.contains("d")){
                list.remove(i);
            }
        }*/


        /*正常删除*/
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            if (next.contains("d")) {
                iterator.remove();
            }
        }
        /*lambda*/
        list.removeIf(next -> next.contains("d"));
        System.out.println(list);


    }
}
