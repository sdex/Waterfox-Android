/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package net.waterfox.android.tabstray.browser

import android.view.LayoutInflater
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.browser.tabstray.TabsAdapter.Companion.PAYLOAD_DONT_HIGHLIGHT_SELECTED_ITEM
import mozilla.components.browser.tabstray.TabsAdapter.Companion.PAYLOAD_HIGHLIGHT_SELECTED_ITEM
import mozilla.components.support.test.robolectric.testContext
import org.junit.Test
import org.junit.runner.RunWith
import net.waterfox.android.databinding.TabTrayItemBinding
import net.waterfox.android.helpers.WaterfoxRobolectricTestRunner
import net.waterfox.android.selection.SelectionHolder
import net.waterfox.android.tabstray.TabsTrayStore
import mozilla.components.browser.state.state.createTab
import mozilla.components.browser.state.store.BrowserStore
import net.waterfox.android.ext.components

@RunWith(WaterfoxRobolectricTestRunner::class)
class BrowserTabsAdapterTest {

    private val context = testContext
    private val interactor = mockk<BrowserTrayInteractor>(relaxed = true)
    private val store = TabsTrayStore()

    @Test
    fun `WHEN bind with payloads is called THEN update the holder`() {
        every { testContext.components.core.thumbnailStorage } returns mockk()
        val adapter = BrowserTabsAdapter(context, interactor, store, mockk())
        val holder = mockk<AbstractBrowserTabViewHolder>(relaxed = true)

        adapter.updateTabs(
            listOf(
                createTab(url = "url", id = "tab1")
            ),
            null,
            selectedTabId = "tab1"
        )

        adapter.onBindViewHolder(holder, 0, listOf(PAYLOAD_HIGHLIGHT_SELECTED_ITEM))

        verify { holder.updateSelectedTabIndicator(true) }

        adapter.onBindViewHolder(holder, 0, listOf(PAYLOAD_DONT_HIGHLIGHT_SELECTED_ITEM))

        verify { holder.updateSelectedTabIndicator(false) }
    }

    @Test
    fun `WHEN the selection holder is set THEN update the selected tab`() {
        every { testContext.components.core.thumbnailStorage } returns mockk()
        every { testContext.components.core.store } returns BrowserStore()
        every { testContext.components.analytics } returns mockk(relaxed = true)
        every { testContext.components.settings } returns mockk(relaxed = true)
        val adapter = BrowserTabsAdapter(context, interactor, store, mockk())
        val binding = TabTrayItemBinding.inflate(LayoutInflater.from(testContext))
        val holder = spyk(
            BrowserTabViewHolder.ListViewHolder(
                imageLoader = mockk(),
                browserTrayInteractor = interactor,
                store = store,
                selectionHolder = null,
                itemView = binding.root
            )
        )
        val tab = createTab(url = "url", id = "tab1")

        every { holder.tab }.answers { tab }

        testSelectionHolder.internalState.add(tab)
        adapter.selectionHolder = testSelectionHolder

        adapter.updateTabs(
            listOf(tab),
            null,
            selectedTabId = "tab1"
        )

        adapter.onBindViewHolder(holder, 0, listOf(PAYLOAD_DONT_HIGHLIGHT_SELECTED_ITEM))

        verify { holder.showTabIsMultiSelectEnabled(any(), true) }
    }

    private val testSelectionHolder = object : SelectionHolder<TabSessionState> {
        override val selectedItems: Set<TabSessionState>
            get() = internalState

        val internalState = mutableSetOf<TabSessionState>()
    }
}