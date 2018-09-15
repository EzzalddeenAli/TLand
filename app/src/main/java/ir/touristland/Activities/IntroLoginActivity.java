package ir.touristland.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.jaredrummler.android.widget.AnimatedSvgView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Classes.PermissionHandler;
import ir.touristland.Fragments.MainFragment;
import ir.touristland.Fragments.ProfileFragment;
import ir.touristland.R;

public class IntroLoginActivity extends BaseActivity implements View.OnClickListener {

    private MainFragment main_fragment = null;
    private ProfileFragment profile_fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_login);

        main_fragment = new MainFragment();
        openFragment(IntroLoginActivity.this, main_fragment);

        if (!Application.preferences.getString("IsFirstRun", "").equals("true")) {
            startActivity(new Intent(IntroLoginActivity.this, WelcomeActivity.class));
            finish();
        } else {
            HSH.vectorTop(IntroLoginActivity.this, findViewById(R.id.txt_support), R.drawable.ic_support2);
            HSH.vectorTop(IntroLoginActivity.this, findViewById(R.id.txt_home), R.drawable.ic_home);
            HSH.vectorTop(IntroLoginActivity.this, findViewById(R.id.txt_profile), R.drawable.ic_profile2);
            AnimatedSvgView svgView = findViewById(R.id.font_svg_view);
            svgView.start();

            TextView txtHome = findViewById(R.id.txt_home);
            txtHome.setOnClickListener(this);
            TextView txtSupport = findViewById(R.id.txt_support);
            txtSupport.setOnClickListener(this);
            TextView txtProfile = findViewById(R.id.txt_profile);
            txtProfile.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        HSH.editor("IsFirstRun", "true");

        if (v.getId() == R.id.txt_support)
            HSH.onOpenPage(IntroLoginActivity.this, AboutUsActivity.class);
        else if (v.getId() == R.id.txt_home)
            openFragment(IntroLoginActivity.this, main_fragment);
        else if (v.getId() == R.id.txt_profile) {
            if (profile_fragment == null)
                profile_fragment = new ProfileFragment();
            openFragment(IntroLoginActivity.this, profile_fragment);
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

    private void openFragment(Activity activity, Fragment fragment) {
        String fragmentTag = fragment.getClass().getSimpleName();
        FragmentManager fragmentManager = ((AppCompatActivity) activity)
                .getSupportFragmentManager();

        boolean fragmentPopped = fragmentManager
                .popBackStackImmediate(fragmentTag, 0);

        FragmentTransaction ftx = fragmentManager.beginTransaction();

        if ((!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) || fragmentTag.contains("Search"))
            ftx.addToBackStack(fragment.getClass().getSimpleName());

        ftx.setCustomAnimations(R.anim.slide_in_right,
                R.anim.slide_out_left, R.anim.slide_in_left,
                R.anim.slide_out_right);
        ftx.replace(R.id.frame, fragment, fragmentTag);
        ftx.commit();
    }

}
