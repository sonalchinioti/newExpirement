package com.android.project_x.activities;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.project_x.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by User on 07-12-2016.
 */
@EActivity(R.layout.activity_speech_to_text)
public class HActivitySpeechToText extends HActivityAppCompatTrackable implements RecognitionListener {
    @ViewById
    ToggleButton btn_convert ;


    @ViewById
    TextView tv_text_to_speech ;



    @ViewById
    TextView tv_setting ;


    private final int SPEECH_RECOGNITION_CODE = 100;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;

    private String LOG_TAG = this.getClass().getSimpleName();


    @AfterViews
    public void AfterViews(){

         // method for setting click listener.
        setSettingClickListener();
        //method for converting speech to text
        startSpeechToText();


    }
    /**
     * Start speech to text intent. This opens up Google Speech Recognition API dialog box to listen the speech input.
     * */
    private void startSpeechToText() {
        speech = SpeechRecognizer.createSpeechRecognizer(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        speech.setRecognitionListener(this);
        btn_convert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
//
                    speech.startListening(recognizerIntent);
                } else {


                    speech.stopListening();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speech != null) {
            speech.destroy();

        }
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i(LOG_TAG, "onReadyForSpeech");

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged");
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived");
    }

    @Override
    public void onEndOfSpeech() {
        btn_convert.setChecked(false);

    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorText(error);
        tv_text_to_speech.setText(errorMessage);
        btn_convert.setChecked(false);

    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
//        for (String result : matches)
            text = matches.get(0) + "\n";

        tv_text_to_speech.setText(text);

    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        Log.i(LOG_TAG, "onEvent");
    }



    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Please opt for offline results";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    public void setSettingClickListener(){
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingVoiceRecognizationSetting();
            }
        });

    }

    public void callingVoiceRecognizationSetting(){

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        ComponentName[] components = new ComponentName[]{
                new ComponentName("com.google.android.voicesearch", "com.google.android.voicesearch.VoiceSearchPreferences"),
                new ComponentName("com.google.android.googlequicksearchbox", "com.google.android.voicesearch.VoiceSearchPreferences"),
                new ComponentName("com.google.android.googlequicksearchbox", "com.google.android.apps.gsa.velvet.ui.settings.VoiceSearchPreferences")
        };
        for (ComponentName componentName : components) {
            try {
                intent.setComponent(componentName);
                startActivity(intent);

                break;
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}
