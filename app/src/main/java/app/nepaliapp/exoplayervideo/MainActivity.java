package app.nepaliapp.exoplayervideo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class MainActivity extends AppCompatActivity {
    PlayerView playerView;
    private ExoPlayer player;
    private boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = findViewById(R.id.player_view);
        initializePlayer();

        playerView.setFullscreenButtonClickListener(new PlayerView.FullscreenButtonClickListener() {
            @Override
            public void onFullscreenButtonClick(boolean isFullScreen) {
                toggleFullScreen();
            }
        });
    }
    private void initializePlayer() {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri("https://firebasestorage.googleapis.com/v0/b/free-bsc-and-mobile.appspot.com/o/Coil_Final.mp4?alt=media&token=90473fd9-b5ce-4082-ac9d-e58219b5b584");
        player.setMediaItem(mediaItem);
        player.prepare();
    }

    private void toggleFullScreen() {
        if (isFullScreen) {
            // Exit fullscreen
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

            playerView.showController();  // Show the controllers

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
            playerView.setLayoutParams(params);

            isFullScreen = false;
        } else {
            // Enter fullscreen
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            playerView.hideController();  // Hide the controllers

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            playerView.setLayoutParams(params);

            isFullScreen = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
