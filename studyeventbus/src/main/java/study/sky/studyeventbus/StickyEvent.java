package study.sky.studyeventbus;

/**
 * Created by Administrator on 2017/12/22 0022.
 * 粘性事件类
 */

public class StickyEvent {

    private String string;

    public StickyEvent(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
