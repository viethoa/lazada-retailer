package com.viethoa.lazadaretailer.screens.home;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.utilities.DeviceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by VietHoa on 19/05/16.
 */
public class DialogMessage extends Dialog {
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.txt_message)
    TextView txtMessage;
    @Bind(R.id.btn_negative)
    TextView btnNegative;
    @Bind(R.id.btn_positive)
    TextView btnPositive;

    public DialogMessage(Context context, @StringRes int title, @StringRes int message, @StringRes int singleButtonText,
                         MessageDialogSingleListener listener) {
        super(context, R.style.Window_DialogStyle);
        initialize(context.getString(title), context.getString(message), context.getString(singleButtonText), null, listener, null);
    }

    public DialogMessage(Context context, @StringRes int title, @StringRes int message, @StringRes int negativeText,
                         @StringRes int positiveText, MessageDialogListener listener) {
        super(context, R.style.Window_DialogStyle);
        initialize(context.getString(title), context.getString(message), context.getString(negativeText),
                context.getString(positiveText), null, listener);
    }

    public DialogMessage(Context context, String title, String message, String singleButtonText,
                         MessageDialogSingleListener listener) {
        super(context, R.style.Window_DialogStyle);
        initialize(title, message, singleButtonText, null, listener, null);
    }

    public DialogMessage(Context context, String title, String message, String negativeText,
                         String positiveText, MessageDialogListener listener) {
        super(context, R.style.Window_DialogStyle);
        initialize(title, message, negativeText, positiveText, null, listener);
    }

    private void initialize(String title, String message, String negativeText, String positiveText,
                            MessageDialogSingleListener singleListener, MessageDialogListener listener) {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_message_layout);

        ButterKnife.bind(this);
        this.singleListener = singleListener;
        this.listener = listener;

        // Title
        if (!TextUtils.isEmpty(title)) {
            txtTitle.setText(title);
        }
        // Message
        if (!TextUtils.isEmpty(message)) {
            txtMessage.setText(message);
            txtMessage.setVisibility(View.VISIBLE);
        } else {
            txtMessage.setVisibility(View.GONE);
        }
        // Should show negative button
        if (!TextUtils.isEmpty(negativeText)) {
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setText(negativeText);
        } else {
            btnNegative.setVisibility(View.GONE);
        }
        // Should show positive button
        if (!TextUtils.isEmpty(positiveText)) {
            btnPositive.setText(positiveText);
            btnPositive.setVisibility(View.VISIBLE);
        } else {
            btnPositive.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        super.show();
        DeviceUtils.hideKeyboard(getContext());
    }

    //----------------------------------------------------------------------------------------------
    //  Listener
    //----------------------------------------------------------------------------------------------

    private MessageDialogListener listener;
    private MessageDialogSingleListener singleListener;

    public interface MessageDialogSingleListener {
        void OnButtonClicked();
    }

    public interface MessageDialogListener {
        void OnNegativeClicked();

        void OnPositiveClicked();
    }

    //----------------------------------------------------------------------------------------------
    //  Listener
    //----------------------------------------------------------------------------------------------

    @OnClick(R.id.btn_negative)
    protected void negativeClicked() {
        dismiss();
        if (listener != null) {
            listener.OnNegativeClicked();
        }
        if (singleListener != null) {
            singleListener.OnButtonClicked();
        }
    }

    @OnClick(R.id.btn_positive)
    protected void positiveClicked() {
        dismiss();
        if (listener != null) {
            listener.OnPositiveClicked();
        }
        if (singleListener != null) {
            singleListener.OnButtonClicked();
        }
    }
}
