/*
 * Copyright 2023 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.mediapipe.examples.poselandmarker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.provider.Settings.Global.getString
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.mediapipe.examples.poselandmarker.fragment.CameraFragment
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min


class OverlayView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var results: PoseLandmarkerResult? = null
    private var pointPaint = Paint()
    private var linePaint = Paint()
    private var textPaint = Paint()

    private var scaleFactor: Float = 1f
    private var imageWidth: Int = 1
    private var imageHeight: Int = 1
    public var angle2 = 0
    public var angle1 = 0
    public var angle3 = 0
    public var angle4 = 0
    public var HipAngle = 0

    // Using application context
    val mainActivity = context as MainActivity



    init {
        initPaints()
    }

    fun clear() {
        results = null
        pointPaint.reset()
        linePaint.reset()
//        textPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        linePaint.color =
            ContextCompat.getColor(context!!, R.color.mp_color_primary)
        linePaint.strokeWidth = LANDMARK_STROKE_WIDTH
        linePaint.style = Paint.Style.STROKE

        pointPaint.color = Color.YELLOW
        pointPaint.strokeWidth = LANDMARK_STROKE_WIDTH
        pointPaint.style = Paint.Style.FILL

        textPaint.color = Color.RED
        textPaint.strokeWidth = 100f
//        textPaint.style = Paint.Style.STROKE
        textPaint.textSize = 80f



    }

    override fun draw(canvas: Canvas) {

        super.draw(canvas)
        results?.let { poseLandmarkerResult ->
            for(landmark in poseLandmarkerResult.landmarks()) {
                for(normalizedLandmark in landmark) {
                    canvas.drawPoint(
                        normalizedLandmark.x() * imageWidth * scaleFactor,
                        normalizedLandmark.y() * imageHeight * scaleFactor,
                        pointPaint
                    )
                }

//                 by me : get 3 landmarks and calculate the angle formed in the middle landmark

                val R_Shoulder = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(11).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(11).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(11).z() * imageHeight * scaleFactor).toDouble())// First coord
                val R_Elbow = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(13).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(13).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(13).z() * imageHeight * scaleFactor).toDouble()) // Second coord
                val R_Wrist = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(15).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(15).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(15).z() * imageHeight * scaleFactor).toDouble())//Third coord

                val radians1 =  atan2(R_Wrist[1] - R_Elbow[1], R_Wrist[0] - R_Elbow[0])
                -atan2(R_Shoulder[1] - R_Elbow[1], R_Shoulder[0] - R_Elbow[0])

                angle1 = (abs(radians1) * 180.0 / PI).toInt()
                if (angle1 > 180)
                {
                    angle1 = 360 - angle1
                }
//                              by me : for singular landmark selection and giving output

                canvas.drawText(angle1.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(13).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(13).y() * imageHeight * scaleFactor,textPaint)




                //Left side
                // by me : get 3 landmarks and calculate the angle formed in the middle landmark
                val L_Shoulder = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(12).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(12).y() * imageHeight * scaleFactor).toDouble())// First coord
                val L_Elbow = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(14).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(14).y() * imageHeight * scaleFactor).toDouble()) // Second coord
                val L_Wrist = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(16).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(16).y() * imageHeight * scaleFactor).toDouble())//Third coord

                val radians2 = atan2(L_Wrist[1] - L_Elbow[1], L_Wrist[0] - L_Elbow[0])
                -atan2(L_Shoulder[1] - L_Elbow[1], L_Shoulder[0] - L_Elbow[0])


                angle2 = (abs(radians2) * 180.0 / PI).toInt()
                if (angle2 > 180)
                {
                    angle2 = 360 - angle2
                }
//              by me : for singular landmark selection and giving output
                canvas.drawText(angle2.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(14).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(14).y() * imageHeight * scaleFactor,textPaint)


                //LShoulder
//                by me : get 3 landmarks and calculate the angle formed in the middle landmark

                val R_Shoulder1 = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(12).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(12).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(12).z() * imageHeight * scaleFactor).toDouble())// First coord
                val L_Shoulder1 = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(11).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(11).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(11).z() * imageHeight * scaleFactor).toDouble()) // Second coord
                val L_Elbow1 = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(13).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(13).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(13).z() * imageHeight * scaleFactor).toDouble())//Third coord

                val radians3 =  atan2(L_Elbow1[1] - L_Shoulder1[1], L_Elbow1[0] - L_Shoulder1[0])
                -atan2(R_Shoulder1[1] - L_Shoulder1[1], R_Shoulder1[0] - L_Shoulder1[0])

                angle3 = (abs(radians3) * 180.0 / PI).toInt()
                if (angle3 > 180)
                {
                    angle3 = 360 - angle3
                }
//                              by me : for singular landmark selection and giving output
                mainActivity.Angle1 = angle3
                canvas.drawText(angle3.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(11).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(11).y() * imageHeight * scaleFactor,textPaint)

                //RShoulder
//                by me : get 3 landmarks and calculate the angle formed in the middle landmark

                val L_Shoulder2 = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(11).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(11).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(11).z() * imageHeight * scaleFactor).toDouble())// First coord
                val R_Shoulder2 = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(12).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(12).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(12).z() * imageHeight * scaleFactor).toDouble()) // Second coord
                val R_Elbow1 = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(14).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(14).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(14).z() * imageHeight * scaleFactor).toDouble())//Third coord

                val radians4 =  atan2(R_Elbow1[1] - R_Shoulder2[1], R_Elbow1[0] - R_Shoulder2[0])
                -atan2(L_Shoulder2[1] - R_Shoulder2[1], L_Shoulder2[0] - R_Shoulder2[0])

                angle4 = (abs(radians3) * 180.0 / PI).toInt()
                if (angle4 > 180)
                {
                    angle4 = 360 - angle4
                }
//                              by me : for singular landmark selection and giving output
                mainActivity.Angle1 = angle4
                canvas.drawText(angle4.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(12).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(12).y() * imageHeight * scaleFactor,textPaint)

                //RHip
//                by me : get 3 landmarks and calculate the angle formed in the middle landmark

                val R_Knee = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(26).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(26).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(26).z() * imageHeight * scaleFactor).toDouble())// First coord
                val R_Hip = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(24).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(24).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(24).z() * imageHeight * scaleFactor).toDouble()) // Second coord
                val R_Ankle = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(28).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(28).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(28).z() * imageHeight * scaleFactor).toDouble())
                val L_Hip = doubleArrayOf(
                    (poseLandmarkerResult.landmarks().get(0).get(23).x() * imageWidth * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(23).y() * imageHeight * scaleFactor).toDouble(),
                    (poseLandmarkerResult.landmarks().get(0).get(23).z() * imageHeight * scaleFactor).toDouble())//Third coord
                //val radians3 =  atan2(L_Elbow1[1] - L_Shoulder1[1], L_Elbow1[0] - L_Shoulder1[0])
                //-atan2(R_Shoulder1[1] - L_Shoulder1[1], R_Shoulder1[0] - L_Shoulder1[0])
                val radians5 =  atan2(R_Hip[1] - R_Knee[1], R_Hip[0] - R_Knee[0]) - atan2(R_Knee[1] - R_Ankle[1], R_Knee[0] - R_Ankle[0])

                HipAngle = (abs(radians5) * 180.0 / PI).toInt()
                if (HipAngle > 180)
                {
                    HipAngle = 360 - HipAngle
                }
//                              by me : for singular landmark selection and giving output
                mainActivity.HipAngle = HipAngle
                canvas.drawText(HipAngle.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(24).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(24).y() * imageHeight * scaleFactor,textPaint)
                canvas.drawText(HipAngle.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(25).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(25).y() * imageHeight * scaleFactor,textPaint)
                canvas.drawText(HipAngle.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(28).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(28).y() * imageHeight * scaleFactor,textPaint)
                canvas.drawText(HipAngle.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(23).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(23).y() * imageHeight * scaleFactor,textPaint)
                canvas.drawText(HipAngle.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(26).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(26).y() * imageHeight * scaleFactor,textPaint)
                canvas.drawText(HipAngle.toString(),
                    poseLandmarkerResult.landmarks().get(0).get(27).x() * imageWidth * scaleFactor,
                    poseLandmarkerResult.landmarks().get(0).get(27).y() * imageHeight * scaleFactor,textPaint)

                PoseLandmarker.POSE_LANDMARKS.forEach {
                    canvas.drawLine(
                        poseLandmarkerResult.landmarks().get(0).get(it!!.start()).x() * imageWidth * scaleFactor,
                        poseLandmarkerResult.landmarks().get(0).get(it.start()).y() * imageHeight * scaleFactor,
                        poseLandmarkerResult.landmarks().get(0).get(it.end()).x() * imageWidth * scaleFactor,
                        poseLandmarkerResult.landmarks().get(0).get(it.end()).y() * imageHeight * scaleFactor,
                        linePaint)
                }
            }
        }
    }

    fun setResults(
        poseLandmarkerResults: PoseLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
        runningMode: RunningMode = RunningMode.IMAGE
    ) {
        results = poseLandmarkerResults
        this.imageHeight = imageHeight
        this.imageWidth = imageWidth

        scaleFactor = when (runningMode) {
            RunningMode.IMAGE,
            RunningMode.VIDEO -> {
                min(width * 1f / imageWidth, height * 1f / imageHeight)
            }
            RunningMode.LIVE_STREAM -> {
                // PreviewView is in FILL_START mode. So we need to scale up the
                // landmarks to match with the size that the captured images will be
                // displayed.
                max(width * 1f / imageWidth, height * 1f / imageHeight)
            }
        }
        invalidate()
    }

    companion object {
        private const val LANDMARK_STROKE_WIDTH = 12F
    }
}