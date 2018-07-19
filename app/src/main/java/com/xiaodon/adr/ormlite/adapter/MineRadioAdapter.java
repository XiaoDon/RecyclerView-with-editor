package com.xiaodon.adr.ormlite.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaodon.adr.ormlite.MyItem;
import com.xiaodon.adr.ormlite.R;
import com.xiaodon.adr.ormlite.bean.Loan;
import com.xiaodon.adr.ormlite.dao.LoanDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaOdon on 2017/9/6.
 */

public class MineRadioAdapter extends RecyclerView.Adapter<MineRadioAdapter.ViewHolder> {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;
    private List<MyItem> mMyItemList;
    private OnItemClickListener mOnItemClickListener;
    private LoanDao loanDao;

    public MineRadioAdapter(Context context) {
        this.context = context;
        loanDao = new LoanDao(context);
    }


    public void notifyAdapter(List<MyItem> myItem, boolean isAdd) {
        if (!isAdd) {
            this.mMyItemList = myItem;
        } else {
            this.mMyItemList.addAll(myItem);
        }
        notifyDataSetChanged();
    }

    public List<MyItem> getMyLiveList() {
        if (mMyItemList == null) {
            mMyItemList = new ArrayList<>();
        }
        return mMyItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loan, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyItemList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyItem myItem = mMyItemList.get(holder.getAdapterPosition());

        final Loan myItemLoan = loanDao.getLoanbyId(myItem.getId());

        holder.mloan_way.setText(myItemLoan.getLoan_way());
        holder.mpay_way.setText(myItemLoan.getRepay_way());
        holder.mloan_total.setText(String.valueOf(myItemLoan.getTotalmomey())+"万");
        holder.mloan_rate.setText(String.valueOf(myItemLoan.getRate())+"%");
        holder.mloan_year.setText(String.valueOf(myItemLoan.getLoad_term())+"年");
        holder.mpay_year.setText(myItemLoan.getAdvance_time());
        holder.mfirst_year.setText(myItemLoan.getFrist_time());

        if (mEditMode == MYLIVE_MODE_CHECK) {
            holder.mCheckBox.setVisibility(View.GONE);
        } else {
            holder.mCheckBox.setVisibility(View.VISIBLE);

            if (myItem.isSelected()) {
                holder.mCheckBox.setImageResource(R.mipmap.ic_checked);
            } else {
                holder.mCheckBox.setImageResource(R.mipmap.ic_uncheck);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mMyItemList);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos, List<MyItem> myLiveList);
    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.loan_way_val)
        TextView mloan_way;
        @BindView(R.id.pay_way_val)
        TextView mpay_way;
        @BindView(R.id.loan_total_val)
        TextView mloan_total;
        @BindView(R.id.loan_rate_val)
        TextView mloan_rate;
        @BindView(R.id.loan_year_val)
        TextView mloan_year;
        @BindView(R.id.pay_year_val)
        TextView mpay_year;
        @BindView(R.id.first_time_val)
        TextView mfirst_year;

        @BindView(R.id.root_view)
        RelativeLayout mRootView;
        @BindView(R.id.check_box)
        ImageView mCheckBox;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }


}
