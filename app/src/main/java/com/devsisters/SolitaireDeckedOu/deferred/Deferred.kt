package com.devsisters.SolitaireDeckedOu.deferred

import android.app.Activity
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

private const val APPS_ID = "BaGKFXUaRRFQRoVFzoTpNf"

@Suppress("FunctionName")
suspend fun AppsData(activity: Activity): AppsMap = suspendCancellableCoroutine {
    AppsFlyerLib.getInstance().init(APPS_ID, object : AppsFlyerConversionListener {
        override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
            it.resumeWith(Result.success(p0))
        }

        override fun onConversionDataFail(p0: String?) {
            it.resumeWith(Result.failure(Exception(p0)))
        }

        override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}

        override fun onAttributionFailure(p0: String?) {
            it.resumeWith(Result.failure(Exception(p0)))
        }

    }, activity)
    AppsFlyerLib.getInstance().start(activity)
}

@Suppress("FunctionName")
suspend fun DeepString(activity: Activity): String = suspendCancellableCoroutine {
    AppLinkData.fetchDeferredAppLinkData(activity) { appLinkData ->
        it.resumeWith(Result.success(appLinkData?.targetUri.toString()))
    }
}

@Suppress("FunctionName", "BlockingMethodInNonBlockingContext")
suspend fun AdvertisingId(activity: Activity): String = withContext(Dispatchers.IO) {
    AdvertisingIdClient.getAdvertisingIdInfo(activity).id.toString()
}