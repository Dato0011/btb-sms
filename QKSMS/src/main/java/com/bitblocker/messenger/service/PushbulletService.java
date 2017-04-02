package com.bitblocker.messenger.service;

import com.bitblocker.messenger.data.ConversationLegacy;
import com.bitblocker.messenger.mmssms.Message;
import com.bitblocker.messenger.mmssms.Transaction;
import com.bitblocker.messenger.transaction.NotificationManager;
import com.bitblocker.messenger.transaction.SmsHelper;
import com.bitblocker.messenger.ui.popup.QKReplyActivity;
import com.pushbullet.android.extension.MessagingExtension;

public class PushbulletService extends MessagingExtension {
    private final String TAG = "PushbulletService";

    @Override
    protected void onMessageReceived(String conversationIden, String body) {
        long threadId = Long.parseLong(conversationIden);
        ConversationLegacy conversation = new ConversationLegacy(getApplicationContext(), threadId);

        Transaction sendTransaction = new Transaction(getApplicationContext(), SmsHelper.getSendSettings(getApplicationContext()));
        Message message = new com.bitblocker.messenger.mmssms.Message(body, conversation.getAddress());
        message.setType(com.bitblocker.messenger.mmssms.Message.TYPE_SMSMMS);
        sendTransaction.sendNewMessage(message, conversation.getThreadId());

        QKReplyActivity.dismiss(conversation.getThreadId());

        NotificationManager.update(getApplicationContext());
    }

    @Override
    protected void onConversationDismissed(String conversationIden) {
        long threadId = Long.parseLong(conversationIden);
        ConversationLegacy conversation = new ConversationLegacy(getApplicationContext(), threadId);
        conversation.markRead();
    }

}
