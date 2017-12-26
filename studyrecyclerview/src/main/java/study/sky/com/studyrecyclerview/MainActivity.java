package study.sky.com.studyrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private List<String> datas;
    private MyRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();

        //设置recyclerView的适配器
        adapter = new MyRecyclerAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        //LayoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        datas = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            datas.add("Content" + i);
        }
    }

    @OnClick({R.id.btn_add, R.id.btn_delete, R.id.btn_list, R.id.btn_grid, R.id.btn_staggered})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                adapter.addData(0, "new Content");
                recyclerView.scrollToPosition(0);//添加的时候视图定位到第0条
                break;
            case R.id.btn_delete:
                adapter.removeData(0);
                break;
            case R.id.btn_list:
                //设置list效果
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                break;
            case R.id.btn_grid:
                //设置grid效果
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
                break;
            case R.id.btn_staggered:
                //设置瀑布流效果
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
    }
}
