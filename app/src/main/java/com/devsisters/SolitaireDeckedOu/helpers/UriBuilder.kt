package com.devsisters.SolitaireDeckedOu.helpers

import android.app.Application
import androidx.core.net.toUri
import com.appsflyer.AppsFlyerLib
import com.devsisters.SolitaireDeckedOu.deferred.DeferredData
import java.util.*

class UriBuilder(
    private val deferredData: DeferredData,
    private val app: Application,
    private val initUri: String
) {
    fun build(): String =
        initUri.toUri().buildUpon().apply {
            appendQueryParameter(SECURE_GET_PARAMETR, SECURE_KEY)
            appendQueryParameter(DEV_TMZ_KEY, TimeZone.getDefault().id)
            appendQueryParameter(GADID_KEY, deferredData.advertisingId)
            appendQueryParameter(DEEPLINK_KEY, deferredData.deepString)
            appendQueryParameter(
                SOURCE_KEY,
                deferredData.appsMap?.get("media_source").toString()
            )
            appendQueryParameter(
                AF_ID_KEY,
                AppsFlyerLib.getInstance().getAppsFlyerUID(app.applicationContext)
            )
            appendQueryParameter(
                ADSET_ID_KEY,
                deferredData.appsMap?.get("adset_id").toString()
            )
            appendQueryParameter(
                CAMPAIGN_ID_KEY,
                deferredData.appsMap?.get("campaign_id").toString()
            )
            appendQueryParameter(
                APP_CAMPAIGN_KEY,
                deferredData.appsMap?.get("campaign").toString()
            )
            appendQueryParameter(ADSET_KEY, deferredData.appsMap?.get("adset").toString())
            appendQueryParameter(ADGROUP_KEY, deferredData.appsMap?.get("adgroup").toString())
            appendQueryParameter(
                ORIG_COST_KEY,
                deferredData.appsMap?.get("orig_cost").toString()
            )
            appendQueryParameter(
                AF_SITEID_KEY,
                deferredData.appsMap?.get("af_siteid").toString()
            )
        }.toString()

    companion object {
        private const val SECURE_GET_PARAMETR = "SrORcpTB8d"
        private const val SECURE_KEY = "L8ECKHE6Lr"
        private const val DEV_TMZ_KEY = "dmesT11XdG"
        private const val GADID_KEY = "hxH0IEwYJU"
        private const val DEEPLINK_KEY = "E14d29ue9u"
        private const val SOURCE_KEY = "cEtLmOiBlN"
        private const val AF_ID_KEY = "sAs5XHgbEb"
        private const val ADSET_ID_KEY = "vnhSoKJROV"
        private const val CAMPAIGN_ID_KEY = "ga3ed9Cpfb"
        private const val APP_CAMPAIGN_KEY = "CY4Anfs3zo"
        private const val ADSET_KEY = "WBULgOHGeU"
        private const val ADGROUP_KEY = "i1oEZDldBK"
        private const val ORIG_COST_KEY = "q8hb8QutR4"
        private const val AF_SITEID_KEY = "z354QvUvk0"
    }
}