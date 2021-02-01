package by.epam_training.homework04.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Text implements Serializable {
    private static final long serialVersionUID = 277975350392061428L;
    private List<Sentence> sentences = new ArrayList<>();
    private List<Code> codes = new ArrayList<>();

    public Text() {
    }
    
    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public List<Code> getCodes() {
        return codes;
    }

    public void setCodes(List<Code> codes) {
        this.codes = codes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return Objects.equals(sentences, text.sentences) &&
                Objects.equals(codes, text.codes);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + sentences.hashCode();
        result = 31 * result + codes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        for (Sentence sentence : this.getSentences()){
            for (Word word : sentence.getWords()){
                text.append(word.getName()).append(" ");
            }
            text.append("\n");
        }
        return text.toString();
    }
}
