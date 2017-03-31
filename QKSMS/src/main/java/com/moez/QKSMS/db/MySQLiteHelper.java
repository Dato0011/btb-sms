package com.moez.QKSMS.db;

/**
 * Created by dato0 on 3/31/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.moez.QKSMS.antispam.Keyword;
import com.moez.QKSMS.antispam.KeywordType;
import com.moez.QKSMS.antispam.Repository;
import com.moez.QKSMS.antispam.ScoreCombination;
import com.moez.QKSMS.antispam.ScoreValue;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMBINATION = "combination";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRIMARY = "_primary";
    public static final String COLUMN_SECONDARY = "secondary";
    public static final String COLUMN_MULTIPLIER = "multiplier";

    public static final String TABLE_SCORE_VALUE = "score_value";
    public static final String COLUMN_VALUE = "value";

    public static final String TABLE_KEYWORD = "keyword";
    public static final String COLUMN_KEYWORD = "keyword";
    public static final String COLUMN_MODIFIER = "modifier";
    public static final String COLUMN_TYPE = "type";

    public static final String TABLE_META = "meta";

    private static final String DATABASE_NAME = "btb.antispam";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_COMBINATION = "CREATE TABLE "
            + TABLE_COMBINATION + "("
            + COLUMN_PRIMARY + " INTEGER,"
            + COLUMN_SECONDARY + " INTEGER,"
            + COLUMN_MULTIPLIER + " INTEGER);";
    private static final String CREATE_KEYWORD = "CREATE TABLE "
            + TABLE_KEYWORD + "(" + COLUMN_KEYWORD
            + " varchar(32), "
            + COLUMN_MODIFIER + " INTEGER,"
            + COLUMN_TYPE + " INTEGER);";
    private static final String CREATE_SCORE_VALUE = "CREATE TABLE "
            + TABLE_SCORE_VALUE + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_VALUE + " INTEGER);";
    private static final String CREATE_META = "CREATE TABLE "
            + TABLE_META + "(" + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_VALUE + " varchar(512));";

    private void populateDatabase(SQLiteDatabase database) {
        /*** SCORES ***/
        List<ScoreValue> scores = new ArrayList<>();
        scores.add(new ScoreValue(Repository.KEYWORD_MODIFIER_HIGH, (short)30));
        scores.add(new ScoreValue(Repository.KEYWORD_MODIFIER_CRITICAL, (short)50));
        scores.add(new ScoreValue(Repository.SENDER_ID_WORD, (short)30));
        scores.add(new ScoreValue(Repository.NO_UNSAFE_KEYWORD, (short)-30));
        scores.add(new ScoreValue(Repository.SENDER_ID_NUMBER, (short)-30));
        scores.add(new ScoreValue(Repository.THREAD_HAS_SENT_SMS, (short)-50));

        /*** COMBINATIONS ***/
        List<ScoreCombination> combinations = new ArrayList<ScoreCombination>();
        combinations.add(new ScoreCombination(Repository.KEYWORD_MODIFIER_CRITICAL, Repository.KEYWORD_MODIFIER_HIGH, (short)5));
        combinations.add(new ScoreCombination(Repository.KEYWORD_MODIFIER_CRITICAL, Repository.KEYWORD_MODIFIER_CRITICAL, (short)5));
        combinations.add(new ScoreCombination(Repository.KEYWORD_MODIFIER_CRITICAL, Repository.SENDER_ID_WORD, (short)5));

        combinations.add(new ScoreCombination(Repository.KEYWORD_MODIFIER_HIGH, Repository.KEYWORD_MODIFIER_HIGH, (short)2));
        combinations.add(new ScoreCombination(Repository.KEYWORD_MODIFIER_HIGH, Repository.SENDER_ID_WORD, (short)3));

        combinations.add(new ScoreCombination(Repository.NO_UNSAFE_KEYWORD, Repository.SENDER_ID_NUMBER, (short) 3));
        combinations.add(new ScoreCombination(Repository.NO_UNSAFE_KEYWORD, Repository.THREAD_HAS_SENT_SMS, (short) 4));

        combinations.add(new ScoreCombination(Repository.SENDER_ID_NUMBER, Repository.THREAD_HAS_SENT_SMS, (short) 4));

        /*** KEYWORDS ***/
        List<Keyword> keywords = new ArrayList<Keyword>();
        keywords.add(new Keyword("ichqare", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("gtavazob", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("dagvikavshirdi", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("servisi", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("aqcia", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("iafad", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("iapad", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("sheidzine", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("fasdakleb", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("pasdakleb", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("pasad", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("fasad", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("laridan", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("gakidvashia", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));
        keywords.add(new Keyword("miighe", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.WORD));

        keywords.add(new Keyword("\\d{2}%", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.REGEX));
        keywords.add(new Keyword("\\b\\w+\\s\\d{5}\\b", Repository.KEYWORD_MODIFIER_HIGH, KeywordType.REGEX));
        keywords.add(new Keyword("\\bstop.\\d{5}\\b", Repository.KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        keywords.add(new Keyword("\\bno.\\d{5}\\b", Repository.KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        keywords.add(new Keyword("\\boff.\\d{5}\\b", Repository.KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        keywords.add(new Keyword("\\bnosms\\d{5}\\b", Repository.KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        keywords.add(new Keyword("\\bnosms.\\d{5}\\b", Repository.KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        keywords.add(new Keyword("\\bno.sms.\\d{5}\\b", Repository.KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        keywords.add(new Keyword("\\bstop\\d{5}\\b", Repository.KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));
        keywords.add(new Keyword("\\bstop.\\d{5}\\b", Repository.KEYWORD_MODIFIER_CRITICAL, KeywordType.REGEX));

        for(ScoreValue score: scores) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, score.getId());
            values.put(COLUMN_VALUE, score.getValue());
            database.insert(MySQLiteHelper.TABLE_SCORE_VALUE, null, values);
        }

        for(ScoreCombination combination: combinations) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_PRIMARY, combination.getPrimary());
            values.put(COLUMN_SECONDARY, combination.getSecondary());
            values.put(COLUMN_MULTIPLIER, combination.getMultiplier());
            database.insert(MySQLiteHelper.TABLE_COMBINATION, null, values);
        }

        for(Keyword keyword: keywords) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_KEYWORD, keyword.getKeyword());
            values.put(COLUMN_MODIFIER, keyword.getModifier());
            values.put(COLUMN_TYPE, keyword.getType().getNumVal());
            database.insert(MySQLiteHelper.TABLE_KEYWORD, null, values);
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, Repository.META_THRESHOLD);
        values.put(COLUMN_VALUE, 150);
        database.insert(TABLE_META, null, values);
    }

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_COMBINATION);
        database.execSQL(CREATE_KEYWORD);
        database.execSQL(CREATE_SCORE_VALUE);
        database.execSQL(CREATE_META);
        populateDatabase(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }
}