package de.ichexample.canvas_3;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iboalali on 11-Aug-20.
 */
class Word {

    @NonNull
    private String word;
    private String wordWithoutHighlightedDiacritics;
    int[] highlightIndices;

    public Word(@NonNull String word, char c) {
        this.word = word;
        highlightIndices = getHighlightIndices(word, c);
        wordWithoutHighlightedDiacritics = getWordWithoutHighlightedDiacritics(word, c);
    }

    @NonNull
    public String getWord() {
        return word;
    }

    public String getWordWithoutHighlightedDiacritics() {
        return wordWithoutHighlightedDiacritics;
    }

    private static int[] getHighlightIndices(@NonNull String word, char c) {
        List<Integer> list = new ArrayList<>();

        int i = -1;
        while ((i = word.indexOf(c, i + 1)) != -1) {
            list.add(i);
        }

        int[] result = new int[list.size()];
        for (i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    private static String getWordWithoutHighlightedDiacritics(@NonNull String word, char c) {
        return word.replace(String.valueOf(c), "");
    }
}
