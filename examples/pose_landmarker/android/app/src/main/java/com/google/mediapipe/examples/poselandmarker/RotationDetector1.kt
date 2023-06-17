package com.google.mediapipe.examples.poselandmarker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class RotationDetector1(private val context: Context) : SensorEventListener {
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val rotationSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private var azimuthDegrees: Float = 0f
    private var pitchDegrees: Float = 0f
    private var rollDegrees: Float = 0f

    fun startDetection() {
        rotationSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopDetection() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)

            val rotationAngles = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, rotationAngles)

            azimuthDegrees = Math.toDegrees(rotationAngles[0].toDouble()).toFloat()
            pitchDegrees = Math.toDegrees(rotationAngles[1].toDouble()).toFloat()
            rollDegrees = Math.toDegrees(rotationAngles[2].toDouble()).toFloat()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do nothing
    }

    fun getAzimuthDegrees(): Float {
        return azimuthDegrees
    }

    fun getPitchDegrees(): Float {
        return pitchDegrees
    }

    fun getRollDegrees(): Float {
        return rollDegrees
    }
}
