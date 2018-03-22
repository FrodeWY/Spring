package sort;



import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionSort {
    public static void main(String[] args) {
        List<Users> users = initList();
//        users.sort((u1,u2)->u1.getAge().compareTo(u2.getAge()));
//        users.sort((Users u1,Users u2)->u1.getAge().compareTo(u2.getAge()));
//        users.sort(Users::compareAge);
        /*jdk8 升序*/
//        Collections.sort(users, Comparator.comparing(Users::getAge));
        /*jdk8 reversed() 降序*/
//        Collections.sort(users, Comparator.comparing(Users::getAge).reversed());
        /*jdk8组合排序*/
//        Collections.sort(users, Comparator.comparing(Users::getAge).reversed().thenComparing(Users::getName));
//        users.forEach((e)-> System.out.println(e.getName()+" "+e.getAge()));

        /*Map<String ,Long> map=new HashMap<>();
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            Map.Entry entry= (Map.Entry) iterator.next();
            String key= (String) entry.getKey();
            Long value= (Long) entry.getValue();
            System.out.println("key:"+key+" value:"+value);
        }*/
//      在使用增强for循环时，不能对元素进行赋值；
        int[]aa={1,2,5,2};
        for (int a :aa){
            a=2; //不能改变数组的值
        }
        System.out.println(aa[2]);
    }
    public static List<Users> initList(){

        List <Users> list=new ArrayList<Users>();
        list.add(new Users(1,"lili"));
        list.add(new Users(4,"ll"));
        list.add(new Users(4,"aa"));
        list.add(new Users(2,"ii"));
        list.add(new Users(2,"ii"));
        Users users = Users.create(Users::new);
        users.setAge(13);
        users.setName("dfsd");
        list.add(new Users(3,"rr"));
        list.add(new Users(16,"ur"));
        return list;
    }
}
