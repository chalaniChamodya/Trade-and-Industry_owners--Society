package lk.ijse.Trade_and_Industrial_owners_Society.Utill;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class QRCodeReader {
    private static WebcamPanel panel = null;
    private static Webcam webcam = null;

    public static String initWebcam(){
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(size);

        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        panel.setFPSDisplayed(true);

        JFrame jFrame = new JFrame();
        jFrame.add(panel);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Handle the window closing event
                webcam.close();
                jFrame.dispose(); // This will dispose of the JFrame
            }
        });

        do {
            try {
                BufferedImage image = webcam.getImage();
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(binaryBitmap);

                if ( result.getText() != null ) {
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    webcam.close();
                    return result.getText();
                }
            } catch (Exception e) {
            }
        } while (true);
    }
}
