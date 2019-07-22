package sample;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Optional;

public class Controller {
    private Player player = new Player() {
        @Override
        public void onPause() {
            super.onPause();
            playOrPause.setText("Play");
        }
        @Override
        public void onPlay() {
            super.onPlay();
            playOrPause.setText("Pause");
        }

        @Override
        public void onTimeChange(Duration oldTime, Duration newTime) {
            super.onTimeChange(oldTime, newTime);
            seekSlider.setValue(newTime.toMillis());
        }

        @Override
        public void onMediaChanged(Media media) {
            super.onMediaChanged(media);
            double total = media.getDuration().toMillis();
            playOrPause.setText("Play");
            seekSlider.setValue(0);
            seekSlider.setMax(total);
        }
    };

    @FXML
    MenuItem open;

    public void openMedia() {
        //MediaPlayer mediaPlayer = player.getMediaPlayer();
        Optional<MediaPlayer> mediaPlayer = Optional.ofNullable(player.getMediaPlayer());
        mediaPlayer.filter(player -> player.getStatus() == MediaPlayer.Status.PLAYING)
                .ifPresent(MediaPlayer::pause);
        FileChooser fileChooser = new FileChooser();
        Optional<File> mediaFile = Optional.ofNullable(fileChooser.showOpenDialog(new Stage()));
        if (mediaFile.isPresent()) {
            File file = mediaFile.get();
            Player.mediaFiles.put(file.getName(), file.getAbsoluteFile());
            updateListView();
            player.setMedia(mediaFile.get());
            view.setMediaPlayer(player.getMediaPlayer());
        }
    }

    @FXML
    ListView<String> listView;

    @FXML
    private void listViewTracker() {
        String media = listView.getSelectionModel().getSelectedItem();
        if (media != null) {
            breakPlaying();
            player.setMedia(Player.mediaFiles.get(media));
            view.setMediaPlayer(player.getMediaPlayer());
        }
    }

    private void breakPlaying() {
        player.getMediaPlayer().stop();
    }

    private void updateListView() {
        listView.setOrientation(Orientation.VERTICAL);
        listView.getItems().clear();
//        listView.refresh();
        for (String name : Player.mediaFiles.keySet()) {
            //TODO: починить выбор конкретного файла и указатель на него
            listView.getItems().add(name);
        }
    }

    @FXML
    Slider volume;

    @FXML
    private void setVolume() {
        player.getMediaPlayer().setVolume(volume.getValue() / 100.0);
    }

//    @FXML
//    private void listChoose() {
//        player.setMedia();
//        updateListView();
//    }

    @FXML
    Button playOrPause;
    @FXML
    MediaView view;

    public void playOrPause() {
        MediaPlayer.Status playerStatus = player.getMediaPlayer().getStatus();
        if (playerStatus == MediaPlayer.Status.READY ||
                playerStatus == MediaPlayer.Status.PAUSED) {
            player.play();
            if (view.getMediaPlayer() == null) view.setMediaPlayer(player.getMediaPlayer());
        } else {
            player.pause();
        }
    }

    @FXML
    Slider seekSlider;

    public void seek() {
        player.setSeekAfterSliderMoving(seekSlider.getValue());
    }


    @FXML
    MenuItem close;

    public void closeApplication() {

    }

    @FXML
    public void setFullScreen() {
        Stage stage = (Stage) view.getScene().getWindow();
        if (!stage.isFullScreen()) {
            //view.resize();
            stage.setFullScreen(true);
        } else stage.setFullScreen(false);
    }
}