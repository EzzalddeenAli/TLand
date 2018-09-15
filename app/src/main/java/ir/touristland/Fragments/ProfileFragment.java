package ir.touristland.Fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.touristland.Activities.AboutUsActivity;
import ir.touristland.Activities.WelcomeActivity;
import ir.touristland.Application;
import ir.touristland.Classes.BaseFragment;
import ir.touristland.Classes.HSH;
import ir.touristland.R;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    public static final String GOOGLE_CLIENT_ID = "642093830131-gkpp28n0vrl6jpgs2p4njq43tie6f2lq.apps.googleusercontent.com";
    public static TextView txt_name;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //private MainComponent component;
    @Inject
    ImageLoader imageLoader;
    @Inject
    DisplayImageOptions options;
    private Uri uri;
    private ImageView img_profile;
    private TextView txt_pic, txtCredit, txtShoppingBasket, txt_passengers,
    /*txt_myads, txt_wanted,*/ txtFinancial, txtRefund, txt_rulls,
            txt_support, txt_share, txt_exit;
    //private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private View rootView = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            Application.getComponent().Inject(this);
            //component = Application.get((AppCompatActivity) getActivity()).getComponent();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(GOOGLE_CLIENT_ID)
                    .requestEmail()
                    .requestProfile()
                    .requestScopes(new Scope(Scopes.PLUS_ME), new Scope("https://www.googleapis.com/auth/user.phonenumbers.read"), new Scope("https://www.googleapis.com/auth/user.birthday.read"))
                    .requestServerAuthCode(GOOGLE_CLIENT_ID)
                    .build();
            mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
            //mAuth = FirebaseAuth.getInstance();

          /*  txt_pic = rootView.findViewById(R.id.txt_pic);
            img_profile = rootView.findViewById(R.id.img_profile);
            txt_name = rootView.findViewById(R.id.txt_name);
            txt_name.setText(Application.preferences.getString(getString(R.string.FullName), ""));
            imageLoader.displayImage(Application.preferences.getString(getString(R.string.ProfileImage), "0"), img_profile, options);

            txt_pic.setPaintFlags(txt_pic.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txt_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    {
                        new PermissionHandler().checkPermission(getActivity(), permissions, new PermissionHandler.OnPermissionResponse() {
                            @Override
                            public void onPermissionGranted() {
                                final Dialog dialog_image_profile = new Dialog(getActivity());
                                dialog_image_profile.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog_image_profile.setContentView(R.layout.dialog_select_image_profile);
                                dialog_image_profile.setCancelable(true);
                                File dir = new File(Environment.getExternalStoragePublicDirectory("PayeBash/Profile").getPath());
                                if (!dir.exists())
                                    dir.mkdirs();
                                dialog_image_profile.findViewById(R.id.rb1).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            File file = new File(Environment.getExternalStoragePublicDirectory("PayeBash/Profile"), "file" + String.valueOf(System.currentTimeMillis() + ".jpg"));
                                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                                                uri = Uri.fromFile(file);
                                            else
                                                uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", file);
                                            camIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                            camIntent.putExtra("return-data", true);
                                            getActivity().startActivityForResult(camIntent, 0);
                                            dialog_image_profile.dismiss();
                                        } catch (Exception e) {
                                        }
                                    }
                                });

                                dialog_image_profile.findViewById(R.id.rb2).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        getActivity().startActivityForResult(pickPhoto, 1);
                                        dialog_image_profile.dismiss();
                                    }
                                });

                                //HSH.dialog(dialog_image_profile);
                                dialog_image_profile.show();
                            }

                            @Override
                            public void onPermissionDenied() {
                                HSH.showtoast(getActivity(), "برای تغییر تصویر پروفایل دسترسی را صادر نمایید.");
                            }
                        });
                    }
                }
            });*/

            txtShoppingBasket = rootView.findViewById(R.id.txt_shopping_basket);
            txt_passengers = rootView.findViewById(R.id.txt_passengers);
            txtCredit = rootView.findViewById(R.id.txt_credit);
            txtFinancial = rootView.findViewById(R.id.txt_financial);
            txtRefund = rootView.findViewById(R.id.txt_refund);
            txt_rulls = rootView.findViewById(R.id.txt_rulls);
            txt_support = rootView.findViewById(R.id.txt_support);
            txt_share = rootView.findViewById(R.id.txt_share);
            txt_exit = rootView.findViewById(R.id.txt_exit);
            txtShoppingBasket.setOnClickListener(this);
            txt_passengers.setOnClickListener(this);
            txtCredit.setOnClickListener(this);
            txtFinancial.setOnClickListener(this);
            txtRefund.setOnClickListener(this);
            txt_rulls.setOnClickListener(this);
            txt_support.setOnClickListener(this);
            txt_share.setOnClickListener(this);
            txt_exit.setOnClickListener(this);

            HSH.vectorRight(getActivity(), txtShoppingBasket, R.drawable.ic_shopping_basket);
            HSH.vectorRight(getActivity(), txt_passengers, R.drawable.ic_passengers);
            HSH.vectorRight(getActivity(), txtCredit, R.drawable.ic_credit);
            HSH.vectorRight(getActivity(), txtFinancial, R.drawable.ic_financial);
            HSH.vectorRight(getActivity(), txtRefund, R.drawable.ic_refund);
            HSH.vectorRight(getActivity(), txt_rulls, R.drawable.ic_rules);
            HSH.vectorRight(getActivity(), txt_support, R.drawable.ic_contact);
            HSH.vectorRight(getActivity(), txt_share, R.drawable.ic_share);
            HSH.vectorRight(getActivity(), txt_exit, R.drawable.ic_sign_out);
        }
        //((TitleMain) getContext()).FragName("پروفایل");
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.txt_shopping_basket:
                final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
                dialog.setTitleText("ویرایش پروفایل کاربری");
                dialog.setContentText("");
                dialog.setConfirmText("اطلاعات اصلی");
                dialog.setCancelText("اطلاعات تکمیلی");
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        /*Intent i = new Intent(getActivity(), RegisterActivity.class);
                        i.putExtra("Type", "Update");
                        startActivityForResult(i, 123);
                        dialog.dismissWithAnimation();*/
                    }
                });
                dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                    }
                });
                dialog.setCancelable(true);
                //HSH.dialog(dialog);
                break;
            case R.id.txt_passengers:
                /*Dialog dialogNotif = new Dialog(getActivity());
                dialogNotif.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogNotif.setContentView(R.layout.dialog_notification);
                final SwitchCompat compatSwitch = dialogNotif.findViewById(R.id.compatSwitch);
                final SwitchCompat compatSwitch2 = dialogNotif.findViewById(R.id.compatSwitch2);
                final SwitchCompat compatSwitch3 = dialogNotif.findViewById(R.id.compatSwitch3);
                compatSwitch.setTypeface(Application.font);
                compatSwitch2.setTypeface(Application.font);
                compatSwitch3.setTypeface(Application.font);
                compatSwitch.setChecked(Boolean.valueOf(Application.preferences.getString("BeupNotif", "true")));
                compatSwitch2.setChecked(Boolean.valueOf(Application.preferences.getString("CommentNotif", "true")));
                compatSwitch3.setChecked(Boolean.valueOf(Application.preferences.getString("EventRequestNotif", "true")));
                compatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        HSH.editor("BeupNotif", String.valueOf(compatSwitch.isChecked()));
                    }
                });
                compatSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        HSH.editor("CommentNotif", String.valueOf(compatSwitch2.isChecked()));
                    }
                });
                compatSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        HSH.editor("EventRequestNotif", String.valueOf(compatSwitch3.isChecked()));
                    }
                });
                HSH.dialog(dialogNotif);
                dialogNotif.show();*/
                break;
            case R.id.txt_credit:
                /*i = new Intent(getActivity(), WelcomeActivity.class);
                i.putExtra("advantage", "advantage");
                startActivity(i);*/
                break;
           /* case R.id.txt_myads:
                Application.myAds = 42907631;
                MyAds();
                break;
            case R.id.txt_wanted:
                Application.myAds = 1;
                FavoriteOrRecent("Wanted");
                break;*/
            case R.id.txt_financial:
                Application.myAds = 1;
                //FavoriteOrRecent("History");
                break;
            case R.id.txt_refund:
                Application.myAds = 1;
                //FavoriteOrRecent("Favorite");
                break;
            case R.id.txt_rulls:
                //HSH.Ruls(getActivity());
                break;
            case R.id.txt_support:
                HSH.onOpenPage(getActivity(), AboutUsActivity.class);
                break;
            case R.id.txt_share:
                String shareBody = "سلام.این برنامه خیلی باحاله.با پایه باش دیگه تنها نیستی و میتونی زندگی جدیدی رو تجربه کنی\n " + Application.preferences.getString("ApkUrl", "http://cafebazaar.ir/app/ir.payebash/?l=fa") + "";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "پایه باش\n\n");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری"));
                break;
            case R.id.txt_exit:
                final SweetAlertDialog _dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("خروج از حساب")
                        .setContentText(getString(R.string.sure))
                        .setConfirmText("بله")
                        .setCancelText("فعلا نه")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                try {
                                    String query = "DELETE FROM RecentVisit " +
                                            "WHERE IsFavorite != 'false' or IsMine = 'true' ";
                                    Application.database.execSQL(query);
                                } catch (Exception e1) {
                                }
                                Application.editor.clear();
                                Application.editor.apply();
                                Application.editor.commit();
                                //mAuth.signOut();
                                mGoogleSignInClient.signOut();
                                HSH.showtoast(getActivity(), "از حساب خود خارج شدید");
                                //HSH.onOpenPage(getActivity(), LoginActivity.class);
                                getActivity().finish();
                            }
                        });
                _dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        _dialog.dismissWithAnimation();
                    }
                });
                _dialog.setCancelable(true);
                HSH.dialog(_dialog);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            txt_name.setText(Application.preferences.getString(getString(R.string.FullName), ""));
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        CropImage.activity(uri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(getActivity());
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        uri = data.getData();
                        CropImage.activity(uri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(getActivity());
                    }
                    break;
            }
        } catch (Exception e) {
        }

        if (resultCode == -1 && (requestCode != 0 && requestCode != 1 && null != data))
            try {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                img_profile.setImageURI(result.getUri());
                /*if (NetworkUtils.getConnectivity(getActivity()) != false)
                    saveProfileAccount(result.getUri());
                else
                    HSH.showtoast(getActivity(), "خطا در اتصال به اینترنت");*/

            } catch (Exception e) {
            }
    }
}