
package com.google.mediapipe.examples.poselandmarker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.mediapipe.examples.poselandmarker.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var sensorManager: SensorManager
    private lateinit var rotationSensor: Sensor
    private var azimuthDegrees: Float = 0.0f
    private var pitchDegrees: Float = 0.0f
    private var rollDegrees: Float = 0.0f
    private var rotation: Int = 0
    private lateinit var rotationTextView: TextView
     var Angle1: Int = 0
     var Angle2: Int = 0
    private var rep: Int = 0
    private var repCount: Float = 0f
    public val MainMenu = MainMenu()

    companion object {
        @JvmStatic
        private var View_selection: Int = 0

        @JvmStatic
        fun setViewSelection(value: Int) {
            View_selection = value
        }

        @JvmStatic
        fun getViewSelection(): Int {
            return View_selection
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        rotationTextView = findViewById(R.id.textView6)

        // Start rotation detection when the app starts
        startDetection()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnNavigationItemReselectedListener {
            // Ignore the reselection

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        // Stop rotation detection when the app is destroyed
        stopDetection()
    }

    private fun startDetection() {
        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun stopDetection() {
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

            if (View_selection == 1) {
                if (pitchDegrees > 0.00f && rollDegrees > 0) {
                    // LandScape Camera Right side
                    //Fornt View PushUp
                    //                if (MainMenu.excerise_selection == 1) {

                    if (Angle1 <= 45) {
                        if (rep == 0) {
                            repCount += .5f
                            rep = 1
                        }
                    } else if (Angle1 >= 45) {
                        if (rep == 1) {
                            repCount += 0.5f
                            rep = 0
                        }
                    }
//                        } //else if (View_selection == 2) { ///Side view
                    //                        if (Angle1 <= 135) {
                    //                            if (rep == 0) {
                    //                                repCount += .5f
                    //                                rep = 1
                    //                            }
                    //                        } else if (Angle1 >= 135) {
                    //                            if (rep == 1) {
                    //                                repCount += 0.5f
                    //                                rep = 0
                    //                            }
                    //                        }
                    //                    }
                    rotation = repCount.toInt() // 0
                    //                }
                } else if (pitchDegrees < 0 && rollDegrees < 0) {
                    // Portrait
                    if (Angle1 >= 45) {
                        if (rep == 0) {
                            repCount += .5f
                            rep = 1
                        }
                    } else if (Angle1 <= 45) {
                        if (rep == 1) {
                            repCount += 0.5f
                            rep = 0
                        }
                    }
                    rotation = repCount.toInt() // 1
                } else if (pitchDegrees > 0 && pitchDegrees < 5 && rollDegrees < 0) {
                    // LandScape Camera Left Side
                    if (Angle1 >= 135) {
                        if (rep == 0) {
                            repCount += .5f
                            rep = 1
                        }
                    } else if (Angle1 <= 135) {
                        if (rep == 1) {
                            repCount += 0.5f
                            rep = 0
                        }
                    }
                    rotation = repCount.toInt()// 2
                } else if (pitchDegrees > 10 && rollDegrees < 0) {
                    // Inverse Portrait
                    if (Angle1 <= 135) {
                        if (rep == 0) {
                            repCount += .5f
                            rep = 1
                        }
                    } else if (Angle1 >= 135) {
                        if (rep == 1) {
                            repCount += 0.5f
                            rep = 0
                        }
                    }
                    rotation = repCount.toInt()// 3
                }

                // Update the rotation value on the TextView
                rotationTextView.text =  "Rep $rotation"
            }

        }
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do nothing
    }

    override fun onBackPressed() {
        finish()
    }
}
