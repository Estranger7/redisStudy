package com.estranger.www.model;

/**
 * Created by：Estranger
 * Description：
 * Date：2020/12/1 16:54
 */
public class Task {

    private String id;

    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
