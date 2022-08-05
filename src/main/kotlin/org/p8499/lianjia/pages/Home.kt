package org.p8499.lianjia.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.p8499.lianjia.common.click
import org.p8499.lianjia.common.retryTo

class Home {
    private lateinit var webDriver: WebDriver
    private lateinit var javascriptExecutor: JavascriptExecutor

    fun load(wDriver: WebDriver) {
        "https://sh.lianjia.com/"
                .takeIf { wDriver.currentUrl != it }
                ?.also { wDriver.navigate().retryTo(it) }
        webDriver = wDriver
        javascriptExecutor = webDriver as JavascriptExecutor
    }

    fun login() {
        val webDriverWait = WebDriverWait(webDriver, java.time.Duration.ofMinutes(1))
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.className("btn-login"))))
        javascriptExecutor.click(webDriver.findElement(By.className("btn-login")))
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.className("change_login_type"))))
        javascriptExecutor.click(webDriver.findElement(By.className("change_login_type")))
        webDriver.findElement(By.className("phonenum_input")).sendKeys("13611981113")
        webDriver.findElement(By.className("password_input")).sendKeys("Sounet0273668")
        webDriverWait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.className("login_submit"))))
        webDriver.findElement(By.className("login_submit")).click()

        webDriverWait.until(
                ExpectedConditions.refreshed(
                        ExpectedConditions.stalenessOf(webDriver.findElement(By.className("login_submit")))
                )
        )
    }
}