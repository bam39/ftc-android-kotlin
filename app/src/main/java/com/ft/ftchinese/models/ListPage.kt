package com.ft.ftchinese.models

import android.content.Context
import android.content.res.Resources
import com.ft.ftchinese.R
import com.ft.ftchinese.util.Fetch
import com.ft.ftchinese.util.Store
import kotlinx.coroutines.experimental.async

/**
 * ListPage contains the data used by a page in ViewPager
 */
data class ListPage (
        val title: String, // A Tab's title
        val name: String,  // Cache filename used by this tab
        val listUrl: String? = null, // Where to fetch HTML fragment to be loaded in WebView.loadDataWith
        val webUrl: String? = null // A complete html page to be loaded directly into a WebView
) {
    suspend fun htmlFromCache(context: Context?): String? {
        val job = async {
            Store.load(context, "$name.html")
        }

        return job.await()
    }

    suspend fun htmlFromFragment(resources: Resources): String? {
        if (listUrl == null) {
            return null
        }

        val readJob = async {
            Store.readRawFile(resources, R.raw.list)
        }

        val fetchJob = async {
            Fetch().get(listUrl).string()
        }

        val template = readJob.await()
        val htmlFragment = fetchJob.await()

        if (template == null || htmlFragment == null) {
            return null
        }

        return template.replace("{list-content}", htmlFragment)
    }

    /**
     * Save the HTML generated by `htmlFromFragment()`
     */
    fun cache(context: Context?, htmlString: String) {
        async {
            Store.save(context, "$name.html", htmlString)
        }
    }

    companion object {


        val newsPages = arrayOf(
                ListPage(title = "首页", name = "news_frontpage", listUrl = "https://api003.ftmailbox.com/?webview=ftcapp&bodyonly=yes&maxB=1&backupfile=localbackup&showIAP=yes&pagetype=home&001"),
                ListPage(title = "中国", name = "news_china", listUrl = "https://api003.ftmailbox.com/channel/china.html?webview=ftcapp&bodyonly=yes"),
                ListPage(title = "独家", name = "news_scoop", listUrl = "https://api003.ftmailbox.com/channel/exclusive.html?webview=ftcapp&bodyonly=yes&ad=no&001"),
                ListPage(title = "编辑精选", name = "news_editor_choice", listUrl = "https://api003.ftmailbox.com/channel/editorchoice.html?webview=ftcapp&bodyonly=yes&ad=no&showEnglishAudio=yes&018"),
                ListPage(title = "全球", name = "news_global", listUrl = "https://api003.ftmailbox.com/channel/world.html?webview=ftcapp&bodyonly=yes&002"),
                ListPage(title = "观点", name = "news_opinions", listUrl = "https://api003.ftmailbox.com/channel/opinion.html?webview=ftcapp&bodyonly=yes&ad=no"),
                ListPage(title = "专栏", name = "news_column", listUrl = "https://api003.ftmailbox.com/channel/column.html?webview=ftcapp&bodyonly=yes&ad=no"),
                ListPage(title = "金融市场", name = "news_markets", listUrl = "https://api003.ftmailbox.com/channel/markets.html?webview=ftcapp&bodyonly=yes"),
                ListPage(title = "商业", name = "news_business", listUrl = "https://api003.ftmailbox.com/channel/markets.html?webview=ftcapp&bodyonly=yes"),
                ListPage(title = "科技", name = "news_technology", listUrl = "https://api003.ftmailbox.com/channel/technology.html?webview=ftcapp&bodyonly=yes&001"),
                ListPage(title = "教育", name = "news_education", listUrl = "https://api003.ftmailbox.com/channel/education.html?webview=ftcapp&bodyonly=yes&001"),
                ListPage(title = "管理", name = "news_management", listUrl = "https://api003.ftmailbox.com/channel/management.html?webview=ftcapp&bodyonly=yes"),
                ListPage(title = "生活时尚", name = "news_life_style", listUrl = "https://api003.ftmailbox.com/channel/lifestyle.html?webview=ftcapp&bodyonly=yes"),
                ListPage(title = "特别报导", name = "news_special_report", webUrl = "http://www.ftchinese.com/channel/special.html?webview=ftcapp&ad=no&001"),
                ListPage(title = "热门文章", name = "news_top_stories", listUrl = "https://api003.ftmailbox.com/channel/weekly.html?webview=ftcapp&bodyonly=yes"),
                ListPage(title = "数据新闻", name = "news_data", listUrl = "https://api003.ftmailbox.com/channel/datanews.html?webview=ftcapp&bodyonly=yes"),
                ListPage(title = "会议活动", name = "news_events", webUrl = "http://www.ftchinese.com/m/events/event.html?webview=ftcapp"),
                ListPage(title = "FT研究院", name = "news_fta", webUrl = "http://www.ftchinese.com/m/marketing/intelligence.html?webview=ftcapp&001")
        )

        val englishPages = arrayOf(
                ListPage(title = "英语电台", name = "english_radio", listUrl = "https://api003.ftmailbox.com/channel/radio.html?webview=ftcapp&bodyonly=yes"),
                ListPage("金融英语速读", name = "english_finance", listUrl = "https://api003.ftmailbox.com/channel/speedread.html?webview=ftcapp&bodyonly=yes"),
                ListPage("双语阅读", name = "english_bilingual", listUrl = "https://api003.ftmailbox.com/channel/ce.html?webview=ftcapp&bodyonly=yes"),
                ListPage("原声视频", name = "english_video", listUrl = "https://api003.ftmailbox.com/channel/ev.html?webview=ftcapp&bodyonly=yes&001")
        )

        val ftaPages = arrayOf(
                ListPage(title = "商学院观察", name = "fta_story", listUrl = "https://api003.ftmailbox.com/m/corp/preview.html?pageid=mbastory&webview=ftcapp&bodyonly=yes"),
                ListPage(title = "热点观察", name = "fta_hot", listUrl = "https://api003.ftmailbox.com/m/corp/preview.html?pageid=hotcourse&webview=ftcapp&bodyonly=yes"),
                ListPage(title = "MBA训练营", name = "fta_gym", listUrl = "https://api003.ftmailbox.com/channel/mbagym.html?webview=ftcapp&bodyonly=yes"),
                ListPage(title = "互动小测", name = "fta_quiz", listUrl = "https://api003.ftmailbox.com/m/corp/preview.html?pageid=quizplus&webview=ftcapp&bodyonly=yes"),
                ListPage(title = "深度阅读", name = "fta_reading", listUrl = "https://api003.ftmailbox.com/m/corp/preview.html?pageid=mbaread&webview=ftcapp&bodyonly=yes")
        )

        val videoPages = arrayOf(
                ListPage(title = "最新", name = "video_latest", listUrl = "https://api003.ftmailbox.com/channel/stream.html?webview=ftcapp&bodyonly=yes&norepeat=yes"),
                ListPage(title = "政经", name = "video_politics", listUrl = "https://api003.ftmailbox.com/channel/vpolitics.html?webview=ftcapp&bodyonly=yes&norepeat=yes"),
                ListPage(title = "商业", name = "video_business", listUrl = "https://api003.ftmailbox.com/channel/vbusiness.html?webview=ftcapp&bodyonly=yes&norepeat=yes"),
                ListPage(title = "秒懂", name = "video_explain", listUrl = "https://api003.ftmailbox.com/channel/explainer.html?webview=ftcapp&bodyonly=yes&norepeat=yes"),
                ListPage(title = "金融", name = "video_finance", listUrl = "https://api003.ftmailbox.com/channel/vfinance.html?webview=ftcapp&bodyonly=yes&norepeat=yes"),
                ListPage(title = "文化", name = "video_culture", listUrl = "https://api003.ftmailbox.com/channel/vculture.html?webview=ftcapp&bodyonly=yes&norepeat=yes"),
                ListPage(title = "高端视点", name = "video_top", webUrl = "http://www.ftchinese.com/channel/viewtop.html?webview=ftcapp&norepeat=no"),
                ListPage(title = "FT看见", name = "video_feature", listUrl = "https://api003.ftmailbox.com/channel/vfeatures.html?webview=ftcapp&bodyonly=yes&norepeat=yes"),
                ListPage(title = "有色眼镜", name = "video_tinted", listUrl = "https://api003.ftmailbox.com/channel/videotinted.html?webview=ftcapp&bodyonly=yes&norepeat=yes")
        )
    }
}