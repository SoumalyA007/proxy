package testCases;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TC001_Search_and_Detail_Speed {


    public String getRandomKeyword(){

        List<String> keywords = Arrays.asList("abc","def");

        return keywords.get(new Random().nextInt(keywords.size()));
    }





}
