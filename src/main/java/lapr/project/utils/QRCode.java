package lapr.project.utils;

import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.*;

/**
 * The type Qr code.
 */
public class QRCode {

    private QRCode() {

    }


    private static final Map<com.google.zxing.EncodeHintType,String> hintMap = new HashMap<>();


    /**
     * Cria um QRCode em formato BUfferedImage recebendo uma string com a informação a colocar no QRCode.
     *
     * @param data informação a colocar no QRCode
     * @return the buffered image
     * @throws WriterException the writer exception
     */
    public static BufferedImage createQRCode(String data) throws WriterException {
            return createQRCode(data, 200,200);
        }

        private static BufferedImage createQRCode(String qrCodeData,
                                                  int qrCodeheight, int qrCodewidth)
                throws WriterException {
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(qrCodeData.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8),
                    BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, QRCode.hintMap);

            return MatrixToImageWriter.toBufferedImage(matrix);
        }

    /**
     * Converte o QRCode em formato BufferedImage para texto (String).
     *
     * @param bufferedImage the buffered image
     * @return the string
     * @throws NotFoundException the not found exception
     */
    public static String convertToText (BufferedImage bufferedImage) throws NotFoundException {
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap binarybitmapobj = new BinaryBitmap(new HybridBinarizer(source));

            Result resultobj = new MultiFormatReader().decode(binarybitmapobj);

           return resultobj.getText();
        }

}
