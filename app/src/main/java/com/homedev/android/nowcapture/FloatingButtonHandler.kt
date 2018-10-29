package com.homedev.android.nowcapture

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.view.*
import android.widget.ImageView
import com.homedev.android.nowcapture.ui.MainActivity
import android.view.WindowManager
import android.os.Build



// TODO: 잘 모르겠어서 일단 suppressLint
@SuppressLint("ClickableViewAccessibility")
class FloatingButtonHandler(val service: Service) {
    var floatingButton: View? = null
    var windowManager: WindowManager? = null

    fun setUp() {
        windowManager = service.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        floatingButton = LayoutInflater.from(service).inflate(R.layout.floating_button, null)
        val params = createParam()
        floatingButton!!.visibility = View.VISIBLE
        windowManager!!.addView(floatingButton, params)

        val closeButton = floatingButton!!.findViewById(R.id.close_btn) as ImageView
        closeButton.setOnClickListener {
            service.stopSelf()
        }

        val floatingButtonImage = floatingButton!!.findViewById(R.id.chat_head_profile_iv) as ImageView
        floatingButtonImage.setOnTouchListener(FloatingButtonOnTouchListener(service, params))
    }

    private fun createParam(): WindowManager.LayoutParams {
        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                PixelFormat.TRANSLUCENT)

        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = 0
        params.y = 100
        return params
    }

    fun close() {
        if (floatingButton != null) windowManager?.removeView(floatingButton)
    }

    inner class FloatingButtonOnTouchListener(val service: Service, val params: WindowManager.LayoutParams): View.OnTouchListener {
        private var lastAction: Int = 0
        private var initialX: Int = 0
        private var initialY: Int = 0
        private var initialTouchX: Float = 0.toFloat()
        private var initialTouchY: Float = 0.toFloat()

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y

                    initialTouchX = event.rawX
                    initialTouchY = event.rawY

                    lastAction = event.action
                    return true
                }
                MotionEvent.ACTION_UP -> {
                    if (lastAction == MotionEvent.ACTION_DOWN) {
                        val intent = Intent(service, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        service.startActivity(intent)

                        service.stopSelf()
                    }
                    lastAction = event.action
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    params.x = initialX + (event.rawX - initialTouchX).toInt()
                    params.y = initialY + (event.rawY - initialTouchY).toInt()

                    windowManager!!.updateViewLayout(floatingButton, params)
                    lastAction = event.action
                    return true
                }
            }
            return false
        }
    }
}