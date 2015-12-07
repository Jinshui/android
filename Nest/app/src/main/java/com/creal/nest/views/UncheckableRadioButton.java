package com.creal.nest.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class UncheckableRadioButton extends RadioButton {
    public UncheckableRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void toggle() {
        setChecked(!isChecked());
    }

}
