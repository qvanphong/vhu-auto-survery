//  WIP. Học Thread rồi về làm lại
// Known Bug: Không thể hủy khi đang tiến hành

package com.phong.selenium;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

//    static Thread autoCheckThread = new Thread(AutoCheck.getInstance(), "Auto Check");

    //    public static void main(String[] args) {
//        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
//
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setBinary("C:\\Users\\Admin\\AppData\\Local\\CentBrowser\\Application\\chrome.exe");
//        WebDriver driver = new ChromeDriver(chromeOptions);
//        driver.get("http://portal.vhu.edu.vn");
//    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);


//        autoCheckThread.start();
//        synchronized (autoCheckThread){
//            try{
//                System.out.println("HELLO?");
//                autoCheckThread.wait();
//                System.out.println("Working!");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


    }
}


