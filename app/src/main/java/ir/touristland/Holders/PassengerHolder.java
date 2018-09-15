package ir.touristland.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import ir.touristland.R;

public class PassengerHolder extends RecyclerView.ViewHolder {
    public RadioButton txtName;
    public TextView txtBirthDay;
    public TextView txtNationalCode;
    public LinearLayout linearDelete;
    public LinearLayout linearEdit;

    public PassengerHolder(View view) {
        super(view);
        this.txtName = view.findViewById(R.id.txt_name);
        this.txtBirthDay = view.findViewById(R.id.txt_birthdate);
        this.txtNationalCode = view.findViewById(R.id.txt_nationalCode);
        linearDelete = itemView.findViewById(R.id.linearDelete);
        linearEdit = itemView.findViewById(R.id.linearEdit);
    }
}