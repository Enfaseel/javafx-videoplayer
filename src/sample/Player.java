package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

interface PlayerEvents {
    void onPlay();
    void onPause();
    void onTimeChange(Duration oldTime, Duration newTime);
    void onMediaChanged(Media media);
}
class Player implements PlayerEvents {
    static Map<String, File> mediaFiles = new HashMap<>();
    private MediaPlayer mediaPlayer;
    private Media currentMedia;

    @Override
    public void onPause() {}

    @Override
    public void onTimeChange(Duration oldTime, Duration newTime) {}

    @Override
    public void onMediaChanged(Media media) {}

    @Override
    public void onPlay() {}

    void setMedia(File filename) {
        Media media = new Media(filename.toURI().toString());
        Optional<MediaPlayer> player = Optional.ofNullable(mediaPlayer);
        player.ifPresent(MediaPlayer::dispose);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnReady(() -> onMediaChanged(mediaPlayer.getMedia()));
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> onTimeChange(oldValue, newValue));

        //listView.setOnMouseClicked(event -> mediaPlayer = new MediaPlayer(new Media(mediaFiles.get(listView.getSelectionModel().getSelectedItem()).toURI().toString())));
        //mediaPlayer = new MediaPlayer(new Media(mediaFiles.get("").toURI().toString()));
    }

    void play() {
        onPlay();
        mediaPlayer.play();
    }

    void pause() {
        onPause();
        mediaPlayer.pause();
    }

    void setSeekAfterSliderMoving(double sliderState) {
        mediaPlayer.seek(Duration.millis(sliderState));
    }

    MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
