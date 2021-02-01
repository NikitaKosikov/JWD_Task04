package by.epam_training.homework04.server.service.parser.impl;

import by.epam_training.homework04.server.service.parser.FileParser;
import by.epam_training.homework04.entity.Code;
import by.epam_training.homework04.entity.Sentence;
import by.epam_training.homework04.entity.Text;
import by.epam_training.homework04.entity.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextParser implements FileParser {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("regex");

    public Text fillTextComponents(String textOfFile){
        Text text = new Text();
        List<Sentence> sentences = fillSentencesOfText(textOfFile);
        List<Code> codes = searchCodeInText(textOfFile);
        text.setSentences(sentences);
        text.setCodes(codes);
        return text;
    }

    private List<Sentence> fillSentencesOfText(String text){
        List<Word> words;
        List<Sentence> sentence = new ArrayList<>();
        Pattern sentencePattern = Pattern.compile(resourceBundle.getString("sentence.regex"));
        Matcher sentenceMatcher = sentencePattern.matcher(text);
        while (sentenceMatcher.find()){
            words = searchWordsInSentence(sentenceMatcher.group());
            sentence.add(new Sentence(words));
        }
        return sentence;
    }

    private List<Word> searchWordsInSentence(String text){
        List<Word> words = new ArrayList<>();
        Pattern nameWordPattern = Pattern.compile(resourceBundle.getString("words.in.sentence.regex"));
        Matcher nameWordMatcher = nameWordPattern.matcher(text);
        while (nameWordMatcher.find()){
            words.add(new Word(nameWordMatcher.group()));
        }
        return words;
    }

    private List<Code> searchCodeInText(String text){
        List<Code> codes = new ArrayList<>();
        Pattern codePattern = Pattern.compile(resourceBundle.getString("code.in.text.regex"));
        Matcher codeMatcher = codePattern.matcher(text);
        StringBuilder s = new StringBuilder();
        while (codeMatcher.find()){
            if (codeMatcher.group().contains("}")){
                s.append('\n');
            }
            s.append(codeMatcher.group());
        }

        String[] strings = s.toString().split(resourceBundle.getString("code.separation"));
        for (String codeBlock : strings){
            Code code = new Code();
            code.setBlocksOfCode(codeBlock);
            codes.add(code);
        }
        return codes;
    }
}
