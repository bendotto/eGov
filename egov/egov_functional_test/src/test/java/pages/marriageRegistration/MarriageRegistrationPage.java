package pages.marriageRegistration;

import entities.marriageRegistration.MarriageRegistrationInformation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import java.util.List;

/**
 * Created by manjunatha-lap on 24/01/2017.
 */
public class MarriageRegistrationPage extends BasePage {
    public WebDriver driver;

    @FindBy(id = "select-registrationunit")
    private WebElement registrationUnit;

    @FindBy(id = "txt-street")
    private List<WebElement> street;

    @FindBy(id = "select-locality")
    private WebElement locality;

    @FindBy(id = "txt-locality")
    private List<WebElement> localityTxt;

    @FindBy(id = "txt-city")
    private List<WebElement> city;

    @FindBy(id = "txt-dateOfMarriage")
    private WebElement dateOfMarriage;

    @FindBy(id = "select-venue")
    private  WebElement venueOfMarriage;

    @FindBy(id = "txt-placeofmrg")
    private WebElement placeOfMarriage;

    @FindBy(css = "input[type='file'][id='marriage-photo']")
    private WebElement marriagePhoto;

    @FindBy(id = "husband.name.firstName")
    private WebElement brideFullName;

    @FindBy(id = "txt-lastName")
    private List<WebElement> lastName;

    @FindBy(id = "txt-parentsName")
    private WebElement parentsName;

    @FindBy(css = "input[type='file'][id='husband-photo']")
    private WebElement photoUpload;

    @FindBy(css = "input[type='file'][id='wife-photo']")
    private WebElement wifePhotoUpload;

    @FindBy(xpath = ".//*[@id='settingstab']/li[2]/a")
    private WebElement witnessInfoLink;

    @FindBy(id = "witnesses0.name.firstName")
    private WebElement witnessName1;

    @FindBy(id = "witnesses1.name.firstName")
    private WebElement witnessName2;

    @FindBy(id = "witnesses2.name.firstName")
    private WebElement witnessName3;

    @FindBy(id = "witnesses3.name.firstName")
    private WebElement witnessName4;

    public  MarriageRegistrationPage(WebDriver driver){ this.driver = driver;}

    public void enterApplicantsInformation(MarriageRegistrationInformation marriageRegistrationInformation) {
        waitForElementToBeClickable(registrationUnit, driver);
        registrationUnit.sendKeys(marriageRegistrationInformation.getRegistrationUnit());
        waitForElementToBeClickable(street.get(0), driver);
        street.get(0).sendKeys(marriageRegistrationInformation.getStreet());
        waitForElementToBeClickable(locality, driver);
        locality.sendKeys(marriageRegistrationInformation.getLocality());
        waitForElementToBeClickable(city.get(0), driver);
        city.get(0).sendKeys(marriageRegistrationInformation.getCity());
        waitForElementToBeClickable(placeOfMarriage, driver);
        placeOfMarriage.sendKeys(marriageRegistrationInformation.getPlaceOfMarriage());
        marriagePhoto.sendKeys(System.getProperty("user.dir") + "\\src\\test\\resources\\logo.jpg");
    }


    public void enterBrideGroomInformation(MarriageRegistrationInformation marriageRegistrationInformation,String name )
    {
        WebElement fullName = driver.findElement(By.id(name + ".name.firstName"));
        fullName.sendKeys(marriageRegistrationInformation.getFullName());

//        waitForElementToBeClickable(brideFullName, driver);
//        brideFullName.sendKeys(marriageRegistrationInformation1.getFullName());

        waitForElementToBeClickable(lastName.get(0), driver);
        lastName.get(0).sendKeys("N");
        waitForElementToBeClickable(lastName.get(1), driver);
        lastName.get(1).sendKeys("P");
        photoUpload.sendKeys(System.getProperty("user.dir") + "\\src\\test\\resources\\logo.jpg");

        WebElement parentsNamegb = driver.findElement(By.name(name + ".parentsName"));
        parentsNamegb.sendKeys(marriageRegistrationInformation.getFathersMothersName());

        WebElement religion = driver.findElement((By.id(name + ".religion")));
        religion.sendKeys(marriageRegistrationInformation.getReligion());

//        waitForElementToBeClickable(ageInYearsAsOnMarriage, driver);
//        ageInYearsAsOnMarriage.sendKeys("32");
//        waitForElementToBeClickable(ageInMonthsAsOnMarriage, driver);
//        ageInMonthsAsOnMarriage.sendKeys("0");

        WebElement ageInYears = driver.findElement(By.id(name + ".ageInYearsAsOnMarriage"));
        ageInYears.sendKeys("30");
        WebElement ageInMonth = driver.findElement(By.id(name + ".ageInMonthsAsOnMarriage"));
        ageInMonth.sendKeys("8");

        WebElement martialStatus = driver.findElement(By.id(name + ".maritalStatus"));
        martialStatus.sendKeys(marriageRegistrationInformation.getStatusAtTheTimeMarriage());

        WebElement residenceAddress = driver.findElement(By.id(name + ".contactInfo.residenceAddress"));
        residenceAddress.sendKeys(marriageRegistrationInformation.getResidenceAddress());
        WebElement officeAddress = driver.findElement(By.id(name + ".contactInfo.officeAddress"));
        officeAddress.sendKeys(marriageRegistrationInformation.getOfficeAddress());

        WebElement localitygb = driver.findElement(By.name(name + ".locality"));
        localitygb.sendKeys(marriageRegistrationInformation.getLocality());

        WebElement streetbg = driver.findElement(By.name(name + ".street"));
        streetbg.sendKeys(marriageRegistrationInformation.getStreet());

        WebElement citygb = driver.findElement(By.name(name + ".city"));
        citygb.sendKeys(marriageRegistrationInformation.getCity());

        WebElement mobileNogb = driver.findElement(By.id(name + ".contactInfo.mobileNo"));
        mobileNogb.sendKeys(marriageRegistrationInformation.getPhoneNo());

        WebElement occupationgb = driver.findElement(By.id(name+".occupation"));
        occupationgb.sendKeys(marriageRegistrationInformation.getOccupation());

        WebElement qualificationgb = driver.findElement(By.id(name+".qualification"));
        qualificationgb.sendKeys(marriageRegistrationInformation.getEducationQualification());

        wifePhotoUpload.sendKeys(System.getProperty("user.dir") + "\\src\\test\\resources\\logo.jpg");

    }

    public void entersWitnessesInformation() {
        waitForElementToBeClickable(witnessInfoLink, driver);
        jsClick(witnessInfoLink, driver);

        for(int i=0; i<=3; i++)
        {
            int res = 0;
            int sum = i+res;
            System.out.println(sum);
            WebElement witnessName = driver.findElement(By.id("witnesses"+sum+".name.firstName"));
            witnessName.sendKeys("Witness Names");
            WebElement witnessLastName = driver.findElement(By.id("witnesses"+sum+".name.lastName"));
            witnessLastName.sendKeys("Witness Last Names");
            WebElement relativeName = driver.findElement(By.id("witnesses["+sum+"].relativeName"));
            relativeName.sendKeys("Relative Name");

        }

//        waitForElementToBeClickable(witnessName1, driver);
//        witnessName1.sendKeys("AAAA");


    }
}