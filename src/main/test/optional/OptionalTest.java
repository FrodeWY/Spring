package optional;

import sort.Users;

import java.util.Optional;

public class OptionalTest {
    public static void main(String[] args) {
        Users users=new Users(1,"ll");
        Users users1=null;
        Users users2=new Users(5,"tt");
        /*ofNullable（）创建一个optional对象，允许value为null*/
        System.out.println(Optional.ofNullable(users1));
        /*get()返回当前值，如果为空则报异常*/
//        System.out.println(Optional.ofNullable(users1).get());
        /*of()创建一个optional对象，不允许value为null,否则报NullPointException*/
//        System.out.println(Optional.of(users1));
        /*orElse(other)返回当前值，如果为null则返回other*/
        System.out.println(Optional.ofNullable(users1).orElse(users2));
        /*orElseGet()和orElse()类似，只是orElseGet支持函数式接口来生成other值*/
        System.out.println(Optional.ofNullable(users1).orElseGet(() -> new Users(4, "get")));
        Optional<Users> optional = Optional.ofNullable(users1);
        /*isPresent()判断当前value是否为null，如果不为null返回true，否则false*/
        if(optional.isPresent()){
            System.out.println("not null");
        }else {
            System.out.println("null");
        }



    }
}
