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
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by iboalali on 02-Aug-20.
 */
class ArabicWithDiacriticsPageView extends View {

    private Rect mCanvasRect = new Rect();
    private TextPaint mTextPaint;
    private TextPaint mErrorTextPaint;
    private TextPaint mSelectedTextPaint;

    // default value '\u0000'
    private char mSelectedChar;
    private int mMargin;
    private int mLinePadding;
    private int mSpaceBetweenWords;
    String[] mDiacritics;
    private Page mPage;
    @ColorInt
    private int mBackgroundColor = -1;

    public ArabicWithDiacriticsPageView(Context context) {
        super(context);
        init();
    }

    public ArabicWithDiacriticsPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArabicWithDiacriticsPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ArabicWithDiacriticsPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setPage(@NonNull Page page) {
        this.mPage = page;
        this.mPage.processPage(getContext());
        invalidate();
    }

    private void init() {
        mTextPaint = new TextPaint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.textSize));
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextLocale(new Locale("ar", "", ""));

        mSelectedTextPaint = new TextPaint(mTextPaint);
        mSelectedTextPaint.setColor(Color.RED);

        mErrorTextPaint = new TextPaint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mErrorTextPaint.setTextSize(R.dimen.normalTextSize);
        mErrorTextPaint.setColor(Color.BLACK);

        mLinePadding = getResources().getDimensionPixelOffset(R.dimen.line_padding);
        mMargin = mSpaceBetweenWords = getResources().getDimensionPixelOffset(R.dimen.default_margin);
        mDiacritics = getResources().getStringArray(R.array.diacritics);
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        mBackgroundColor = backgroundColor;
        invalidate();
    }

    public void setBackgroundColorResource(@ColorRes int backgroundColorResource) {
        mBackgroundColor = ResourcesCompat.getColor(getResources(), backgroundColorResource, null);
    }

    public void setSelectedChar(char c) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBackgroundColor != -1) {
            canvas.drawColor(mBackgroundColor);
        }

        if (mPage == null) {
            // TODO: fix this
            float posX = mMargin;
            float posY = mMargin + getTop();
            canvas.drawTextRun("No Text 1 2 3 4 5 6 7 8 9", 0, 25, 0, 25, posX, posY, false, mErrorTextPaint);
            //canvas.drawTextRun("No Text 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9 1 2 3 4 5 6 7 8 9", 0, 79, 0, 79, posX, posY, false, mErrorTextPaint);
            return;
        }

        // the methods getWidth() and getTop() can only give the correct value after the
        // initialization phase
        int posY = mMargin + getTop();

        mCanvasRect.left = 50;
        mCanvasRect.top = 50;
        mCanvasRect.right = getWidth() - 50;
        mCanvasRect.bottom = getHeight() - 50;

        for (Line line : mPage.getLines()) {
            float posX = getWidth() - mMargin;

            if (mPage.isSelectedCharArabicDiacritics()) {
                for (Word word : line.getWords()) {
                    String wordString = word.getWord();
                    String wordWithout = word.getWordWithoutHighlightedDiacritics();

                    int wordLength = wordString.length();
                    int wordLengthWithout = wordWithout.length();

                    canvas.drawTextRun(wordString, 0, wordLength, 0, wordLength, posX, posY, true, mSelectedTextPaint);
                    canvas.drawTextRun(
                            wordWithout,
                            0,
                            wordLengthWithout,
                            0,
                            wordLengthWithout,
                            posX,
                            posY,
                            true,
                            mTextPaint
                    );

                    float desiredWidth = Layout.getDesiredWidth(wordString, 0, wordLength, mTextPaint);
                    posX -= (desiredWidth + mSpaceBetweenWords);
                }
            } else {
                for (Word word : line.getWords()) {
                    String wordString = word.getWord();
                    int wordLength = wordString.length();
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
                        canvas.drawTextRun(wordString, index, index + 1, 0, wordLength, selectedPosX, posY, true, mSelectedTextPaint);
                    }

                    float desiredWidth = Layout.getDesiredWidth(wordString, 0, wordLength, mTextPaint);
                    posX -= (desiredWidth + mSpaceBetweenWords);
                }
            }

            posY += mLinePadding;
        }
    }
}
