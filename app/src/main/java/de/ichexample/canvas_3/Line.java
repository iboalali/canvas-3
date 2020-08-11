package de.ichexample.canvas_3;

import androidx.annotation.NonNull;

/**
 * Created by iboalali on 11-Aug-20.
 */
class Line {
    @NonNull
    private Word[] words;

    public Line(@NonNull String[] words, char c) {
        this.words = new Word[words.length];

        for (int i = 0; i < words.length; i++) {
            this.words[i] = new Word(words[i], c);
        }
    }

    @NonNull
    public Word[] getWords() {
        return words;
    }
}
