package scenarioSteps;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import java.awt.Robot;

import static utils.ProcesEnder.isProcessRunning;
import static utils.ProcesEnder.killProcess;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ApplicationSteps implements En {
    private Runtime runTime;
    private Process process;
    private final String WiniumServiceName = "Winium.Desktop.Driver.exe";
    private WiniumDriver driver;
    private WebElement window;

    @Before
    public void setUp(){
        //Define Winium Driver service and start it
        try {
            System.out.println("Starting Winium Driver");
            runTime = Runtime.getRuntime();
            process = runTime.exec("cmd /c start " + WiniumServiceName);
         } catch (IOException e) {
            e.printStackTrace();
        }

        //Using the Winium driver, open the Wise application
        try {
        DesktopOptions options= new DesktopOptions();
        options.setApplicationPath("c://bng//demo.cmd");
        //options.setDebugConnectToRunningApp(false);
        options.setLaunchDelay(2);
            driver=new WiniumDriver(new URL("http://localhost:9999"),options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        //Wait for the Wise application to startup
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
        //When all tests are finished, shutdown the application

        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_F4);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Closing the driver is not needed, as this results in some errors
        //Shutting down the Winium server will suffice
        //        try {
        //            driver.close();
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        }

        //When the application is closed, shut down the Winium driver
        try {
            if (process.isAlive()) {
                System.out.println("Shutting down Winium Driver");
                process.destroy();
            } else {
                Runtime.getRuntime().exec("taskkill /F /IM " + WiniumServiceName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ApplicationSteps() {
        Given("^I login as user \"([^\"]*)\"$", (String username) -> {
            System.out.println("Attemptoing to login as user " + username);
        });
        When("^I search for a book with the title \"([^\"]*)\"$", (String bookTitle) -> {
            System.out.println("Searching for a book");
        });
        Then("^I can use the ISBN$", () -> {
            System.out.println("ISBN will be displayed here");
        });
        When("^I search for a person with the name \"([^\"]*)\"$", (String personName) -> {
            System.out.println("Searching for a person");
        });
        Then("^I can use the customernumber$", () -> {
            System.out.println("Customer number will be displayed here");
        });


    }
}
