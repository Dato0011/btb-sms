package com.moez.QKSMS.antispam;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Created by dato0 on 3/30/2017.
 */

public class Cache {
    private List<Keyword> _keywords;
    private Map<Short, ScoreValue> _scoreValues;
    private Map<Short, List<ScoreCombination>> _combinations;
    private short _spamThreshold;

    public void invalidate(Context context) {
        Repository repository = new Repository(context);
        _keywords = repository.getKeywords();
        _scoreValues = repository.getScoreValues();
        _combinations = repository.getScoreCombinations();
        _spamThreshold = repository.getThreshold();
    }

    public List<Keyword> getKeywords(Context context) {
        if(_keywords == null) {
            invalidate(context);
        }
        return _keywords;
    }

    public short getSpamThreshold(Context context) {
        if(_spamThreshold == 0) {
            invalidate(context);
        }
        return _spamThreshold;
    }

    public Map<Short, ScoreValue> getScoreValues(Context context) {
        if(_scoreValues == null) {
            invalidate(context);
        }

        return _scoreValues;
    }

    public Map<Short, List<ScoreCombination>> getCombinations(Context context) {
        if(_combinations == null) {
            invalidate(context);
        }

        return _combinations;
    }
}
