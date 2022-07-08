package com.devsisters.SolitaireDeckedOu.helpers

import android.app.Application
import com.devsisters.SolitaireDeckedOu.deferred.DeferredData
import com.onesignal.OneSignal

class GoldBookSender(
    private val deferredData: DeferredData,
    private val app: Application
) {
    fun send() {
        OneSignal.initWithContext(app)
        OneSignal.setAppId(ONE_SIGNAL_ID)
        OneSignal.setExternalUserId(deferredData.advertisingId)

        val campaign = deferredData.appsMap?.get("campaign").toString()

        when {
            campaign == "null" && deferredData.deepString == "null" -> {
                OneSignal.sendTag("key2", "organic")
            }
            deferredData.deepString != "null" -> {
                OneSignal.sendTag(
                    "key2", deferredData.deepString
                        .replace("myapp://", "")
                        .substringBefore("/")
                )
            }
            campaign != "null" -> {
                OneSignal.sendTag("key2", campaign.substringBefore("_"))
            }

        }
    }

    companion object {
        private const val ONE_SIGNAL_ID = "a129c765-4a4b-4e41-a86c-2eca1f1409f7"
    }
}
