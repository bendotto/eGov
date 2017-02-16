package pages.works;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;

import javax.swing.text.html.CSS;
import java.security.Key;
import java.util.Calendar;
import java.util.List;

import static com.jayway.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by manjunatha-lap on 22/12/2016.
 */
public class LetterOfAcceptancePage extends BasePage
{
    private WebDriver driver;
    public LetterOfAcceptancePage(WebDriver driver){this.driver=driver;}

    @FindBy(id = "fileNumber")
    private WebElement fileNumber;

    @FindBy(id = "fileDate")
    private WebElement fileDate;

    @FindBy(className = "caret")
    private WebElement tenderPer;

    @FindBy(id = "tenderFinalizedPercentage")
    private WebElement tenderFinalizedPercentage;

    @FindBy(id = "contractorSearch")
    private WebElement firmName;

    @FindBy(css = "div[class='alert text-center'][style='color:green;']")
    private WebElement loaNumber;

    @FindBy(id = "contractPeriod")
    private WebElement contractPeriod;

    @FindBy(id = "defectLiabilityPeriod")
    private WebElement defectLiabilityPeriod;

    @FindBy(id = "engineerIncharge")
    private WebElement engineerIncharge;

    @FindBy(id = "save")
    private WebElement saveButton;

    @FindBy(linkText = "View LOA PDF")
    private WebElement viewLOAPDF;

    @FindBy(id = "closeButton")
    private WebElement closeButton;

    @FindBy(linkText = "Close")
    private WebElement closeLink;

    @FindBy(css = "button[id='btnsearch'][type='button']")
    private WebElement searchLOAButton;

    @FindBy(xpath = ".//*[@id='workOrderNumber']")
    private WebElement workOrderNumber;

    @FindBy(xpath = "(//*[@id='resultTable']/tbody/tr/td/input)[1]")
     private WebElement selectLOA;

    @FindBy(id = "btnmodifyloa")
     private WebElement modifyLOAbutton;

    @FindBy(id = "revisedValue")
    private WebElement revisedValue;

    @FindBy(id = "modify")
     private WebElement modifyButton;

    @FindBy(id = "actionDropdown")
     private WebElement actionDropdown;

    @FindBy(xpath = "//*[@id='workOrder']/div[2]/div/a[1]")
     private WebElement closeViewLOA;

    @FindBy(xpath = ".//*[@id='btnsearch']")
      private WebElement searchButton;

    @FindBy(xpath = ".//*[@id='resultTable']/tbody/tr[1]/td[1]/input")
      private WebElement reqFileLink;

    @FindBy(id = "btncreateloa")
      private WebElement createLOAButton;

    @FindBy(id = "spillOverFlag")
     private WebElement spillOverCheck;

    @FindBy(id = "workOrderNumber")
    private WebElement LOANumber;

    @FindBy(id = "workOrderDate")
    private WebElement agreementDate;

    String RandomString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

    public void enterLOAdetails()
    {
        waitForElementToBeClickable(fileNumber, driver);
        fileNumber.sendKeys(RandomString);
        waitForElementToBeClickable(fileDate, driver);
        fileDate.sendKeys(getCurrentDate()+ Keys.TAB);
        waitForElementToBeClickable(tenderFinalizedPercentage, driver);
        tenderFinalizedPercentage.sendKeys("5"+Keys.TAB);
        waitForElementToBeClickable(firmName, driver);
        firmName.sendKeys("KMC055");
        waitForElementToBeVisible( driver.findElement(By.className("tt-dropdown-menu")),driver);
        WebElement dropdown = driver.findElement(By.className("tt-dropdown-menu"));
        jsClick(dropdown, driver);
        waitForElementToBeClickable(contractPeriod, driver);
        contractPeriod.sendKeys("400");
        waitForElementToBeClickable(defectLiabilityPeriod, driver);
        defectLiabilityPeriod.sendKeys("1"+Keys.TAB);
        waitForElementToBeClickable(engineerIncharge, driver);
        new Select(engineerIncharge).selectByVisibleText("A.P.Sreenivasulu - Assistant Engineer");
    }

    public String saveAndClose() {

     waitForElementToBeVisible(saveButton, driver);
     jsClick(saveButton, driver);
     waitForElementToBeVisible(loaNumber,driver);
     String loaText = loaNumber.getText();
     String workNumber = (loaText.split("\\ ")[5]);
     return workNumber;
    }

    public void searchForLOA(String number) {
        waitForElementToBeClickable(workOrderNumber, driver);
        workOrderNumber.sendKeys(number);
        waitForElementToBeClickable(searchLOAButton, driver);
        searchLOAButton.click();
        waitForElementToBeClickable(actionDropdown, driver);
        new Select(actionDropdown).selectByVisibleText("View LOA");
        switchToNewlyOpenedWindow(driver);
        waitForElementToBeClickable(closeViewLOA, driver);
        closeViewLOA.click();

        for (String winHandle : driver.getWindowHandles()) {
            String title = driver.switchTo().window(winHandle).getCurrentUrl();
            if(title.equals("http://kurnool-uat.egovernments.org/egworks/searchletterofacceptance/searchform")){
                break;
            }
        }
        waitForElementToBeVisible(closeLink,driver);
        closeLink.click();

        switchToPreviouslyOpenedWindow(driver);

    }

    public void close() {
        waitForElementToBeVisible(closeButton,driver);
        closeButton.click();

        switchToPreviouslyOpenedWindow(driver);
    }

    public void searchForLOAModify(String number) {
        waitForElementToBeClickable(workOrderNumber, driver);
        workOrderNumber.sendKeys(number);
        waitForElementToBeClickable(searchLOAButton, driver);
        searchLOAButton.click();
        waitForElementToBeClickable(selectLOA, driver);
        selectLOA.click();
        waitForElementToBeClickable(modifyLOAbutton, driver);
        modifyLOAbutton.click();
        waitForElementToBeClickable(revisedValue, driver);
        revisedValue.sendKeys("8");
        waitForElementToBeClickable(modifyButton, driver);
        modifyButton.click();
    }

    public void searchForApplication() {
        waitForElementToBeVisible(searchButton,driver);
        searchButton.click();
        waitForElementToBeVisible(reqFileLink,driver);
        jsClick(reqFileLink,driver);
        waitForElementToBeVisible(createLOAButton,driver);
        createLOAButton.click();
    }

    public String successMessage(){
        waitForElementToBeVisible(loaNumber, driver);
        String msg = loaNumber.getText();
        return msg;
    }

    public void searchForSpilloverEstimate() {
        waitForElementToBeClickable(spillOverCheck, driver);
        spillOverCheck.click();
        waitForElementToBeVisible(searchButton,driver);
        searchButton.click();
        waitForElementToBeVisible(reqFileLink,driver);
        jsClick(reqFileLink,driver);
        waitForElementToBeVisible(createLOAButton,driver);
        createLOAButton.click();
    }

    public void entersSpilloverLOADetails()
    {
        waitForElementToBeClickable(LOANumber, driver);
        LOANumber.sendKeys("LOA/"+get6DigitRandomInt());
        waitForElementToBeClickable(fileNumber, driver);
        fileNumber.sendKeys("FN"+RandomString);
        waitForElementToBeClickable(fileDate, driver);
        fileDate.sendKeys(getCurrentDate()+ Keys.TAB);
        waitForElementToBeClickable(tenderFinalizedPercentage, driver);
        tenderFinalizedPercentage.sendKeys("8"+Keys.TAB);
        waitForElementToBeClickable(agreementDate, driver);
        agreementDate.sendKeys(getCurrentDate()+Keys.TAB);
        waitForElementToBeClickable(firmName, driver);
        firmName.sendKeys("KMC055");
        waitForElementToBeVisible( driver.findElement(By.className("tt-dropdown-menu")),driver);
        WebElement dropdown = driver.findElement(By.className("tt-dropdown-menu"));
        dropdown.click();
        waitForElementToBeClickable(contractPeriod, driver);
        contractPeriod.sendKeys("500");
        waitForElementToBeClickable(defectLiabilityPeriod, driver);
        defectLiabilityPeriod.sendKeys("1"+Keys.TAB);
        waitForElementToBeClickable(engineerIncharge, driver);
        new Select(engineerIncharge).selectByVisibleText("A.P.Sreenivasulu - Assistant Engineer");
    }
}