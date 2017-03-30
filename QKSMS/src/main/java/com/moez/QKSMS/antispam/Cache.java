package com.moez.QKSMS.antispam;

import java.util.List;
import java.util.Map;

/**
 * Created by dato0 on 3/30/2017.
 */

public class Cache {
    private final IRepository _repository;
    private List<Keyword> _keywords;
    private Map<Short, ScoreValue> _scoreValues;
    private Map<Short, List<ScoreCombination>> _combinations;
    private short _spamThreshold;

    public Cache(IRepository repository) {
        _repository = repository;
    }

    public List<Keyword> getKeywords() {
        if(_keywords == null) {
            _keywords = _repository.getKeywords();
        }
        return _keywords;
    }

    public short getSpamThreshold() {
        return _repository.getThreshold();
    }

    public Map<Short, ScoreValue> getScoreValues() {
        if(_scoreValues == null) {
            _scoreValues = _repository.getScoreValues();
        }

        return _scoreValues;
    }

    public Map<Short, List<ScoreCombination>> getCombinations() {
        if(_combinations == null) {
            _combinations = _repository.getScoreCombinations();
        }

        return _combinations;
    }
}
