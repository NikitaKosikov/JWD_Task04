package by.epam_training.homework04.server.service;

import by.epam_training.homework04.entity.Sentence;
import by.epam_training.homework04.entity.Text;
import by.epam_training.homework04.entity.Word;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class FileOperationService {
    public static Text executeFileOperation(Text text,int taskNumber, ObjectInputStream in, ObjectOutputStream out)
            throws IOException, ClassNotFoundException {

        switch (taskNumber) {
            case 1 -> {
                return findSentenceWithSameWords(text);
            }
            case 2 -> {
                return SortSentenceByWords(text);
            }
            case 3 -> {
                return findWordThatIsInFirstSentenceButNotOther(text);
            }
            case 4 -> {
                out.writeObject("Enter the size of the words you want to search for: ");
                out.flush();
                int lengthOfWords = Integer.parseInt((String) in.readObject());
                return findWordsOfGivenLengthInQuestionSentence(text, lengthOfWords);
            }
            case 5 -> {
                return replaceFirstWordOfSentenceWithLastWord(text);
            }
            case 6 -> {
                return printWordsInAlphabeticalOrderByFirstLetter(text);
            }
            case 7 -> {
                return SortWordsByIncreasingProportionOfVowels(text);
            }
            case 8 -> {
                return WordsStartingWithVowelsSortedAlphabeticallyByFirstConsonantLetterOfWord(text);
            }
            case 9 -> {
                out.writeObject("Enter the character: ");
                out.flush();
                char character = in.readObject().toString().charAt(0);
                return sortWordsByIncreasingNumberLetterInWord(text, character);
            }
            case 10 -> {
                List<String> strings = new ArrayList<>();
                String word;
                do {
                    out.writeObject("Enter list: ");
                    out.flush();
                    word = (String) in.readObject();
                    strings.add(word);
                    out.writeObject("Continue: y/n");
                    out.flush();
                }while ("y".equals(in.readObject()));
                return forEachWordFromListFindHowManyTimesItOccursInSentencesAndSortIt(text, strings);
            }
            case 11 -> {
                out.writeObject("Enter the first letter of the word: ");
                out.flush();
                char firstCharacter = in.readObject().toString().charAt(0);
                out.writeObject("Enter the last letter of the word: ");
                out.flush();
                char lastCharacter = in.readObject().toString().charAt(0);
                return removeSubStringMaxLengthInEverySentence(text,firstCharacter, lastCharacter);
            }
            case 12 -> {
                out.writeObject("Enter the size of the words you want to delete for: ");
                out.flush();
                int lengthOfWord = Integer.parseInt((String) in.readObject());
                return deleteWordsOfGivenLengthThatStartWithConsonantLetter(text, lengthOfWord);
            }
            case 13 -> {
                out.writeObject("Enter one letter: ");
                out.flush();
                char character = in.readObject().toString().charAt(0);
                return SortWordsDescendingOrderOfNumberOfOccurrencesOfGivenCharacter(text, character);
            }
            case 14 -> {
                return findSubStringMaxLengthThatIsPalindrome(text);
            }
            case 15 -> {
                return convertWordsByRemovingFirstAndLastLetterOccurrences(text);
            }
            case 16 -> {
                out.writeObject("Enter a substring: ");
                out.flush();
                String subString = (String) in.readObject();
                out.writeObject("Enter a length word: ");
                out.flush();
                int length = Integer.parseInt((String) in.readObject());
                return replaceWordsOfGivenLengthSubString(text, subString, length);
            }
            default ->
                System.out.println("This task never exist");

        }
        return null;
    }

    public static Text findSentenceWithSameWords(Text text){
        List<Sentence> sentences = text.getSentences();
        List<Sentence> foundSentence = new ArrayList<>();
        for(Sentence sentence : sentences) {
            List<String> words = new ArrayList<>();
            for(Word word : sentence.getWords()) {
                words.add(word.getName());
            }
            boolean isWordRepeated = false;
            for(Word word : sentence.getWords()) {
                words.remove(word.getName());
                if(words.contains(word.getName())) {
                    isWordRepeated = true;
                    break;
                }
            }
            if(isWordRepeated) {
                foundSentence.add(sentence);
            }
        }
        text.setSentences(foundSentence);
        return text;
    }

    public static Text SortSentenceByWords(Text text) {
        List<Sentence> sentences = text.getSentences();
        Collections.sort(sentences);
        StringBuilder s = new StringBuilder();
        for (Sentence sentence : sentences) {
            for (Word word : sentence.getWords()) {
                s.append(word.getName()).append(" ");
            }
        }
        return text;
    }

    public static Text findWordThatIsInFirstSentenceButNotOther(Text text){
        Sentence firstSentence = text.getSentences().get(0);
        List<Word> words = new ArrayList<>();
        List<Word> foundWords = new ArrayList<>();
        for (Word wordInFirstSentence : firstSentence.getWords()){
            boolean isFoundWord = false;
            for(Sentence sentence : text.getSentences()){
                if (!sentence.equals(firstSentence)) {
                    words.addAll(sentence.getWords());
                    for (Word wordOtherSentences : words) {
                        if (wordOtherSentences.getName().equals(wordInFirstSentence.getName())) {
                            isFoundWord = true;
                            break;
                        }
                    }
                }
            }
            if (!isFoundWord){
                foundWords.add(wordInFirstSentence);
            }
        }
        text.getSentences().clear();
        text.setSentences(new ArrayList<>());
        text.getSentences().add(new Sentence());
        text.getSentences().get(0).setWords(foundWords);
        return text;
    }

    public static Text findWordsOfGivenLengthInQuestionSentence(Text text, int length){
        List<Word> words = new ArrayList<>();
        for (Sentence sentence : text.getSentences()){
            if (sentence.toString().contains("?")){
                for (Word word : sentence.getWords()){
                    if (word.getName().length() == length){
                        if (!words.contains(word)){
                            words.add(word);
                        }
                    }
                }
            }
        }
        text.getSentences().clear();
        text.setSentences(new ArrayList<>());
        text.getSentences().add(new Sentence());
        text.getSentences().get(0).setWords(words);
        return text;
    }

    public static Text replaceFirstWordOfSentenceWithLastWord(Text text){
        for (Sentence sentence : text.getSentences()){

            Word firstWord = sentence.getWords().get(0);
            Word lastWord =  sentence.getWords().get(sentence.getWords().size() - 1);

            sentence.getWords().remove(0);
            sentence.getWords().add(0, lastWord);

            sentence.getWords().remove(sentence.getWords().size() - 1);
            sentence.getWords().add(sentence.getWords().size(), firstWord);

        }
        return text;
    }

    public static Text printWordsInAlphabeticalOrderByFirstLetter(Text text){
      List<Word> words = new ArrayList<>();
       for (Sentence sentence : text.getSentences()){
           for (Word word : sentence.getWords()){
               word.setName(word.getName().toLowerCase());
               words.add(word);
           }
       }
       Collections.sort(words);
       for (int i = 1; i < words.size(); i++) {
            if (words.get(i-1).getName().charAt(0) == words.get(i).getName().charAt(0)){
                words.get(i-1).setName("\t " + words.get(i-1).getName());
            }else {
                words.get(i-1).setName("\t " + words.get(i-1).getName() + "\n");
            }
       }
       text.getSentences().clear();
       List<Sentence> sentence = new ArrayList<>();
       sentence.add(new Sentence());
       sentence.get(0).setWords(words);
       text.setSentences(sentence);
       return text;
    }

    public static Text SortWordsByIncreasingProportionOfVowels(Text text) {
        List<Word> words = new ArrayList<>();
        String vowels = "a e i o u y";
        Map<String, Double> vowelsProportion = new HashMap<>();
        for (Sentence sentence : text.getSentences()) {
            words.addAll(sentence.getWords());
        }
        for (Word word : words) {
            double countVowelsLetter = 0;
            double countLetterInWord = word.getName().length();
            for (int i = 1; i <= countLetterInWord; i++) {
                String letter = word.getName().toLowerCase().substring(i-1 , i);
                if (vowels.contains(letter)) {
                    countVowelsLetter++;
                }
            }
            double proportionOfVowels = countVowelsLetter / countLetterInWord;
            vowelsProportion.put(word.getName(), proportionOfVowels);
        }

        words.clear();
        double valueOfProportion = 0;
        String nameOfWord = null;

        for (int i = 0; i < vowelsProportion.size(); i++) {
            for (Map.Entry<String, Double> word : vowelsProportion.entrySet()) {
                if (valueOfProportion > word.getValue() || valueOfProportion == 0) {
                    nameOfWord = word.getKey();
                    valueOfProportion = word.getValue();
                }
            }
            words.add(new Word());
            words.get(i).setName(nameOfWord);
            vowelsProportion.remove(nameOfWord);
        }
        text.getSentences().clear();
        text.setSentences(new ArrayList<>());
        text.getSentences().add(new Sentence());
        text.getSentences().get(0).setWords(words);

        return text;
    }

    public static Text WordsStartingWithVowelsSortedAlphabeticallyByFirstConsonantLetterOfWord(Text text){
        String vowels = "a e i o u y";
        Character[] consonantLetters =
                {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'x', 'z' ,'w'};

        List<Word> words = new ArrayList<>();
        for(Sentence sentence : text.getSentences()){
            words.addAll(sentence.getWords());
        }

        for (int i = 0; i < words.size(); i++) {
            int minPositionOfWord = 0;
            int position = 0;
            char character = 0;
            char minCharacter = 0 ;
            for (int j = i; j < words.size(); j++) {
                boolean isFoundCharacter = false;
                String nameOfWord = words.get(j).getName().toLowerCase();
                if (vowels.contains(nameOfWord.substring(0, 1))) {
                    for (int k = 1; k < nameOfWord.length(); k++) {
                        for (Character consonantCharacter : consonantLetters) {
                            if (nameOfWord.charAt(k) == consonantCharacter && minCharacter == 0) {
                                minCharacter = consonantCharacter;
                                minPositionOfWord = j;
                                isFoundCharacter = true;
                                break;
                            }else if (nameOfWord.charAt(k) == consonantCharacter) {
                                character = consonantCharacter;
                                position = j;
                                isFoundCharacter = true;
                                break;
                            }
                        }
                        if (isFoundCharacter){
                            break;
                        }
                    }
                }
                if (minCharacter > character && character!=0) {
                    minPositionOfWord = position;
                }
            }
            if (position!=0 && minPositionOfWord!=0){
            String temp = words.get(i).getName();
            words.get(i).setName(words.get(minPositionOfWord).getName());
            words.get(minPositionOfWord).setName(temp);
            }
        }
        return text;
    }

    public static Text sortWordsByIncreasingNumberLetterInWord(Text text, char character){

        List<Word> words = new ArrayList<>();
        List<Sentence> sentences = text.getSentences();
        for(Sentence sentence : sentences){
            words.addAll(sentence.getWords());
        }

        for (int i = 0; i < words.size(); i++) {
            int positionWordsWithMinMatches = i;
            int minCountOfMatchesWithCharacter = 0;
            int countOfMatchesWithCharacter;
            for (int j = 0; j < words.size() - i; j++) {
                countOfMatchesWithCharacter = 0;
                String nameOfWord = words.get(j).getName();
                for (int k = 0; k < nameOfWord.length(); k++) {
                    if (nameOfWord.charAt(k)==character){
                        countOfMatchesWithCharacter++;
                    }
                }
                if (countOfMatchesWithCharacter > minCountOfMatchesWithCharacter){
                    positionWordsWithMinMatches = j;
                    minCountOfMatchesWithCharacter = countOfMatchesWithCharacter;
                }
            }
                String name = words.get(words.size() - 1 - i).getName();
                words.get(words.size() - 1 - i).setName(words.get(positionWordsWithMinMatches).getName());
                words.get(positionWordsWithMinMatches).setName(name);

        }
        return text;
    }

    public static Text forEachWordFromListFindHowManyTimesItOccursInSentencesAndSortIt(Text text, List<String> listWords){

        List<Sentence> sentences = text.getSentences();
        for (int i = 0; i < sentences.size(); i++) {
            int positionSentenceWithMaxMatches = i;
            int maxCountOfMatchesWithList = 0;

            for (int j = i + 1; j < sentences.size(); j++) {
                int countOfMatchesWithList = 0;
                for(Word word : sentences.get(j-1).getWords()) {
                    if(listWords.contains(word.getName())) {
                        countOfMatchesWithList++;
                    }
                }
                if (maxCountOfMatchesWithList < countOfMatchesWithList){
                    positionSentenceWithMaxMatches = j-1;
                    maxCountOfMatchesWithList = countOfMatchesWithList;
                }
            }
            List<Word> wordsOfSentence = sentences.get(i).getWords();
            sentences.get(i).setWords(sentences.get(positionSentenceWithMaxMatches).getWords());
            sentences.get(positionSentenceWithMaxMatches).setWords(wordsOfSentence);

        }
        return text;
    }

    public static Text removeSubStringMaxLengthInEverySentence(Text text, char firstLetter, char lastLetter){
        for (Sentence sentence : text.getSentences()){
            int positionWordWithMaxLength = 0;
            int wordWithMaxLength = -1;
            List<Word> words = sentence.getWords();
            for (int i = 0; i < words.size(); i++) {
                int start = words.get(i).toString().indexOf(firstLetter);
                int end = words.get(i).toString().lastIndexOf(lastLetter);
                int length = end - start;

                if (start >= 0 && end > 0 && length>=0){
                    if (wordWithMaxLength < length){
                        wordWithMaxLength = length;
                        positionWordWithMaxLength = i;
                    }
                }
            }
            if (positionWordWithMaxLength!=0){
                words.remove(positionWordWithMaxLength);
            }
        }
        return text;
    }

    public static Text deleteWordsOfGivenLengthThatStartWithConsonantLetter(Text text, int length){
        String consonantLetters = "b c d f g h j k l m n p q r s t v x y z w";
        for (Sentence sentence : text.getSentences()){
            List<Word> words = sentence.getWords();
            for (int i = 0; i < words.size(); i++) {
                if (words.get(i).getName().length() == length
                        && consonantLetters.contains(words.get(i).getName().toLowerCase().substring(0,1))){
                    words.remove(i--);
                }
            }
        }
        return text;
    }

    public static Text SortWordsDescendingOrderOfNumberOfOccurrencesOfGivenCharacter(Text text, char character){

        List<Word> words = new ArrayList<>();
        for(Sentence sentence : text.getSentences()){
            words.addAll(sentence.getWords());
        }
        int positionOfWordWithMaxCharacter = -1;
        for (int i = 0; i < words.size(); i++) {
            int countMatchesCharInPreviousFoundWord = 0;
            for (int j = i; j < words.size(); j++) {
                int numberOfMatchesOfSpecifiedCharacter = 0;
                String name = words.get(j).getName();
                while (!name.isEmpty()){
                    int index;
                    if ((index = name.indexOf(character)) != -1){
                        name = name.substring(index + 1);
                        numberOfMatchesOfSpecifiedCharacter++;
                    }else {
                        name = "";
                    }
                }
                if (countMatchesCharInPreviousFoundWord < numberOfMatchesOfSpecifiedCharacter){
                    positionOfWordWithMaxCharacter = j;
                    countMatchesCharInPreviousFoundWord = numberOfMatchesOfSpecifiedCharacter;
                }else if (countMatchesCharInPreviousFoundWord == numberOfMatchesOfSpecifiedCharacter){
                    if (words.get(j).compareTo(words.get(positionOfWordWithMaxCharacter)) < 0){
                        String nameWord = words.get(j).getName();
                        words.get(j).setName(words.get(positionOfWordWithMaxCharacter).getName());
                        words.get(positionOfWordWithMaxCharacter).setName(nameWord);
                    }
                }
            }
            String nameWord = words.get(i).getName();
            words.get(i).setName(words.get(positionOfWordWithMaxCharacter).getName());
            words.get(positionOfWordWithMaxCharacter).setName(nameWord);
        }

        text.getSentences().clear();
        text.setSentences(new ArrayList<>());
        text.getSentences().add(new Sentence());
        text.getSentences().get(0).setWords(words);
        return text;
    }

    public static Text findSubStringMaxLengthThatIsPalindrome(Text text){
        int lengthPalindromeWord = 0;
        Map<String, Integer> palindromeInfo = new HashMap<>();
        List<Word> words = new ArrayList<>();
        for (Sentence sentence : text.getSentences()){
            words = sentence.getWords();
            for (Word word : words) {
                String nameOfWord = word.getName();
                for (int j = 0; j < nameOfWord.length(); j++) {
                    int lastCharacter = nameOfWord.length() - 1;
                    for (int k = lastCharacter; k > j; k--) {

                        String start = nameOfWord.substring(j, j + 1);
                        String last = nameOfWord.substring(k, k + 1);
                        if (start.equals(last)) {
                            if (lengthPalindromeWord == 0) {
                                lengthPalindromeWord = k - j + 1;
                            }
                            j++;
                        } else {
                            lengthPalindromeWord = 0;
                        }
                    }
                    if (lengthPalindromeWord != 0) {
                        palindromeInfo.put(word.getName(), lengthPalindromeWord);
                        lengthPalindromeWord = 0;
                    }

                }
            }
        }

        String wordPalindrome = null;
        lengthPalindromeWord = 0;
        for (Map.Entry<String, Integer> palindrome : palindromeInfo.entrySet()){
            if (lengthPalindromeWord < palindrome.getValue() || lengthPalindromeWord == 0){
                wordPalindrome = palindrome.getKey();
                lengthPalindromeWord = palindrome.getValue();
            }
        }

        words.clear();
        Word word = new Word();
        word.setName(wordPalindrome);
        words.add(word);

        text.getSentences().clear();
        text.setSentences(new ArrayList<>());
        text.getSentences().add(new Sentence());
        text.getSentences().get(0).setWords(words);
        return text;
    }

    public static Text convertWordsByRemovingFirstAndLastLetterOccurrences(Text text){
        for (Sentence sentence : text.getSentences()){
            for(Word word : sentence.getWords()){
                String firstLetter = word.getName().substring(0,1);
                String lastLetter = word.getName().substring(word.getName().length() - 1);
                if ("?".equals(lastLetter) || "?".equals(firstLetter)){
                    lastLetter = "\\?";
                }
                word.setName(word.getName().replaceAll(firstLetter, "").replaceAll(lastLetter, ""));
            }
        }
        return text;
    }

    public static Text replaceWordsOfGivenLengthSubString(Text text,String subString, int length){
        for (Sentence sentence : text.getSentences()){
            for (Word word : sentence.getWords()){
                if (word.getName().length() == length){
                    word.setName(subString);
                }
            }
        }
        return text;
    }
}
