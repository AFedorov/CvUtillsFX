package tech.fivebit;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Test {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    public static void main (String[] args) {
        Mat image = Imgcodecs.imread("C:\\JProjects\\data\\IMG_9375.jpg");
        if (image.empty()) {
            System.out.println("Не удалось загрузить изображение из файла");
            return;
        }
        System.out.println(image);
        CvUtilsFX.showImage(image, "Тестовое изображение");

        image.release();
    }
}
