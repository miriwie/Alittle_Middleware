package com.example.a_little_middleware;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnPreparedListener{
    private MediaPlayer mp;     //Teil des Media Frameworks und der aufbauenden Frameworks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Implimentierung Themenwechsel - Nutzung der API AppCompatDelegate
        SwitchCompat toggle = (SwitchCompat) findViewById(R.id.switch1);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    // The toggle is disabled
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    public void onClick(View view) {
        //Weitere Schritte einleiten, abhängig vom gedrückten Button.
        switch(view.getId()) {
            case R.id.startButton:
                doPlayAudio();
                break;
            case R.id.stopButton:
                doStopAudio();
                break;
            case R.id.startWebButton:
                try {
                    EditText mySource = (EditText) findViewById(R.id.textInput);
                    doPlayAudio(mySource.getText().toString());
                }
                catch (Exception e) {
                    // error handling logic here
                    TextView textView = (TextView) findViewById(R.id.textView2);
                    textView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
    private void doPlayAudio() {
        //lädt die lokale Audiodatei in den Mediaplayer und spielt diese ab.
        mp = MediaPlayer.create(this, R.raw.audio_file);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.start();
    }

    private void doPlayAudio(String audioUrl) throws Exception {
        //Ein neuer MediaPlayer wird initialisiert und die URL übergeben. Er spielt diese nun ab.
        mp = new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setDataSource(audioUrl);
        mp.setOnPreparedListener(this);
        mp.prepareAsync(); //arbeitet im Hindergrund und ruft dann automatisch onPrepared() auf
    }

    private void doStopAudio() {
        //Gibt es ein Mediaplayer, soll er stoppen.
        if (mp != null) {
        mp.stop(); }
    }
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    protected void onDestroy() {
        //Bei Ende der Activity wird eventueller Mediaplayer zerstört.
        super.onDestroy();
        if(mp != null) {
            mp.release(); }
    }


}