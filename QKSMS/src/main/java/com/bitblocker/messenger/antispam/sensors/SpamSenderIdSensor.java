package com.bitblocker.messenger.antispam.sensors;

import android.content.Context;

import com.bitblocker.messenger.data.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dato0 on 3/30/2017.
 */

public class SpamSenderIdSensor implements ISensor {
    @Override
    public List<Short> analyze(Message msg, Context context) {
        final short SENDER_ID_WORD   = 102;
        final short SENDER_ID_NUMBER = 202;

        List<Short> result = new ArrayList<>();
        String address = msg.getAddress();

        if(address.length() < 9) {
            result.add(SENDER_ID_WORD);
        }
        else {
            for (int i = 0; i < address.length(); ++i) {
                char c = address.charAt(i);
                if (Character.isLetter(c)) {
                    result.add(SENDER_ID_WORD);
                    break;
                }
            }
        }

        if(result.size() == 0) {
            result.add(SENDER_ID_NUMBER);
        }
        return result;
    }
}
