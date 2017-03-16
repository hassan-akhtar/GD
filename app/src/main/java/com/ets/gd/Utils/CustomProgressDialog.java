package com.ets.gd.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.ets.gd.R;


public class CustomProgressDialog extends Dialog {
    private MaterialProgressBar progress1;

    private Context mContext;
    private CustomProgressDialog dialog;

    public CustomProgressDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    private CustomProgressDialog(Context context, int theme) {
        super(context, R.style.ProgressDialog);
    }

    public void show(CharSequence message) {

        dialog = new CustomProgressDialog(mContext, R.style.ProgressDialog);
        dialog.setContentView(R.layout.view_material_progress);

        progress1 = (MaterialProgressBar) dialog.findViewById(R.id.progress1);

        if (!"".equals(message)) {
            progress1.setShowProgressText(true);
        } else {
            progress1.setShowProgressText(false);
        }

        progress1.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        dialog.setCancelable(false);

        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        if (dialog != null) {
            dialog.show();
        }
    }

    public CustomProgressDialog dismiss(CharSequence message) {
        if (dialog != null) {
            dialog.dismiss();
        }

        return dialog;

    }


}
