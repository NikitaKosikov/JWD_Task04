package by.epam_training.homework04.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sentence implements Comparable<Sentence>, Serializable {
    private static final long serialVersionUID = -3160283716650536889L;
    private List<Word> words = new ArrayList<>();

    public Sentence() {
    }

    public Sentence(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return Objects.equals(words, sentence.words);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + words.hashCode();
        return result;
    }

    @Override
    public int compareTo(Sentence o) {
        return Integer.compare(this.getWords().size(), o.getWords().size());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Word word : words){
            sb.append(word).append(" ");
        }
        return sb.toString();
    }
}
