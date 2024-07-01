package com.genius.dgchat

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.digitalgenius.chatwidgetsdk.interactions.DGChatMethods
import com.digitalgenius.chatwidgetsdk.interactions.DGChatViewAnimator
import com.digitalgenius.chatwidgetsdk.ui.DGChatView
import kotlin.random.Random

class NavigationActivity : AppCompatActivity() {

    private var screenNumber = 0
    private val dgChatAnimator = object : DGChatViewAnimator {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val dgChatView = findViewById<DGChatView>(R.id.navigation_dgchatview)
        val methods: DGChatMethods = dgChatView.show(dgChatAnimator)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (dgChatView.visibility == View.VISIBLE && dgChatView.isChatViewMaximized) {
                    dgChatView.hide()
//                    methods.minimizeWidget()
                    return
                }

                if (supportFragmentManager.backStackEntryCount > 0) {
                    popFromStack()
                    return
                }

                finish()
            }
        })

        pushToStack()
        findViewById<Button>(R.id.navigation_back).setOnClickListener {
            popFromStack()
        }
        findViewById<Button>(R.id.navigation_show_widget).setOnClickListener {
            dgChatView.show(dgChatAnimator)
        }
        findViewById<Button>(R.id.navigation_forward).setOnClickListener {
            pushToStack()
        }
    }

    private fun pushToStack() {
        val title = "Screen #${screenNumber++}"
        val fragmentName = "NavigationFragment_$title"

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .replace(R.id.screen_container, NavigationFragment.newInstance(title))
            .addToBackStack(fragmentName)
            .commit()

        findViewById<Button>(R.id.navigation_back).isEnabled = screenNumber > 0
    }

    private fun popFromStack() {
        supportFragmentManager.popBackStack()
        screenNumber--

        findViewById<Button>(R.id.navigation_back).isEnabled = screenNumber > 0
    }
}


class NavigationFragment : Fragment() {
    private val rnd = Random(System.currentTimeMillis())
    private val backColor = Color.argb(
        255,
        rnd.nextInt(256),
        rnd.nextInt(256),
        rnd.nextInt(256)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_navigation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setBackgroundColor(backColor)

        view.findViewById<TextView>(R.id.navigation_title).text =
            arguments?.getString(PARAM_TITLE) ?: "Undefined"
    }


    companion object {
        private const val PARAM_TITLE = "NavigationFragment.Params.Title"

        fun newInstance(title: String) = NavigationFragment().apply {
            arguments = bundleOf(PARAM_TITLE to title)
        }
    }
}