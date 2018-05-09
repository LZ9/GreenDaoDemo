package com.lodz.android.greendaodemo.bean;

/**
 * 访查数据
 * Created by zhouL on 2018/5/9.
 */
public class VisitBean {

    /** 编号 */
    public long id;
    /** 姓名 */
    public String name;
    /** 时间 */
    public String time;
    /** 内容 */
    public String content;

    public VisitBean(long id, String name, String time, String content) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.content = content;
    }
}
