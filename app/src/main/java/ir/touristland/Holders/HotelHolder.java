package ir.touristland.Holders;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import ir.touristland.BR;
import ir.touristland.Moudle.TriangleLabelView;
import ir.touristland.R;

public class HotelHolder extends RecyclerView.ViewHolder {

    public TextView txtPrice, txtDiscount;
    public ImageView imgPost;
    public TriangleLabelView lbl_state;

    ViewDataBinding binding;

    public HotelHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        this.txtPrice = binding.getRoot().findViewById(R.id.txt_price);
        this.txtDiscount = binding.getRoot().findViewById(R.id.txt_discount);
        //this.txtAddress = binding.getRoot().findViewById(R.id.txt_address);
        this.imgPost = binding.getRoot().findViewById(R.id.img_post);
        this.lbl_state = binding.getRoot().findViewById(R.id.lbl_state);
    }

    public void bind(Object obj) {
        binding.setVariable(BR.HotelItem, obj);
        binding.executePendingBindings();
    }
}