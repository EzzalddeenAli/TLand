package ir.touristland.Holders;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import ir.touristland.BR;
import ir.touristland.Moudle.TriangleLabelView;
import ir.touristland.R;

public class FlightHolder extends RecyclerView.ViewHolder {

    public TextView txtStartTime;
    public TextView txt_title;
    public ImageView img_post;
    public TriangleLabelView lbl_state;

    ViewDataBinding binding;

    public FlightHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        this.txtStartTime = binding.getRoot().findViewById(R.id.txt_start_time);
        this.txt_title = binding.getRoot().findViewById(R.id.txt_title);
        this.img_post = binding.getRoot().findViewById(R.id.img_post);
        this.lbl_state = binding.getRoot().findViewById(R.id.lbl_state);
    }

    public void bind(Object obj) {
        binding.setVariable(BR.FlightItem, obj);
        binding.executePendingBindings();
    }
}