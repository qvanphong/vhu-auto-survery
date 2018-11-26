package com.phong.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class AutoCheck implements Runnable {
    private static AutoCheck autoCheck = new AutoCheck();

    public static AutoCheck getInstance() {
        return autoCheck;
    }


    private Thread t;
    private WebDriver driver;
    private List<WebElement> listOfSurvey;


    public void run() {
        synchronized (this) {
            String onWebsite = "On ";

            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setBinary("C:\\Users\\Admin\\AppData\\Local\\CentBrowser\\Application\\chrome.exe");
            driver = new ChromeDriver(chromeOptions);
            driver.get("http://portal.vhu.edu.vn");
            System.out.println(onWebsite + driver.getCurrentUrl());
            //Get "Sinh Viên" Tab
            WebElement svTab = driver.findElement(By.xpath("//*[@id=\"portlet_e73fe5a4-2b31-4549-abc4-e3beda7246bf\"]/div/div/ul/li[1]/a"));
            svTab.click();
            String loginUrl = driver.getCurrentUrl();
            System.out.println(onWebsite + loginUrl);
            //Login
            driver.findElement(By.name("ctl09$txtUserName")).sendKeys(Status.username);
            driver.findElement(By.name("ctl09$txtPassword")).sendKeys(Status.password);
            driver.findElement(By.name("ctl09$btnDangnhap")).click();
            try {
                Alert alert = driver.switchTo().alert();
                System.out.println(alert.getText());
                driver.close();
                driver.quit();
                notify();
                return;

            } catch (NoAlertPresentException e) {
                Status.isLogged = true;
            }


            //find "Khảo sát" tab
            ((ChromeDriver) driver).findElementByXPath("//*[@id=\"pnCenterDisplay\"]/div/div[1]/div[1]/div[2]/div[8]/a").click();
            //Test invailid link
            findAllExistSurvey();
//            for(WebElement e : listOfSurvey){
//                System.out.println(e.toString());
//            }
            String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
            for (WebElement webElement : listOfSurvey) {
                try {
                    webElement.sendKeys(selectLinkOpeninNewTab);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            driver.close();
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            System.out.println(tabs.size());
            for (int i = 0; i < listOfSurvey.size(); i++) {
                try {
                    driver.switchTo().window(tabs.get(i));
                    doSurvey();
                } catch (BrokenBarrierException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            driver.quit();
            notify();
        }
    }

    private void findAllExistSurvey() {
        if (listOfSurvey == null) listOfSurvey = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String id = "ctl09_ctl00_grvHocphandangky_hplComment_" + i;
            boolean isExist = driver.findElements(By.id(id)).size() > 0;
            if (isExist) {
                listOfSurvey.add(driver.findElement(By.id(id)));
            } else {
                return;
            }
        }
    }

    private void doSurvey() throws BrokenBarrierException, InterruptedException {
//        for(int question = 0; question < 4; question++){
//            for (int choice = 0; choice < 5; choice++){
//                driver.findElement(By.id("dtlComments_grvComments_"+question+"_rdbRattot_"+choice)).click();
//            }
//        }

        final CyclicBarrier gate = new CyclicBarrier(3);

        Thread t1 = new Thread(() -> {
            try {
                gate.await();
                System.out.println("Working on question 3-4");
                for (int question = 3; question > 1; question--) {
                    for (int choice = 4; choice >= 0; choice--) {
                        driver.findElement(By.id("dtlComments_grvComments_" + question + "_rdbRattot_" + choice)).click();
                    }
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            //do stuff
        });
        Thread t2 = new Thread(() -> {
            try {
                gate.await();
                System.out.println("Working on question 1-2");
                for (int question = 0; question < 2; question++) {
                    for (int choice = 0; choice < 5; choice++) {
                        driver.findElement(By.id("dtlComments_grvComments_" + question + "_rdbRattot_" + choice)).click();
                    }
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        gate.await();
        synchronized (t1){
            System.out.println("Wait for thread done");
            t1.wait();
            System.out.println("Thread's done, submit!");
            driver.findElement(By.name("btnSave")).click();
            driver.close();
        }
    }

    void start() {
        System.out.println("Auto Check Runnable Begin!");
        if (t == null) {
            t = new Thread(this, "Auto Check");
            t.start();
        }
    }
}
