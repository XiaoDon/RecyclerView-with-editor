package com.xiaodon.adr.ormlite;

/**
 * Created by xiaOdon on 2017/9/6.
 */

public class MyItem {

    private Integer id;//database id
    private boolean Selected;

    public Integer getId() {
        return id;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }
}
