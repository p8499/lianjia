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
        "https://sh.lianjia.com/ershoufang/pg${page}co${sort}c$community/"
                .takeIf { wDriver.currentUrl != it }
                ?.also { wDriver.navigate().retryTo(it) }
        webDriver = wDriver
        javascriptExecutor = webDriver as JavascriptExecutor
    }

    fun name() = webDriver
            .findElementOrNull(By.className("agentCardResblockTitle"))
            ?.text ?: ""

    fun lowestUnitPrice() = webDriver
            .findElementOrNull(By.className("sellListContent"))
            ?.findElements(By.xpath("li[@data-lj_action_resblock_id=\"$community\"]"))
            ?.firstOrNull()
            ?.findElementOrNull(By.className("info"))
            ?.findElementOrNull(By.className("priceInfo"))
            ?.findElementOrNull(By.className("unitPrice"))
            ?.findElementOrNull(By.tagName("span"))
            ?.text?.substringBefore("元/平")?.replace(",", "")?.toInt()
}