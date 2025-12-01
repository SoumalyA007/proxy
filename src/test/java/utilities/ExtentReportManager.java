package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import testBases.baseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;
    String repName;

    public void onStart(ITestContext testContext){

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm").format(new Date());
        repName = "Test-Report" + timeStamp+".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);
        sparkReporter.config().setDocumentTitle("Automate Proxy");
        sparkReporter.config().setReportName("Proxy");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("User",System.getProperty("user.name"));

        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("OS",os);

        List<String> groups = testContext.getCurrentXmlTest().getIncludedGroups();
        if(!groups.isEmpty()){
            extent.setSystemInfo("Groups",groups.toString());
        }


    }

    public void onTestSuccess(ITestResult result){
        test = extent.createTest(result.getClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS,"Successfully passed test"+result.getMethod());
        List<String> reporterLogs = Reporter.getOutput(result);
        for(String log : reporterLogs){
            test.log(Status.INFO, log);
        }
    }

    public void onTestFailure(ITestResult result){
        test = extent.createTest(result.getClass().getName());
        test.assignCategory(result.getMethod().getGroups());

        try{
            WebDriver driver = (WebDriver)result.getTestContext().getAttribute("WebDriver");

            baseClass bc = new baseClass();
            bc.driver = driver;
            String imgPath = bc.captureScreenshot(result.getName());
            test.addScreenCaptureFromPath(imgPath);


        }catch(Exception e){
            System.out.println("Could not take screenshot : " + e);
        }
        List<String> reporterLogs = Reporter.getOutput(result);
        for(String log : reporterLogs){
            test.log(Status.INFO, log);
        }

    }

    public void onTestSkipped(ITestResult result){
        test = extent.createTest(result.getClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "Test skipped : "+result.getName());
        test.log(Status.INFO,result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext context){
        extent.flush();
        String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
