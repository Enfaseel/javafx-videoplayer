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
        volume.valueProperty().addListener(ov -> {
            if (volume.isValueChanging()) {
                player.getMediaPlayer().setVolume(volume.getValue() / 100.0);
            }
        });
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
            view.setMediaPlayer(player.getMediaPlayer());
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
    }

    private void sliderMoving() {
        player.getMediaPlayer().currentTimeProperty().addListener(ov -> updateValues());
    }

    private void updateValues() {
        MediaPlayer mediaPlayer = player.getMediaPlayer();
        double current = mediaPlayer.getCurrentTime().toMillis();
        double total = mediaPlayer.getTotalDuration().toMillis();
        seekSlider.setValue((current / total) * 100);
    }

    @FXML
    MenuItem close;

    public void closeApplication() {
        System.exit(0);
    }
}