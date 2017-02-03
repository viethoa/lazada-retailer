package com.viethoa.lazadaretailer.screens.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.viethoa.lazadaretailer.R;

/**
 * Created by VietHoa on 27/04/16.
 */
public class SecureEditTextBox extends EditText implements View.OnTouchListener {

    private Context mContext;
    private DrawableWithText showDrawableRight;
    private DrawableWithText disableShowDrawableRight;
    private DrawableWithText hideDrawableRight;
    private DrawableWithText disableHideDrawableRight;


    public SecureEditTextBox(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public SecureEditTextBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public SecureEditTextBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        this.setLongClickable(false);
        this.setTextIsSelectable(false);
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setUpDrawableRight();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        this.setOnTouchListener(this);
        setUpDrawableRight();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (motionEvent.getRawX() >= this.getRight() - this.getTotalPaddingRight()) {
                if (this.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
                    this.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    this.setCompoundDrawables(null, null, showDrawableRight, null);
                } else {
                    this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    this.setCompoundDrawables(null, null, hideDrawableRight, null);
                }
                return true;
            }
        }
        return false;
    }

    private void setUpDrawableRight() {
        showDrawableRight = new DrawableWithText()
                .setmDrawable(getResources().getDrawable(R.mipmap.icon_show_active))
                .setmColor(mContext.getResources().getColor(R.color.colorPrimary))
                .allCaps(true);

        disableShowDrawableRight = new DrawableWithText()
                .setmDrawable(getResources().getDrawable(R.mipmap.icon_show_inactive))
                .setmColor(Color.GRAY)
                .allCaps(true);

        hideDrawableRight = new DrawableWithText()
                .setmDrawable(getResources().getDrawable(R.mipmap.icon_hide_active))
                .setmColor(mContext.getResources().getColor(R.color.colorPrimary))
                .allCaps(true);

        disableHideDrawableRight = new DrawableWithText()
                .setmDrawable(getResources().getDrawable(R.mipmap.icon_show_inactive))
                .setmColor(Color.GRAY)
                .allCaps(true);

        if (this.length() > 0 && this.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
            this.setCompoundDrawablesWithIntrinsicBounds(null, null, hideDrawableRight, null);
        } else if (this.length() <= 0 && this.getTransformationMethod() == HideReturnsTransformationMethod.getInstance()) {
            this.setCompoundDrawablesWithIntrinsicBounds(null, null, disableHideDrawableRight, null);
        } else if (this.length() > 0 && this.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            this.setCompoundDrawablesWithIntrinsicBounds(null, null, showDrawableRight, null);
        } else if (this.length() <= 0 && this.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
            this.setCompoundDrawablesWithIntrinsicBounds(null, null, disableShowDrawableRight, null);
        }

    }
}