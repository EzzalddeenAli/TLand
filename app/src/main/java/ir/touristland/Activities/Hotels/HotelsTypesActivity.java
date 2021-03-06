package ir.touristland.Activities.Hotels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Activities.Kishgardi.KishgardiCentersActivity;
import ir.touristland.Asynktask.AsynctaskHotelsTypes;
import ir.touristland.Constant.AVLoadingIndicatorView;
import ir.touristland.Interfaces.KishgardiTypeInteface;
import ir.touristland.Models.CenterTypeItem;
import ir.touristland.R;

public class HotelsTypesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kishgardi);
        ((TextView) findViewById(R.id.toolbar_title)).setText("رزرو هتل");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        ((AVLoadingIndicatorView) findViewById(R.id.avi)).show();
        GetCentersTypes();
    }

    private void GetCentersTypes() {
        KishgardiTypeInteface m = new KishgardiTypeInteface() {
            @Override
            public void getResult(retrofit2.Response<List<CenterTypeItem>> list) throws Exception {
                try {
                    LinearLayout linearKishgardi = findViewById(R.id.linear_kishgardi);
                    linearKishgardi.setGravity(Gravity.TOP);
                    linearKishgardi.removeAllViews();
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    for (CenterTypeItem item : list.body()) {
                        View view = inflater.inflate(R.layout.item_kish_type, null);
                        view.setId(item.getId());
                        ((TextView) view).setText(item.getName());
                        linearKishgardi.addView(view);
                        view.setOnClickListener(v ->
                        {
                            Intent i = new Intent(HotelsTypesActivity.this, KishgardiCentersActivity.class);
                            i.putExtra("feedItem", item);
                            startActivity(i);
                        });
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void getError() throws Exception {
            }
        };
        new AsynctaskHotelsTypes(HotelsTypesActivity.this, m).getData();
    }
}
