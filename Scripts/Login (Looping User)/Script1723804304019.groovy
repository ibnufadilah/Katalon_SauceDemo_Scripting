import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.util.KeywordUtil

// Memuat data dari file Excel
def testData = TestDataFactory.findTestData('Data Files/data_login')

// Buka browser di luar loop
WebUI.openBrowser('')

// Looping melalui data dan mengisi formulir
for (int i = 1; i <= testData.getRowNumbers(); i++) {
    // Mendapatkan nama email dan password dari file excel
    String userName = testData.getValue('Username', i)
    String password = testData.getValue('Password', i)
    
    // Navigasi ke halaman login
    WebUI.navigateToUrl('https://www.saucedemo.com/')
    
    // Isi username dan password
    WebUI.setText(findTestObject('Object Repository/Login Sauce Demo/input_Swag Labs_user-name'), userName)
    WebUI.setText(findTestObject('Object Repository/Login Sauce Demo/input_Swag Labs_password'), password)
    
    // Klik tombol login
    WebUI.click(findTestObject('Object Repository/Login Sauce Demo/input_Swag Labs_login-button'))
    
   	try {
		//verifikasi kalo sudah masuk ke link beranda
		String currentUrl = WebUI.getUrl()
		
		if (currentUrl == 'https://www.saucedemo.com/inventory.html') {
			WebUI.comment("Login Berhasil : " + userName)
			KeywordUtil.logInfo("Login berhasil : " + userName)
		} else {
			WebUI.comment("Login gagal : " + userName)
			KeywordUtil.markWarning("Login gagal : " + userName)
		}
		} 
		catch (Exception e) {
			WebUI.comment("Login gagal : " + userName)
			KeywordUtil.markWarning("Login gagal : " + userName)
		}
		
	
	// Tambahkan delay untuk menunggu beberapa detik sebelum mengisi formulir berikutnya
	WebUI.delay(2)
}

// Tutup browser setelah semua iterasi selesai
WebUI.closeBrowser()