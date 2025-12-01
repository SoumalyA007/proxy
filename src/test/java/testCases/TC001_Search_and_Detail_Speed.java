package testCases;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import testBases.baseClass;
import testBases.urlEncode;
import utilities.CDPNetworkManager;
import utilities.CDPNetworkManager.SearchResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TC001_Search_and_Detail_Speed extends baseClass {


    public String getRandomKeyword(){

        List<String> keywords = Arrays.asList("abc","def");

        return keywords.get(new Random().nextInt(keywords.size()));
    }



    @Test
    public void test_search_detail_speed_15_keywords() throws InterruptedException {
        logger.info("***Starting TC001_Search_Detail_speed_with_15_keywords");

        Assert.assertTrue(driver instanceof ChromeDriver, " The driver you chose does not support the process we are following to calculate the speedas CDP works only on google");

        long search_threshold_sec = Long.parseLong(p.getProperty("search_threshold_sec","6"));
        long detail_threshold_sec = Long.parseLong(p.getProperty("detail_threshold sec","7"));
        int iteration =Integer.parseInt(p.getProperty("search_iterations"));

        List<Long> allSearchResponseTimes = new ArrayList<>();
        List<Long> allDetailResponseTimes = new ArrayList<>();

        // OOS counters
        int totalProductsChecked = 0;
        int totalProductsOOS = 0;

        List<String> failureMessages = new ArrayList<>();

        Reporter.log("<---- Aggregated test for the speed test of detail and search has started  ---->",false);
        logger.info("<---- Aggregated test for the speed test of detail and search has started  ---->");

        for(int i=1;i<=iteration;i++){
            String keyword = getRandomKeyword();
            String searchURL = urlEncode.buildURL(keyword);

            Reporter.log("----- Iteration " + i + " / " + iteration + " -----", false);
            Reporter.log("Keyword: " + keyword, false);
            Reporter.log("Navigating to: " + searchURL, false);

            SearchResult searchResult =CDPNetworkManager.captureFirstRequestResponse(driver,
                    () -> driver.get(searchURL),
                    "search_product"
                    );




        }




    }



}
