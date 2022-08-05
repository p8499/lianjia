package org.p8499.lianjia.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.p8499.lianjia.common.findElementOrNull
import org.p8499.lianjia.common.retryTo

class Xiaoqu {
    private lateinit var webDriver: WebDriver
    private lateinit var javascriptExecutor: JavascriptExecutor

    fun load(wDriver: WebDriver) {
        "https://sh.lianjia.com/xiaoqu/"
                .takeIf { wDriver.currentUrl != it }
                ?.also { wDriver.navigate().retryTo(it) }
        webDriver = wDriver
        javascriptExecutor = webDriver as JavascriptExecutor
    }

    fun districtList(): List<String> = webDriver
            .findElementOrNull(By.xpath("//div[@data-role=\"ershoufang\"]"))
            ?.findElementOrNull(By.tagName("div"))
            ?.findElements(By.tagName("a"))
            ?.map { it.getAttribute("href").substringAfter("xiaoqu/").substringBefore("/") }
        ?: listOf()
}