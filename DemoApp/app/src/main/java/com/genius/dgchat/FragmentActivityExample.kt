package com.genius.dgchat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.digitalgenius.chatwidgetsdk.ui.DGChatView

interface IFragmentActivityCommunicator {
    fun onChatViewCreated(view: DGChatView)
}

class FragmentActivityExample: AppCompatActivity(), IFragmentActivityCommunicator {
    private var mDGChatView: DGChatView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mDGChatView ?: finish()

                if (mDGChatView?.visibility == View.VISIBLE) {
//                    mDGChatView?.lastSize
                    if (mDGChatView?.isChatViewMaximized == true) {
                        mDGChatView?.show()?.minimizeWidget()
                        return
                    }
                }

                finish()
            }
        })

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.slide_in,
                R.anim.slide_out
            )
            .replace(R.id.activity_fragment_container, FragmentExample())
            .commit()
    }

    override fun onChatViewCreated(view: DGChatView) {
        mDGChatView = view
    }
}

class FragmentExample: Fragment() {
    private var mCommunicator: IFragmentActivityCommunicator? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context !is IFragmentActivityCommunicator) {
            throw RuntimeException("Parent must implement ${IFragmentActivityCommunicator::class.java.name}")
        }
        mCommunicator = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_example, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dgchatView = view.findViewById<DGChatView>(R.id.dgchatview).apply {
            show()
        }
        mCommunicator?.onChatViewCreated(dgchatView)
    }
}