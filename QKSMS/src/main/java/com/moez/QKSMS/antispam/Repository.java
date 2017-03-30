package com.moez.QKSMS.antispam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dato0 on 3/30/2017.
 */

public class Repository implements IRepository {
    private final short KEYWORD_MODIFIER_HIGH = 100;
    private final short KEYWORD_MODIFIER_CRITICAL = 101;
    private final short SENDER_ID_WORD = 102;

    private final short NO_UNSAFE_KEYWORD   = 201;
    private final short SENDER_ID_NUMBER    = 202;
    private final short THREAD_HAS_SENT_SMS = 203;

    @Override
    public List<Keyword> getKeywords() {
        List<Keyword> result = new ArrayList<Keyword>();
        result.add(new Keyword("ichqare", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("gtavazob", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("dagvikavshirdi", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("servisi", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("aqcia", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("iafad", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("iapad", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("sheidzine", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("fasdakleb", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("pasdakleb", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("pasad", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("fasad", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("laridan", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("gakidvashia", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("miighe", KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        result.add(new Keyword("sms no", KEYWORD_MODIFIER_CRITICAL, KeywordType.WORD));
        result.add(new Keyword("sms off", KEYWORD_MODIFIER_CRITICAL, KeywordType.WORD));
        result.add(new Keyword("smsoff", KEYWORD_MODIFIER_CRITICAL, KeywordType.WORD));
        result.add(new Keyword("nosms", KEYWORD_MODIFIER_CRITICAL, KeywordType.WORD));

        result.add(new Keyword("\\b\\w+\\s\\d{5}\\b", KEYWORD_MODIFIER_HIGH, KeywordType.REGEX));
        result.add(new Keyword("\\bno.\\d{5}\\b", KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        result.add(new Keyword("\\boff.\\d{5}\\b", KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        result.add(new Keyword("\\bnosms\\d{5}\\b", KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        result.add(new Keyword("\\bnosms.\\d{5}\\b", KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        result.add(new Keyword("\\bstop\\d{5}\\b", KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        result.add(new Keyword("\\bstop.\\d{5}\\b", KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        return result;
    }

    @Override
    public Map<Short, ScoreValue> getScoreValues() {
        Map<Short, ScoreValue> result = new HashMap<Short, ScoreValue>();
        result.put(KEYWORD_MODIFIER_HIGH, new ScoreValue(KEYWORD_MODIFIER_HIGH, (short)30));
        result.put(KEYWORD_MODIFIER_CRITICAL, new ScoreValue(KEYWORD_MODIFIER_CRITICAL, (short)50));
        result.put(SENDER_ID_WORD, new ScoreValue(KEYWORD_MODIFIER_CRITICAL, (short)30));
        result.put(NO_UNSAFE_KEYWORD, new ScoreValue(KEYWORD_MODIFIER_CRITICAL, (short)-30));
        result.put(SENDER_ID_NUMBER, new ScoreValue(KEYWORD_MODIFIER_CRITICAL, (short)-30));
        result.put(THREAD_HAS_SENT_SMS, new ScoreValue(KEYWORD_MODIFIER_CRITICAL, (short)-50));
        return result;
    }

    @Override
    public Map<Short, List<ScoreCombination>> getScoreCombinations() {
        Map<Short, List<ScoreCombination>> result = new HashMap<Short, List<ScoreCombination>>();
        List<ScoreCombination> criticals = new ArrayList<ScoreCombination>();
        criticals.add(new ScoreCombination(KEYWORD_MODIFIER_CRITICAL, KEYWORD_MODIFIER_HIGH, (short)5));
        criticals.add(new ScoreCombination(KEYWORD_MODIFIER_CRITICAL, KEYWORD_MODIFIER_CRITICAL, (short)5));
        criticals.add(new ScoreCombination(KEYWORD_MODIFIER_CRITICAL, SENDER_ID_WORD, (short)5));
        result.put(KEYWORD_MODIFIER_CRITICAL, criticals);

        List<ScoreCombination> highs = new ArrayList<ScoreCombination>();
        highs.add(new ScoreCombination(KEYWORD_MODIFIER_HIGH, KEYWORD_MODIFIER_HIGH, (short)2));
        highs.add(new ScoreCombination(KEYWORD_MODIFIER_HIGH, SENDER_ID_WORD, (short)3));
        result.put(KEYWORD_MODIFIER_HIGH, highs);

        List<ScoreCombination> safes = new ArrayList<>();
        safes.add(new ScoreCombination(NO_UNSAFE_KEYWORD, SENDER_ID_NUMBER, (short) 3));
        safes.add(new ScoreCombination(NO_UNSAFE_KEYWORD, THREAD_HAS_SENT_SMS, (short) 4));
        result.put(NO_UNSAFE_KEYWORD, safes);

        List<ScoreCombination> safes2 = new ArrayList<>();
        safes.add(new ScoreCombination(SENDER_ID_NUMBER, THREAD_HAS_SENT_SMS, (short) 4));
        result.put(SENDER_ID_NUMBER, safes);

        return result;
    }

    @Override
    public short getThreshold() {
        return 150;
    }
}
