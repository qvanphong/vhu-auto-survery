package com.phong.selenium;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField usrField;

    @FXML
    private JFXPasswordField pwField;

    @FXML
    private JFXButton loginButton;

    public void initialize() {
        loginButton.setOnAction(e -> {
            Status.username = usrField.getText();
            Status.password = pwField.getText();
            System.out.println(Status.username + Status.password);
            AnchorPane waitPane;
            try {
                waitPane = FXMLLoader.load(getClass().getResource("/wait.fxml"));
                rootPane.getChildren().setAll(waitPane);
            } catch (IOException e1) {
                e1.printStackTrace();
            }


//            AutoCheck autoCheck = new AutoCheck();
//            autoCheck.start();
//            synchronized (autoCheck){
//                try {
//                    System.out.println("synchronized of Auto Check Begin");
//                    autoCheck.wait();
//                    if(!Status.isLogged){
//                        System.out.println("FAIL TO LOGGIN");
//                        System.out.println("Send retry window");
//                    }
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//
//            }











//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("Run1");
//                    Platform.runLater(() -> {
//                        System.out.println("Run2");
//                        autoCheck.start();
//                        synchronized (AutoCheck.getInstance()) {
//                            System.out.println("Start work");
//                            try {
//                                AutoCheck.getInstance().wait();
//                                System.out.println("Auto Check Done");
//                            } catch (InterruptedException e1) {
//                                e1.printStackTrace();
//                            }
//                        }
//                    });
//
//
//                }
//            };
//            Thread run = new Thread(runnable, "RUNNABLE");
//            run.start();



//            runnable.run();
//            if(!Status.isLogged)
//                System.out.println("FAIL PROGRAM!");


//            synchronized (AutoCheck.getInstance()){
//                System.out.println("Start work");
//                try {
//                    AutoCheck.getInstance().wait();
//                    System.out.println("Auto Check Done");
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//            }
        });


    }


}
