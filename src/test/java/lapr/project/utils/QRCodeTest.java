package lapr.project.utils;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class QRCodeTest {

    @Test
    void createQRCode() throws IOException, WriterException, NotFoundException {
        String expResult="code1";
        BufferedImage qrCode= QRCode.createQRCode("code1");
        String result=QRCode.convertToText(qrCode);
        assertEquals(expResult,result);
    }

    @Test
    void convertToText() throws IOException, WriterException, NotFoundException {
        String expResult="code1";
        BufferedImage qrCode=QRCode.createQRCode("code1");
        String result=QRCode.convertToText(qrCode);
        assertEquals(expResult,result);
    }
}