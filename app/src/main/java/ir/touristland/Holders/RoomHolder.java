package ir.touristland.Holders;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.databinding.library.baseAdapters.BR;

import ir.touristland.Moudle.TriangleLabelView;
import ir.touristland.R;

public class RoomHolder extends RecyclerView.ViewHolder {

    public TextView txtService, txtExtraPerson, txtBeds, txtPrice;
    public Button btnIncrease, btnDecrease;
    public TextView btnReserv;
    public ImageView imgRoom;
    public TriangleLabelView lbl_state;
    public LinearLayout roomPossibilities;

    ViewDataBinding binding;

    public RoomHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        this.txtService = binding.getRoot().findViewById(R.id.txt_service);
        this.txtExtraPerson = binding.getRoot().findViewById(R.id.txt_extra_person);
        this.txtPrice = binding.getRoot().findViewById(R.id.txt_price);
        this.txtBeds = binding.getRoot().findViewById(R.id.txt_beds);
        this.btnIncrease = binding.getRoot().findViewById(R.id.btn_Increase);
        this.btnDecrease = binding.getRoot().findViewById(R.id.btn_Decrease);
        this.btnReserv = binding.getRoot().findViewById(R.id.btn_reserv);
        this.imgRoom = binding.getRoot().findViewById(R.id.img_room);
        this.lbl_state = binding.getRoot().findViewById(R.id.lbl_state);
        this.roomPossibilities = binding.getRoot().findViewById(R.id.room_possibilities);
    }

    public void bind(Object obj) {
        binding.setVariable(BR.RoomsItem, obj);
        binding.executePendingBindings();
    }
}