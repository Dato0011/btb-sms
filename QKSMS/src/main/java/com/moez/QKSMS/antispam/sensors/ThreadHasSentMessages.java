package com.moez.QKSMS.antispam.sensors;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.moez.QKSMS.data.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dato0 on 3/30/2017.
 */

public class ThreadHasSentMessages implements ISensor {
    @Override
    public List<Short> analyze(Message msg, Context context) {
        final short THREAD_HAS_SENT_SMS = 203;

        Uri mSmsinboxQueryUri = Uri.parse("content://sms/sent");
        Cursor cursor = context.getContentResolver().query(mSmsinboxQueryUri, new String[] { "_id" }, "thread_id = ?", new String[] { String.valueOf(msg.getThreadId()) }, null);

        List<Short> result = new ArrayList<>();
        int count = cursor.getCount();
        for(int i = 0; i < count; ++i) {
            result.add(THREAD_HAS_SENT_SMS);
        }

        return result;
    }
}
