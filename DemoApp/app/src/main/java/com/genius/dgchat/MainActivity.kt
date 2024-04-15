package com.genius.dgchat

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.digitalgenius.chatwidgetsdk.DGChatSdk
import com.digitalgenius.chatwidgetsdk.ext.attachDGChatViewToLifecycle
import com.digitalgenius.chatwidgetsdk.interactions.DGChatMethods
import com.digitalgenius.chatwidgetsdk.interactions.DGChatViewAnimator
import com.digitalgenius.chatwidgetsdk.interactions.IDGChatWidgetListener
import com.digitalgenius.chatwidgetsdk.ui.DGChatView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var methods: DGChatMethods? = null
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                methods?.minimizeWidget()
            }
        })

        DGChatSdk.init(
            "f0c07546-af4c-4963-9e23-3e9343eaf13b",
            "dev.us",
            "2.2.1",
            true,
            crmPlatform = "sunco",
            crmVersion = "1.0.0",
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
            },
        )
        attachDGChatViewToLifecycle()


        setContentView(R.layout.activity_main)

        val chatView = findViewById<DGChatView>(R.id.screen_chat_view)
        findViewById<Button>(R.id.screen_btn_sdk).setOnClickListener {
            methods = chatView.show(
                object : DGChatViewAnimator {
                    override val animLength: Long
                        get() = 800

                    override fun onAnimationStarted(view: View) {
                        view.alpha = 0f
                        view.translationX = -700f
                        view.translationY = 700f
                    }

                    override fun onAnimate(view: View, progress: Float) {
                        view.translationX = -700f - (-700f * progress)
                        view.translationY = 700f - (700f * progress)
                        view.scaleX = progress
                        view.scaleY = progress
                        view.rotation = 360 * progress
                        view.alpha = progress
                    }

                    override fun onAnimationFinished(view: View) {

                    }
                }
            )
        }
    }

}