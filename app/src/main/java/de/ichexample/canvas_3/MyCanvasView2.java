package de.ichexample.canvas_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

/**
 * Created by iboalali on 02-Aug-20.
 */
class MyCanvasView2 extends View {

    private Paint mTextPaint;
    private Paint mDiacriticsPaint;
    private int mMargin;
    private int mLinePadding;
    @ColorInt
    private int mBackgroundColor;
    String[] mLines = new String[]{
            "ها هو هي",
            "ه ههه",
            "بِسْمِ ٱللَّٰهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
            "هادي نَهْر",
            "كَتَب واجِباته",
            "حَيَوانٌ جَميلٌ",
            "يُحِبُ ألتُّفّاحَ",
            "زارَ نَبيهٌ أُمَّهُ"
    };

    public MyCanvasView2(Context context) {
        super(context);
        init();
    }

    public MyCanvasView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCanvasView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyCanvasView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.textSize));
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextLocale(new Locale("ar", "JO", ""));

        mDiacriticsPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mDiacriticsPaint.setTextAlign(Paint.Align.RIGHT);
        mDiacriticsPaint.setTextSize(getResources().getDimension(R.dimen.textSize));
        mDiacriticsPaint.setColor(Color.RED);
        mDiacriticsPaint.setTextLocale(new Locale("ar", "JO", ""));

        mBackgroundColor = ResourcesCompat.getColor(getResources(), R.color.mycolor, null);
        mLinePadding = getResources().getDimensionPixelOffset(R.dimen.line_padding);
        mMargin = getResources().getDimensionPixelOffset(R.dimen.default_margin);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackgroundColor);

        // the methods getWidth() and getTop() can only give the correct value after the
        // initialization phase
        int posY = mMargin + getTop();

        for (String line : mLines) {
            int posX = getWidth() - mMargin;

            String lineWithoutArabicDiacritics = removeArabicDiacriticsFromString(line);

            // This method gets you the boundaries of the given text.
            // This is not needed if we draw the whole line at once.
            //mTextPaint.getTextBounds(line, 0, line.length(), textBound);

            // skip drawing the arabic text in red, if there are no diacritics in this line, to
            // improve performance.
            if (!lineWithoutArabicDiacritics.equals(line)) {
                canvas.drawTextRun(line, 0, line.length(), 0, line.length(), posX, posY, true, mDiacriticsPaint);
            }

            canvas.drawTextRun(lineWithoutArabicDiacritics, 0, lineWithoutArabicDiacritics.length(), 0, lineWithoutArabicDiacritics.length(), posX, posY, true, mTextPaint);
            posY += mLinePadding;
        }
    }

    private String removeArabicDiacriticsFromString(@NonNull String line) {
        String[] diacritics = getResources().getStringArray(R.array.diacritics);

        for (String diacritic : diacritics) {
            line = line.replace(diacritic, "");
        }

        return line;
    }
}
