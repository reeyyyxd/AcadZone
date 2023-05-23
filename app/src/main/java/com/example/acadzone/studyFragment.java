package com.example.acadzone;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class studyFragment extends Fragment {

    private EditText mEditTextInput;
    private TextView mTextViewCountDown;
    private Button mButtonSet;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private Button mButtonStop;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;
    private boolean mTimerStopped;

    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;

    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_TIME_LEFT = "time_left";
    private static final String KEY_TIMER_RUNNING = "timer_running";

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);
        mEditTextInput = view.findViewById(R.id.edit_text_input);
        mTextViewCountDown = view.findViewById(R.id.text_view_countdown);
        mButtonSet = view.findViewById(R.id.button_set);
        mButtonStartPause = view.findViewById(R.id.button_start_pause);
        mButtonReset = view.findViewById(R.id.button_reset);
        mButtonStop = view.findViewById(R.id.button_stop);

        mButtonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input1 = mEditTextInput.getText().toString();
                if (input1.length() == 0) {
                    Toast.makeText(getActivity(), "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput1 = Long.parseLong(input1) * 60000;
                if (millisInput1 == 0) {
                    Toast.makeText(getActivity(), "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(millisInput1);
                mEditTextInput.setText("");
            }
        });

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        saveTimerState();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mTimerRunning || mTimerStopped) {
            retrieveTimerState();
            updateWatchInterface();
        }
    }

    private void saveTimerState() {
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong(KEY_START_TIME, mStartTimeInMillis);
        editor.putLong(KEY_TIME_LEFT, mTimeLeftInMillis);
        editor.putBoolean(KEY_TIMER_RUNNING, mTimerRunning);

        if (mTimerRunning) {
            editor.putLong("end_time", mEndTime);
        }

        editor.apply();

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    private void retrieveTimerState() {
        SharedPreferences prefs = getActivity().getSharedPreferences("prefs", 0);

        mStartTimeInMillis = prefs.getLong(KEY_START_TIME, 0);
        mTimeLeftInMillis = prefs.getLong(KEY_TIME_LEFT, mStartTimeInMillis);
        mTimerRunning = prefs.getBoolean(KEY_TIMER_RUNNING, false);

        if (mTimerRunning) {
            mEndTime = prefs.getLong("end_time", 0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            if (mTimeLeftInMillis < 0) {
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateWatchInterface();
            } else {
                startTimer();
            }
        }
    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mTimerStopped = false;
                updateWatchInterface();
            }
        }.start();

        mTimerRunning = true;
        mTimerStopped = false;
        updateWatchInterface();
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mTimerStopped = false;
        updateWatchInterface();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
        mButtonSet.setVisibility(View.VISIBLE); // Add this line to make the set button visible after reset
    }

    private void stopTimer() {
        mCountDownTimer.cancel();
        mTimerStopped = true;
        updateWatchInterface();
        Toast.makeText(getActivity(), "Timer stopped", Toast.LENGTH_SHORT).show();
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mButtonSet.setVisibility(View.INVISIBLE);
            mButtonReset.setVisibility(View.VISIBLE);
            mButtonStop.setVisibility(View.VISIBLE);
            mButtonStartPause.setText("pause");
        } else {
            if (mTimerStopped) {
                mButtonSet.setVisibility(View.VISIBLE);
            } else {
                mButtonSet.setVisibility(View.INVISIBLE);
            }
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStop.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("start");
            // remove the following line to keep the pause/start button always visible
            mButtonStartPause.setVisibility(mTimeLeftInMillis > 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }


    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}