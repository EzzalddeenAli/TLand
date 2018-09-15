package ir.touristland.Activities.Flight;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.JDF;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Moudle.Roozh;
import ir.touristland.R;

public class PassengerActivity extends BaseActivity implements View.OnClickListener {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    JDF mToday = new JDF();
    String Name, Family, NameEn, FamilyNameEn, PassengerType, birthday, Sex = "2", nationalCode, s;
    private EditText etName, etFamily, etNameEn, etFamilyEn, etBirhDay, etNationalCode;
    private TextView btn_register;
    private RadioGroup rgSex;
    private RadioButton rdMale, rdFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        DeclareElements();
    }

    private void DeclareElements() {
        rgSex = findViewById(R.id.rg_sex);
        rdMale = findViewById(R.id.rd_male);
        rdFemale = findViewById(R.id.rd_female);
        rgSex.setOnCheckedChangeListener((RadioGroup group, int checkedId) ->
        {
            if (checkedId == R.id.rd_male) {
                rdMale.setBackgroundResource(R.drawable.style_textview);
                rdFemale.setBackgroundResource(R.drawable.style_edittext);
                rdMale.setTextColor(Color.WHITE);
                rdFemale.setTextColor(Color.BLACK);
                Sex = "2";
            } else {
                rdMale.setBackgroundResource(R.drawable.style_edittext2);
                rdFemale.setBackgroundResource(R.drawable.style_textview2);
                rdMale.setTextColor(Color.BLACK);
                rdFemale.setTextColor(Color.WHITE);
                Sex = "1";
            }
        });

        etName = findViewById(R.id.et_name);
        etFamily = findViewById(R.id.et_family);
        etNameEn = findViewById(R.id.et_name_en);
        etFamilyEn = findViewById(R.id.et_family_en);
        etBirhDay = findViewById(R.id.et_birthDay);
        etNationalCode = findViewById(R.id.et_nationalcode);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        etBirhDay.setOnClickListener(this);

        etName.addTextChangedListener(new addListenerOnTextChange(etName));
        etFamily.addTextChangedListener(new addListenerOnTextChange(etFamily));
        etNameEn.addTextChangedListener(new addListenerOnTextChange(etNameEn));
        etFamilyEn.addTextChangedListener(new addListenerOnTextChange(etFamilyEn));
        etBirhDay.addTextChangedListener(new addListenerOnTextChange(etBirhDay));
        etNationalCode.addTextChangedListener(new addListenerOnTextChange(etNationalCode));

        (findViewById(R.id.img_back)).setOnClickListener(v -> finish());
    }

    private void getDate(final View v) {
        new DatePicker.Builder()
                .id(v.getId())
                .future(false, "false")
                .showYearFirst(true)
                .maxYear(1397)
                .minYear(1300)
                .build(new DateSetListener() {
                    @Override
                    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year, String DayName, String monthName) {
                        Roozh jCal = new Roozh();
                        ((EditText) v).setText(HSH.toPersianNumber(DayName + " " + day + " " + monthName + " " + year));
                        v.setTag(jCal.PersianToGregorian(year, month, day));
                    }
                })
                .show(getSupportFragmentManager(), "");
    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {

            case R.id.et_birthDay: {
                getDate(etBirhDay);
                break;
            }
            case R.id.btn_register:
                //ارسال سوال
                try {
                    Name = etName.getText().toString().trim();
                    Family = etFamily.getText().toString().trim();
                    NameEn = etNameEn.getText().toString().trim();
                    FamilyNameEn = etFamilyEn.getText().toString().trim();
                    if (Name.length() < 3) {
                        error(etName, "نام را وارد نمایید(حداقل 3 حرف)");
                    } else if (Family.length() < 3)
                        error(etFamily, "نام خانوادگی را وارد نمایید");
                    else if (NameEn.length() < 3)
                        error(etNameEn, "نام(انگلیسی) را وارد نمایید");
                    else if (FamilyNameEn.length() < 3)
                        error(etFamilyEn, "نام خانوادگی(انگلیسی) را وارد نمایید");
                    else if (!checkNationalCode(etNationalCode))
                        error(etNationalCode, "کد ملی معتبر وارد نمایید");
                    else {
                        PassengerType = getIntent().getExtras().getString("PassengerType");
                        birthday = etBirhDay.getTag().toString();
                        long diff = Calculate(birthday, mToday.getGregorianDate());
                        if (PassengerType.equals("Adult") && diff < 12)
                            HSH.showtoast(PassengerActivity.this, "سن بزرگسال باید بالای 12 سال باشد");

                        else if (PassengerType.equals("Child")
                                && ((diff >= 12) || (diff < 2)))
                            HSH.showtoast(PassengerActivity.this, "سن کودک باید بین 2 تا 12 سال باشد");

                        else if (PassengerType.equals("Baby")
                                && diff >= 2)
                            HSH.showtoast(PassengerActivity.this, "سن نوزاد باید زیر 2 سال باشد");

                        else {

                            String WO = getIntent().getExtras().getString("WhichOne") + "[";
                            nationalCode = etNationalCode.getText().toString().trim();
                            if (PassengerType.equals("Adult") &&
                                    getIntent().getExtras().getString("WhichOne").equals("1")) {
                                s = "PrimeryPhoneNumber[" + "]" + "PrimeryEmail[" + "]" + "PrimeryName[" + Name + "]" + "PrimeryLatinName[" + NameEn + "]"
                                        + "PrimeryLastName[" + Family + "]" + "PrimeryLatinLastName[" + FamilyNameEn + "]"
                                        + "PrimeryNumberPasportOrMeli[" + nationalCode + "]"
                                        + "Primerysex[" + Sex + "]" + "PrimeryPassengerCode[" + nationalCode + "]";
                                RegisterPassenger();
                            } else if (PassengerType.equals("Adult")) {
                                s = "Name" + WO + Name + "]" + "LatinName" + WO + NameEn + "]" + "LastName" + WO + Family + "]" +
                                        "LatinLastName" + WO + FamilyNameEn + "]" +
                                        "NumberPasportOrMeli" + WO + nationalCode + "]" +
                                        "PassengerCode" + WO + nationalCode + "]" + "Sex" + WO + Sex + "]";
                                RegisterPassenger();
                            } else if (PassengerType.equals("Child")) {
                                s = "ChildName" + WO + Name + "]" + "ChildLatinName" + WO + NameEn + "]" + "ChildLastName" + WO + Family + "]" +
                                        "ChildLatinLastName" + WO + FamilyNameEn + "]" + "ChildBirthDay" + WO + birthday + "]" +
                                        "ChildNumberPasportOrMeli" + WO + nationalCode + "]" +
                                        "ChildPassengerCode" + WO + nationalCode + "]";
                                RegisterPassenger();
                            } else if (PassengerType.equals("Baby")) {
                                s = "InfantName" + WO + Name + "]" + "InfantLatinName" + WO + NameEn + "]" + "InfantLastName" + WO + Family + "]" +
                                        "InfantLatinLastName" + WO + FamilyNameEn + "]" + "InfantBirthDay" + WO + birthday + "]" +
                                        "InfantNumberPasportOrMeli" + WO + nationalCode + "]" +
                                        "InfantPassengerCode" + WO + nationalCode + "]";
                                RegisterPassenger();
                            }
                        }
                    }
                } catch (Exception e) {
                }

                break;

            default:
                break;
        }
    }

    private void RegisterPassenger() {
        Cursor cr = Application.database.rawQuery("SELECT Id from passengers WHERE NationalCode='" + nationalCode + "'", null);
        if (cr.getCount() == 0) {
            String query = "INSERT INTO passengers(Type,Name_fa,LastName_fa,Name_en,LastName_en,NationalCode,BirthDay,Sex,StringData) VALUES " +
                    "('" + PassengerType + "','" + Name + "','" + Family + "','" + NameEn +
                    "','" + FamilyNameEn + "','" + nationalCode + "','" + birthday + "','" + Sex + "','" + s + "') ";
            Application.database.execSQL(query);

            cr = Application.database.rawQuery("SELECT * from passengers order by Id  LIMIT 1", null);
            Intent resultData = new Intent();
            //resultData.putExtra("ViewId", getIntent().getExtras().getString("ViewId"));
            resultData.putExtra("PassengerId", getIntent().getExtras().getString("Id"));
            setResult(Activity.RESULT_OK, resultData);
            finish();
        } else {
            HSH.showtoast(PassengerActivity.this, "کد ملی تکراری می باشد");
                                    /*String query = "UPDATE passengers SET " +
                                            "Name_fa='" + Name + "' , LastName_fa = '" + Family + "', Name_en = '" + NameEn + "', LastName_en = '" + FamilyNameEn + "'" +
                                            ", NationalCode = '" + nationalCode + "', BirthDay = '" + birthday + "', Sex = '" + Sex + "' " +
                                            "WHERE NationalCode='" + nationalCode + "' ";
                                    Application.database.execSQL(query);*/
        }
    }


    private void error(EditText et, String s) {
        et.setError(HSH.setTypeFace(PassengerActivity.this, s));
        et.requestFocus();
    }

    private boolean checkNationalCode(EditText et_nationalCode) {
        String s = et_nationalCode.getText().toString();
        if (s.length() == 10) {
            int sum = 0;
            int cnt = 2;
            for (int i = s.length() - 1; i >= 1; i--) {
                try {
                    sum += Integer.parseInt(String.valueOf(s.charAt(i - 1))) * (cnt++);
                } catch (Exception e) {
                }
            }
            if ((sum % 11) < 2) {
                try {

                    if (Integer.parseInt(String.valueOf(s.substring(s.length() - 1))) == (sum % 11)) {
                        HSH.editor("nationalcode", s.trim());
                        if (HSH.isNetworkConnection(PassengerActivity.this))
                            return true;
                        else
                            HSH.showtoast(PassengerActivity.this, "خطا در اتصال به اینترنت.");
                    } else
                        return false;
                } catch (Exception e) {
                }
            } else {
                if (Integer.parseInt(String.valueOf(s.substring(s.length() - 1))) == (11 - (sum % 11))) {
                    HSH.editor("nationalcode", s.trim());
                    if (HSH.isNetworkConnection(PassengerActivity.this))
                        return true;
                    else
                        HSH.showtoast(PassengerActivity.this, "خطا در اتصال به اینترنت.");

                } else
                    return false;
            }

        } else
            return false;

        return false;
    }

    private long Calculate(String t1, String t2) {
        try {
            Date startDate = dateFormat.parse(t1);
            Date endDate = dateFormat.parse(t2);

            long diff = endDate.getTime() - startDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            int years = (int) days / 365;
            return years;
        } catch (Exception e) {
            return 0;
        }
    }

    public class addListenerOnTextChange implements TextWatcher {
        EditText mEdittextview;

        public addListenerOnTextChange(EditText edittextview) {
            super();
            this.mEdittextview = edittextview;
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mEdittextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
    }
}
