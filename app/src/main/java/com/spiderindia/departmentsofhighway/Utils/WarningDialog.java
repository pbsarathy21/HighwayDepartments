package com.spiderindia.departmentsofhighway.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spiderindia.departmentsofhighway.R;

/**
 * Created by pyr on 19-May-18.
 */

public class WarningDialog {
    Context context_;
    Dialog dialog_popup;
    TextView txt_message;
    Button close_buton;

    public WarningDialog(Context context, String message)
    {
        this.context_ = context;
        dialog_popup=new Dialog(context_);
        dialog_popup.setContentView(R.layout.popup_to_show_connection_message);
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
