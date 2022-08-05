package org.p8499.lianjia.common

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

fun JavascriptExecutor.click(webElement: WebElement): Any? = executeScript("arguments[0].click()", webElement)

fun WebDriver.hasElement(by: By) =
        try {
            findElement(by)
            true
        } catch (e: Exception) {
            false
        }

fun WebDriver.findElementOrNull(by: By) =
        try {
            findElement(by)
        } catch (e: Exception) {
            null
        }

fun WebDriver.Navigation.retryTo(url: String, limit: Int = Int.MAX_VALUE) {
    var times = 0
    while (true) {
        try {
            to(url)
            return
        } catch (e: Exception) {
            times += 1
            println("Retrying navigating to $url ($times / $limit)")
            if (times > limit - 1) throw e
        }
    }
}

fun WebElement.hasElement(by: By) =
        try {
            findElement(by)
            true
        } catch (e: Exception) {
            false
        }

fun WebElement.findElementOrNull(by: By) =
        try {
            findElement(by)
        } catch (e: Exception) {
            null
        }