package com.moez.QKSMS.antispam;

import java.util.List;
import java.util.Map;

/**
 * Created by dato0 on 3/30/2017.
 */

public interface IRepository {
    List<Keyword> getKeywords();
    Map<Short, ScoreValue> getScoreValues();
    Map<Short, List<ScoreCombination>> getScoreCombinations();
    short getThreshold();
}
