package testBases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.decorators.WebDriverDecorator;
import org.testng.ITestContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;



public class baseClass {
    public WebDriver driver;
    public Properties p;
    public Logger logger;

    public void setup(String os, String browser, ITestContext testContext) throws IOException {

        FileReader fr = new FileReader("./src//test//resources//config.properties");
        p = new Properties();
        p.load(fr);

        logger = LogManager.getLogger(this.getClass());

        switch(browser.toLowerCase()){
            case "chrome":
                driver= new ChromeDriver();
            default:
                System.out.println("Driver has issue");
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();

        driver.get(p.getProperty("appURL"));
    }


}
