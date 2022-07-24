package org.p8499.lianjia.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.p8499.lianjia.common.retryTo

class Xiaoqu {
    private lateinit var webDriver: WebDriver
    private lateinit var javascriptExecutor: JavascriptExecutor

    fun load(wDriver: WebDriver) {
        wDriver.navigate().retryTo("https://sh.lianjia.com/xiaoqu/")
        webDriver = wDriver
        javascriptExecutor = webDriver as JavascriptExecutor
    }

    fun districtList(): List<String> = webDriver
        .findElement(By.xpath("//div[@data-role=\"ershoufang\"]"))
        .findElement(By.tagName("div"))
        .findElements(By.tagName("a"))
        .map { it.getAttribute("href").substringAfter("xiaoqu/").substringBefore("/") }
}