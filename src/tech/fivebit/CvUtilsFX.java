package tech.fivebit;

import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


public class CvUtilsFX {

    private static WritableImage MatToImageFX(Mat mat) {
        if (mat == null || mat.empty()) return null;

        if (mat.depth() == CvType.CV_8U) {}
        else if (mat.depth() == CvType.CV_16U) {
            Mat mat_16 = new Mat();
            mat.convertTo(mat_16, CvType.CV_8U, 255.0 / 65535);
            mat = mat_16;
        }
        else if (mat.depth() == CvType.CV_32F) {
            Mat mat_32 = new Mat();
            mat.convertTo(mat_32, CvType.CV_8U, 255);
            mat = mat_32;
        }
        else
            return null;

        if (mat.channels() == 1) {
            Mat mat_bgra = new Mat();
            Imgproc.cvtColor(mat, mat_bgra, Imgproc.COLOR_GRAY2BGR);
            mat = mat_bgra;
        }
        else if (mat.channels() == 3) {
            Mat mat_bgra = new Mat();
            Imgproc.cvtColor(mat, mat_bgra, Imgproc.COLOR_BGR2BGRA);
            mat = mat_bgra;
        }
        else if (mat.channels() == 4) {}
        else
            return null;

        byte[] buf = new byte[mat.channels() * mat.cols() * mat.rows()];
        mat.get(0, 0, buf);

        WritableImage writableImage = new WritableImage(mat.cols(), mat.rows());
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        pixelWriter.setPixels(0,0, mat.cols(), mat.rows(),
                WritablePixelFormat.getByteBgraInstance(),
                buf, 0, mat.cols() * 4);
        return writableImage;
    }

    public static void showImage (Mat img, String title) {
        Image image = MatToImageFX(img);

        Stage stage = new Stage();
        ScrollPane scrollPane = new ScrollPane();
        ImageView imageView = new ImageView();
        if (image !=null) {
            imageView.setImage(image);
            if (image.getWidth() < 1000) {
                scrollPane.setPrefWidth(image.getWidth() + 5);
            }
            else scrollPane.setPrefWidth(1000.);
            if (image.getHeight() < 700) {
                scrollPane.setPrefHeight(image.getHeight() + 5);
            }
            else scrollPane.setPrefHeight(700.);
        }
        scrollPane.setContent(imageView);
        scrollPane.setPannable(true);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(scrollPane);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}