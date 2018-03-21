package sort;

import java.util.Objects;
import java.util.function.Supplier;

public class Users {
    private Integer age;
    private String name;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Users() {
    }

    public Users(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public static int compareAge(Users users, Users users1) {
        return users.getAge().compareTo(users1.getAge());
    }
    public static Users create(Supplier<Users> supplier){
        return supplier.get();
    }
    public static void updateName(Users users){
        users.setName("update");
    }
    public void updateAge(){
        this.setAge(12);
    }
    public void changeAge(Users users){
        this.setAge(users.getAge()+10);
    }
    @Override
    public String toString() {
        return "Users{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users users = (Users) o;
        return Objects.equals(getAge(), users.getAge()) &&
                Objects.equals(getName(), users.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAge(), getName());
    }


}
