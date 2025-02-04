/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.orient.ui.widget.gridpage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author cginechen
 * @date 2016-10-21
 */

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    private Drawable mDivider;
    private int mSpanCount;

    public GridDividerItemDecoration(Context context, int spanCount) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mSpanCount = spanCount;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int position = parent.getChildLayoutPosition(child);
            int column = (position + 1) % 3;
            column  = column == 0 ? mSpanCount : column;

            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child));
            final int bottom = top + mDivider.getIntrinsicHeight();
            final int left = child.getRight() + params.rightMargin +
                    Math.round(ViewCompat.getTranslationX(child));
            final int right = left + mDivider.getIntrinsicHeight();

            mDivider.setBounds(child.getLeft(), top, right, bottom);
            mDivider.draw(c);

            if(column < mSpanCount) {
                mDivider.setBounds(left, child.getTop(), right, bottom);
                mDivider.draw(c);
            }

        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if((position+1) % mSpanCount > 0) {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        }
    }
}
