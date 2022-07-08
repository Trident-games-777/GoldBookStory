package com.devsisters.SolitaireDeckedOu.deferred

typealias AppsMap = MutableMap<String, Any>?

data class DeferredData(
    val appsMap: AppsMap = null,
    val deepString: String = "",
    val advertisingId: String = ""
)