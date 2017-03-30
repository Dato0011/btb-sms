package com.moez.QKSMS.antispam.sensors;

import com.moez.QKSMS.data.Message;

import java.util.List;

/**
 * Created by dato0 on 3/30/2017.
 */

public interface ISensor {
    List<Short> analyze(Message msg);
}
