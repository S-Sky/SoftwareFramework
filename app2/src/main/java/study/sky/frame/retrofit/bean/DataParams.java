package study.sky.frame.retrofit.bean;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class DataParams {
    private int pagesize;
    private String time;

    public DataParams(int pagesize, String time) {
        this.pagesize = pagesize;
        this.time = time;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
