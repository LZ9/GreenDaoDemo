package com.lodz.android.greendaodemo.db;

import com.lodz.android.greendaodemo.bean.VisitBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 数据库接口
 * Created by zhouL on 2018/5/9.
 */
public interface DaoHelper {

    /** 查询访查列表 */
    Observable<List<VisitBean>> queryVisitList();

    /** 插入访查数据 */
    Observable<Long> insertVisitData(String name, String content);

    /** 删除访查数据 */
    Observable<Boolean> deleteVisitData(long id);

    /** 更新访查数据 */
    Observable<Boolean> updateVisitData(long id, String content);
}
