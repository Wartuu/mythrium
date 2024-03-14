package xyz.mythrium.backend.controller.api;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mythrium.backend.service.OtpService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/example-OTP")
public class OtpTest {


    @Autowired
    OtpService service;

    public OtpTest(OtpService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] example() throws Exception {
        ByteArrayOutputStream bstream = new ByteArrayOutputStream();
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix matrix = writer.encode("otpauth://totp/TESTER?secret=ExampleSecretCode&issuer=Mythrium", BarcodeFormat.QR_CODE, 150, 150);
        ImageIO.write(MatrixToImageWriter.toBufferedImage(matrix), "png", bstream);
        System.out.println("Check with google-auth. Code should be equal to:\n CODE (6-digit): " + service.generateTOTP("ExampleSecretCode"));

        return bstream.toByteArray();
    }

}
