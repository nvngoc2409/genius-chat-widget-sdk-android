package com.genius.dgchat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.digitalgenius.chatwidgetsdk.DGChatSdk
import com.digitalgenius.chatwidgetsdk.interactions.IDGChatWidgetListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDGChatSdk()
//        https://demos.dgwidgetdeployments.com/general/widget-test?env=dev.us&initversion=2.2.0&widgetId=c8f53916-ad17-4be8-8f58-383ea76bc5f8&version=2.4.0
//        https://demos.dgwidgetdeployments.com/general/widget-test?env=eu&initversion=2.2.0&widgetId=b2813082-fe22-40aa-99f9-91f0b974efaa&version=2.4.0

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.straight_btn).setOnClickListener {
            startActivity(Intent(this, StraightActivity::class.java))
        }
        findViewById<Button>(R.id.embedded_btn).setOnClickListener {
            startActivity(Intent(this, EmbeddedActivity::class.java))
        }
        findViewById<Button>(R.id.navigation_btn).setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }
        findViewById<Button>(R.id.fragment_btn).setOnClickListener {
            startActivity(Intent(this, FragmentActivityExample::class.java))
        }
    }

    private fun initDGChatSdk() {
        DGChatSdk.init(
            "b2813082-fe22-40aa-99f9-91f0b974efaa",
            "eu",
            "2.3.1",
            true,
            callbacks = object : IDGChatWidgetListener {
                override fun onChatMinimizeClick() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onChatMinimizeClick",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onCSATPopoverCloseClicked() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onCSATPopoverCloseClicked",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onChatEndClick() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onChatEndClick",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onChatLauncherClick() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onChatLauncherClick",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onChatProactiveButtonClick() {
                    Toast.makeText(
                        this@MainActivity,
                        "User callback -> onChatProactiveButtonClick",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }
}