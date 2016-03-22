package com.sample.customrecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.sample.customrecycler.model.SampleModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SampleRecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private SampleRecyclerViewAdapter mAdapter;
    List<SampleModel> mList;
    int temp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_recycler);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_test);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mList = getList();
        mAdapter = new SampleRecyclerViewAdapter(this, mList, mRecyclerView);
        mAdapter.setOnPullUpListener(new SampleRecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mAdapter.setDownloadingProgress(true,mList);
                loadData();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addOnClickCallback(new CustomRecyclerViewAdapter.OnClickCallback() {
            @Override
            public void onViewClickListener(View v, int position) {
                Toast.makeText(getBaseContext(),"hello "+position,Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void loadData(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mAdapter.setDownloadingProgress(false,mList);
                mList.addAll(getList());
                mAdapter.notifyDataSetChanged();
                mAdapter.setLoaded();
            }
        },2000);
    }

    public List<SampleModel> getList() {
        List<SampleModel> list = new ArrayList<>();
        list.add(new SampleModel("1"+temp));
        list.add(new SampleModel("2"+temp));
        list.add(new SampleModel("3"+temp));
        list.add(new SampleModel("4"+temp));
        list.add(new SampleModel("5"+temp));
        list.add(new SampleModel("6"+temp));
        list.add(new SampleModel("7"+temp));
        list.add(new SampleModel("8"+temp));
        list.add(new SampleModel("9"+temp));
        list.add(new SampleModel("1"+temp));
        list.add(new SampleModel("2"+temp));
        list.add(new SampleModel("3"+temp));
        list.add(new SampleModel("4"+temp));
        list.add(new SampleModel("5"+temp));
        list.add(new SampleModel("6"+temp));
        list.add(new SampleModel("7"+temp));
        list.add(new SampleModel("8"+temp));
        list.add(new SampleModel("9"+temp));

        temp++;
        return list;
    }

}
