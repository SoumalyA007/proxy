package testCases;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testBases.baseClass;
import testBases.urlEncode;
import utilities.CDPNetworkManager;
import utilities.CDPNetworkManager.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import pageObject.detail_page;

public class TC001_Search_and_Detail_Speed extends baseClass {


    public String getRandomKeyword(){

        List<String> keywords = Arrays.asList( "wireless bluetooth headphones",
                "noise cancelling earbuds",
                "over ear gaming headset",
                "studio monitoring headphones",
                "true wireless stereo earbuds",
                "portable bluetooth speaker",
                "waterproof outdoor speaker",
                "smart home security camera",
                "4k action sports camera",
                "digital single lens camera",
                "mirrorless camera bundle",
                "portable mini projector",
                "1080p home theater projector",
                "4k ultra hd projector",
                "wifi enabled printer",
                "laser mono printer",
                "all in one inkjet printer",
                "portable photo printer",
                "usb c fast charger",
                "65w gan charger",
                "multiple device charger",
                "magnetic wireless charger",
                "universal travel adapter",
                "usb c docking station",
                "8k hdmi certified cable",
                "ultra high speed hdmi cable",
                "wireless charging power bank",
                "high capacity power bank",
                "solar powered power bank",
                "portable external ssd",
                "mechanical gaming keyboard",
                "wireless keyboard mouse combo",
                "rgb gaming mouse pad",
                "programmable gaming mouse",
                "ergonomic office keyboard",
                "gaming laptop cooling pad",
                "curved gaming monitor",
                "ultrawide computer monitor",
                "frameless ips monitor",
                "dual monitor arm mount",
                "adjustable standing desk",
                "electric height adjustable desk",
                "ergonomic office chair",
                "lumbar support office chair",
                "mesh breathable office chair",
                "compact home office desk",
                "noise reducing office partition",
                "smart digital alarm clock",
                "touch control table lamp",
                "adjustable desk reading lamp",
                "smart led light bulbs",
                "multicolor ambient light strip",
                "battery powered night light",
                "solar garden path lights",
                "outdoor waterproof string lights",
                "motion activated security lights",
                "wireless video doorbell",
                "smart home thermostat",
                "portable air cooler",
                "compact mini air conditioner",
                "smart wifi ceiling fan",
                "tower fan with remote",
                "cool mist ultrasonic humidifier",
                "essential oil aromatherapy diffuser",
                "large room air purifier",
                "hepa filter air purifier",
                "robot vacuum cleaner",
                "robotic vacuum mop combo",
                "cordless handheld vacuum",
                "lightweight upright vacuum",
                "steam mop floor cleaner",
                "home carpet deep cleaner",
                "kitchen knife block set",
                "stainless steel cookware set",
                "nonstick frying pan set",
                "cast iron Dutch oven",
                "ceramic bakeware set",
                "silicone baking mats",
                "electric pressure cooker",
                "stainless steel electric kettle",
                "temperature control kettle",
                "high power food processor",
                "compact personal blender",
                "professional stand mixer",
                "automatic espresso machine",
                "cold brew coffee maker",
                "stainless steel toaster oven",
                "air fryer oven combo",
                "digital food kitchen scale",
                "instant read meat thermometer",
                "bamboo cutting board set",
                "reusable silicone food bags",
                "oven safe mixing bowls",
                "glass meal prep containers",
                "insulated stainless steel bottle",
                "stainless steel lunch box",
                "leak proof bento lunch box",
                "thermal insulated lunch bag",
                "dish drying rack expandable",
                "over sink drying rack",
                "laundry folding drying stand",
                "collapsible storage bins",
                "vacuum sealed storage bags",
                "underbed storage organizer",
                "heavy duty clothes hangers",
                "large woven laundry basket",
                "fabric shoe storage organizer",
                "bamboo wardrobe storage box",
                "queen size bed mattress",
                "memory foam mattress topper",
                "orthopedic neck pillow",
                "cooling gel memory pillow",
                "microfiber bed sheet set",
                "luxury cotton comforter",
                "weighted blanket for sleep",
                "anti allergy pillow protector",
                "blackout thermal curtains",
                "room darkening roller blinds",
                "large area living room rug",
                "washable non slip rug",
                "modern wall art paintings",
                "floating wooden wall shelves",
                "indoor plant ceramic pots",
                "metal floor plant stand",
                "decorative fairy string lights",
                "foldable fabric futon sofa",
                "wooden coffee table set",
                "ergonomic gaming chair",
                "reclining gaming chair",
                "ultra soft bath towels",
                "quick dry bath mat",
                "shower caddy organizer",
                "rainfall shower head",
                "steam shower cleaner",
                "multi function hair dryer",
                "ceramic hair straightener",
                "automatic hair curler",
                "professional beard trimmer",
                "beard grooming kit",
                "nose and ear trimmer",
                "electric shaver for men",
                "premium safety razor set",
                "organic face moisturizer",
                "vitamin c facial serum",
                "retinol night cream",
                "exfoliating facial scrub",
                "hydrating sheet mask pack",
                "charcoal peel off mask",
                "deep cleansing face wash",
                "sulfate free shampoo",
                "keratin hair conditioner",
                "hair repair treatment mask",
                "organic argan hair oil",
                "professional makeup brushes",
                "full coverage foundation",
                "matte liquid lipstick set",
                "eyeshadow palette kit",
                "long lasting makeup setting spray",
                "women's running shoes",
                "women's chunky sneakers",
                "women's winter coat",
                "lightweight women's jacket",
                "high waist yoga leggings",
                "women's sports bra set",
                "women's mini backpack purse",
                "designer crossbody handbag",
                "genuine leather women's wallet",
                "men's casual sneakers",
                "men's running shoes",
                "men's cargo jogger pants",
                "men's cotton polo shirts",
                "men's waterproof winter jacket",
                "men's lightweight windbreaker",
                "men's leather belt set",
                "men's stainless steel watch",
                "travel laptop backpack",
                "anti theft laptop backpack",
                "hard shell carry on luggage",
                "4 wheel spinner suitcase",
                "large travel duffel bag",
                "portable digital luggage scale",
                "memory foam travel neck pillow",
                "compact travel umbrella",
                "waterproof hiking boots",
                "camping hiking backpack",
                "ultralight trekking poles",
                "sleeping bag for camping",
                "waterproof camping tent",
                "camping cookware mess kit",
                "rechargeable camping lantern",
                "usb rechargeable headlamp",
                "portable solar camping panel",
                "outdoor folding camping chair",
                "inflatable pool lounge chair",
                "heavy duty garden hose",
                "expandable garden hose",
                "electric high pressure washer",
                "garden pruning shears",
                "stainless steel hose nozzle",
                "automatic pet feeder",
                "smart pet water fountain",
                "retractable dog leash",
                "adjustable pet grooming brush",
                "orthopedic dog bed",
                "cat litter deodorizer",
                "pet hair lint remover",
                "cat climbing tower tree",
                "kids educational wooden toys",
                "kids bluetooth headphones",
                "magnetic building block set",
                "interactive learning tablet",
                "remote control stunt car",
                "kids waterproof school backpack",
                "baby diaper changing pad",
                "portable baby bottle warmer",
                "infant car seat stroller combo",
                "soft cotton baby swaddle blankets",
                "silicone baby feeding set",
                "baby safety cabinet locks",
                "smart digital thermometer",
                "blood pressure monitor",
                "pulse oximeter fingertip",
                "resistance workout bands",
                "adjustable dumbbell set",
                "foldable home treadmill",
                "exercise rowing machine",
                "indoor exercise bike magnetic",
                "yoga mat with alignment lines",
                "foam roller deep tissue",
                "ab roller wheel kit",
                "home gym adjustable bench",
                "mountain bike helmet",
                "cycling gloves padded",
                "carbon fiber phone case",
                "clear magsafe phone case",
                "tempered glass screen protector",
                "laptop cooling pad",
                "gaming console controller",
                "nintendo switch accessories",
                "playstation charging station",
                "xbox wireless controller",
                "4k streaming media player",
                "wifi mesh router system",
                "wireless barcode scanner",
                "thermal shipping label printer",
                "barcode label sticker rolls",
                "receipt thermal paper rolls",
                "disposable nitrile gloves",
                "touchless hand sanitizer dispenser",
                "safety industrial goggles",
                "noise reduction ear protectors",
                "toolbox drill set combo",
                "cordless electric drill",
                "heavy duty wrench set",
                "magnetic screwdriver set",
                "precision tool kit",
                "folding utility knife",
                "stainless steel tape measure",
                "wall mounted bike rack",
                "car dashboard phone mount",
                "wireless car charger mount",
                "car scratch remover kit",
                "car vacuum portable",
                "waterproof car seat cover",
                "leather car steering cover",
                "tire pressure gauge digital",
                "car windshield sun shade",
                "motorcycle riding gloves",
                "motorcycle waterproof cover",
                "electric guitar starter kit",
                "guitar tuning pedal",
                "portable digital piano",
                "studio condenser microphone",
                "microphone boom arm stand",
                "sound absorbing foam panels",
                "professional ring light kit",
                "softbox photography lighting",
                "3d printer filament pla",
                "3d printer enclosure tent",
                "adjustable guitar stand",
                "premium acoustic guitar strings",
                "electric violin beginner set",
                "wood carving chisel set",
                "acrylic paint brush set",
                "adult coloring book set",
                "diamond painting art kit",
                "sewing machine portable",
                "knitting needle starter set",
                "reusable cotton shopping bags",
                "eco friendly bamboo toothbrushes",
                "biodegradable trash bags",
                "stainless steel reusable straws");

        return keywords.get(new Random().nextInt(keywords.size()));
    }



    @Test
    public void test_search_detail_speed_15_keywords() throws InterruptedException, IOException {
        logger.info("***Starting TC001_Search_Detail_speed_with_15_keywords");

        Assert.assertTrue(driver instanceof ChromeDriver, " The driver you chose does not support the process we are following to calculate the speedas CDP works only on google");

        long search_threshold_sec = Long.parseLong(p.getProperty("search_threshold_sec", "6"));
        long detail_threshold_sec = Long.parseLong(p.getProperty("detail_threshold_sec", "7"));
        int iteration = Integer.parseInt(p.getProperty("search_iterations"));
        double oos_threshold_percentage = Double.parseDouble(p.getProperty("oos_threshold_percentage", "70"));

        List<Double> allSearchResponseTimes = new ArrayList<>();
        List<Double> allDetailResponseTimes = new ArrayList<>();

        // OOS counters
        int totalProductsOOS = 0;

        List<String> failureMessages = new ArrayList<>();
        List<String> oosURLs = new ArrayList<>();

        Reporter.log("<---- Aggregated test for the speed test of detail and search has started  ---->", false);
        logger.info("<---- Aggregated test for the speed test of detail and search has started  ---->");

        SoftAssert softAssert = new SoftAssert();
        for (int i = 1; i <= iteration; i++) {
            String keyword = getRandomKeyword();
            String searchURL = urlEncode.buildURL(keyword);

            Reporter.log("----- Iteration " + i + " / " + iteration + " -----", false);
            Reporter.log("Keyword: " + keyword, false);
            Reporter.log("Navigating to: " + searchURL, false);

            SearchResult searchResult = CDPNetworkManager.captureFirstRequestResponse(driver,
                    () -> driver.get(searchURL),
                    "search_product"
            );

            String searchedURL = searchResult.getUrl();
            int productCount = searchResult.getProductUrls().size();
            double respTime = searchResult.getResponseTimeMs();
            String body = searchResult.getResponseBody();

            Reporter.log("Searhed product URL: " + searchedURL, false);
            logger.info("Searhed product URL: " + searchedURL);
            Reporter.log("Number of products found: " + productCount, false);
            logger.info("Number of products found: " + productCount);
            Reporter.log("Response time taken for search: " + respTime, false);
            logger.info("Response time taken for search" + respTime);

            logger.info("The reponse body :: " + body);

            allSearchResponseTimes.add(respTime);


            if (respTime > search_threshold_sec) {
                softAssert.assertTrue(false,
                        "Response time exceeded! Actual: " + respTime + " sec, Threshold: " + search_threshold_sec + " sec");

                String fm = "Iteration " + i + " | Keyword '" + keyword + "' → search_product too slow: " + respTime + "ms (threshold " + search_threshold_sec + "ms)";

                Reporter.log(fm, true);
                failureMessages.add(fm);
                String imgPath = captureScreenshot("search_" + keyword.replaceAll("\\s+", "_") + "_" + i);
                Reporter.log("Search screenshot: " + imgPath, true);
            }


            if (searchedURL == null) {
                softAssert.assertTrue(false,
                        "No Search URL Found ");
                Reporter.log("No Search URL Found!!!!");
            }

            if (productCount == 0 || productCount < 2) {
                softAssert.assertTrue(false, "Please verify... Either there is no products for current Keyword or very less products" + productCount);
                Reporter.log("Please verify... Either there is no products for current Keyword or very less products: " + productCount);
                String imgPath = captureScreenshot("product_quantity_" + keyword.replaceAll("\\s+", "_") + "_" + i);
                Reporter.log("Search screenshot: " + imgPath, true);

            }

            if (respTime == 0) {
                softAssert.assertTrue(false, "Product response time is 0");
                Reporter.log("Product response time is 0");
            }


            Reporter.log("**Starting Detail Page check");

            List<String> urls = searchResult.getProductUrls();
            String productUrl = urls.get(new Random().nextInt(urls.size()));

            Reporter.log("Chosen URL for detail page check : " + productUrl);

            SearchResult detailResult = CDPNetworkManager.captureFirstRequestResponse(driver,
                    () ->driver.get(productUrl),
                    "detail_product");

            double detail_resp_time = detailResult.getResponseTimeMs();
            String detaildURL = detailResult.getUrl();

            Reporter.log("detail_product URL: " + detailResult.getUrl(), false);
            Reporter.log("detail_product Response Time: " + detail_resp_time + "s", false);

            allDetailResponseTimes.add(detail_resp_time);


            if (detail_resp_time > detail_threshold_sec) {
                softAssert.assertTrue(false,
                        "Response time exceeded! Actual: " + detail_resp_time + " sec, Threshold: " + detail_threshold_sec + " sec");

                String fm = "Iteration " + i + " | URL '" + productUrl + "' → detail_product too slow: " + detail_resp_time + "s (threshold " + detail_threshold_sec + "s)";

                Reporter.log(fm, true);
                String imgPath = captureScreenshot("detail_" + keyword.replaceAll("\\s+", "_") + "_" + i);
                Reporter.log("Detail_screenshot: " + imgPath, true);
                failureMessages.add(fm);
            }
            detail_page dp = new detail_page(driver);
            String status = dp.is_displayed_inStock();

            switch(status){
                case "IN_STOCK":
                    Reporter.log("Found the product in stock");
                    break;
                case "OOS":
                    totalProductsOOS++;
                    Reporter.log("The product is Out of Stock" + productUrl);
                    oosURLs.add(productUrl);
                    break;
                default:
                    Reporter.log("Could not find the stock status");
                    break;
            }


        }

        double OOSPercentage=0.0;
        OOSPercentage = (totalProductsOOS*100)/iteration;

        Reporter.log("===== OOS Summary =====", true);
        Reporter.log("Total Products Checked: " + iteration, true);
        Reporter.log("Total Products OOS: " + totalProductsOOS, true);
        Reporter.log("OOS Percentage: " + String.format("%.2f", OOSPercentage) + "%", true);
        Reporter.log("OOS Threshold: " + p.getProperty("oos_threshold_percentage") + "%", true);

        logger.info("===== OOS Summary =====");
        logger.info("Total Products Checked: " + iteration);
        logger.info("Total Products OOS: " + totalProductsOOS);
        logger.info("OOS Percentage: " + String.format("%.2f", OOSPercentage) + "%");

        // Check if OOS percentage exceeds threshold
        if (OOSPercentage > oos_threshold_percentage) {
            String oosFailureMsg = String.format(
                    "OOS percentage %.2f%% exceeds threshold of %.2f%%. Total checked: %d, OOS: %d",
                    OOSPercentage, oos_threshold_percentage, iteration, totalProductsOOS
            );

            Reporter.log("FAILURE: " + oosFailureMsg, true);
            logger.error(oosFailureMsg);

            // Log all OOS URLs
            Reporter.log("OOS Product URLs:", true);
            for (String oosUrl : oosURLs) {
                Reporter.log("  - " + oosUrl, true);
            }

            softAssert.assertTrue(false, oosFailureMsg);
        } else {
            Reporter.log("PASS: OOS percentage is within acceptable limits", true);
            logger.info("OOS percentage is within acceptable limits");
        }



        softAssert.assertAll();


    }



}