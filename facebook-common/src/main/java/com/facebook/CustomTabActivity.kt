/*
 * Copyright (c) 2014-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.facebook

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.FragmentActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

/*
 * Login from a custom tab redirects here. Pass the url on to CustomTabMainActivity so it can return
 * the result.
 */
class CustomTabActivity : FragmentActivity() {
  private var closeReceiver: BroadcastReceiver? = null
  private val handler = registerForActivityResult(StartActivityForResult()) {
    if (it.resultCode == RESULT_CANCELED) {
      // We weren't able to open CustomTabMainActivity from the back stack. Send a broadcast
      // instead.
      val broadcast = Intent(CUSTOM_TAB_REDIRECT_ACTION)
      broadcast.putExtra(CustomTabMainActivity.EXTRA_URL, intent.dataString)
      LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast)

      // Wait for the custom tab to be removed from the back stack before finishing.
      val closeReceiver =
        object : BroadcastReceiver() {
          override fun onReceive(context: Context, intent: Intent) {
            finish()
          }
        }
      LocalBroadcastManager.getInstance(this)
        .registerReceiver(closeReceiver, IntentFilter(DESTROY_ACTION))
      this.closeReceiver = closeReceiver
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    handler.launch(Intent(this, CustomTabMainActivity::class.java).apply {
      action = CUSTOM_TAB_REDIRECT_ACTION
      putExtra(CustomTabMainActivity.EXTRA_URL, intent.dataString)

      // these flags will open CustomTabMainActivity from the back stack as well as closing this
      // activity and the custom tab opened by CustomTabMainActivity.
      addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
    })
  }

  override fun onDestroy() {
    closeReceiver?.let { LocalBroadcastManager.getInstance(this).unregisterReceiver(it) }
    super.onDestroy()
  }

  companion object {
    @JvmField
    val CUSTOM_TAB_REDIRECT_ACTION =
        CustomTabActivity::class.java.simpleName + ".action_customTabRedirect"

    @JvmField val DESTROY_ACTION = CustomTabActivity::class.java.simpleName + ".action_destroy"
  }
}
