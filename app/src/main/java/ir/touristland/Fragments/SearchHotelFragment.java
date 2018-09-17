package ir.touristland.Fragments;


import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alirezaafkar.sundatepicker.DatePicker;
import com.alirezaafkar.sundatepicker.components.JDF;
import com.alirezaafkar.sundatepicker.interfaces.DateSetListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ir.touristland.Activities.Hotels.HotelListActivity;
import ir.touristland.Adapters.HotelCitiesAdapter;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Models.NumberPassenger;
import ir.touristland.Moudle.Roozh;
import ir.touristland.R;

public class SearchHotelFragment extends Fragment implements View.OnClickListener {

    private final int REQ_CODE_SPEECH_INPUT1 = 100;
    JDF mToday = new JDF();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Roozh jCal = new Roozh();
    View rootView = null;
    private Map<String, String> params = new HashMap<>();
    private EditText etFrom, etDeparture;
    private Spinner spStayingTime;
    private ImageView mic;
    private Button btnSearch;

    public void DeclareElements() {
        etFrom = rootView.findViewById(R.id.et_from);
        etDeparture = rootView.findViewById(R.id.et_departure);
        spStayingTime = rootView.findViewById(R.id.sp_staying_time);

        etFrom.setOnClickListener(this);
        etDeparture.setOnClickListener(this);

        HSH.vectorRight(getActivity(), rootView.findViewById(R.id.lbl_from), R.drawable.ic_hotel3);
        //HSH.vectorRight(getActivity(), rootView.findViewById(R.id.lbl_to), R.drawable.ic_hotel3);

        mic = rootView.findViewById(R.id.mic);
        mic.setOnClickListener(this);
        btnSearch = rootView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_hotel, container, false);
        DeclareElements();
        return rootView;
    }

    private void CheckParams() {
        String From = etFrom.getTag().toString().trim();
        String Departure = etDeparture.getTag().toString().trim();
        //String Return = etReturn.getTag().toString().trim();
        if (From.length() == 0) {
            if (etFrom.getText().toString().trim().length() > 0) {
                Cursor cr = Application.database.rawQuery("SELECT * from Citys Where CityNameFa like '%" + etFrom.getText() + "%' order by CityNameFa  LIMIT 1", null);
                cr.moveToFirst();
                etFrom.setTag(cr.getString(cr.getColumnIndex("Code")));
            } else {
                HSH.showtoast(getActivity(), "مبدا را انتخاب نمایید");
                selectLocation(etFrom);
            }
        } else if (Departure.length() == 0)
            if (etDeparture.getText().toString().trim().length() > 0) {
                String[] temp = etDeparture.getText().toString().split("/");
                etDeparture.setTag(jCal.PersianToGregorian(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
            } else {
                HSH.showtoast(getActivity(), "تاریخ رفت را وارد نمایید");
                getDate(etDeparture);
            }
        /*else if (rootView.findViewById(R.id.linear_return).getVisibility() == View.VISIBLE && Return.length() == 0) {
            if (etReturn.getText().toString().trim().length() > 0) {
                String[] temp = etReturn.getText().toString().split("/");
                etReturn.setTag(jCal.PersianToGregorian(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
            } else {
                HSH.showtoast(getActivity(), "تاریخ برگشت را وارد نمایید");
                getDate(etReturn);
            }
        }*/
        else {
            //adapter.ClearFeed();
            params.put(getString(R.string.city), etFrom.getTag().toString());
            params.put(getString(R.string.PersianFrom), etFrom.getText().toString());
            params.put(getString(R.string.PersianDate), etDeparture.getText().toString());
            params.put("numberOfNights", (spStayingTime.getSelectedItemPosition() + 1) + "");
            /*params.put(getString(R.string.ChechinDateStr), etDeparture.getTag().toString());
            params.put(getString(R.string.PersianDateReturn), etReturn.getText().toString());
            params.put(getString(R.string.ChechoutDateStr), etReturn.getTag().toString());
            params.put(getString(R.string.NumberOfNights), String.valueOf(Calculate(etDeparture.getContentDescription().toString(), etReturn.getContentDescription().toString())));*/
           /* params.put(getString(R.string.AdultCount), String.valueOf(((Spinner) rootView.findViewById(R.id.spAdult)).getSelectedItemPosition() + 1));
            NumberPassenger.Companion.getInstance().).setNumberAdult(((Spinner) rootView.findViewById(R.id.spAdult)).getSelectedItemPosition() + 1);*/
           /*
            params.put(getString(R.string.ChildCount), String.valueOf(((Spinner) rootView.findViewById(R.id.spChild)).getSelectedItemPosition()));
            params.put(getString(R.string.InfantCount), String.valueOf(((Spinner) rootView.findViewById(R.id.spBaby)).getSelectedItemPosition()));
            params.put(getString(R.string.ApiSiteID), getString(R.string.ApiSiteIDValue));

            NumberPassenger.Companion.getInstance().).setNumberAdult(((Spinner) rootView.findViewById(R.id.spAdult)).getSelectedItemPosition() + 1);
            NumberPassenger.Companion.getInstance().).setNumberChild(((Spinner) rootView.findViewById(R.id.spChild)).getSelectedItemPosition());
            NumberPassenger.Companion.getInstance().).setNumberBaby(((Spinner) rootView.findViewById(R.id.spBaby)).getSelectedItemPosition());*/
            NumberPassenger.Companion.getInstance().setParams(params);
            HSH.onOpenPage(getActivity(), HotelListActivity.class);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.et_from:
                selectLocation(v);
                break;
            case R.id.et_departure:
                getDate(v);
                break;
            case R.id.mic:
                MIC(R.id.mic);
                break;
            case R.id.mic2:
                MIC(R.id.mic2);
                break;
            case R.id.btn_search:
                // if (Calculate(etDeparture.getContentDescription().toString(), etReturn.getContentDescription().toString()) > -1) {
                CheckParams();
                /*} else
                    HSH.showtoast(getActivity(), "تاریخ برگشت باید بعد از تاریخ رفت باشد.");*/
                break;
            default:
                break;
        }
    }

    private void getDate(final View v) {
        new DatePicker.Builder()
                .id(v.getId())
                .minYear(1397)
                .maxYear(1398)
                .maxMonth(12)
                .future(true, "true")
                .build(new DateSetListener() {
                    @Override
                    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year, String DayName, String monthName) {
                        ((EditText) v).setText(HSH.toPersianNumber(DayName + " " + day + " " + monthName + " " + year));
                        v.setTag(year + "/" + month + "/" + day);
                        v.setContentDescription(jCal.PersianToGregorian(year, month, day));
                        String date = "";
                        if ((month + "").length() == 1)
                            date = (year + "").substring(2, 4) + "-0" + month + "-" + day;
                        if ((day + "").length() == 1)
                            date = (year + "").substring(2, 4) + "-" + month + "-0" + day;
                        if ((day + "").length() == 1 && (month + "").length() == 1)
                            date = (year + "").substring(2, 4) + "-0" + month + "-0" + day;

                        params.put("inDate", date);
                    }
                })
                .show(getActivity().getSupportFragmentManager(), "");
    }

    private void MIC(int v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
        try {
            if (v == R.id.mic) {
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "شهر مبدا خود را بگویید");
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT1);
            }
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    "خطا",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void selectLocation(final View v) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_select_city);
        dialog.setCancelable(true);
        final LinearLayout ll_dialog = dialog.findViewById(R.id.ll_dialog);
        final TextView textTitle = dialog.findViewById(R.id.text_title);
        final ListView lv_city = dialog.findViewById(R.id.lv_city);
        final EditText txt_city = dialog.findViewById(R.id.txt_city);

        if (v.getId() == R.id.et_from)
            textTitle.setText("مبدا خود را انتخاب نمایید");
        else if (v.getId() == R.id.et_to)
            textTitle.setText("مقصد خود را انتخاب نمایید");
        final HotelCitiesAdapter adapterCity = new HotelCitiesAdapter(getActivity(), (item -> {
            if (v instanceof Button)
                ((Button) v).setText(item.getNameFa());
            else if (v instanceof EditText)
                ((EditText) v).setText(item.getNameFa());
            v.setTag(item.getName());
            dialog.dismiss();
        }));
        lv_city.setAdapter(adapterCity);

        txt_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapterCity.filter(txt_city.getText().toString().trim());
            }
        });

        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HSH.display(getActivity(), ll_dialog);
            }
        }, 50);
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
            return days;
        } catch (Exception e) {
            return 0;
        }
    }
}
