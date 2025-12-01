package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.devtools.v142.network.model.*;

import javax.naming.directory.SearchResult;

public class CDPNetworkManager {



    public static class SearchResult {
        private String url;
        private double waitedMs;
        private double responseTimeMs;
        private String responseBody;
        private List<String> productUrls;

        public SearchResult(String url, double waitedMs, double responseTimeMs, String responseBody, List<String> productUrls) {
            this.url = url;
            this.waitedMs = waitedMs;
            this.responseTimeMs = responseTimeMs;
            this.responseBody = responseBody;
            this.productUrls = productUrls;
        }

        // Getters
        public String getUrl() { return url; }
        public double getWaitedMs() { return waitedMs; }
        public double getResponseTimeMs() { return responseTimeMs; }
        public String getResponseBody() { return responseBody; }
        public List<String> getProductUrls(){return productUrls;}
    }


    public static SearchResult captureFirstRequestResponse(WebDriver driver,Runnable trigger,String toMatchRequest) throws InterruptedException {

        DevTools devTools = ((HasDevTools)driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()));

        AtomicReference<RequestId> requestIdRef = new AtomicReference<>();
        AtomicReference<String> capturedUrl = new AtomicReference<>();
        AtomicReference<List<String>> extractedUrls = new AtomicReference<>(new ArrayList<>());

        ConcurrentHashMap<RequestId,MonotonicTime> startTimes=new ConcurrentHashMap<>();
        ConcurrentHashMap<RequestId,String> idToUrl = new ConcurrentHashMap<>();

        AtomicReference<MonotonicTime> endTime = new AtomicReference<>();
        AtomicReference<String> responseBody = new AtomicReference<>();

        devTools.addListener(Network.requestWillBeSent(), sendReqEvent -> {
            String url = sendReqEvent.getRequest().getUrl();
            RequestId rId = sendReqEvent.getRequestId();

            idToUrl.put(rId, url);



            if(requestIdRef.get()==null && url.contains(toMatchRequest)){
                requestIdRef.set(rId);
                capturedUrl.set(url);
                startTimes.put(rId,sendReqEvent.getTimestamp());
                System.out.println("[CDP] " + toMatchRequest + " REQUEST detected. id=" + rId + " url=" + url);
            }

            if(sendReqEvent.getRedirectResponse()!=null){
                System.out.println("[CDP] Redirected response cointaines data for Request ID:"  + rId);
                startTimes.putIfAbsent(rId,sendReqEvent.getTimestamp());
            }

        });

        devTools.addListener(Network.responseReceived(), respReceived -> {

            RequestId rId = respReceived.getRequestId();
            String url = respReceived.getResponse().getUrl();
            int status = respReceived.getResponse().getStatus();
            System.out.println("[CDP] responseReceived: id=" + rId + " url=" + url + " status=" + status);

            if(requestIdRef.get()!=null){
                boolean matchbyRId = requestIdRef.get().equals(rId);
                boolean matchbyURL = (capturedUrl.get().equals(url)&&capturedUrl.get()!=null);

                if(matchbyURL || matchbyRId){
                    endTime.set(respReceived.getTimestamp());
                }
                try {
                    Network.GetResponseBodyResponse bodyResponse = devTools.send(Network.getResponseBody(rId));
                    String body = bodyResponse.getBody();
                    responseBody.set(body);
                    List<String> urls = extractProductUrls(body);
                    extractedUrls.set(urls);
                    System.out.println("[CDP] Response body captured for id=" + rId);
                    System.out.println("[CDP] Extracted " + urls.size() + " product URLs");
                } catch (Exception e) {
                    System.out.println("[CDP] Could not get response body: " + e.getMessage());
                }

                if(endTime.get()!=null){
                    System.out.println("[CDP] response Received: id=" + rId + " and End time set.");
                }else{
                    System.out.println("[CDP] Did not receive response for id=" + rId + "url : " + url);
                }

            }
        });

        trigger.run();

        double waitedMs= 0;
        long pollMs= 200;
        double timeoutMs = 10000;

        while(requestIdRef.get() == null && waitedMs<timeoutMs){
            Thread.sleep(pollMs);
            waitedMs+=pollMs;
        }

        if(requestIdRef.get()==null){
            try{
                devTools.clearListeners();
            }catch(Exception ignored){}
            try{
                devTools.close();
            }catch(Exception ignored){}
            return new SearchResult(null, waitedMs, 0, null, new ArrayList<>());

        }

        double extraMs = 0;
        while(endTime.get()==null && extraMs<10000){
            Thread.sleep(100);
            extraMs+=100;
        }

        double responseTimeMs = 0;
        MonotonicTime start = startTimes.get(requestIdRef.get());
        MonotonicTime finish = endTime.get();
        // If start is null (redirect case), you can still do your URL-based fallback:
        if (start == null && capturedUrl.get() != null) {
            for (RequestId rid : startTimes.keySet()) {
                String url = idToUrl.get(rid);
                if (capturedUrl.get().equals(url)) {
                    start = startTimes.get(rid);
                    break;
                }
            }
        }


        if (start != null && finish != null) {
            // MonotonicTime → Number → double seconds
            double startSec  = ((Number) start.toJson()).doubleValue();
            double finishSec = ((Number) finish.toJson()).doubleValue();

            responseTimeMs = Math.round(finishSec - startSec);
        }
        try { devTools.clearListeners(); } catch (Exception ignored) {}
        try { devTools.close(); } catch (Exception ignored) {}
        try { devTools.clearListeners(); } catch (Exception ignored) {}

        return new SearchResult(capturedUrl.get(), waitedMs, responseTimeMs,responseBody.get(),extractedUrls.get());



    }
    private static List<String> extractProductUrls(String responseBody) {
        List<String> urls = new ArrayList<>();
        if (responseBody == null || responseBody.isEmpty()) {
            return urls;
        }

        String pattern = "https://www\\.ubuy\\.qa/en/search[^\\s\"'<>]*";
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(responseBody);

        while (matcher.find()) {
            String foundUrl = matcher.group();
            if (!urls.contains(foundUrl)) {  // Avoid duplicates
                urls.add(foundUrl);
            }
        }

        return urls;
    }
}
