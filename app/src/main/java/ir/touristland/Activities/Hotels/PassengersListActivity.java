package ir.touristland.Activities.Hotels;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Activities.Flight.AddPassengerActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Models.NumberPassenger;
import ir.touristland.R;

public class PassengersListActivity extends BaseActivity implements View.OnClickListener {

    EditText etMobile, etEmail;
    TextView btnSelectTicket;
    int viewId;
    int numberPassenger;

    private void DeclareElements() {
        findViewById(R.id.img_back).setOnClickListener(this);
        etMobile = findViewById(R.id.et_mobile);
        etEmail = findViewById(R.id.et_email);
        btnSelectTicket = findViewById(R.id.btn_select_ticket);
        btnSelectTicket.setOnClickListener(this);
        etMobile.setText(HSH.toPersianNumber(Application.preferences.getString("Mobile", "")));
        etEmail.setText(Application.preferences.getString("Email", ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passengers_list_hotel);

        DeclareElements();

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        LinearLayout linearAdult = findViewById(R.id.linearAdult);
        for (int i = 1; i <= NumberPassenger.getInstance().getNumberAdult(); i++) {
            view = inflater.inflate(R.layout.item_passenger, null);
            TextView v = view.findViewById(R.id.text);
            v.setId(i);
            v.setText(HSH.toPersianNumber("اطلاعات مسافر " + String.valueOf(i)));
            v.setTag(String.valueOf(i));
            v.setOnClickListener(this);
            HSH.vectorRight(PassengersListActivity.this, v, R.drawable.ic_man);
            linearAdult.addView(view);
        }
        if (NumberPassenger.getInstance().getNumberChild() == 0)
            findViewById(R.id.txt_child).setVisibility(View.GONE);
        if (NumberPassenger.getInstance().getNumberBaby() == 0)
            findViewById(R.id.txt_baby).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_select_ticket:
                String mob = HSH.toEnglishNumber(etMobile.getText().toString().trim());
                String email = etEmail.getText().toString().trim();
                if (mob.length() != 11 || !mob.startsWith("09"))
                    HSH.showtoast(PassengersListActivity.this, "شماره موبایل خود را وارد کنید");
                else if (email.length() < 10 || !email.contains("@"))
                    HSH.showtoast(PassengersListActivity.this, "ایمیل خود را وارد کنید");
                else {
                    Application.editor.putString("Mobile", mob);
                    Application.editor.putString("Email", email);
                    Application.editor.apply();
                    Application.editor.commit();
                    String StringData = "[";
                    String passengersIds = "";
                    numberPassenger = NumberPassenger.getInstance().getNumberAdult();
                    for (int i = 1; i <= numberPassenger; i++) {
                        if (null == findViewById(i).getContentDescription()) {
                            HSH.showtoast(PassengersListActivity.this, "لطفا مسافر شماره " + String.valueOf(i) + " را انتخاب کنید");
                            break;
                        }
                        Cursor cr = Application.database.rawQuery("SELECT * from passengers WHERE Id='" + findViewById(i).getContentDescription() + "'", null);
                        cr.moveToFirst();
                        StringData += "{Name:" + cr.getString(cr.getColumnIndex("Name_fa")) + "," +
                                "Family:" + cr.getString(cr.getColumnIndex("LastName_fa")) + "," +
                                "NationalCode:" + cr.getString(cr.getColumnIndex("NationalCode")) + "}";
                        passengersIds += cr.getString(cr.getColumnIndex("Id")) + ",";
                        if (i == numberPassenger) {
                            StringData = "]";
                            //StringData = StringData.replace("PrimeryPhoneNumber[", "PrimeryPhoneNumber[" + mob);
                            //StringData = StringData.replace("PrimeryEmail[", "PrimeryEmail[" + email);
                            Intent in = new Intent(PassengersListActivity.this, ir.touristland.Activities.Hotels.AssignmentActivity.class);
                            in.putExtra("Ids", passengersIds);
                            in.putExtra("Email", email);
                            in.putExtra("Mobile", mob);
                            in.putExtra("RoomItem", getIntent().getExtras().getSerializable("RoomItem"));
                            in.putExtra("Date", getIntent().getExtras().getString("Date"));

                            startActivity(in);
                        }
                    }
                }
                break;
            default:
                viewId = v.getId();
                LinearLayout l = (LinearLayout) (v.getParent()).getParent();
                Intent i = new Intent(PassengersListActivity.this, AddPassengerActivity.class);
                i.putExtra("PassengerType", l.getTag().toString());
                i.putExtra("WhichOne", v.getTag().toString());
                //i.putExtra("ViewId", v.getId());
                i.putExtra("Toolbar_title", ((TextView) v).getText().toString());
                startActivityForResult(i, 123);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 123) {
                TextView v = findViewById(viewId);
                Cursor cr = Application.database.rawQuery("SELECT * from passengers WHERE Id='" + Integer.parseInt(data.getStringExtra("PassengerId")) + "'", null);
                cr.moveToFirst();
                v.setContentDescription(data.getStringExtra("PassengerId"));
                v.setText(cr.getString(cr.getColumnIndex("Name_fa")) + " " + cr.getString(cr.getColumnIndex("LastName_fa")));
            }
        } catch (Exception e) {
        }
    }

}