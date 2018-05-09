package com.lodz.android.greendaolib.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.lodz.android.greendaolib.bean.VisitTable;

import com.lodz.android.greendaolib.dao.VisitTableDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig visitTableDaoConfig;

    private final VisitTableDao visitTableDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        visitTableDaoConfig = daoConfigMap.get(VisitTableDao.class).clone();
        visitTableDaoConfig.initIdentityScope(type);

        visitTableDao = new VisitTableDao(visitTableDaoConfig, this);

        registerDao(VisitTable.class, visitTableDao);
    }
    
    public void clear() {
        visitTableDaoConfig.clearIdentityScope();
    }

    public VisitTableDao getVisitTableDao() {
        return visitTableDao;
    }

}
