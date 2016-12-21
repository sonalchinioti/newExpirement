package com.android.project_x.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.project_x.activities.HActivitySpeechToText_;
import com.android.project_x.receiver.HPhoneStateListener;

import java.util.Date;

/**
 * Created by User on 19-12-2016.
 */

public class HCallReceiver  extends HPhoneStateListener {
    @Override
    protected void onIncomingCallReceived(Context ctx, String number, Date start) {

    }

    @Override
    protected void onIncomingCallAnswered(Context ctx, String number, Date start) {


        HActivitySpeechToText_.intent(ctx).flags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK).start();
    }

    @Override
    protected void onIncomingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx ,number , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        Toast.makeText(ctx ,number , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onOutgoingCallEnded(Context ctx, String number, Date start, Date end) {
        Toast.makeText(ctx ,number , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onMissedCall(Context ctx, String number, Date start) {

    }
}
