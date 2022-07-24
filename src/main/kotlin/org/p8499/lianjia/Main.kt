package org.p8499.lianjia

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.p8499.lianjia.service.*

fun main() {
    val workBook = XSSFWorkbook()

    val webDriver = getWebDriver()
    login(webDriver)
    for (district in districtList(webDriver)) {

        for (street in streetList(webDriver, district)) {
            for (community in sellingCommunityList(webDriver, street)) {
                val lowestPrice = lowestUnitPrice(webDriver, community)
                val recentPriceList = recentDealUnitPriceList(webDriver, community)
                val percent = lowestPrice?.div(recentPriceList.average())?.times(100)

            }

        }
    }
    webDriver.quit()


}

fun getWebDriver(): WebDriver {
    System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe")
    val chromeOptions = ChromeOptions().apply {
        addArguments("--headless")
    }
    return ChromeDriver(chromeOptions).apply {
        manage().window().maximize()
    }
}

