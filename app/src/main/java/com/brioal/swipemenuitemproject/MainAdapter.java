package com.brioal.swipemenuitemproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.brioal.swipemenuitem.OnMenuItemClickListener;
import com.brioal.swipemenuitem.OnStatueChangeListener;
import com.brioal.swipemenuitem.SwipeMenuItemView;

/**
 * Github : https://github.com/Brioal
 * Email : brioal@foxmial.com
 * Created by Brioal on 2017/2/15.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ItemViewHolder> {
    private Context mContext;
    private Toast mToast;
    private int mOpenIndex = -1;

    public MainAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.mItemView.setOpen(false);
        holder.mTv.setText(position + "");
        holder.mItemView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onClicked(int position) {
                switch (position) {
                    case 0:
                        showToast("点击第" + position + "的置顶");
                        break;
                    case 1:
                        showToast("点击第" + position + "的删除");
                        break;
                }
            }
        });
        holder.mItemView.setOnStatueChangeListener(new OnStatueChangeListener() {
            @Override
            public void isOpened(boolean isOpen) {
                if (isOpen) {
                    if (mOpenIndex != -1&&mOpenIndex!=position) {
                        notifyItemChanged(mOpenIndex);
                    }
                    mOpenIndex = position;
                } else {
                    if (mOpenIndex == position) {
                        mOpenIndex = -1;
                    }
                }
            }
        });
        holder.mItemView.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("点击第"+position+"个");
                if (mOpenIndex != -1) {
                    notifyItemChanged(mOpenIndex);
                    mOpenIndex = -1;
                }
            }
        });
        if (mOpenIndex == holder.getAdapterPosition()) {
            if (!holder.mItemView.isOpen()) {
                holder.mItemView.setOpen(true);
            }
        } else {
            holder.mItemView.setOpen(false);
        }
    }

    public boolean isAllClose() {
        if (mOpenIndex != -1) {
            notifyItemChanged(mOpenIndex);
            mOpenIndex = -1;
            return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTv;
        private SwipeMenuItemView mItemView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTv = (TextView) itemView.findViewById(R.id.item_tv);
            mItemView = (SwipeMenuItemView) itemView;
        }
    }

    private void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

}
