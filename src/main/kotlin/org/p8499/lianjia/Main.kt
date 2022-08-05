package org.p8499.lianjia

import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.p8499.lianjia.service.*
import java.io.FileOutputStream

fun main() {
    val workBook = XSSFWorkbook()
    val dataFormat = workBook.createDataFormat()
    val webDriver = getWebDriver()
    login(webDriver)
    for (district in districtList(webDriver)) {
        val sheet = workBook.createSheet(district)
        var i = 0
        for (street in streetList(webDriver, district)) {
            for (community in sellingCommunityList(webDriver, street)) {
                val row = sheet.createRow(i++)
                val name = communityName(webDriver, community)
                val price = communityLowestPrice(webDriver, community)
                val url = webDriver.currentUrl
                val refPriceList = communityHistoryPriceList(webDriver, community)
                with(row.createCell(0, CellType.STRING)) {
                    setCellValue(street)
                }
                with(row.createCell(1, CellType.STRING)) {
                    setCellValue(name)
                }
                price?.toDouble()?.also {
                    with(row.createCell(2, CellType.NUMERIC)) {
                        cellStyle.dataFormat = dataFormat.getFormat("###,###,###,##0")
                        setCellValue(it)
                    }
                }
                refPriceList.average().takeUnless { it.isNaN() }?.also {
                    with(row.createCell(3, CellType.NUMERIC)) {
                        cellStyle.dataFormat = dataFormat.getFormat("###,###,###,##0")
                        setCellValue(it)
                    }
                }
                refPriceList.size.takeIf { it > 0 }?.toDouble()?.also {
                    with(row.createCell(4, CellType.NUMERIC)) {
                        cellStyle.dataFormat = dataFormat.getFormat("###,###,###,##0")
                        setCellValue(it)
                    }
                }
                with(row.createCell(5, CellType.FORMULA)) {
                    cellStyle.dataFormat = dataFormat.getFormat("0%")
                    cellFormula = "IFERROR(C${row.rowNum + 1}/D${row.rowNum + 1}-1,\"\")"
                }
                with(row.createCell(6, CellType.STRING)) {
                    setCellValue(url)
                }
                Thread.sleep(1000)
            }
            workBook.write(FileOutputStream("C:\\Users\\jdeuser\\lianjia.xlsx"))
        }
    }
    webDriver.quit()
    workBook.close()
}

fun getWebDriver(): WebDriver {
    System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe")
    val chromeOptions = ChromeOptions().apply {
//        addArguments("--headless")
    }
    return ChromeDriver(chromeOptions).apply {
        manage().window().maximize()
    }
}

