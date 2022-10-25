/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package net.waterfox.android

import android.content.Context

/**
 * A single source for setting feature flags that are mostly based on build type.
 */
object FeatureFlags {
    /**
     * Pull-to-refresh allows you to pull the web content down far enough to have the page to
     * reload.
     */
    val pullToRefreshEnabled = Config.channel.isDebug

    /**
     * Enables the Sync Addresses feature.
     */
    const val syncAddressesFeature = false

    /**
     * Enables the "recent" tabs feature in the home screen.
     */
    const val showRecentTabsFeature = true

    /**
     * Enables UI features based on history metadata.
     */
    const val historyMetadataUIFeature = true

    /**
     * Enables the recently saved bookmarks feature in the home screen.
     */
    const val recentBookmarksFeature = true

    /**
     * Identifies and separates the tabs list with a secondary section containing least used tabs.
     */
    const val inactiveTabs = true

    /**
     * Identifies and separates the tabs list with a group containing search term tabs.
     */
    val tabGroupFeature = Config.channel.isDebug

    /**
     * Allows tabs to be dragged around as long as tab groups are disabled
     */
    val tabReorderingFeature = Config.channel.isDebug

    /**
     * Show Pocket recommended stories on home.
     */
    @Suppress("UNUSED_PARAMETER")
    fun isPocketRecommendationsFeatureEnabled(context: Context): Boolean {
        return false
    }

    /**
     * Show Pocket sponsored stories in between Pocket recommended stories on home.
     */
    fun isPocketSponsoredStoriesFeatureEnabled(context: Context): Boolean {
        return isPocketRecommendationsFeatureEnabled(context) && Config.channel.isDebug
    }

    /**
     * Enables showing the homescreen onboarding card.
     */
    const val showHomeOnboarding = false

    /**
     * Enables history improvement features.
     */
    const val historyImprovementFeatures = true

    /**
     * Separates history into local and synced from other sources.
     */
    val showSyncedHistory = Config.channel.isDebug

    /**
     * Enables the Task Continuity enhancements.
     */
    val taskContinuityFeature = Config.channel.isDebug

    /**
     * Enables the Unified Search feature.
     */
    val unifiedSearchFeature = Config.channel.isDebug

    /**
     * Enables receiving from the messaging framework.
     */
    const val messagingFeature = true
    /**
     * Enables compose on the tabs tray items.
     */
    val composeTabsTray = Config.channel.isDebug
}