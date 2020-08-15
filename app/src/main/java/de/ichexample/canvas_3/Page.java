package de.ichexample.canvas_3;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * Created by iboalali on 11-Aug-20.
 */
class Page {
    @NonNull
    private Line[] lines;
    private char selectedChar;
    private boolean isSelectedCharArabicDiacritics;

    public Page(@NonNull Line[] lines, char selectedChar) {
        this.lines = lines;
        this.selectedChar = selectedChar;
    }

    public void processPage(@NonNull Context context) {
        this.isSelectedCharArabicDiacritics = isCharArabicDiacritics(context, selectedChar);
    }

    @NonNull
    public Line[] getLines() {
        return lines;
    }

    public char getSelectedChar() {
        return selectedChar;
    }

    public boolean isSelectedCharArabicDiacritics() {
        return isSelectedCharArabicDiacritics;
    }

    private static boolean isCharArabicDiacritics(@NonNull Context context, char c) {
        String[] diacritics = context.getResources().getStringArray(R.array.diacritics);
        for (String diacritic : diacritics) {
            if (diacritic.charAt(0) == c) {
                return true;
            }
        }

        return false;
    }

}
