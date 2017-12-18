package study.sky.studyjson.bean;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class JsonToJavaObject {

    private String name;
    private int age;
    private boolean male;

    @Override
    public String toString() {
        return "JsonToJavaObject{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", male=" + male +
                '}';
    }

    public JsonToJavaObject() {
    }

    public JsonToJavaObject(String name, int age, boolean male) {
        this.name = name;
        this.age = age;
        this.male = male;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }
}
