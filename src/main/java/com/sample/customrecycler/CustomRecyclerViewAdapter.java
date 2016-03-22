package com.sample.customrecycler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

/**
 * Created by Harish Sharma on 16/9/15.
 */
public abstract class CustomRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROGRESSBAR = 0;
    private static int mVisibleThreshold = 2;
    private int mLastVisibleItem, mTotalItemCount;
    private boolean loading;
    private OnLoadMoreListener mOnPullUpListener;
    private OnClickCallback mOnViewClickListener;

    public CustomRecyclerViewAdapter(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(dx==0 && dy==0)
                        return;
                    mTotalItemCount = linearLayoutManager.getItemCount();
                    mLastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && mTotalItemCount <= (mLastVisibleItem + mVisibleThreshold)) {
                        if (mOnPullUpListener != null) {
                            mOnPullUpListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public RecyclerView.ViewHolder getProgressViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.progressbar, parent, false);

        return new ProgressViewHolder(v);
    }

    public void showProgressBar(RecyclerView.ViewHolder holder) {
        ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
    }

    //used for single type of items in list
    public int getViewType(List mNotificationList, int position) {
        return mNotificationList.get(position) == null ? VIEW_PROGRESSBAR : VIEW_ITEM;
    }

    //used for multiple type of items in list
    public int getViewType(int id) {
        return id != VIEW_PROGRESSBAR ? id : VIEW_PROGRESSBAR;
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnPullUpListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnPullUpListener = onLoadMoreListener;
    }

    public boolean isItemView(int viewType) {
        return viewType != VIEW_PROGRESSBAR;
    }

    public boolean isProgerssViewHolder(RecyclerView.ViewHolder holder) {
        return holder instanceof ProgressViewHolder;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        }
    }

    public void addOnClickCallback(OnClickCallback listener){
        mOnViewClickListener =listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public CustomViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
            if(mOnViewClickListener !=null) {
                mOnViewClickListener.onViewClickListener(view, getAdapterPosition());
            }
        }
    }


    public void setDownloadingProgress(boolean status, List list) {
        if (status) {
            list.add(null);
            this.notifyItemInserted(list.size() - 1);
        } else if (list != null && list.size() > 0 && list.get(list.size() - 1) == null) {
            list.remove(list.size() - 1);
            this.notifyItemRemoved(list.size());
        }
    }

    public interface OnClickCallback {
        void onViewClickListener(View v, int position);
    }



}