package com.moez.QKSMS.antispam.sensors;

import android.content.Context;

import com.moez.QKSMS.antispam.Cache;
import com.moez.QKSMS.antispam.Keyword;
import com.moez.QKSMS.antispam.KeywordType;
import com.moez.QKSMS.data.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dato0 on 3/30/2017.
 */

public class SpamKeywordSensor implements ISensor {
    private Cache _cache;

    public SpamKeywordSensor(Cache cache) {
        _cache = cache;
    }

    private String normalizeBody(String body) {
        body = body.replace('\r', ' ').replace('\n', ' ');
        while(body.contains("  ")) {
            body = body.replace("  ", " ");
        }
        body = body.replace('\t', ' ').trim();
        return body;
    }

    @Override
    public List<Short> analyze(Message msg, Context context) {
        final short NO_UNSAFE_KEYWORD = 201;

        List<Keyword> keywords = _cache.getKeywords();
        String body = normalizeBody(msg.getBody().toString());
        if(body.isEmpty()) {
            return null;
        }

        List<Short> modifiers = new ArrayList<Short>();
        for(Keyword keyword: keywords) {
            if(keyword.getType() == KeywordType.WORD) {
                if(body.indexOf(keyword.getKeyword()) != -1) {
                    modifiers.add(keyword.getModifier());
                }
            }
            else if(keyword.getType() == KeywordType.REGEX) {
                Pattern pattern = keyword.getPattern();
                Matcher match = pattern.matcher(body);
                if(match.find()) {
                    modifiers.add(keyword.getModifier());
                }
            }
        }

        if(modifiers.size() == 0) {
            modifiers.add(NO_UNSAFE_KEYWORD);
        }
        return modifiers;
    }
}
