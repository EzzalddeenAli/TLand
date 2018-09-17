package ir.touristland.Activities.Flight

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import java.util.ArrayList

import ir.touristland.Activities.BaseActivity
import ir.touristland.Adapters.PassengersAdapter
import ir.touristland.Application
import ir.touristland.Classes.HSH
import ir.touristland.Models.PassengerItem
import ir.touristland.R

class AddPassengerActivity : BaseActivity(), View.OnClickListener {

    lateinit var adapter: PassengersAdapter
    var bnd: Bundle? = null
    var feed: MutableList<PassengerItem> = ArrayList()
    private var rv: RecyclerView? = null
    private var layoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_passenger)
        bnd = intent.extras
        DeclareElements()
        val txt_add_passenger = findViewById<TextView>(R.id.txt_add_passenger)
        txt_add_passenger.setOnClickListener(this)
        HSH.vectorRight(this@AddPassengerActivity, txt_add_passenger, R.drawable.ic_add_passenger)
    }

    private fun DeclareElements() {
        findViewById<View>(R.id.img_back).setOnClickListener { _ -> finish() }
        rv = findViewById(R.id.rv_passenger)
        rv!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this@AddPassengerActivity)
        rv!!.layoutManager = layoutManager
        adapter = PassengersAdapter(this@AddPassengerActivity, feed)
        rv!!.adapter = adapter
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.txt_add_passenger -> {
                val i = Intent(this@AddPassengerActivity, PassengerActivity::class.java)
                i.putExtra("PassengerType", bnd!!.getString("PassengerType"))
                i.putExtra("WhichOne", bnd!!.getString("WhichOne"))
                i.putExtra("Toolbar_title", bnd!!.getString("Toolbar_title"))
                //i.putExtra("ViewId",  bnd.getString("ViewId"));
                startActivity(i)
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        feed.clear()
        val cr = Application.database.rawQuery("SELECT * from passengers where Type like '%" + bnd!!.getString("PassengerType") + "%'", null)
        while (cr.moveToNext()) {
            val item = PassengerItem()
            item.id = cr.getString(cr.getColumnIndex("Id"))
            item.fullName = cr.getString(cr.getColumnIndex("Name_fa")) + " " + cr.getString(cr.getColumnIndex("LastName_fa"))
            item.birthDate = cr.getString(cr.getColumnIndex("BirthDay"))
            item.nationalCode = cr.getString(cr.getColumnIndex("NationalCode"))
            item.sex = cr.getString(cr.getColumnIndex("Sex"))
            feed.add(item)
        }
        adapter.notifyDataSetChanged()
    }
}
