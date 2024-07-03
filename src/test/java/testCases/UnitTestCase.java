package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationCore.BasePage;
import pomClasses.POMLogin;
import pomClasses.POMUnits;
import utility.ExcelUtility;

import utility.PropertyReadUtility;
import org.testng.annotations.DataProvider;

import java.io.IOException;

import org.testng.Assert;

public class UnitTestCase extends BasePage {
	POMLogin objPomLogin;
	static String url = PropertyReadUtility.readConfigFile("login_url");
	static String browser = PropertyReadUtility.readConfigFile("browser");
	
	POMUnits objPomUnits;


	@Test(priority = 2, enabled = true)
	public void logIn() throws IOException {
		objPomLogin = new POMLogin(driver.get());
		String username = ExcelUtility.readStringData(1, 0);
		String password = ExcelUtility.integerData(1, 1);
		objPomLogin.loginVerification(username, password);
		SoftAssert objassert = new SoftAssert();
		objassert.assertEquals(objPomLogin.isElementDisplayed(), true);	
		objassert.assertAll();
	}

	@Test(priority = 3, enabled = true, dataProvider = "testdata", groups= {"sequential"})
	public void Add_units(String tData1, String tData2) throws InterruptedException, IOException {
		objPomLogin = new POMLogin(driver.get());
		objPomUnits = new POMUnits(driver.get());
		objPomLogin.product_click();
		objPomUnits.units_click();
		objPomUnits.addUnits(tData1, tData2);

		String actual_message = "Unit added successfully";
		String message = objPomUnits.getMessage();
		Assert.assertTrue(actual_message.contains(message));
		

	}

	@Test(priority = 4, enabled = true, groups= {"sequential"})
	public void search_units() throws InterruptedException {
		boolean status = objPomUnits.searchUnits(PropertyReadUtility.readConfigFile("unit_test_data"));
		SoftAssert objassert = new SoftAssert();
		objassert.assertEquals(status, true);
		objassert.assertAll();

	}

	@Test(priority = 5, enabled = true, groups= {"sequential"})
	public void deleteUnits() throws InterruptedException {
		objPomUnits.delete_unit(PropertyReadUtility.readConfigFile("unit_test_data"));
		String actual_message = "Unit deleted successfully";
		String message = objPomUnits.getMessage();
		SoftAssert objassert = new SoftAssert();
		objassert.assertEquals(actual_message, message);
		objassert.assertAll();

	}

	@DataProvider(name = "testdata")
	public Object[][] TestDataFeed() {
		Object[][] unitsData = new Object[1][2];
		unitsData[0][0] = "Test_unit";
		unitsData[0][1] = "t_unit";
		return unitsData;
	}

}
