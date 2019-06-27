package com.igeek.ebuy.util.pojo;

/**
 * @author hftang
 * @date 2019-06-27 10:13
 * @desc easyuitree 节点
 */
public class EasyUITreeNode {

    private long id;

    private String text;

    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EasyUITreeNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
