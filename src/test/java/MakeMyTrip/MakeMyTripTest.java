package MakeMyTrip;

/*
 * For resolving the OTP issue after every login,here default browser is used instead of opening new session every time.
 * Use your user_name and password for logging into the account.
 * Close all the browsers before running the test_case.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import resources.base;
import resources.readData;

public class MakeMyTripTest extends base {

	public WebDriver driver;
	FileInputStream file;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	Row row;

	@BeforeTest
	public void initialize() throws IOException {

		driver = initializeDriver();
		driver.get(prop.getProperty("url"));

	}

	@Test(priority = 1)
	public void logintest() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String userName = readData.getCellData(0, 0);
		String password = readData.getCellData(0, 1);
		System.out.println(userName);
		System.out.println(password);

		if (driver.findElements(By.xpath("//p[contains(text(),'Login/Signup for Best Prices')]")).size() != 0) {
			driver.findElement(By.xpath("//span[@class='blue__circle appendRight10']")).click();

			driver.findElement(By.id("username")).sendKeys(userName);// change your user name in Data.xlsx
			driver.findElement(By.xpath("//span[contains(text(),'Continue')]")).click();
			driver.findElement(By.xpath("//*[@id='password']")).sendKeys(password);// change your password in Data.xlsx
			driver.findElement(By.xpath("//button[@class='capText font16']")).click();

		} else {
			driver.findElement(By.xpath("//p[contains(text(), 'Login or Create Account')]")).click();
			driver.findElement(By.id("username")).sendKeys(userName);// change your user name in Data.xlsx
			driver.findElement(By.xpath("//span[contains(text(),'Continue')]")).click();
			driver.findElement(By.xpath("//*[@id='password']")).sendKeys(password);// change your password in Data.xlsx
			driver.findElement(By.xpath("//button[@class='capText font16']")).click();
		}

		WebElement exp = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='makeFlex column userNameText  latoBold']")));
		Assert.assertEquals(exp.getText(), "Hey USER");

	}

	@Test(priority = 2)
	public void hotelBookingtest() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, 40);
		String fName = readData.getCellData(0, 2);
		String lName = readData.getCellData(0, 3);
		driver.findElement(By.cssSelector("[class='chNavIcon appendBottom2 chSprite chHotels ']")).click();
		driver.findElement(By.id("city")).click();
		driver.findElement(By.cssSelector("[class='react-autosuggest__input react-autosuggest__input--open']"))
				.sendKeys("goa");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[class='locusLabel appendBottom5']")));

		List<WebElement> options = driver.findElements(By.cssSelector("[class='locusLabel appendBottom5']"));

		for (WebElement option : options)

		{

			if (option.getText().equalsIgnoreCase("Goa, India"))

			{

				option.click();

				break;

			}

		}

		while (!driver.findElement(By.cssSelector("[class='DayPicker-Caption']")).getText().contains("December")) {
			driver.findElement(By.cssSelector("[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
		}

		List<WebElement> dates = driver.findElements(By.className("DayPicker-Day"));

		int count = dates.size();

		for (int i = 0; i < count; i = i + 1) {
			String text = driver.findElements(By.className("DayPicker-Day")).get(i).getText();
			if (text.equalsIgnoreCase("8")) {
				driver.findElements(By.className("DayPicker-Day")).get(i).click();
				break;
			}
		}

		while (!driver.findElement(By.cssSelector("[class='DayPicker-Caption']")).getText().contains("December")) {
			driver.findElement(By.cssSelector("[class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
		}

		List<WebElement> checkDates = driver.findElements(By.className("DayPicker-Day"));

		int check = checkDates.size();

		for (int j = 0; j < check; j = j + 1) {
			String newText = driver.findElements(By.className("DayPicker-Day")).get(j).getText();
			if (newText.equalsIgnoreCase("10")) {
				driver.findElements(By.className("DayPicker-Day")).get(j).click();
				break;
			}

		}

		driver.findElement(By.id("guest")).click();
		driver.findElement(By.xpath("//li[@data-cy='adults-2']")).click();
		driver.findElement(By.xpath("//li[@data-cy='children-1']")).click();
		Select age = new Select(driver.findElement(By.className("ageSelectBox")));
		age.selectByVisibleText("1");
		driver.findElement(By.xpath("//button[@class='primaryBtn btnApply']")).click();
		driver.findElement(By.xpath("//span[@data-cy='travelForText']")).click();
		driver.findElement(By.xpath("//li[contains(text(),'Leisure')]")).click();
		driver.findElement(By.id("hsw_search_button")).click();

		WebElement selectRating = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='4 & above (Very Good)']")));
		selectRating.click();

		WebElement hotelFilter = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Hotel']")));
		hotelFilter.click();

		WebElement selectHotel = wait
				.until(ExpectedConditions.elementToBeClickable(By.id("htl_id_seo_201912051542136454")));
		selectHotel.click();

		ArrayList<String> e = new ArrayList<String>(driver.getWindowHandles());

		driver.switchTo().window(e.get(1));

		driver.findElement(By.cssSelector("a#detpg_headerright_book_now")).click();

		driver.findElement(By.id("fName")).sendKeys(fName);
		driver.findElement(By.id("lName")).sendKeys(lName);

		driver.findElement(By.xpath("//a[@class='primaryBtn btnPayNow']//div[1]")).click();
		driver.findElement(By.id("CC")).click();

		WebElement card = wait.until(ExpectedConditions.elementToBeClickable(By.id("PAYMENT_cardNumber")));
		card.sendKeys("23334568965232541");

		driver.findElement(By.id("PAYMENT_nameOnCard")).sendKeys("abcd");

		Select expiryDate = new Select(driver.findElement(By.id("PAYMENT_expiryMonth")));
		expiryDate.selectByIndex(4);

		Select expiryYear = new Select(driver.findElement(By.id("PAYMENT_expiryYear")));
		expiryYear.selectByVisibleText("2022");
		driver.findElement(By.id("PAYMENT_cvv")).sendKeys("022");
		driver.findElement(By.xpath("//button[@id='widgetPayBtnDP']")).click();
		String actual = driver.findElement(By.id("PAYMENT_cardNumber_err")).getText();
		Assert.assertEquals(actual, "Card not eligible for Pay Later. Please proceed with full Payment.");
		driver.switchTo().window(e.get(0));
		driver.findElement(By.id("loginTrigger")).click();
		driver.findElement(By.xpath("//a[@data-cy='userMenuLogout']")).click();

	}

	@AfterMethod
	public void screenShot(ITestResult result) {

		if (ITestResult.FAILURE == result.getStatus()) {
			try {

				TakesScreenshot screenshot = (TakesScreenshot) driver;

				File src = screenshot.getScreenshotAs(OutputType.FILE);

				FileUtils.copyFile(src, new File("D:\\" + result.getName() + ".png"));
				System.out.println("Successfully captured a screenshot");
			} catch (Exception e) {
				System.out.println("Exception while taking screenshot " + e.getMessage());
			}
		}
	}

	@AfterTest
	public void teardown() {

		driver.quit();
	}

}
