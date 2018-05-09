package com.lodz.android.greendaodemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lodz.android.component.base.activity.AbsActivity;
import com.lodz.android.component.rx.subscribe.observer.ProgressObserver;
import com.lodz.android.component.rx.utils.RxUtils;
import com.lodz.android.component.widget.adapter.decoration.RoundItemDecoration;
import com.lodz.android.component.widget.base.TitleBarLayout;
import com.lodz.android.core.utils.ToastUtils;
import com.lodz.android.greendaodemo.bean.VisitBean;
import com.lodz.android.greendaodemo.db.DaoHelper;
import com.lodz.android.greendaodemo.db.GreenDaoImpl;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class MainActivity extends AbsActivity {

    private static final String[] NAMES = new String[]{"Michael", "Trevor", "Franklin"};

    /** 标题栏 */
    @BindView(R.id.title_bar_layout)
    TitleBarLayout mTitleBarLayout;
    /** 输入框 */
    @BindView(R.id.input_edit)
    EditText mInputEdit;
    /** 添加按钮 */
    @BindView(R.id.add_btn)
    Button mAddBtn;
    /** 列表 */
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private VisitListAdapter mAdapter;

    private DaoHelper mDaoHelper;

    @Override
    protected int getAbsLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mAdapter = new VisitListAdapter(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.onAttachedToRecyclerView(mRecyclerView);// 如果使用网格布局请设置此方法
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(RoundItemDecoration.createBottomDivider(getContext(), 1, android.R.color.darker_gray, 0, 5));
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    protected void setListeners() {
        super.setListeners();

        // 添加按钮
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mInputEdit.getText())) {
                    ToastUtils.showShort(getContext(), "请输入插入内容");
                    return;
                }
                insertVisitData(mInputEdit.getText().toString());
            }
        });

        mAdapter.setListener(new VisitListAdapter.Listener() {

            @Override
            public void onClickUpdate(VisitBean bean) {
                if (TextUtils.isEmpty(mInputEdit.getText())) {
                    ToastUtils.showShort(getContext(), "请输入修改内容");
                    return;
                }
                updateVisitData(bean.id, mInputEdit.getText().toString());
            }

            @Override
            public void onClickDelete(VisitBean bean) {
                deleteVisitData(bean.id);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mDaoHelper = new GreenDaoImpl();
        queryVisitData();

    }

    /** 查询数据 */
    private void queryVisitData() {
        mDaoHelper.queryVisitList()
                .compose(RxUtils.<List<VisitBean>>ioToMainObservable())
                .compose(this.<List<VisitBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new ProgressObserver<List<VisitBean>>() {
                    @Override
                    public void onPgNext(List<VisitBean> list) {
                        mAdapter.setData(list);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {
                        ToastUtils.showShort(getContext(), "查询失败");
                    }
                }.create(getContext(), "查询中", false));
    }

    /**
     * 插入数据
     * @param content 内容
     */
    private void insertVisitData(String content) {
        mDaoHelper.insertVisitData(NAMES[new Random().nextInt(3)], content)
                .flatMap(new Function<Long, ObservableSource<List<VisitBean>>>() {
                    @Override
                    public ObservableSource<List<VisitBean>> apply(Long aLong) throws Exception {
                        return mDaoHelper.queryVisitList();
                    }
                })
                .compose(RxUtils.<List<VisitBean>>ioToMainObservable())
                .compose(this.<List<VisitBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new ProgressObserver<List<VisitBean>>() {
                    @Override
                    public void onPgNext(List<VisitBean> list) {
                        mAdapter.setData(list);
                        mAdapter.notifyDataSetChanged();
                        mInputEdit.setText("");
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {
                        ToastUtils.showShort(getContext(), "插入失败");
                    }
                }.create(getContext(), "正在插入数据", false));

    }

    /**
     * 删除数据
     * @param id 编号
     */
    private void deleteVisitData(long id) {
        mDaoHelper.deleteVisitData(id)
                .flatMap(new Function<Boolean, ObservableSource<List<VisitBean>>>() {
                    @Override
                    public ObservableSource<List<VisitBean>> apply(Boolean isSuccess) throws Exception {
                        if (!isSuccess){
                            throw new IllegalArgumentException("delete fail");
                        }
                        return mDaoHelper.queryVisitList();
                    }
                })
                .compose(RxUtils.<List<VisitBean>>ioToMainObservable())
                .compose(this.<List<VisitBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new ProgressObserver<List<VisitBean>>() {
                    @Override
                    public void onPgNext(List<VisitBean> list) {
                        mAdapter.setData(list);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {
                        ToastUtils.showShort(getContext(), "删除失败");
                    }
                }.create(getContext(), "正在删除数据", false));
    }

    /**
     * 更新数据
     * @param id 编号
     * @param content 内容
     */
    private void updateVisitData(long id, String content) {
        mDaoHelper.updateVisitData(id, content)
                .flatMap(new Function<Boolean, ObservableSource<List<VisitBean>>>() {
                    @Override
                    public ObservableSource<List<VisitBean>> apply(Boolean isSuccess) throws Exception {
                        if (!isSuccess){
                            throw new IllegalArgumentException("update fail");
                        }
                        return mDaoHelper.queryVisitList();
                    }
                })
                .compose(RxUtils.<List<VisitBean>>ioToMainObservable())
                .compose(this.<List<VisitBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new ProgressObserver<List<VisitBean>>() {
                    @Override
                    public void onPgNext(List<VisitBean> list) {
                        mAdapter.setData(list);
                        mAdapter.notifyDataSetChanged();
                        mInputEdit.setText("");
                    }

                    @Override
                    public void onPgError(Throwable e, boolean isNetwork) {
                        ToastUtils.showShort(getContext(), "更新失败");
                    }
                }.create(getContext(), "正在更新数据", false));
    }

}
