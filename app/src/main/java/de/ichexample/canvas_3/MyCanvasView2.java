package de.ichexample.canvas_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by iboalali on 02-Aug-20.
 */
class MyCanvasView2 extends View {

    private Rect mTextBound = new Rect();
    private Rect mCanvasRect = new Rect();
    private Paint mTextPaint;
    private Paint mSelectedPaint;
    private Paint mRectPaint;
    private TextPaint mTextPaint2;
    private TextPaint mSelectedPaint2;
    private int mMargin;
    private int mLinePadding;
    @ColorInt
    private int mBackgroundColor;
    int[][] mIndices;
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

    String[] mLines_2 = new String[]{
            "ك",
            "كا  كو  كي",
            "كامِل  مَلِك  مَكان  كَبير",
            "البَيتُ  كَبير",
            "كَتَبَ  أَبي  في  دَفْتَري",
            "مَكانُ  المَلِكِ  كَبير"
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

        mTextPaint2 = new TextPaint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTextPaint2.setTextAlign(Paint.Align.RIGHT);
        mTextPaint2.setTextSize(getResources().getDimension(R.dimen.textSize));
        mTextPaint2.setColor(Color.BLACK);
        mTextPaint2.setTextLocale(new Locale("ar", "JO", ""));

        mSelectedPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mSelectedPaint.setTextAlign(Paint.Align.RIGHT);
        mSelectedPaint.setTextSize(getResources().getDimension(R.dimen.textSize));
        mSelectedPaint.setColor(Color.RED);
        mSelectedPaint.setTextLocale(new Locale("ar", "JO", ""));

        mSelectedPaint2 = new TextPaint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mSelectedPaint2.setTextAlign(Paint.Align.RIGHT);
        mSelectedPaint2.setTextSize(getResources().getDimension(R.dimen.textSize));
        mSelectedPaint2.setColor(Color.RED);
        mSelectedPaint2.setTextLocale(new Locale("ar", "JO", ""));

        mRectPaint = new Paint();
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setColor(Color.BLUE);
        mRectPaint.setStrokeWidth(3);

        mIndices = new int[][]{
                getIndicesForCharInLine(mLines_2[0], 'ك'),
                getIndicesForCharInLine(mLines_2[1], 'ك'),
                getIndicesForCharInLine(mLines_2[2], 'ك'),
                getIndicesForCharInLine(mLines_2[3], 'ك'),
                getIndicesForCharInLine(mLines_2[4], 'ك'),
                getIndicesForCharInLine(mLines_2[5], 'ك')
        };

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

        mCanvasRect.left = 50;
        mCanvasRect.top = 50;
        mCanvasRect.right = getWidth() - 50;
        mCanvasRect.bottom = getHeight() - 50;

        for (int i = 0; i < mLines_2.length; i++) {
            float posX = getWidth() - mMargin;
            String line = mLines_2[i];

            //String lineWithoutArabicDiacritics = removeArabicDiacriticsFromString(line);

            // This method gets you the boundaries of the given text.
            // This is not needed if we draw the whole line at once.
            //mTextPaint.getTextBounds(line, 0, line.length(), textBound);

            // skip drawing the arabic text in red, if there are no diacritics in this line, to
            // improve performance.
            //if (!lineWithoutArabicDiacritics.equals(line)) {
            //    canvas.drawTextRun(line, 0, line.length(), 0, line.length(), posX, posY, true, mSelectedPaint);
            //}

            canvas.drawTextRun(line, 0, line.length(), 0, line.length(), posX, posY, true, mTextPaint2);

            for (int j = 0; j < mIndices[i].length; j++) {
                int start = mIndices[i][j];
                //int end = (start + 1 >= line.length())? start + 1 : start + 2;
                int end = start + 1;
                int contextStart = Math.max(start - 1, 0);
                int contextEnd = end;
                //int offset = (start == 0)? 0 : end;
                int offset = start;

                mSelectedPaint.getTextBounds(line, 0, start, mTextBound);
                int width = mTextBound.width();
                float desiredWidth = Layout.getDesiredWidth(line, 0, start, mSelectedPaint2);

                float calculatedOffset = mSelectedPaint.getRunAdvance(
                        line,
                        start,
                        end,
                        contextStart,
                        contextEnd,
                        true,
                        offset
                );

                posX -= desiredWidth;

                canvas.drawTextRun(line, start, start + 1, 0, line.length(), posX, posY, true, mSelectedPaint2);

                canvas.drawRect(mCanvasRect, mRectPaint);
                canvas.drawRect(mTextBound, mRectPaint);
            }

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

    private int[] getIndicesForCharInLine(@NonNull String line, char c) {
        List<Integer> list = new ArrayList<>();

        int i = -1;
        while ((i = line.indexOf(c, i + 1)) != -1) {
            list.add(i);
        }

        int[] result = new int[list.size()];
        for (i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }
}
