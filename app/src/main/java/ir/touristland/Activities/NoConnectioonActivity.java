package ir.touristland.Activities;

import android.os.Bundle;
import android.view.View;

import ir.touristland.Classes.HSH;
import ir.touristland.Classes.NetworkUtils;
import ir.touristland.R;


public class NoConnectioonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connectioon);

        if (NetworkUtils.getConnectivity(NoConnectioonActivity.this) != false) {
            HSH.onOpenPage(NoConnectioonActivity.this, SplashActivity.class);
            finish();
        }
    }

    public void no_connection_retry_click(View v) {
        if (NetworkUtils.getConnectivity(NoConnectioonActivity.this) != false) {
            HSH.onOpenPage(NoConnectioonActivity.this, SplashActivity.class);
            finish();
        }
    }
}
