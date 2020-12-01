package com.tiandi.logistics;

import com.tiandi.logistics.utils.MinioUtil;
import org.apache.commons.lang.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/1 14:15
 */
public class Barcode4jTest {

    @Test
    public void test() {
        String code128 = generateBarCode128("0011234567890998");
        System.out.println(code128);
    }

    /**
     * 生成code128条形码
     *
     * @param message       要生成的文本
     * @return 条形码在服务器上面的地址
     */
    public String generateBarCode128(String message) {
        Code128Bean bean = new Code128Bean();
        // 分辨率
        int dpi = 512;
        // 设置两侧是否留白
        bean.doQuietZone(true);

        // 设置条形码高度和宽度
        bean.setBarHeight((double) ObjectUtils.defaultIfNull(10.00, 9.0D));
        bean.setModuleWidth(0.3);
        // 设置文本位置（包括是否显示）
        if (false) {
            bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
        }
        // 设置图片类型
        String format = "image/png";

        ByteArrayOutputStream ous = new ByteArrayOutputStream();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                BufferedImage.TYPE_BYTE_BINARY, false, 0);

        // 生产条形码
        bean.generateBarcode(canvas, message);
        try {
            canvas.finish();
        } catch (IOException e) {

        }

        ByteArrayInputStream inputStream = new ByteArrayInputStream(ous.toByteArray());
        String filePath = MinioUtil.getInstance().upLoadFile(inputStream, format);

        return filePath;
    }
}
