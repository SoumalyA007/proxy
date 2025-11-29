package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.devtools.v142.network.model.*;

import javax.naming.directory.SearchResult;

public class CDPNetworkManager {



    public class searchResult(){

    }


    public static searchResult captureFirstRequestResponse(WebDriver driver,Runnable trigger,String toMatchRequest) throws InterruptedException {

        DevTools devTools = ((HasDevTools)driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()));

        AtomicReference<RequestId> requestIdRef = new AtomicReference<>();
        AtomicReference<String> capturedUrl = new AtomicReference<>();

        ConcurrentHashMap<RequestId,MonotonicTime> startTimes=new ConcurrentHashMap<>();
        ConcurrentHashMap<RequestId,String> idToUrl = new ConcurrentHashMap<>();

        AtomicReference<MonotonicTime> endTime = new AtomicReference<>();

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
            return new SearchResult(null, waitedMs,0);

        }

        double extraMs = 0;
        while(endTime.get()==null && extraMs<10000){
            Thread.sleep(100);
            extraMs+=100;
        }







    }
}
