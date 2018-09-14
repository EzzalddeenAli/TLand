package ir.touristland.Activities.Flight;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.touristland.Activities.BaseActivity;
import ir.touristland.Adapters.PassengersAdapter;
import ir.touristland.Application;
import ir.touristland.Classes.HSH;
import ir.touristland.Models.PassengerItem;
import ir.touristland.R;

public class AddPassengerActivity extends BaseActivity implements View.OnClickListener {

    PassengersAdapter adapter;
    Bundle bnd;
    List<PassengerItem> feed = new ArrayList<>();
    private RecyclerView rv;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passenger);
        bnd = getIntent().getExtras();
        DeclareElements();
        TextView txt_add_passenger = findViewById(R.id.txt_add_passenger);
        txt_add_passenger.setOnClickListener(this);
        HSH.vectorRight(AddPassengerActivity.this, txt_add_passenger, R.drawable.ic_add_passenger);
    }

    private void DeclareElements() {
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        rv = findViewById(R.id.rv_passenger);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(AddPassengerActivity.this);
        rv.setLayoutManager(layoutManager);
        adapter = new PassengersAdapter(AddPassengerActivity.this, feed);
        rv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_add_passenger:
                Intent i = new Intent(AddPassengerActivity.this, PassengerActivity.class);
                i.putExtra("PassengerType", bnd.getString("PassengerType"));
                i.putExtra("WhichOne", bnd.getString("WhichOne"));
                i.putExtra("Toolbar_title", bnd.getString("Toolbar_title"));
                //i.putExtra("ViewId",  bnd.getString("ViewId"));
                startActivity(i);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        feed.clear();
        Cursor cr = Application.database.rawQuery("SELECT * from passengers where Type like '%" + bnd.getString("PassengerType") + "%'", null);
        while (cr.moveToNext()) {
            PassengerItem item = new PassengerItem();
            item.setId(cr.getString(cr.getColumnIndex("Id")));
            item.setFullName(cr.getString(cr.getColumnIndex("Name_fa")) + " " + cr.getString(cr.getColumnIndex("LastName_fa")));
            item.setBirthDate(cr.getString(cr.getColumnIndex("BirthDay")));
            item.setNationalCode(cr.getString(cr.getColumnIndex("NationalCode")));
            item.setSex(cr.getString(cr.getColumnIndex("Sex")));
            feed.add(item);
        }
        adapter.notifyDataSetChanged();
    }
}
