package de.ichexample.canvas_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

/**
 * Created by iboalali on 02-Aug-20.
 */
class MyCanvasView2 extends View {

    private Paint mTextPaint;
    private int posY;
    private int margin;
    private int linePadding;
    @ColorInt
    private int backgroundColor;
    Rect textBound = new Rect();
    String[] lines = new String[]{
            "ها هو هي",
            "ه ههه",
            "ه هاء",
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

    private void init() {
        mTextPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.textSize));
        mTextPaint.setColor(Color.BLACK);
        Locale arabicLocale = new Locale("ar", "JO", "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mTextPaint.setTextLocale(arabicLocale);
        }

        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.mycolor, null);
        linePadding = getResources().getDimensionPixelOffset(R.dimen.line_padding);
        margin = getResources().getDimensionPixelOffset(R.dimen.default_margin);
        posY = margin;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(backgroundColor);

        // the methods getWidth() and getTop() can only give the correct value after the
        // initialization phase
        int posX = getWidth() - margin;
        posY += getTop();

        for (String line : lines) {
            // This method gets you the boundaries of the given text.
            // This is not needed if we draw the whole line at once.
            //mTextPaint.getTextBounds(line, 0, line.length(), textBound);
            
            canvas.drawText(line, posX, posY, mTextPaint);
            posY += linePadding;
        }
    }
}
