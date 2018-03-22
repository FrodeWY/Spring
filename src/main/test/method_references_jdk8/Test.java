package method_references_jdk8;

import sort.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法引用是只需要使用方法的名字，而具体调用交给函数式接口，需要和Lambda表达式配合使用*/
public class Test {
    public static void main(String[] args) {

        /*class::new 调用默认构造器*/
        Users users = Users.create(Users::new);
        users.setName("dsf");
        users.setAge(2);
        /*类静态方法引用 Class::static_method*/
        List<Users> list=new ArrayList();
        list.add(users);
        list.forEach(Users::updateName);
        System.out.println(users);

        /*类普通方法引用 Class::method,方法不能带参数*/
        list.forEach(Users::updateAge);
        System.out.println(users);
        /*实例方法引用 instance::method*/
        list.forEach(users::changeAge);
        list.forEach(System.out::println);
    }
}
