package com.bitblocker.messenger.antispam;

import java.util.regex.Pattern;

/**
 * Created by dato0 on 3/30/2017.
 */

public class Keyword {
    private String _keyword;
    private short _modifier;
    private KeywordType _type;
    private Pattern _pattern;

    public String getKeyword() {
        return _keyword;
    }

    public void setKeyword(String keyword) {
        this._keyword = keyword;
    }

    public short getModifier() {
        return _modifier;
    }

    public void setModifier(short modifier) {
        this._modifier = modifier;
    }

    public KeywordType getType() {
        return _type;
    }

    public void setType(KeywordType type) {
        this._type = type;
    }

    public Keyword() { ; }

    public Keyword(String keyword, short modifier, KeywordType type) {
        _keyword = keyword;
        _modifier = modifier;
        _type = type;
    }

    public Pattern getPattern() {
        if(_pattern == null && _type == KeywordType.REGEX) {
            _pattern = Pattern.compile(_keyword);
        }
        return _pattern;
    }
}
