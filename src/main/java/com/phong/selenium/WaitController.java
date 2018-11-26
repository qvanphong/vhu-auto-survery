package com.phong.selenium;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WaitController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton retryButton;

    @FXML
    private Label infoLabel;

    public void initialize(){
        infoLabel.setText("Đang tiến hành...");
        retryButton.setVisible(false);
        cancelButton.setVisible(true);
        retryButton.setDisable(true);
        cancelButton.setDisable(false);
        cancelButton.setOnAction(e -> System.exit(1));
        retryButton.setOnAction(e -> {
            try {
                AnchorPane loginPane = FXMLLoader.load(getClass().getResource("/login.fxml"));
                rootPane.getChildren().setAll(loginPane);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        Platform.runLater(() -> {
            AutoCheck autoCheck = new AutoCheck();
            autoCheck.start();
            synchronized (autoCheck){
                try {
                    System.out.println("synchronized of Auto Check Begin");
                    autoCheck.wait();
                    if(!Status.isLogged){
                        infoLabel.setText("Có lỗi với tài khoản, vui lòng thử lại");
                        retryButton.setVisible(true);
                        cancelButton.setVisible(false);
                        retryButton.setDisable(false);
                        cancelButton.setDisable(true);
                    }else{
                        infoLabel.setText("Hoàn thành!");
                        cancelButton.setText("Thoát");
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }
}
