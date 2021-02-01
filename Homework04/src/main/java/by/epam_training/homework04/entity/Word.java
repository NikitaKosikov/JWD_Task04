package by.epam_training.homework04.entity;

import java.io.Serializable;
import java.util.Objects;

public class Word implements Serializable, Comparable<Word> {
    private static final long serialVersionUID = 4175664111338969707L;
    private String name;

    public Word(){}
    public Word(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(name, word.name);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public int compareTo(Word otherWord) {
        return this.getName().toLowerCase().compareTo(otherWord.getName().toLowerCase());
    }

    @Override
    public String toString() {
        return name;
    }
}
