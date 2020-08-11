package de.ichexample.canvas_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
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

    private static final String TAG = "CanvasDraw";
    private Rect mTextBound = new Rect();
    private Rect mCanvasRect = new Rect();
    private TextPaint mBigTextPaint;
    private Paint mRectPaint;
    private TextPaint mTextPaint;
    private TextPaint mSelectedPaint;
    private int mMargin;
    private int mLinePadding;
    private int mSpaceBetweenWords;
    private char mSelectedChar = 'ك';
    String[] mDiacritics;
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

    String[][] multiLine = new String[][]{
            {"ك"},
            {"كا", "كو", "كي"},
            {"كامِل", "مَلِك", "مَكان", "كَبير"},
            {"كَلِمتكَ", "البَيتُ", "كَبير"},
            {"كَتَبَ", "أَبي", "في", "دَفْتَري"},
            {"مَكانُ", "المَلِكِ", "كَبير"}
    };

    Line[] lines = new Line[]{
            new Line(multiLine[0], mSelectedChar),
            new Line(multiLine[1], mSelectedChar),
            new Line(multiLine[2], mSelectedChar),
            new Line(multiLine[3], mSelectedChar),
            new Line(multiLine[4], mSelectedChar),
            new Line(multiLine[5], mSelectedChar),
    };

    Line[] oneLine = new Line[]{
            new Line(new String[]{"المَلِكِ"}, mSelectedChar),
            new Line(new String[]{"دَفْتَري"}, mSelectedChar),
            new Line(new String[]{"كَلِمتكَ"}, mSelectedChar)
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
        Locale arabicLocale = new Locale("ar", "", "");

        mBigTextPaint = new TextPaint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mBigTextPaint.setTextAlign(Paint.Align.RIGHT);
        mBigTextPaint.setTextSize(getResources().getDimension(R.dimen.bigTextSize));
        mBigTextPaint.setColor(Color.BLACK);
        mBigTextPaint.setTextLocale(arabicLocale);

        mTextPaint = new TextPaint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.textSize));
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextLocale(arabicLocale);

        mSelectedPaint = new TextPaint(mTextPaint);
        mSelectedPaint.setColor(Color.RED);

        mRectPaint = new Paint();
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setColor(Color.BLUE);
        mRectPaint.setStrokeWidth(3);

        mBackgroundColor = ResourcesCompat.getColor(getResources(), R.color.mycolor, null);
        mLinePadding = getResources().getDimensionPixelOffset(R.dimen.line_padding);
        mMargin = mSpaceBetweenWords = getResources().getDimensionPixelOffset(R.dimen.default_margin);
        mDiacritics = getResources().getStringArray(R.array.diacritics);
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

        for (Line line : lines) {
            float posX = getWidth() - mMargin;

            Log.d(TAG, "Start at posx: " + posX);

            for (Word word : line.getWords()) {
                String wordString = word.getWord();
                int wordLength = wordString.length();
                Log.d(TAG, "Draw word: \"" + wordString + "\" with length " + wordLength);
                canvas.drawTextRun(wordString, 0, wordLength, 0, wordLength, posX, posY, true, mTextPaint);

                for (int index : word.highlightIndices) {
                    float advance = mTextPaint.getRunAdvance(
                            wordString,
                            0,
                            index,
                            0,
                            wordLength,
                            true,
                            index
                    );
                    float selectedPosX = posX - advance;
                    Log.d(TAG, "Width until the selected letter " + advance + "px");
                    canvas.drawTextRun(wordString, index, index + 1, 0, wordLength, selectedPosX, posY, true, mSelectedPaint);
                }

                // draw lines between letters
                //for (int i = 0; i < wordLength; i++) {
                //    char character = wordString.charAt(i);
                //    if (isCharArabicDiacritics(character)) {
                //        continue;
                //    }
                //    float advance = mTextPaint.getRunAdvance(
                //            wordString,
                //            0,
                //            i,
                //            0,
                //            wordLength,
                //            true,
                //            i
                //    );
                //    float selectedPosX = posX - advance;
                //    Log.d(TAG, "Letter " + character + " with width until the letter " + advance + "px");
                //    canvas.drawLine(selectedPosX, posY - 100, selectedPosX, posY + 10, mRectPaint);
                //}

                float desiredWidth = Layout.getDesiredWidth(wordString, 0, wordLength, mTextPaint);
                Log.d(TAG, "Word width is: " + desiredWidth + "px");
                posX -= (desiredWidth + mSpaceBetweenWords);
                Log.d(TAG, "New posx after Word width and space: " + posX);
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

    private boolean isCharArabicDiacritics(char c) {
        for (String diacritic : mDiacritics) {
            if (diacritic.charAt(0) == c) {
                return true;
            }
        }

        return false;
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
