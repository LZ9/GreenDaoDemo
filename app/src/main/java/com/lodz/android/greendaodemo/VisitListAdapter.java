package com.lodz.android.greendaodemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lodz.android.component.widget.adapter.recycler.BaseRecyclerViewAdapter;
import com.lodz.android.greendaodemo.bean.VisitBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 访查列表适配器
 * Created by zhouL on 2018/5/9.
 */
public class VisitListAdapter extends BaseRecyclerViewAdapter<VisitBean>{

    private Listener mListener;

    public VisitListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataViewHolder(getLayoutView(parent, R.layout.item_visit_list));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, int position) {
        VisitBean bean = getItem(position);
        if (bean == null){
            return;
        }
        showItem((DataViewHolder) holder, bean);
    }

    private void showItem(DataViewHolder holder, final VisitBean bean) {
        holder.nameTv.setText(bean.name);
        holder.timeTv.setText(bean.time);
        holder.contentTv.setText(bean.content);
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickUpdate(bean);
                }
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickDelete(bean);
                }
            }
        });
    }

    class DataViewHolder extends RecyclerView.ViewHolder{

        /** 姓名 */
        @BindView(R.id.name)
        TextView nameTv;
        /** 时间 */
        @BindView(R.id.time)
        TextView timeTv;
        /** 内容 */
        @BindView(R.id.content)
        TextView contentTv;
        /** 更新按钮 */
        @BindView(R.id.update_btn)
        Button updateBtn;
        /** 删除按钮 */
        @BindView(R.id.delete_btn)
        Button deleteBtn;

        private DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    public interface Listener{

        void onClickUpdate(VisitBean bean);

        void onClickDelete(VisitBean bean);
    }
}
