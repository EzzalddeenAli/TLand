package ir.touristland.Activities.Kishgardi;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Activities.PlacesActivity;
import ir.touristland.Adapters.KishCentersAdapter;
import ir.touristland.Application;
import ir.touristland.Asynktask.AsynctaskGetPost;
import ir.touristland.Asynktask.AsynctaskKishgardiCenters;
import ir.touristland.Classes.HSH;
import ir.touristland.Interfaces.IWebservice;
import ir.touristland.Interfaces.KishgardiCenterInteface;
import ir.touristland.Models.CenterItem;
import ir.touristland.Models.CenterTypeItem;
import ir.touristland.R;

public class KishgardiCentersActivity extends BaseActivity {

    private final int REQ_CODE_SPEECH_INPUT1 = 100;
    CenterTypeItem fFeed;
    @Inject
    ImageLoader imageLoader;
    AsynctaskGetPost getPost;
    IWebservice.IWebservice2 m;
    private RecyclerView rv;
    private Map<String, String> params = new HashMap<>();
    private SwipeRefreshLayout swipeContainer;
    private ProgressWheel pb;
    private KishCentersAdapter adapter;
    private int MY_DATA_CHECK_CODE = 0;
    private LinearLayoutManager layoutManager;

    public void DeclareElements() {
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        swipeContainer = findViewById(R.id.swipeContainer);
        pb = findViewById(R.id.pb);
        rv = findViewById(R.id.rv_centers);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(KishgardiCentersActivity.this);
        rv.setLayoutManager(layoutManager);
        adapter = new KishCentersAdapter(KishgardiCentersActivity.this, imageLoader);
        rv.setAdapter(adapter);
        findViewById(R.id.img_centers_map).setOnClickListener(v ->
                HSH.onOpenPage(this, PlacesActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kishgardi_list);
        Application.getComponent2().Inject(this);
        DeclareElements();
        fFeed = (CenterTypeItem) getIntent().getExtras().getSerializable("feedItem");
        ((TextView) findViewById(R.id.toolbar_title)).setText(fFeed.getName());
        GetCenters();
    }

    private void GetCenters() {
        Map<String, String> params = new HashMap<>();
        params.put("CenterTypeId", String.valueOf(fFeed.getId()));
        KishgardiCenterInteface m = new KishgardiCenterInteface() {
            @Override
            public void getResult(retrofit2.Response<List<CenterItem>> list) throws Exception {
                try {
                    for (CenterItem item : list.body())
                        adapter.addItem(item);
                    pb.setVisibility(View.GONE);
                    findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
                } catch (Exception e) {
                }
            }

            @Override
            public void getError() throws Exception {
            }
        };
        new AsynctaskKishgardiCenters(KishgardiCentersActivity.this, params, m).getData();
    }
}
