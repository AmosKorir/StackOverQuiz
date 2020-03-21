package com.q.stackoverquiz;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final ImageView player = findViewById(R.id.play);
    player.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        playMusic(1);
      }
    });
    mediaPlayer = new MediaPlayer();
  }

  int file = 0;
  MediaPlayer mediaPlayer;

  int x = 0;

  public void playMusic(int i) {
    x = x + i;
    if (x == 1) {
      file = R.raw.tone1;
    } else if (x == 2) {
      file = R.raw.tone2;
    } else {
      x = 0;
      file = R.raw.tone3;
    }
    mediaPlayer.reset();
    Toast.makeText(this, x + "", Toast.LENGTH_SHORT).show();
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    AssetFileDescriptor assetFileDescriptor =
        MainActivity.this.getResources().openRawResourceFd(file);
    if (assetFileDescriptor == null) return;
    try {
      mediaPlayer.setDataSource(
          assetFileDescriptor.getFileDescriptor(),
          assetFileDescriptor.getStartOffset(),
          assetFileDescriptor.getLength()
      );
      assetFileDescriptor.close();

      mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
          if (mediaPlayer != null) {
            mediaPlayer.release();
          }
        }
      });

      mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(final MediaPlayer mediaPlayer) {
          mediaPlayer.start();
        }
      });
      mediaPlayer.prepareAsync();
    } catch (IllegalArgumentException e) {
      //                            HelpFunctions.showLog("ERROR = " + e);
    } catch (IllegalStateException e) {
      //                            HelpFunctions.showLog("ERROR = " + e);
    } catch (IOException e) {
      //                            HelpFunctions.showLog("ERROR = " + e);
    }
  }
}
