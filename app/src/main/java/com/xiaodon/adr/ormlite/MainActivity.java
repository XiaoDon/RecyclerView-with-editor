package com.xiaodon.adr.ormlite;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaodon.adr.ormlite.adapter.MineRadioAdapter;
import com.xiaodon.adr.ormlite.bean.Loan;
import com.xiaodon.adr.ormlite.dao.LoanDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiaOdon on 2017/9/6.
 */

public class MainActivity extends Activity implements View.OnClickListener,MineRadioAdapter.OnItemClickListener{

    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_select_num)
    TextView mTvSelectNum;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.select_all)
    TextView mSelectAll;
    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout mLlMycollectionBottomDialog;
    @BindView(R.id.btn_editor)
    TextView mBtnEditor;

    MineRadioAdapter mRadioAdapter = null;
    private LinearLayoutManager mLinearLayoutManager;
    private List<MyItem> myItemsList = new ArrayList<>();
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;
    private LoanDao mLoanDao = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //初始化
        initview();
        initdata();
        initlistener();
    }

    public void initview(){

    }

    public void initdata() {

        //数据结构插入测试
        //Loan lb1 = new Loan("商业","等额本息",250,4,25,"2018.12","2017.12");
        //LoanDao loanDao = new LoanDao(getApplicationContext());
        //Loan loan1 = loanDao.getLoanbyId(1);
        //Log.e("xiaodon", loan1.getAdvance_time());

        //添加adapter
        mRadioAdapter = new MineRadioAdapter(this);
        mRecyclerview.setAdapter(mRadioAdapter);
        //添加layoutmanager
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        //添加diliver_line
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        itemDecorationHeader.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_main_bg_height_1));
        mRecyclerview.addItemDecoration(itemDecorationHeader);
        //添加自定义分组头部sectiondecoration
        mRecyclerview.addItemDecoration(new SectionDecoration(this, new SectionDecoration.DecorationCallBack() {
            @Override
            public long getGroupId(int position) {
                String reg_time = mLoanDao.getLoanbyId(mRadioAdapter.getMyLiveList().get(position).getId()).getReg_time();
                //String[] str1 = reg_time.split("年"); int year = Integer.parseInt(str1[0]);
                //String[] str2 = str1[1].split("月"); int mon = Integer.parseInt(str2[0]);
                //String[] str3 = str2[1].split("日")；int day = Integer.parseInt(str3[0]);
                reg_time = reg_time.replace("年","");
                reg_time = reg_time.replace("月","");
                reg_time = reg_time.replace("日","");
                return Long.parseLong(reg_time);
            }

            @Override
            public String getGroupFirstLine(int position) {

                String reg_time = mLoanDao.getLoanbyId(mRadioAdapter.getMyLiveList().get(position).getId()).getReg_time();
                return reg_time;
            }
        }));


        mLoanDao = new LoanDao(this);
        //清空表
        mLoanDao.delAll();

        //批量产生item's raw data
        for(int i=0;i<10;i++) {
            Loan mLoan = new Loan();
            mLoan.setAdvance_time("2018.12." + i);
            mLoan.setFrist_time("2017.12." + i);
            mLoan.setLoad_term(i);
            mLoan.setLoan_way("商业");
            mLoan.setRate(i+i/10);
            mLoan.setRepay_way("等额本息" + i);
            mLoan.setTotalmomey(i);

            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH);
            int day = now.get(Calendar.DAY_OF_MONTH);
            String reg_time = ""+year+"年"+month+"月"+day+"日";
            mLoan.setReg_time(reg_time);//注册时间

            mLoanDao.add(mLoan);
        }
        List<Loan> loanList = mLoanDao.getAllLoan();
        for(Loan l:loanList){
            MyItem mMyItemraw = new MyItem();
            mMyItemraw.setId(l.getLoan_id());
            mMyItemraw.setSelected(false);
            myItemsList.add(mMyItemraw);
            Log.e("读取数据：", l.getReg_time());
        }
        mRadioAdapter.notifyAdapter(myItemsList, false);
    }

    private void initlistener() {
        mRadioAdapter.setOnItemClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mSelectAll.setOnClickListener(this);
        mBtnEditor.setOnClickListener(this);
    }

    /**
     * 根据选择的数量是否为0来判断按钮是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            mBtnDelete.setBackgroundResource(R.drawable.button_shape);
            mBtnDelete.setEnabled(true);
            mBtnDelete.setTextColor(Color.WHITE);
        } else {
            mBtnDelete.setBackgroundResource(R.drawable.button_noclickable_shape);
            mBtnDelete.setEnabled(false);
            mBtnDelete.setTextColor(ContextCompat.getColor(this, R.color.color_b7b8bd));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete:
                deleteVideo();
                break;
            case R.id.select_all:
                selectAllMain();
                break;
            case R.id.btn_editor:
                updataEditMode();
                break;
            default:
                break;
        }
    }

    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (mRadioAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                mRadioAdapter.getMyLiveList().get(i).setSelected(true);
            }
            index = mRadioAdapter.getMyLiveList().size();
            mBtnDelete.setEnabled(true);
            mSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                mRadioAdapter.getMyLiveList().get(i).setSelected(false);
            }
            index = 0;
            mBtnDelete.setEnabled(false);
            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        mRadioAdapter.notifyDataSetChanged();
        setBtnBackground(index);
        mTvSelectNum.setText(String.valueOf(index));
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0){
            mBtnDelete.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = mRadioAdapter.getMyLiveList().size(), j =0 ; i > j; i--) {
                    MyItem myLive = mRadioAdapter.getMyLiveList().get(i-1);
                    if (myLive.isSelected()) {
                        int removeId = myLive.getId();
                        mLoanDao.del(removeId);
                        mRadioAdapter.getMyLiveList().remove(myLive);
                        index--;
                    }
                }
                index = 0;
                mTvSelectNum.setText(String.valueOf(0));
                setBtnBackground(index);
                if (mRadioAdapter.getMyLiveList().size() == 0){
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                }
                mRadioAdapter.notifyDataSetChanged();
                builder.dismiss();
            }
        });
    }
    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            mBtnEditor.setText("取消");
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
            //隐藏item的选中图标
            for(int i=0,j = mRadioAdapter.getMyLiveList().size();i<j;i++){
                MyItem myItem = mRadioAdapter.getMyLiveList().get(i);
                if(myItem.isSelected()){
                    myItem.setSelected(false);
                }
            }
            index = 0;
            editorStatus = true;
        } else {
            mBtnEditor.setText("编辑");
            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            editorStatus = false;
            clearAll();
        }
        mRadioAdapter.setEditMode(mEditMode);
    }

    private void clearAll() {
        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        mSelectAll.setText("全选");
        setBtnBackground(0);
    }



    @Override
    public void onItemClickListener(int pos, List<MyItem> myLiveList) {
        if (editorStatus) {
            MyItem myLive = myLiveList.get(pos);
            boolean isSelect = myLive.isSelected();
            if (!isSelect) {
                index++;
                myLive.setSelected(true);
                if (index == myLiveList.size()) {
                    isSelectAll = true;
                    mSelectAll.setText("取消全选");
                }

            } else {
                myLive.setSelected(false);
                index--;
                isSelectAll = false;
                mSelectAll.setText("全选");
            }
            setBtnBackground(index);
            mTvSelectNum.setText(String.valueOf(index));
            mRadioAdapter.notifyDataSetChanged();
        }
    }
}
