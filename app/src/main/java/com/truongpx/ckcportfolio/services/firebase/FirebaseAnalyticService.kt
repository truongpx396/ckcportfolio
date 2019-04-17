package com.truongpx.ckcportfolio.services.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseAnalyticService {
    private var firebaseAnalytics: FirebaseAnalytics

    private constructor(context: Context) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    companion object {
        private var instance: FirebaseAnalyticService? = null
        fun getInstance(context: Context): FirebaseAnalyticService {
            return instance
                ?: FirebaseAnalyticService(context)
        }
    }

    fun logEvent(itemId: String, itemName: String, contentType: String) {
        var bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType)
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    fun logEventEnterSearchScreen() {
        logEvent("1", "EnterSearchScreen", "SearchScreenFragment")
    }

    fun logEventEnterCreatePortfolioScren() {
        logEvent("2", "EnterCreatePortfolioScreen", "CreatePortfolioScreenFragment")
    }

    fun logEventDeletePortfolio() {
        logEvent("3", "DeletePortfolio", "DeletePortfolioMenuButton")
    }


}