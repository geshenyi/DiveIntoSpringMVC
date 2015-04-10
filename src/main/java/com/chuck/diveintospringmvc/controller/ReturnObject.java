package com.chuck.diveintospringmvc.controller;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by ssge on 2015/3/20.
 */
public class ReturnObject {

    public interface WithoutPasswordView {};
    public interface WithPasswordView extends WithoutPasswordView {};

    private String param1;
    private String param2;

    @JsonView(WithoutPasswordView.class)
    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    @JsonView(WithPasswordView.class)
    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
}
