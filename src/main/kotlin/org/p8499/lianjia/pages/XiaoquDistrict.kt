package org.p8499.lianjia.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.p8499.lianjia.common.retryTo

class XiaoquDistrict(val district: String) {
    private lateinit var webDriver: WebDriver
    private lateinit var javascriptExecutor: JavascriptExecutor

    fun load(wDriver: WebDriver) {
        wDriver.navigate().retryTo("https://sh.lianjia.com/xiaoqu/$district")
        webDriver = wDriver
        javascriptExecutor = webDriver as JavascriptExecutor
    }

    fun streetList(): List<String> = webDriver
        .findElement(By.xpath("//div[@data-role=\"ershoufang\"]"))
        .findElement(By.xpath("div[2]"))
        .findElements(By.tagName("a"))
        .map { it.getAttribute("href").substringAfter("xiaoqu/").substringBefore("/") }
}