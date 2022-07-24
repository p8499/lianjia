package org.p8499.lianjia.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.p8499.lianjia.common.findElementOrNull
import org.p8499.lianjia.common.retryTo

class XiaoquStreet(val street: String, val page: Int) {
    private lateinit var webDriver: WebDriver
    private lateinit var javascriptExecutor: JavascriptExecutor

    fun load(wDriver: WebDriver) {
        wDriver.navigate().retryTo("https://sh.lianjia.com/xiaoqu/$street/pg$page")
        webDriver = wDriver
        javascriptExecutor = webDriver as JavascriptExecutor
    }

    fun communityList(): List<String> = webDriver
        .findElementOrNull(By.className("listContent"))
        ?.findElements(By.tagName("li"))
        ?.map { it.getAttribute("data-housecode") } ?: listOf()

    fun sellingCommunityList(): List<String> = webDriver
        .findElementOrNull(By.className("listContent"))
        ?.findElements(By.tagName("li"))
        ?.filter {
            it.findElement(By.className("totalSellCount"))
                .findElement(By.tagName("span"))
                .text.toInt() > 0
        }
        ?.map { it.getAttribute("data-housecode") } ?: listOf()
}