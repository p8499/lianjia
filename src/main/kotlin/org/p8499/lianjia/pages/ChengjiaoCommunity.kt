package org.p8499.lianjia.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.p8499.lianjia.common.findElementOrNull
import org.p8499.lianjia.common.retryTo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ChengjiaoCommunity(val community: String, val page: Int) {
    private lateinit var webDriver: WebDriver
    private lateinit var javascriptExecutor: JavascriptExecutor

    fun load(wDriver: WebDriver) {
        wDriver.navigate().retryTo("https://sh.lianjia.com/chengjiao/pg${page}c$community")
        webDriver = wDriver
        javascriptExecutor = webDriver as JavascriptExecutor
    }

    fun unitPriceList(): List<Int> = webDriver
        .findElementOrNull(By.className("listContent"))
        ?.findElements(By.tagName("li"))
        ?.asSequence()
        ?.map { it.findElement(By.className("info")) }
        ?.map { it.findElement(By.className("flood")) }
        ?.map { it.findElement(By.className("unitPrice")) }
        ?.map { it.findElement(By.className("number")) }
        ?.map { it.text.dropWhile { it == ',' }.toInt() }
        ?.toList() ?: listOf()

    fun recentUnitPriceList(): List<Int> = webDriver
        .findElementOrNull(By.className("listContent"))
        ?.findElements(By.tagName("li"))
        ?.asSequence()
        ?.map { it.findElement(By.className("info")) }
        ?.filter {
            it.findElement(By.className("address"))
                .findElement(By.className("dealDate"))
                .text.let(::toLocalDate).isAfter(LocalDate.now().minusMonths(12))
        }?.map { it.findElement(By.className("flood")) }
        ?.map { it.findElement(By.className("unitPrice")) }
        ?.map { it.findElement(By.className("number")) }
        ?.map { it.text.replace(",", "").toInt() }
        ?.toList() ?: listOf()

    fun toLocalDate(str: String) = LocalDate.parse(str, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
}