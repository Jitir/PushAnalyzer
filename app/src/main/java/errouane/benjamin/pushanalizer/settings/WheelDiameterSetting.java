package errouane.benjamin.pushanalizer.settings;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import errouane.benjamin.pushanalizer.R;

/**
 * Created by Benni on 26.02.2015.
 */
public class WheelDiameterSetting extends DialogPreference {
    private float diameter;
    private float DEFAULT_VALUE = 60;
    private ImageView image;
    private Button plusButton, minusButton;
    private TextView sizeText;
    private final float MILLIMETERS_PER_INCH = 25.4f;
    private final float YDPI = getContext().getResources().getDisplayMetrics().ydpi;
    private final float MILLIMETERS_TO_PIXELS_FACTOR = YDPI / MILLIMETERS_PER_INCH;
    private Activity activity;

    public WheelDiameterSetting(Context context, AttributeSet attrs) {
        super(context, attrs);

        activity = (Activity) context;
        setDialogLayoutResource(R.layout.wheel_diameter_dialog);
        setNegativeButtonText(android.R.string.cancel);
        setPositiveButtonText(android.R.string.ok);
    }

    private void updateSize() {
        if(image != null) {
            image.getLayoutParams().height = (int)(diameter * MILLIMETERS_TO_PIXELS_FACTOR);
            image.requestLayout();
        }
        if(sizeText != null) {
            sizeText.setText(String.format("%d mm", (int)diameter));
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        plusButton = (Button) view.findViewById(R.id.plusButton);
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diameter++;
                updateSize();
            }
        });
        minusButton = (Button) view.findViewById(R.id.minusButton);
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diameter--;
                updateSize();
            }
        });
        image = (ImageView) view.findViewById(R.id.imageView);
        sizeText = (TextView) view.findViewById(R.id.sizeTextView);

        updateSize();
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if(positiveResult) {
            persistFloat(diameter);
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if(restorePersistedValue) {
            diameter = getPersistedFloat(this.DEFAULT_VALUE);
        } else {
            diameter = (Float)defaultValue;
            persistFloat(diameter);
        }
        updateSize();
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getFloat(index, DEFAULT_VALUE);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        // Check whether this Preference is persistent (continually saved)
        if (isPersistent()) {
            // No need to save instance state since it's persistent,
            // use superclass state
            return superState;
        }

        // Create instance of custom BaseSavedState
        final SavedState myState = new SavedState(superState);
        // Set the state's value with the class member that holds current
        // setting value
        myState.value = diameter;
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // Check whether we saved the state in onSaveInstanceState
        if (state == null || !state.getClass().equals(SavedState.class)) {
            // Didn't save the state, so call superclass
            super.onRestoreInstanceState(state);
            return;
        }

        // Cast state to custom BaseSavedState and pass to superclass
        SavedState myState = (SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());

        // Set this Preference's widget to reflect the restored state
        diameter = myState.value;
    }

    private static class SavedState extends BaseSavedState {
        // Member that holds the setting's value
        // Change this data type to match the type saved by your Preference
        float value;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            // Get the current preference's value
            value = source.readFloat();  // Change this to read the appropriate data type
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            // Write the preference's value
            dest.writeFloat(value);  // Change this to write the appropriate data type
        }

        // Standard creator object using an instance of this class
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {

                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
