package lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lambda {
    /**
     * lambda用途：
     * 1.只有一个抽象方法的函数式接口
     * 2.集合批量操作
     * 3.流操作
     * 一行执行语句：
     * (parameter)->expression
     * 多行执行语句可以加上{}：
     * (parameter)->{statements;}
     */
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("thread start");
            System.out.println("thread end");
        });
        thread.start();
        //集合批量操作
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "d", "r"));
        list.forEach((e) ->
        {
            if (e.equals("a")) {
                System.out.println(e);
            }
        });
        //流操作
        list.stream().filter((e) -> "a".equals(e));
        System.out.println(list);
    }
}
