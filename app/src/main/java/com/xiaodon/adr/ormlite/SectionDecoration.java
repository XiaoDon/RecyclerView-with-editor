package com.xiaodon.adr.ormlite;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

/**
 * Created by xiaOdon on 2017/9/6.
 */

public class SectionDecoration extends RecyclerView.ItemDecoration {
    private static final String Tag = "SectionDecoration";

    private DecorationCallBack decorationCallBack;
    private Paint paint;
    private TextPaint textPaint;
    private int topGap;
    private Paint.FontMetrics fontMetrics;

    public SectionDecoration(Context context, DecorationCallBack decorationCallBack) {

        Resources res = context.getResources();
        this.decorationCallBack = decorationCallBack;

        paint = new Paint();
        paint.setColor(res.getColor(R.color.section_decoration));


        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLACK);
        textPaint.getFontMetrics(fontMetrics);
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = new Paint.FontMetrics();
        topGap = res.getDimensionPixelSize(R.dimen.sectioned_top);

    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight();
        int childCount = parent.getChildCount();

        for(int i=0;i<childCount;i++){
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            long groupId = decorationCallBack.getGroupId(position);
            if(groupId < 0)
                return ;
            String firstLineText = decorationCallBack.getGroupFirstLine(position).toUpperCase();
            if(position == 0||isFirstInGroup(position)){
                int top = view.getTop()-topGap;
                int bottom = view.getTop();
                c.drawRect(left,top,right,bottom,paint);
                c.drawText(firstLineText,left+50,bottom-30,textPaint);
            }
        }

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int pos = parent.getChildAdapterPosition(view);
        Log.i(Tag, "getItemOffsets：" + pos);
        long groupId = decorationCallBack.getGroupId(pos);
        if (groupId < 0) return;
        if (pos == 0 || isFirstInGroup(pos)) {//同组的第一个才添加padding
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }
    }

    private boolean isFirstInGroup(int position){
        if(position == 0)
            return true;
        else {
            long preGroupId = decorationCallBack.getGroupId(position-1);
            long curGroupId = decorationCallBack.getGroupId(position);
            return preGroupId != curGroupId;
        }
    }

    public interface DecorationCallBack{
        long getGroupId(int position);
        String getGroupFirstLine(int position);
    }
}
