package stream;

import org.springframework.util.SerializationUtils;
import sort.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        List<Users> users = initList();
        /*过滤-找出年龄大于18岁的人*/
        /*List<Users> collect = users.stream().filter((user) -> user.getAge() > 3).collect(Collectors.toList());
        collect.forEach(System.out::println);*/
        /*最大值-找出最大年龄的人*/
        /*OptionalTest<Users>max=users.stream().max((u1,u2)->u1.getAge()-u2.getAge());
        System.out.println(max.get());*/
        /*映射-归纳-求所有人的年纪总和*/
//        OptionalTest<Integer> reduce = users.stream().map(Users::getAge).reduce(Integer::sum);
//        System.out.println(reduce.get());
        /*分组-按年级分组*/
        /*Map<Integer, List<Users>> collect = users.stream().collect(Collectors.groupingBy(Users::getAge));
        System.out.println(collect.entrySet());*/
        /*创建-去重-统计 需要重写equals(),hash()*/
//        Stream<Users> stream=Stream.of(new Users(1,"li"),new Users(1,"li"),new Users(3,"sd"));
        System.out.println(users.stream().distinct().count());
    }
    public static List<Users> initList(){

        List <Users> list=new ArrayList<Users>();
        list.add(new Users(1,"lili"));
        list.add(new Users(4,"ll"));
        list.add(new Users(4,"aa"));
        list.add(new Users(2,"ii"));
        list.add(new Users(2,"ii"));
        list.add(new Users(3,"rr"));
        list.add(new Users(16,"ur"));
        return list;
    }
}
