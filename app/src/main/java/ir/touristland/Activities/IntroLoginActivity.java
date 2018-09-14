package ir.touristland.Activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jaredrummler.android.widget.AnimatedSvgView;
import com.makeramen.roundedimageview.RoundedImageView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.touristland.Activities.Kishgardi.KishgardiTypesActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.PermissionHandler;
import ir.touristland.R;

public class IntroLoginActivity extends BaseActivity implements View.OnClickListener {

    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_login);

        new PermissionHandler().checkPermission(IntroLoginActivity.this, permissions, new PermissionHandler.OnPermissionResponse() {
            @Override
            public void onPermissionGranted() {
            }

            @Override
            public void onPermissionDenied() {
                HSH.showtoast(IntroLoginActivity.this, "برای ورود به اپلیکیشن دسترسی ها را صادر نمایید.");
            }
        });

        if (!Application.preferences.getString("IsFirstRun", "").equals("true")) {
            startActivity(new Intent(IntroLoginActivity.this, WelcomeActivity.class));
            finish();
        } else {
            HSH.vectorTop(IntroLoginActivity.this, findViewById(R.id.txt_support), R.drawable.ic_support2);
            HSH.vectorTop(IntroLoginActivity.this, findViewById(R.id.txt_news), R.drawable.ic_rss);
            AnimatedSvgView svgView = findViewById(R.id.font_svg_view);
            svgView.start();
            RoundedImageView btnFlight = findViewById(R.id.btn_flight);
            RoundedImageView btnKishgardi = findViewById(R.id.btn_kishgardi);
            RoundedImageView btnHotel = findViewById(R.id.btn_hotel);
            btnFlight.setOnClickListener(this);
            btnKishgardi.setOnClickListener(this);
            btnHotel.setOnClickListener(this);

            TextView txtSupport = findViewById(R.id.txt_support);
            txtSupport.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        HSH.editor("IsFirstRun", "true");

        if (v.getId() == R.id.txt_support)
            HSH.onOpenPage(IntroLoginActivity.this, AboutUsActivity.class);
        else {
            Animation animation = AnimationUtils.loadAnimation(IntroLoginActivity.this,
                    R.anim.zoom_out);
            v.startAnimation(animation);
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            switch (v.getId()) {
                                case R.id.btn_flight:
                                    HSH.onOpenPage(IntroLoginActivity.this, TicketActivity.class);
                                    break;
                                case R.id.btn_kishgardi:
                                    HSH.onOpenPage(IntroLoginActivity.this, KishgardiTypesActivity.class);
                                    break;
                                case R.id.btn_hotel:
                                    Intent i = new Intent(IntroLoginActivity.this, TicketActivity.class);
                                    i.putExtra("IsHotel", "Hotel");
                                    startActivity(i);
                                    break;
                            }
                        }
                    }, 250);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return false;
    }

    public void exit() {
        final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        dialog.setTitleText("خروج از برنامه");
        dialog.setContentText("خوشحال میشیم نظر بدین");
        dialog.setConfirmText("نظر میدم");
        dialog.setCancelText("فعلا نه!خروج");
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                try {
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setData(Uri.parse("bazaar://details?id=" + getPackageName()));
                    intent.setPackage("com.farsitel.bazaar");
                    startActivity(intent);
                    dialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                System.exit(0);
                finish();
            }
        });
        HSH.dialog(dialog);
        //dialog.show();
    }

}
