package sample;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Player {

    static Map<String, File> mediaFiles = new HashMap<>();
    private MediaPlayer mediaPlayer;



    void setMedia(File media) {
        //TODO: переделать сеттер текущего трека
        mediaPlayer = new MediaPlayer(new Media(media.toURI().toString()));
        //listView.setOnMouseClicked(event -> mediaPlayer = new MediaPlayer(new Media(mediaFiles.get(listView.getSelectionModel().getSelectedItem()).toURI().toString())));
        //mediaPlayer = new MediaPlayer(new Media(mediaFiles.get("").toURI().toString()));
    }

    void play() {
        //if (mediaPlayer == null) setMedia();
        mediaPlayer.play();
    }

    void pause() {
        mediaPlayer.pause();
    }

    void setSeekAfterSliderMoving(double sliderState) {
        double totalTime = mediaPlayer.getTotalDuration().toMillis();
        mediaPlayer.seek(Duration.millis(totalTime * sliderState / 100));
    }

    MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
