package fi.ptm.recordaudioexample;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author PTM
 */
public class MainActivity extends Activity {

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String path = Environment.getExternalStorageDirectory().getPath();
        outputFile = path+"/recordedAudio.3gpp";
    }

    // start recording audio
    public void startRecording(View view) {
        if (mediaRecorder != null) mediaRecorder.release();
        File file = new File(outputFile);
        if (file.exists()) file.delete();
        try {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(outputFile);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e ) {
            Toast.makeText(getBaseContext(), "Cannot record audio!", Toast.LENGTH_SHORT).show();
        }
    }

    // stop recording audio
    public void stopRecording(View view) {
        if (mediaRecorder != null) mediaRecorder.stop();
    }

    // play recorded file
    public void playAudio(View view) {
        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(outputFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Cannot start audio!", Toast.LENGTH_SHORT).show();
        }
    }

    // stop playing audio
    public void stopAudio(View view) {
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

}
