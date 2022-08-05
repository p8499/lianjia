package org.p8499.lianjia.service

import org.openqa.selenium.WebDriver
import org.p8499.lianjia.pages.*

fun login(webDriver: WebDriver) = Home().apply {
    load(webDriver)
    login()
}

fun districtList(webDriver: WebDriver) = Xiaoqu().apply {
    load(webDriver)
}.districtList()

fun streetList(webDriver: WebDriver, district: String) = XiaoquDistrict(district).apply {
    load(webDriver)
}.streetList()

fun sellingCommunityList(webDriver: WebDriver, street: String): List<String> {
    val list: MutableList<String> = mutableListOf()
    var page = 1
    while (true) {
        val xiaoquStreet = XiaoquStreet(street, page).apply { load(webDriver) }
        list.addAll(xiaoquStreet.sellingCommunityList())
        if (xiaoquStreet.communityList().isNotEmpty())
            page += 1
        else
            break
    }
    return list
}

fun communityLowestPrice(webDriver: WebDriver, community: String) = ErshoufangCommunity(community, 41, 1).apply {
    load(webDriver)
}.lowestUnitPrice()

fun communityName(webDriver: WebDriver, community: String) = ErshoufangCommunity(community, 41, 1).apply {
    load(webDriver)
}.name()

fun communityHistoryPriceList(webDriver: WebDriver, community: String): List<Int> {
    val list: MutableList<Int> = mutableListOf()
    var page = 1
    while (true) {
        val chengjiaoCommunity = ChengjiaoCommunity(community, page).apply { load(webDriver) }
        val recentUnitPriceList = chengjiaoCommunity.recentUnitPriceList()
        val unitPriceList = chengjiaoCommunity.unitPriceList()
        list.addAll(recentUnitPriceList)
        if (recentUnitPriceList.size == unitPriceList.size && recentUnitPriceList.isNotEmpty())
            page += 1
        else break
    }
    return list
}