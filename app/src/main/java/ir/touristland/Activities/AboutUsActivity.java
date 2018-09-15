package ir.touristland.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.R;


public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    TextView txtEmail, txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        DeclareElements();
        try {
            SpannableString ss = new SpannableString("کلیه حقوق اپلیکیشن متعلق به شرکت راهکار آفرین مدیا کیش می باشد.");
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#026d37")), 34, 50, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            TextView txtCopyright = findViewById(R.id.txt_copyright);
            txtCopyright.setTypeface(Application.fontBold);
            txtCopyright.setText(ss);
        } catch (Exception e) {
        }
    }

    private void DeclareElements() {
        txtEmail = findViewById(R.id.txt_email);
        txtPhone = findViewById(R.id.txt_phone);
        txtEmail.setOnClickListener(this);
        txtPhone.setOnClickListener(this);
        HSH.vectorRight(AboutUsActivity.this, txtEmail, R.drawable.ic_email);
        HSH.vectorRight(AboutUsActivity.this, txtPhone, R.drawable.ic_support);
        findViewById(R.id.back).setOnClickListener(v -> finish());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_email:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@kishsolutionmakers.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                try {
                    startActivity(Intent.createChooser(i, "ارسال از طریق :"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(AboutUsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.txt_phone:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:07644421559"));
                startActivity(callIntent);
                break;
        }
    }
}