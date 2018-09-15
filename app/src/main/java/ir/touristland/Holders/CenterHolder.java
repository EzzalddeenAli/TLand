package ir.touristland.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.touristland.Application;
import ir.touristland.R;

public class CenterHolder extends RecyclerView.ViewHolder {
    public TextView txtName;
    public TextView txtAddress;
    public TextView txtTel;
    public ImageView img_post;
    //public LinearLayout mTagLayout;
    //public TriangleLabelView lbl_state;

    public CenterHolder(View view) {
        super(view);
        this.txtName = view.findViewById(R.id.txt_name);
        this.txtAddress = view.findViewById(R.id.txt_address);
        this.txtTel = view.findViewById(R.id.txt_tel);
        this.txtName.setTypeface(Application.font);
        this.txtAddress.setTypeface(Application.font);
        this.txtTel.setTypeface(Application.font);
        this.img_post = view.findViewById(R.id.img_post);
        //this.mTagLayout = view.findViewById(R.id.mTagLayout);
        //this.lbl_state = view.findViewById(R.id.lbl_state);
    }

}