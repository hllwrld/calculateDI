package com.hllwrld.calculatedi

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

/**
 * Example of reading a csv.
 */

class Team(val mName: String, val mComponentNameList: MutableList<String>) {

    var mNum: Float = 0f;

    fun calculateNum(row: List<String>) {
        if (mComponentNameList.any { row[1].contains(it, true) }) {
            // println("find: $row")
            mNum += row[3].toFloat()
        }
    }

    fun print() {
        println("$mName:  $mNum")
    }
}


fun printTeam(rows: List<List<String>>, list: MutableList<Team>, name: String): Float {
    var sum: Float = 0f;

    rows.forEach { row ->
        list.onEach {
            it.calculateNum(row)
        }
    }


    list.onEach {
        sum += it.mNum
        it.print()
    }
    if (list.size > 1) {
        println("$name: ${sum}")
    }
    return sum
}

fun fillData(rows: List<List<String>>, list: MutableList<Team>) {
    rows.forEach { row ->
        list.onEach {
            it.calculateNum(row)
        }
    }
}

val mCoreUITeamList = mutableListOf(
    Team("桌面", mutableListOf("桌面")),
    Team("手势", mutableListOf("全面屏手势", "最近任务")),
    Team("控制中心", mutableListOf("控制中心", "快捷开关")),
    Team("通知栏", mutableListOf("通知栏")),
    Team("状态栏", mutableListOf("虚拟按键", "音量面板", "状态栏", "分屏")),
    Team("AOD", mutableListOf("熄屏")),
    Team("锁屏", mutableListOf("锁屏")),
    Team("长截屏", mutableListOf("长截屏")),
    Team("基础体验", mutableListOf("基础体验"))
);


val mThemeTeamList = mutableListOf(
    Team("壁纸", mutableListOf("超级壁纸")),
    Team("主题", mutableListOf("主题", "画报", "百变"))
);
val mMinusList = mutableListOf(
    Team("智能助理", mutableListOf("智能助理"))
);

fun getCoreUITeamData(rows: List<List<String>>): List<Team> {
    fillData(rows, mCoreUITeamList)
    return mCoreUITeamList
}

fun getThemeUITeamData(rows: List<List<String>>): List<Team> {
    fillData(rows, mThemeTeamList)
    return mThemeTeamList

}

fun getMinusTeamData(rows: List<List<String>>): List<Team> {
    fillData(rows, mMinusList)
    return mMinusList
}


fun main() {


    var sum: Float = 0f
    val rows = mutableListOf<List<String>>()
    csvReader().open("/home/mi/disk1/code/calculateDI/app/src/main/resources/data.csv") {
        readAllAsSequence().forEach {
            rows.add(it)
        }
    }

    val coreUITeamList = mutableListOf(
        Team("桌面", mutableListOf("桌面")),
        Team("手势", mutableListOf("全面屏手势", "最近任务")),
        Team("控制中心", mutableListOf("控制中心", "快捷开关")),
        Team("通知栏", mutableListOf("通知栏")),
        Team("状态栏", mutableListOf("虚拟按键", "音量面板", "状态栏", "分屏")),
        Team("AOD", mutableListOf("熄屏")),
        Team("锁屏", mutableListOf("锁屏")),
        Team("长截屏", mutableListOf("长截屏")),
        Team("基础体验", mutableListOf("基础体验"))
    );


    val themeTeamList = mutableListOf(
        Team("壁纸", mutableListOf("超级壁纸")),
        Team("主题", mutableListOf("主题", "画报", "百变"))
    );

    val minusList = mutableListOf(
        Team("智能助理", mutableListOf("智能助理"))
    );

    sum += printTeam(rows, coreUITeamList, "核心UI")
    sum += printTeam(rows, themeTeamList, "主题壁纸")
    sum += printTeam(rows, minusList, "智能助理")
    println("核心体验: $sum")

}