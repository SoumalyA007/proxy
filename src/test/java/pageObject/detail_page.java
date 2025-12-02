package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import java.time.Duration;

public class detail_page extends basePage {
    private WebDriverWait wait;

    public detail_page(WebDriver driver){
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(7));
    }


    @FindBy(xpath = "//span[@id='availability-status']")
    WebElement inStock;

    public String is_displayed_inStock(){
        try{
            wait.until(ExpectedConditions.visibilityOf(inStock));
            return "IN_STOCK";
        } catch (TimeoutException | NoSuchElementException e) {
            return "OOS";
        }
    }

}