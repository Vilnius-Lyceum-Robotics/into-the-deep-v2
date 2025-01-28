package org.firstinspires.ftc.teamcode.subsystems.vision;

import static org.firstinspires.ftc.teamcode.subsystems.vision.VisionConfiguration.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class SampleProcessor implements VisionProcessor {
    Mat cameraMatrix;
    Mat distCoeffs;

    RayGroundIntersectionProcessor rgip = new RayGroundIntersectionProcessor(FX, FY, CX, CY,
            POS_X, POS_Y, POS_Z - 1.5,
            LEFT_ANGLE, DOWN_ANGLE);

    // Store detection results
    public static class Detection {
        Point center; // pixel coordinates
        Point3d worldPos;
        Rect bounds;
        String color;
        double area;
        MatOfPoint contour; // Store the contour points

        Detection(Point center, Point3d worldPos, Rect bounds, String color, double area, MatOfPoint contour) {
            this.center = center;
            this.worldPos = worldPos;
            this.bounds = bounds;
            this.color = color;
            this.area = area;
            this.contour = contour;
        }

        public Point3d getWorldPos() {
            return worldPos;
        }
    }

    private List<Detection> latestDetections = new ArrayList<>();
    private Paint boxPaint;
    private Paint textPaint;

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        cameraMatrix = new Mat(3, 3, CvType.CV_64F);
        double[] calibrationData = new double[]{
                FX, 0, CX,
                0, FY, CY,
                0, 0, 1
        };
        cameraMatrix.put(0, 0, calibrationData);

        distCoeffs = new Mat(1, 5, CvType.CV_64F);
        double[] distCoeffData = new double[]{K1, K2, P1, P2, K3};
        distCoeffs.put(0, 0, distCoeffData);
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        latestDetections.clear();

        Mat undistorted = new Mat();
        Calib3d.undistort(frame, undistorted, cameraMatrix, distCoeffs);

        return latestDetections;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight,
                            float scaleBmpPxToCanvasPx, float scaleCanvasDensity,
                            Object userContext) {
    }

    public List<Detection> getLatestDetections() {
        return latestDetections;
    }
}