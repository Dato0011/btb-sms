package com.bitblocker.messenger.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bitblocker.messenger.service.MarkReadService;
import com.bitblocker.messenger.ui.popup.QKReplyActivity;

public class MarkReadReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        long threadId = extras.getLong("thread_id");

        Intent readIntent = new Intent(context, MarkReadService.class);
        readIntent.putExtra("thread_id", threadId);
        context.startService(readIntent);

        QKReplyActivity.dismiss(threadId);
    }
}
