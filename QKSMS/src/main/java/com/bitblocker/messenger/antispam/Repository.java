package com.bitblocker.messenger.antispam;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bitblocker.messenger.db.SQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dato0 on 3/30/2017.
 */

public class Repository implements IRepository {
    public static final short META_THRESHOLD = 1;

    public static final short KEYWORD_MODIFIER_HIGH = 100;
    public static final short KEYWORD_MODIFIER_CRITICAL = 101;
    public static final short SENDER_ID_WORD = 102;

    public static final short NO_UNSAFE_KEYWORD   = 201;
    public static final short SENDER_ID_NUMBER    = 202;
    public static final short THREAD_HAS_SENT_SMS = 203;

    private final SQLiteHelper dbFactory;

    public Repository(Context context) {
        dbFactory = new SQLiteHelper(context);
    }

    private Keyword cursorToKeyword(Cursor cursor) {
        Keyword keyword = new Keyword();
        keyword.setKeyword(cursor.getString(0));
        keyword.setModifier(cursor.getShort(1));
        keyword.setType(KeywordType.values()[cursor.getInt(2)]);
        return keyword;
    }

    private ScoreValue cursorToScoreValue(Cursor cursor) {
        ScoreValue score = new ScoreValue();
        score.setId(cursor.getShort(0));
        score.setValue(cursor.getShort(1));
        return score;
    }

    private ScoreCombination cursorToScoreCombination(Cursor cursor) {
        ScoreCombination combination = new ScoreCombination();
        combination.setPrimary(cursor.getShort(0));
        combination.setSecondary(cursor.getShort(1));
        combination.setMultiplier(cursor.getShort(2));
        return combination;
    }

    @Override
    public List<Keyword> getKeywords() {
        SQLiteDatabase database = dbFactory.getReadableDatabase();
        List<Keyword> result = new ArrayList<Keyword>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_KEYWORD,
                new String[] { SQLiteHelper.COLUMN_KEYWORD, SQLiteHelper.COLUMN_MODIFIER, SQLiteHelper.COLUMN_TYPE},
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result.add(cursorToKeyword(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        database.close();
        return result;
    }

    @Override
    public Map<Short, ScoreValue> getScoreValues() {
        SQLiteDatabase database = dbFactory.getReadableDatabase();
        Map<Short, ScoreValue> result = new HashMap<>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_SCORE_VALUE,
                new String[] { SQLiteHelper.COLUMN_ID, SQLiteHelper.COLUMN_VALUE },
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ScoreValue val = cursorToScoreValue(cursor);
            result.put(val.getId(), val);
            cursor.moveToNext();
        }

        cursor.close();
        database.close();
        return result;
    }

    @Override
    public Map<Short, List<ScoreCombination>> getScoreCombinations() {
        SQLiteDatabase database = dbFactory.getReadableDatabase();
        Map<Short, List<ScoreCombination>> result = new HashMap<>();

        Cursor cursor = database.query(SQLiteHelper.TABLE_COMBINATION,
                new String[] { SQLiteHelper.COLUMN_PRIMARY, SQLiteHelper.COLUMN_SECONDARY, SQLiteHelper.COLUMN_MULTIPLIER },
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            List<ScoreCombination> list;
            ScoreCombination combination = cursorToScoreCombination(cursor);
            if(result.containsKey(combination.getPrimary())) {
                list = result.get(combination.getPrimary());
            }
            else {
                list = new ArrayList<>();
                result.put(combination.getPrimary(), list);
            }

            list.add(combination);
            cursor.moveToNext();
        }

        cursor.close();
        database.close();
        return result;
    }

    @Override
    public short getThreshold() {
        SQLiteDatabase database = dbFactory.getReadableDatabase();
        Cursor cursor = database.query(SQLiteHelper.TABLE_META,
                new String[] { SQLiteHelper.COLUMN_VALUE },
                SQLiteHelper.COLUMN_ID + "=?",
                new String[] { String.valueOf(META_THRESHOLD) },
                null, null, null);

        cursor.moveToFirst();
        short result = cursor.getShort(0);
        cursor.close();
        database.close();

        return result;
    }
}
