package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;

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


}
