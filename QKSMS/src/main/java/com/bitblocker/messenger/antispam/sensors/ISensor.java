package com.bitblocker.messenger.antispam.sensors;

import android.content.Context;

import com.bitblocker.messenger.data.Message;

import java.util.List;

/**
 * Created by dato0 on 3/30/2017.
 */

public interface ISensor {
    List<Short> analyze(Message msg, Context context);
}
