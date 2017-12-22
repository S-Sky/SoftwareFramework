package study.sky.studyeventbus;

/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class MessageEventBus {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MessageEventBus{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public MessageEventBus(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
