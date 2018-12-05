package com.spiderindia.departmentsofhighway.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spiderindia.departmentsofhighway.R;

/**
 * Created by pyr on 04-Jul-18.
 */

public class WarningDialogForValidation {
    Context context_;
    Dialog dialog_popup;
    TextView txt_message;
    Button close_buton;

    public WarningDialogForValidation(Context context, String message)
    {
        this.context_ = context;
        dialog_popup=new Dialog(context_);
        dialog_popup.setContentView(R.layout.popup_to_show_validation_msg);
        dialog_popup.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_popup.getWindow().getAttributes().windowAnimations = R.style.fade_in_out_popup;
        close_buton = (Button) dialog_popup.findViewById(R.id.okay_btn);
        txt_message = (TextView) dialog_popup.findViewById(R.id.warning_msg);

        txt_message.setText(message);

        close_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_popup.dismiss();
            }
        });

        dialog_popup.show();

    }

}
