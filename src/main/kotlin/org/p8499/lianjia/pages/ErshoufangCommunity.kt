package org.p8499.lianjia.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.p8499.lianjia.common.findElementOrNull
import org.p8499.lianjia.common.retryTo

class ErshoufangCommunity(val community: String, val sort: Int, val page: Int) {
    private lateinit var webDriver: WebDriver
    private lateinit var javascriptExecutor: JavascriptExecutor

    fun load(wDriver: WebDriver) {
        wDriver.navigate().retryTo("https://sh.lianjia.com/ershoufang/pg${page}co${sort}c$community")
        webDriver = wDriver
        javascriptExecutor = webDriver as JavascriptExecutor
    }

    fun name() = webDriver
        .findElement(By.className("agentCardResblockTitle"))
        .text

    fun lowestUnitPrice() = webDriver
        .findElementOrNull(By.className("sellListContent"))
        ?.findElement(By.xpath("li[1]"))
        ?.findElement(By.className("info"))
        ?.findElement(By.className("priceInfo"))
        ?.findElement(By.className("unitPrice"))
        ?.findElement(By.tagName("span"))
        ?.text?.substringBefore("元/平")?.replace(",","")?.toInt()
}