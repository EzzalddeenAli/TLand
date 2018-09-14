package ir.touristland.Moudle;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import ir.touristland.Classes.HSH;

public class CustomTextViewMedium extends TextView {
    public static Typeface FONT_NAME;


    public CustomTextViewMedium(Context context) {
        super(context);
        if (FONT_NAME == null)
            FONT_NAME = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMedium.ttf");
        this.setTypeface(FONT_NAME);
    }

    public CustomTextViewMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (FONT_NAME == null)
            FONT_NAME = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMedium.ttf");
        this.setTypeface(FONT_NAME);
    }

    public CustomTextViewMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (FONT_NAME == null)
            FONT_NAME = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMedium.ttf");
        this.setTypeface(FONT_NAME);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (text != null)
            text = HSH.toPersianNumber(text.toString());
        super.setText(text, type);
    }
}