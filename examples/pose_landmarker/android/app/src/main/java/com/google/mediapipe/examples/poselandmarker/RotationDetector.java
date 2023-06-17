//package com.google.mediapipe.examples.poselandmarker;
//
//
//import android.content.Context;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import com.google.mediapipe.examples.poselandmarker.fragment.CameraFragment;
//
//public class RotationDetector implements SensorEventListener {
//    private SensorManager sensorManager;
//    private Sensor rotationSensor;
//    private float azimuthDegrees;
//    private float pitchDegrees;
//    private float rollDegrees;
//    private int rotation;
//
//    public RotationDetector(Context context) {
//        // Get the sensor service
//        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//
//        // Get the rotation vector sensor
//        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
//    }
//
//    public void startDetection() {
//        // Register the sensor listener
//        sensorManager.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//
//    public void stopDetection() {
//        // Unregister the sensor listener
//        sensorManager.unregisterListener(this);
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
//            // Get the rotation matrix from the rotation vector
//            float[] rotationMatrix = new float[9];
//            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
//
//            // Extract the rotation angles from the rotation matrix
//            float[] rotationAngles = new float[3];
//            SensorManager.getOrientation(rotationMatrix, rotationAngles);
//
//            // The rotation angles are in radians, so you can convert them to degrees
//            azimuthDegrees = (float) Math.toDegrees(rotationAngles[0]);
//            pitchDegrees = (float) Math.toDegrees(rotationAngles[1]);
//            rollDegrees = (float) Math.toDegrees(rotationAngles[2]);
//
//            if(pitchDegrees > 0 && rollDegrees > 1)
//            {
//                // LandScape Camera Right side
//                rotation = 0;
////                OverlayView.
//            } else if (pitchDegrees < 0 && rollDegrees < 1)
//            {
//                //Portrait
//                rotation = 1;
//            } else if (pitchDegrees > 0 && pitchDegrees < 5 && rollDegrees < 0)
//            {
//                //LandScape Camera Left Side
//                rotation = 2;
//            } else if (pitchDegrees > 10 && rollDegrees < 0)
//            {
//                //Inverse Portrait
//                rotation = 3;
//            }
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // Do nothing
//    }
//
//    // Getter methods to access the rotation values
//    public int getRotation() {
//        return rotation;
//    }
//
//}
//
