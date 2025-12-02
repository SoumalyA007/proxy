package testBases;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;



public class baseClass {
    public WebDriver driver;
    public static Properties p;
    public Logger logger;

    @BeforeClass
    @Parameters({"os","browser"})
    public void setup(String os, String browser, ITestContext testContext) throws IOException {

        FileReader fr = new FileReader("./src//test//resources//config.properties");
        p = new Properties();
        p.load(fr);

        logger = LogManager.getLogger(this.getClass());

        switch(browser.toLowerCase()){
            case "chrome":
                driver= new ChromeDriver();
                break;
            default:
                System.out.println("Driver has issue");
                break;
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.get("https://ubuy.qa");

        testContext.setAttribute("Webdriver",driver);
        driver.manage().window().maximize();

    }

    @AfterClass
    public void teardown(){
        driver.close();
    }

    public String captureScreenshot(String tname) throws IOException {

        String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot)driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        String targetFilePath =System.getProperty("user.dir")+ "\\screenshots\\" +tname + "_" + timestamp+".png";
        File targetFile = new File(targetFilePath);

        FileUtils.copyFile(sourceFile,targetFile);
        return targetFilePath;

    }


}
