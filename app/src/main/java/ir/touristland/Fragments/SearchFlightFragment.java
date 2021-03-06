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

import ir.touristland.Activities.Flight.FlightActivity;
import ir.touristland.Adapters.CitiesAdapter;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Interfaces.setListenerCity;
import ir.touristland.Models.CityItem;
import ir.touristland.Models.NumberPassenger;
import ir.touristland.Moudle.Roozh;
import ir.touristland.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFlightFragment extends Fragment implements View.OnClickListener {

    private final int REQ_CODE_SPEECH_INPUT1 = 100;
    private final int REQ_CODE_SPEECH_INPUT2 = 101;
    JDF mToday = new JDF();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    Roozh jCal = new Roozh();
    View rootView = null;
    private Map<String, String> params = new HashMap<>();
    private EditText etFrom, etTo, etDeparture, etReturn;
    private ImageView mic, mic2;
    //private RadioGroup rgWay, rgSystemic;
    //private RadioButton rdOneWay, rdTwoWay, rdCharter, rdSystemic;
    private Button btnSearch;

    public void DeclareElements() {
        etFrom = rootView.findViewById(R.id.et_from);
        etTo = rootView.findViewById(R.id.et_to);
        etDeparture = rootView.findViewById(R.id.et_departure);
        etReturn = rootView.findViewById(R.id.et_return);

        etFrom.setOnClickListener(this);
        etTo.setOnClickListener(this);
        etDeparture.setOnClickListener(this);
        etReturn.setOnClickListener(this);

        /*rgWay = rootView.findViewById(R.id.rg_way);
        rdOneWay = rootView.findViewById(R.id.rd_one_way);
        rdTwoWay = rootView.findViewById(R.id.rd_tow_way);
        rgWay.setOnCheckedChangeListener((RadioGroup group, int checkedId) ->
        {
            if (checkedId == R.id.rd_one_way) {
                HSH.hide(getActivity(), rootView.findViewById(R.id.linear_return));
                rdOneWay.setBackgroundResource(R.drawable.style_textview);
                rdTwoWay.setBackgroundResource(R.drawable.style_edittext);
                rdOneWay.setTextColor(Color.WHITE);
                rdTwoWay.setTextColor(Color.BLACK);
            } else {
                HSH.display(getActivity(), rootView.findViewById(R.id.linear_return));
                rdOneWay.setBackgroundResource(R.drawable.style_edittext2);
                rdTwoWay.setBackgroundResource(R.drawable.style_textview2);
                rdOneWay.setTextColor(Color.BLACK);
                rdTwoWay.setTextColor(Color.WHITE);
            }
        });

        rgSystemic = rootView.findViewById(R.id.rg_systemic);
        rdCharter = rootView.findViewById(R.id.rd_charter);
        rdSystemic = rootView.findViewById(R.id.rd_systemic);
        rgSystemic.setOnCheckedChangeListener((RadioGroup group, int checkedId) ->
        {
            if (checkedId == R.id.rd_charter) {
                rdCharter.setBackgroundResource(R.drawable.style_textview);
                rdSystemic.setBackgroundResource(R.drawable.style_edittext);
                rdCharter.setTextColor(Color.WHITE);
                rdSystemic.setTextColor(Color.BLACK);
            } else {
                rdCharter.setBackgroundResource(R.drawable.style_edittext2);
                rdSystemic.setBackgroundResource(R.drawable.style_textview2);
                rdCharter.setTextColor(Color.BLACK);
                rdSystemic.setTextColor(Color.WHITE);
            }
        });*/

        HSH.vectorRight(getActivity(), rootView.findViewById(R.id.lbl_from), R.drawable.ic_take_off);
        HSH.vectorRight(getActivity(), rootView.findViewById(R.id.lbl_to), R.drawable.ic_landing);

        mic = rootView.findViewById(R.id.mic);
        mic.setOnClickListener(this);
        mic2 = rootView.findViewById(R.id.mic2);
        mic2.setOnClickListener(this);
        btnSearch = rootView.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_flight, container, false);
        DeclareElements();
        return rootView;
    }

    private void CheckParams() {
        String From = etFrom.getTag().toString().trim();
        String To = etTo.getTag().toString().trim();
        String Departure = etDeparture.getTag().toString().trim();
        String Return = etReturn.getTag().toString().trim();
        if (From.length() == 0) {
            if (etFrom.getText().toString().trim().length() > 0) {
                Cursor cr = Application.database.rawQuery("SELECT * from Citys Where CityNameFa like '%" + etFrom.getText() + "%' order by CityNameFa  LIMIT 1", null);
                cr.moveToFirst();
                etFrom.setTag(cr.getString(cr.getColumnIndex("Code")));
            } else {
                HSH.showtoast(getActivity(), "مبدا را انتخاب نمایید");
                selectLocation(etFrom);
            }
        } else if (To.length() == 0) {
            if (etTo.getText().toString().trim().length() > 0) {
                Cursor cr = Application.database.rawQuery("SELECT * from Citys Where CityNameFa like '%" + etTo.getText() + "%' order by CityNameFa  LIMIT 1", null);
                cr.moveToFirst();
                etTo.setTag(cr.getString(cr.getColumnIndex("Code")));
            } else {
                HSH.showtoast(getActivity(), "مقصد را انتخاب نمایید");
                selectLocation(etTo);
            }
        } else if (Departure.length() == 0)
            if (etDeparture.getText().toString().trim().length() > 0) {
                String[] temp = etDeparture.getText().toString().split("/");
                etDeparture.setTag(jCal.PersianToGregorian(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
            } else {
                HSH.showtoast(getActivity(), "تاریخ رفت را وارد نمایید");
                getDate(etDeparture);
            }
        else if (rootView.findViewById(R.id.linear_return).getVisibility() == View.VISIBLE && Return.length() == 0) {
            if (etReturn.getText().toString().trim().length() > 0) {
                String[] temp = etReturn.getText().toString().split("/");
                etReturn.setTag(jCal.PersianToGregorian(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
            } else {
                HSH.showtoast(getActivity(), "تاریخ برگشت را وارد نمایید");
                getDate(etReturn);
            }
        } else {
            //adapter.ClearFeed();
            params.put(getString(R.string.From), etFrom.getTag().toString());
            params.put(getString(R.string.To), etTo.getTag().toString());
            params.put(getString(R.string.PersianFrom), etFrom.getText().toString());
            params.put(getString(R.string.PersianTo), etTo.getText().toString());
            params.put(getString(R.string.Date), etDeparture.getTag().toString());
            params.put(getString(R.string.PersianDate), etDeparture.getText().toString());
            if (rootView.findViewById(R.id.linear_return).getVisibility() == View.VISIBLE)
                params.put(getString(R.string.ReturnDate), etReturn.getTag().toString());
            else
                params.put(getString(R.string.ReturnDate), "");
            params.put(getString(R.string.AdultCount), String.valueOf(((Spinner) rootView.findViewById(R.id.spAdult)).getSelectedItemPosition() + 1));
            params.put(getString(R.string.ChildCount), String.valueOf(((Spinner) rootView.findViewById(R.id.spChild)).getSelectedItemPosition()));
            params.put(getString(R.string.InfantCount), String.valueOf(((Spinner) rootView.findViewById(R.id.spBaby)).getSelectedItemPosition()));
            params.put(getString(R.string.CustomerId), getString(R.string.ApiSiteIDValue));

            NumberPassenger.getInstance().setNumberAdult(((Spinner) rootView.findViewById(R.id.spAdult)).getSelectedItemPosition() + 1);
            NumberPassenger.getInstance().setNumberChild(((Spinner) rootView.findViewById(R.id.spChild)).getSelectedItemPosition());
            NumberPassenger.getInstance().setNumberBaby(((Spinner) rootView.findViewById(R.id.spBaby)).getSelectedItemPosition());
            NumberPassenger.getInstance().setParams(params);
            /*if (rdCharter.isChecked())
                params.put("FlightType", "Charter");
            else
                params.put("FlightType", "Systemic");*/
            HSH.onOpenPage(getActivity(), FlightActivity.class);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.et_from:
                selectLocation(v);
                break;
            case R.id.et_to:
                selectLocation(v);
                break;
            case R.id.et_departure:
                getDate(v);
                break;
            case R.id.et_return:
                getDate(v);
                break;
            case R.id.mic:
                MIC(R.id.mic);
                break;
            case R.id.mic2:
                MIC(R.id.mic2);
                break;
            case R.id.btn_search:
                if (Calculate(etDeparture.getTag().toString(), etReturn.getTag().toString()) > -1) {
                    CheckParams();
                } else
                    HSH.showtoast(getActivity(), "تاریخ برگشت باید بعد از تاریخ رفت باشد.");
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
                        v.setTag(/*jCal.PersianToGregorian(*/year + month + day);
                        params.put(getString(R.string.GeorgianDate), jCal.PersianToGregorian(year, month, day));
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
            } else {
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                        "شهر مقصد خود را بگویید");
                startActivityForResult(intent, REQ_CODE_SPEECH_INPUT2);
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
        final CitiesAdapter adapterCity = new CitiesAdapter(getActivity(), (new setListenerCity() {
            @Override
            public void onItemCheck(CityItem item) {
                if (v instanceof Button)
                    ((Button) v).setText(item.getCityNameFa());
                else if (v instanceof EditText)
                    ((EditText) v).setText(item.getCityNameFa());
                v.setTag(item.getCode());
                dialog.dismiss();
            }

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
