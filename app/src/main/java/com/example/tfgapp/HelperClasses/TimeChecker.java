package com.example.tfgapp.HelperClasses;

import android.os.CountDownTimer;

public class TimeChecker {
    private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000;
    private CountDownTimer countDownTimer;

    public void startTimer(final OnTimeExpiredListener listener) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(ONE_DAY_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Do nothing
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onTimeExpired();
                }
            }
        };
        countDownTimer.start();
    }

    public interface OnTimeExpiredListener {
        void onTimeExpired();
    }
}
