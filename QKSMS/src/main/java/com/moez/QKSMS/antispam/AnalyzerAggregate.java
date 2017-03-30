package com.moez.QKSMS.antispam;

import android.content.Context;
import android.util.Log;

import com.moez.QKSMS.antispam.sensors.SpamKeywordSensor;
import com.moez.QKSMS.antispam.sensors.SpamSenderIdSensor;
import com.moez.QKSMS.antispam.sensors.ThreadHasSentMessages;
import com.moez.QKSMS.data.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dato0 on 3/30/2017.
 */

public class AnalyzerAggregate {
    private static AnalyzerAggregate _instance;
    private final SpamKeywordSensor _keywordSensor;
    private final SpamSenderIdSensor _senderIdSensor;
    private final ThreadHasSentMessages _sentMsgSensor;
    private final Cache _cache;

    private short calculateCominations() {
        return (short)0;
    }

    public AnalyzerAggregate() {
        _cache = new Cache(new Repository());
        _keywordSensor = new SpamKeywordSensor(_cache);
        _senderIdSensor = new SpamSenderIdSensor();
        _sentMsgSensor = new ThreadHasSentMessages();
    }

    private int countModifiers(List<Short> modifiers, short modifier) {
        int occurences = 0;
        for(short m: modifiers) {
            if(modifier == m) occurences++;
        }
        return occurences;
    }

    public boolean isSpam(Message msg, Context context) {
        Map<Short, ScoreValue> scores = _cache.getScoreValues();
        Map<Short, List<ScoreCombination>> combinations = _cache.getCombinations();
        List<Short> modifiers = new ArrayList<Short>();

        modifiers.addAll(_keywordSensor.analyze(msg, context));
        modifiers.addAll(_senderIdSensor.analyze(msg, context));
        modifiers.addAll(_sentMsgSensor.analyze(msg, context));

        short totalScore = 0;
        Set<String> set = new HashSet<String>();
        for(Short modifier: modifiers) {
            ScoreValue value = scores.get(modifier);
            if(value == null) continue;
            totalScore += value.getValue();

            List<ScoreCombination> combination = combinations.get(modifier);
            if(combination == null) continue;
            for(ScoreCombination c: combination) {
                String key = String.valueOf(c.getPrimary()) + "|" + String.valueOf(c.getSecondary());
                if(set.contains(key)) continue;

                if(c.getPrimary() == c.getSecondary()) {
                    // Did a particular sensor got hit more than once?
                    int occurences = countModifiers(modifiers, modifier);
                    if(occurences > 1) {
                        totalScore += value.getValue() * c.getMultiplier();
                    }
                }
                else {
                    int occurences = countModifiers(modifiers, c.getSecondary());
                    if(occurences > 0) {
                        ScoreValue secondaryValue = scores.get(c.getSecondary());
                        short scoreValue = value.getValue() > secondaryValue.getValue() // We want highest score to be applied to multiplier.
                                ? value.getValue()
                                : secondaryValue.getValue();
                        totalScore += scoreValue * c.getMultiplier();
                    }
                }
                set.add(key);
            }
        }

        Log.d("Analyzer", msg.getBody());
        Log.d("Analyzer", "Score: " + totalScore);
        return totalScore >= _cache.getSpamThreshold();
    }

    public static AnalyzerAggregate Instance() {
        if(_instance == null) {
            _instance = new AnalyzerAggregate();
        }
        return _instance;
    }
}
