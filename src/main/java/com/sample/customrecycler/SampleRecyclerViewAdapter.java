package com.sample.customrecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sample.customrecycler.model.SampleModel;

import java.util.List;

/**
 * Created by  on 13/1/16.
 */
public class SampleRecyclerViewAdapter extends CustomRecyclerViewAdapter {

    private final List<SampleModel> mDataList;
    private final Context mContext;

    public  class ItemViewHolder extends CustomViewHolder{
        public TextView mMessage;

        public ItemViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mMessage = (TextView) v.findViewById(R.id.text_view_sample);

        }


    }



    public SampleRecyclerViewAdapter(Context context, List<SampleModel> list, RecyclerView recyclerView) {
        super(recyclerView);
        mDataList = list;
        mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (isItemView(viewType)) {
            //In case of multiple/single view type put your code logic here...

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_sample_recycler, parent, false);
            vh = new ItemViewHolder(v);
        } else {
            vh = getProgressViewHolder(parent);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isProgerssViewHolder(holder)) {

            //In case of multiple/single view type put your code logic here...

            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            SampleModel model = mDataList.get(position);
            viewHolder.mMessage.setText(model.getText());
        }else{
            showProgressBar(holder);
        }
    }

    @Override
    public int getItemViewType(int position) {

        //if you are using multiple view types than write your logic here and
        // whatever integer you are going to return as per your logic instead of
        // that return getViewType(THE_VALUE_TO_BE_RETURN);

        //if you are using single view type use follows:
        return getViewType(mDataList,position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
