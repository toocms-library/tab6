package com.toocms.tab.widget.navigation;

/**
 * Author：Zero
 * Date：2020/11/10 17:39
 */
public class NavigationItem {

    public String name;
    public String icon;
    public String target_rule;
    public String param;
    public String expand1;
    public String expand2;
    public String expand3;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget_rule() {
        return target_rule;
    }

    public void setTarget_rule(String target_rule) {
        this.target_rule = target_rule;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getExpand1() {
        return expand1;
    }

    public void setExpand1(String expand1) {
        this.expand1 = expand1;
    }

    public String getExpand2() {
        return expand2;
    }

    public void setExpand2(String expand2) {
        this.expand2 = expand2;
    }

    public String getExpand3() {
        return expand3;
    }

    public void setExpand3(String expand3) {
        this.expand3 = expand3;
    }

    @Override
    public String toString() {
        return "NavigationItem{" +
                "name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", target_rule='" + target_rule + '\'' +
                ", param='" + param + '\'' +
                ", expand1='" + expand1 + '\'' +
                ", expand2='" + expand2 + '\'' +
                ", expand3='" + expand3 + '\'' +
                '}';
    }
}
