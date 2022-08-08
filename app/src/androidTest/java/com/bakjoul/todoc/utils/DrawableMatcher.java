package com.bakjoul.todoc.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DrawableMatcher extends TypeSafeMatcher<View> {

    @DrawableRes
    private final int id;
    private final TextDrawablePosition drawablePosition;

    public DrawableMatcher(@DrawableRes int id, TextDrawablePosition drawablePosition) {
        this.id = id;
        this.drawablePosition = drawablePosition;
    }

    @Override
    protected boolean matchesSafely(View view) {
        if (!(view instanceof TextView)) {
            return false;
        }

        Bitmap expectedBitmap = getBitmap(view.getContext().getDrawable(id));
        return getBitmap(((TextView) view).getCompoundDrawables()[drawablePosition.ordinal()])
                .sameAs(expectedBitmap);

    }

    private Bitmap getBitmap(@NonNull Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void describeTo(@NonNull Description description) {
        description.appendText("TextView with compound drawable at position " + drawablePosition + " same as drawable with id " + id);
    }

    public enum TextDrawablePosition {LEFT, TOP, RIGHT, BOTTOM}

}


