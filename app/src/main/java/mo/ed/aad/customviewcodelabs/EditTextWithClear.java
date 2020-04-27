package mo.ed.aad.customviewcodelabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {

    Drawable mClearButtonImage;
    private TextWatcher mTextWatcher;

    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mClearButtonImage= ResourcesCompat.getDrawable(
                getResources(), R.drawable.ic_clear_opaque_24dp,null
        );
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((getCompoundDrawablesRelative()[2] != null)) {
                    float clearButtonStart; // Used for LTR languages
                    float clearButtonEnd;  // Used for RTL languages
                    boolean isClearButtonClicked = false;
                    // TODO: Detect the touch in RTL or LTR layout direction.
                    identifyDirection(event);
                    // TODO: Check for actions if the button is tapped.
                    Boolean x = checkClicked(event, isClearButtonClicked);
                    if (x != null) return x;
                }
                return false;
            }
        });
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private Boolean checkClicked(MotionEvent event, boolean isClearButtonClicked) {
        if (isClearButtonClicked) {
            // Check for ACTION_DOWN (always occurs before ACTION_UP).
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Switch to the black version of clear button.
                mClearButtonImage =
                        ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_clear_black_24dp, null);
                showClearButton();
            }
            // Check for ACTION_UP.
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Switch to the opaque version of clear button.
                mClearButtonImage =
                        ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_clear_opaque_24dp, null);
                // Clear the text and hide the clear button.
                getText().clear();
                hideClearButton();
                return true;
            }
        } else {
            return false;
        }
        return null;
    }

    private void identifyDirection(MotionEvent event) {
        float clearButtonEnd;
        boolean isClearButtonClicked;
        float clearButtonStart;
        if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
            // If RTL, get the end of the button on the left side.
            clearButtonEnd = mClearButtonImage
                    .getIntrinsicWidth() + getPaddingStart();
            // If the touch occurred before the end of the button,
            // set isClearButtonClicked to true.
            if (event.getX() < clearButtonEnd) {
                isClearButtonClicked = true;
            }
        } else {
            // Layout is LTR.
            // Get the start of the button on the right side.
            clearButtonStart = (getWidth() - getPaddingEnd()
                    - mClearButtonImage.getIntrinsicWidth());
            // If the touch occurred after the start of the button,
            // set isClearButtonClicked to true.
            if (event.getX() > clearButtonStart) {
                isClearButtonClicked = true;
            }
        }
    }

    public void addTextChangedListener(TextWatcher watcher) {
    }

    private void showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                mClearButtonImage,
                null
        );
    }

    private void  hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                null,
                null
        );
    }
}