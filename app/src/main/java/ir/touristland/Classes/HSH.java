package ir.touristland.Classes;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.touristland.Activities.Flight.CategoriesFilterDialog;
import ir.touristland.Application;
import ir.touristland.Moudle.Utils;
import ir.touristland.R;

/**
 * Created by hossein1 on 6/28/2014.
 */
public class HSH {

    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog";
    public static Snackbar _snackbar;
    private static String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};
    private static String[] englishNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static void vectorTop(Context cn, TextView view, int a) {
        Drawable drawable = AppCompatResources.getDrawable(cn, a);
        view.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
    }

    public static void vectorRight(Context cn, View view, int a) {
        Drawable drawable = AppCompatResources.getDrawable(cn, a);
        if (view instanceof TextView)
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        else if (view instanceof EditText)
            ((EditText) view).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        else if (view instanceof RadioButton)
            ((RadioButton) view).setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    public static void vectorLeft(Context cn, View view, int a) {
        Drawable drawable = AppCompatResources.getDrawable(cn, a);
        if (view instanceof TextView)
            ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        else if (view instanceof EditText)
            ((EditText) view).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        else if (view instanceof RadioButton)
            ((RadioButton) view).setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    public static void editor(String a, String b) {
        try {
            Application.editor.putString(a, b);
            Application.editor.apply();
            Application.editor.commit();
        } catch (Exception e) {
        }
    }

    public static void applyFontToMenuItem(Context cn, MenuItem mi) {
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", cn), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public static void hideViews(LinearLayout v/*, FloatingActionButton fab*/) {

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        v.animate().translationY(v.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();

        //fab.animate().translationY(fab.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    public static void showViews(LinearLayout v/*, FloatingActionButton fab*/) {
        v.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        //fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public static String Parse(String text) {
        if (text.contains("."))
            return String.format("%,d", Long.parseLong(String.valueOf(new BigDecimal(text.substring(0, text.indexOf("."))).toBigIntegerExact())))
                    + "." + text.substring(text.indexOf(".") + 1, text.length());
        else
            return String.format("%,d", Long.parseLong(String.valueOf(new BigDecimal(text).toBigIntegerExact())));
    }

    public static Spannable spannable(String text) {
        Spannable spannable = null;
        try {
            String[] temp = text.split(":");
            spannable = new SpannableString(HSH.toPersianNumber(text));
            spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#026d37")), temp[0].length() + 1, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
        }

        return spannable;
    }


    public static void myCustomSnackbar(Snackbar snackbar) {
        _snackbar = snackbar;
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_action);
        textView.setGravity(Gravity.LEFT);
        TextView textView2 = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView2.setTypeface(Application.font);
        textView.setTypeface(Application.font);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public static void dialog(Dialog dialog) {
        Window window = dialog.getWindow();
        ViewGroup.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes((WindowManager.LayoutParams) params);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public static String toEnglishNumber(String text) {
        if ("".equals(text)) return "";
        String ch, str = "";
        int i = 0;
        while (text.length() > i) {
            ch = String.valueOf(text.charAt(i));
            if (TextUtils.isDigitsOnly(ch)) str += englishNumbers[Integer.parseInt(ch)];
            else str += ch;
            i++;
        }
        return str;
    }

    public static SpannableStringBuilder setTypeFace(Context cn, String s)

    {
        SpannableStringBuilder ssbuilder = new SpannableStringBuilder(s);
        ssbuilder.setSpan(new CustomTypefaceSpan("fonts/IRANSansMedium.ttf", cn), 0, ssbuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return ssbuilder;
    }

    public static void setTypeFace(ViewGroup viewGroup, Typeface typeface) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(Application.font);
                ((TextView) view).setTextSize(13);
                //((TextView) view).setSingleLine();
            }
            if (view instanceof ViewGroup) {
                setTypeFace(((ViewGroup) view), typeface);
            }
        }
    }

    public static void setTypeFace2(ViewGroup viewGroup, Typeface typeface) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(Application.font);
                ((TextView) view).setTextSize(13);
                //((TextView) view).setSingleLine();
            }
            if (view instanceof ViewGroup) {
                setTypeFace2(((ViewGroup) view), typeface);
            }
        }
    }

    public static void onOpenPage(Context context, @SuppressWarnings("rawtypes") Class tow_class) {
        Intent intent = new Intent(context, tow_class);
        context.startActivity(intent);
    }

    public static boolean isNetworkConnection(Context context) {
        ConnectivityManager con =
                (ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = con.getActiveNetworkInfo();
        if (info == null)
            return false;
        else
            return true;
    }

    public static SweetAlertDialog onProgress_Dialog(Context context, String text) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(text);
        pDialog.setCancelable(true);
        //pDialog.show();

        /*IOSDialog dialog = new IOSDialog.Builder(context)
                .setSpinnerDuration(400)
                .setSpinnerColorRes(R.color.google_blue)
                .setMessageColorRes(R.color.mdtp_white)
                .setMessageContent(HSH.setTypeFace(context, text))
                .setCancelable(true)
                .setMessageContentGravity(Gravity.END)
                .build();*/
        return pDialog;
    }

    public static void showtoast(Context cn, String s) {
        LayoutInflater inflater = (LayoutInflater) cn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastRoot = inflater.inflate(R.layout.item_toast, null);
        TextView t = toastRoot.findViewById(R.id.text);
        t.setText(s);
        Toast toast = new Toast(cn);
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM,
                0, 150);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void selectSubject(Context ctx, View v) {
        ((Activity) ctx).startActivityForResult(new Intent(ctx, CategoriesFilterDialog.class), 456);
    }

    @SuppressLint("NewApi")
    public static String toPersianNumber(String text) {
        if (text.isEmpty()) return "";
        String out = "";
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                int number = Integer.parseInt(String.valueOf(c));
                out += persianNumbers[number];
            } else if (c == '٫') {
                out += '،';
            } else {
                out += c;
            }
        }
        return out;
    }

    public static void  animate(Context cn, ViewGroup dialog) {

        for (int i =0; i < dialog.getChildCount(); i++) {
            Animation animation   =    AnimationUtils.loadAnimation(cn, R.anim.rv_anim);
            animation.setDuration(1000);
            View v = dialog.getChildAt(0);
            v.setAnimation(animation);
            v.animate();
            animation.start();
        }
    }

    //////////////////////////////////////////////////////////////////////////
    public static void setTextViewDrawableColor(TextView textView, int x, int y, int z) {
        try {
            for (Drawable drawable : textView.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(Color.rgb(x, y, z), PorterDuff.Mode.MULTIPLY));
                }
            }
            textView.setTextColor(Color.rgb(x, y, z));
        } catch (Exception e) {
        }
    }

    public static void setMainDrawableColor(LinearLayout layout, View view) {
        for (int j = 0; j < layout.getChildCount(); j++) {
            View v = layout.getChildAt(j);
            if (v.getId() == view.getId() && v instanceof TextView) {
                setTextViewDrawableColor((TextView) v, 0, 0, 0);
            } else if (v instanceof TextView) {
                setTextViewDrawableColor((TextView) v, 150, 150, 150);
            }
        }
    }

    public static void openFragment(Activity activity, Fragment fragment) {
        String fragmentTag = fragment.getClass().getSimpleName();
        FragmentManager fragmentManager = ((AppCompatActivity) activity)
                .getSupportFragmentManager();

        boolean fragmentPopped = fragmentManager
                .popBackStackImmediate(fragmentTag, 0);

        FragmentTransaction ftx = fragmentManager.beginTransaction();

        if ((!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) || fragmentTag.contains("Search"))
            ftx.addToBackStack(fragment.getClass().getSimpleName());

        ftx.setCustomAnimations(R.anim.slide_in_right,
                R.anim.slide_out_left, R.anim.slide_in_left,
                R.anim.slide_out_right);
        ftx.replace(R.id.frame, fragment, fragmentTag);
        ftx.commit();
    }

    public static void display(final Context ctx, final View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                final Animator animator = ViewAnimationUtils.createCircularReveal(v,
                        v.getWidth() - Utils.dpToPx(ctx, 56),
                        Utils.dpToPx(ctx, 23),
                        0,
                        (float) Math.hypot(v.getWidth(), v.getHeight()));
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setEnabled(true);
                        if (v instanceof EditText)
                            ((InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                v.setVisibility(View.VISIBLE);
                if (v.getVisibility() == View.VISIBLE) {
                    animator.setDuration(500);
                    animator.start();
                    v.setEnabled(true);
                }
            } catch (Exception e) {
            }
        } else {
            v.setVisibility(View.VISIBLE);
            v.setEnabled(true);
        }
    }

    public static void hide(final Context ctx, final View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                final Animator animatorHide = ViewAnimationUtils.createCircularReveal(v,
                        v.getWidth() - Utils.dpToPx(ctx, 56),
                        Utils.dpToPx(ctx, 23),
                        (float) Math.hypot(v.getWidth(), v.getHeight()),
                        0);
                animatorHide.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorHide.setDuration(500);
                animatorHide.start();
            } catch (Exception e) {
            }
        } else {
            v.setVisibility(View.GONE);
        }
    }
}