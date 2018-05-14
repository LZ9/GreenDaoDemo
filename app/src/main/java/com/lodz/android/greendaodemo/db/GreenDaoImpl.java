package com.lodz.android.greendaodemo.db;

import com.lodz.android.core.utils.ArrayUtils;
import com.lodz.android.core.utils.DateUtils;
import com.lodz.android.greendaodemo.bean.VisitBean;
import com.lodz.android.greendaolib.GreenDaoManager;
import com.lodz.android.greendaolib.bean.VisitTable;
import com.lodz.android.greendaolib.dao.VisitTableDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * greendao实现数据库接口
 * Created by zhouL on 2018/5/9.
 */
public class GreenDaoImpl implements DaoHelper{
    @Override
    public DbAgent<List<VisitBean>> queryVisitList() {
        return new DbAgent<>(Observable.create(new ObservableOnSubscribe<List<VisitBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<VisitBean>> emitter) throws Exception {
                List<VisitTable> list = GreenDaoManager.get().getDaoSession()
                        .getVisitTableDao()
                        .queryBuilder()
                        .orderDesc(VisitTableDao.Properties.Time)
                        .list();
                if (ArrayUtils.isEmpty(list)){
                    if (emitter.isDisposed()){
                        return;
                    }
                    emitter.onNext(new ArrayList<VisitBean>());
                    emitter.onComplete();
                    return;
                }

                List<VisitBean> results = new ArrayList<>();
                for (VisitTable table : list) {
                    VisitBean bean = new VisitBean(table.getId(), table.getName(),
                            DateUtils.getFormatString(DateUtils.TYPE_2, table.getTime()), table.getContent());
                    results.add(bean);
                }
                if (emitter.isDisposed()){
                    return;
                }
                emitter.onNext(results);
                emitter.onComplete();
            }
        }));
    }

    @Override
    public DbAgent<Long> insertVisitData(final String name, final String content) {
        return new DbAgent<>(Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                VisitTable table = new VisitTable();
                table.setName(name);
                table.setTime(new Date());
                table.setContent(content);
                long id = GreenDaoManager.get().getDaoSession().getVisitTableDao().insert(table);
                if (emitter.isDisposed()){
                    return;
                }
                emitter.onNext(id);
                emitter.onComplete();
            }
        }));
    }

    @Override
    public DbAgent<Boolean> deleteVisitData(final long id) {
        return new DbAgent<>(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                List<VisitTable> list = GreenDaoManager.get().getDaoSession()
                        .getVisitTableDao()
                        .queryBuilder()
                        .where(VisitTableDao.Properties.Id.eq(id))
                        .list();

                if (ArrayUtils.isEmpty(list)){
                    if (emitter.isDisposed()){
                        return;
                    }
                    emitter.onNext(false);
                    emitter.onComplete();
                    return;
                }

                for (VisitTable table : list) {
                    GreenDaoManager.get().getDaoSession().getVisitTableDao().delete(table);
                }
                if (emitter.isDisposed()){
                    return;
                }
                emitter.onNext(true);
                emitter.onComplete();
            }
        }));
    }

    @Override
    public DbAgent<Boolean> updateVisitData(final long id, final String content) {
        return new DbAgent<>(Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                List<VisitTable> list = GreenDaoManager.get().getDaoSession()
                        .getVisitTableDao()
                        .queryBuilder()
                        .where(VisitTableDao.Properties.Id.eq(id))
                        .list();

                if (ArrayUtils.isEmpty(list)){
                    if (emitter.isDisposed()){
                        return;
                    }
                    emitter.onNext(false);
                    emitter.onComplete();
                    return;
                }

                for (VisitTable table : list) {
                    table.setContent(content);
                    table.setTime(new Date());
                    GreenDaoManager.get().getDaoSession().getVisitTableDao().update(table);
                }
                if (emitter.isDisposed()){
                    return;
                }
                emitter.onNext(true);
                emitter.onComplete();
            }
        }));
    }

}
