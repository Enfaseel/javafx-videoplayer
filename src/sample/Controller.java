package sample;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    private Player player = new Player();
    private boolean isPlaying = false;

    @FXML
    MenuItem open;

    public void openMedia() {
        FileChooser fileChooser = new FileChooser();
        File mediaFile = fileChooser.showOpenDialog(new Stage());
        Player.mediaFiles.put(mediaFile.getName(), mediaFile);
        updateListView();
        player.setMedia(mediaFile);
    }

    @FXML
    ListView<String> listView;

    @FXML
    private void listViewTracker() {
        String media = listView.getSelectionModel().getSelectedItem();
        breakPlaying();
        player.setMedia(Player.mediaFiles.get(media));
        view.setMediaPlayer(player.getMediaPlayer());
    }

    private void breakPlaying() {
        player.getMediaPlayer().stop();
        isPlaying = false;
        playOrPause.setText("Play");
    }

    private void updateListView() {
        listView.setOrientation(Orientation.VERTICAL);
        listView.getItems().removeAll();
        for (String name : Player.mediaFiles.keySet()) {
            //TODO: починить выбор конкретного файла и указатель на него
//            if (player.getMediaPlayer() != null) {
//                if (player.getMediaPlayer().getMedia().getSource().equals(Player.mediaFiles.get(name).toURI().toString())) {
//                    listView.getItems().add(">>>" + name);
//                    continue;
//                }
//            }
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
        if (!isPlaying) {
            player.play();
            if (view.getMediaPlayer() == null) view.setMediaPlayer(player.getMediaPlayer());
            sliderMoving();
            playOrPause.setText("Pause");
            isPlaying = true;
        } else {
            player.pause();
            playOrPause.setText("Play");
            isPlaying = false;
        }
    }

    @FXML
    Slider seekSlider;

    public void seek() {
        player.setSeekAfterSliderMoving(seekSlider.getValue());
        sliderMoving();
    }

    private void sliderMoving() {
        MediaPlayer mediaPlayer = player.getMediaPlayer();
        double total = mediaPlayer.getTotalDuration().toMillis();
        mediaPlayer.currentTimeProperty().addListener(ov -> updateValues(mediaPlayer, total));
    }

    private void updateValues(MediaPlayer mediaPlayer, double total) {
//        if (!seekSlider.isHover()) {
        if (!seekSlider.isPressed()) {
            double current = mediaPlayer.getCurrentTime().toMillis();
            seekSlider.setValue((current / total) * 100);
        }
    }

    @FXML
    MenuItem close;

    public void closeApplication() {
        System.exit(0);
    }
}