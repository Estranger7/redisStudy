package com.estranger.www.common.base;


import com.estranger.www.common.enumeration.ResponseStatusEnum;

public class ResponseBase<T> {

    private Integer status = ResponseStatusEnum.NORMAL_RETURNED.getStatus();

    private String desc= ResponseStatusEnum.NORMAL_RETURNED.getDesc();

    private T data;

    private Object dialog;

    public static <T> ResponseBase<T> create() {
        return new ResponseBase<T>();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getDialog() {
        return dialog;
    }

    public void setDialog(Object dialog) {
        this.dialog = dialog;
    }

    public ResponseBase<T> buildData(T t) {
        this.setData(t);
        return this;
    }

    public ResponseBase<T> buildStatus(Integer status) {
        this.setStatus(status);
        return this;
    }
    public ResponseBase<T> buildStatus(ResponseStatusEnum responseStatusEnum) {
        this.setStatus(responseStatusEnum.getStatus());
        this.setDesc(responseStatusEnum.getDesc());
        return this;
    }

    public ResponseBase<T> buildDesc(String desc) {
        this.setDesc(desc);
        return this;
    }

    public ResponseBase<T> buildDialog(Object dialog) {
        this.setDialog(dialog);
        return this;
    }
}
