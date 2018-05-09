package com.lodz.android.greendaolib.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * 访查表
 * Created by zhouL on 2018/5/9.
 */
@Entity
public class VisitTable {
    /** 自增主键 */
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String time;
    @NotNull
    private Date content;


    @Generated(hash = 535092961)
    public VisitTable(Long id, @NotNull String name, @NotNull String time,
            @NotNull Date content) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.content = content;
    }
    @Generated(hash = 1108262006)
    public VisitTable() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Date getContent() {
        return this.content;
    }
    public void setContent(Date content) {
        this.content = content;
    }
}
