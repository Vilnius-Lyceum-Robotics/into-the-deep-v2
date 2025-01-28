package org.firstinspires.ftc.teamcode.subsystems.vision;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.image.ImageProcessor;

public class TfLiteModelInterpreter {
    private String modelPath;
    private String labelPath;

    private Interpreter interpreter;

    private int tensorWidth;
    private int tensorHeight;
    private int tensorChannels;
    private int tensorElements;

    private String[] labels;

    private ImageProcessor imageProcessor;

    public TfLiteModelInterpreter(String modelPath, String labelPath) {
        this.modelPath = modelPath;
        this.labelPath = labelPath;

        FileUtil.loadMappedFile(context, modelPath);

    }
}
