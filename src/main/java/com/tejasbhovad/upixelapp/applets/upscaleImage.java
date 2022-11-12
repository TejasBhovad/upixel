package com.tejasbhovad.upixelapp.applets;

import com.tejasbhovad.upixelapp.Application;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class upscaleImage {
    static BufferedImage rs;

    public static void main(String[] args) throws IOException {
        try {
            BufferedImage foo = ImageIO.read(new File("/Users/home/Downloads/pointer.png"));
            int width = foo.getWidth();
            int height = foo.getHeight();
            int mFactor;
            if (Math.max(width, height) >= 0 && Math.max(width, height) <= 100) {
                mFactor = 20;
            } else if (Math.max(width, height) > 100 && Math.max(width, height) <= 500) {
                mFactor = 5;
            } else if (Math.max(width, height) > 500 && Math.max(width, height) <= 1000) {
                mFactor = 2;
            } else {
                mFactor = 2;
            }
            BufferedImage rs = cover(foo, mFactor);// cover X2
            String home = System.getProperty("user.home");
            Path path = Paths.get(Application.imagePath);
            Path fileTMP = path.getFileName();
            String fileName = fileTMP.toString();
            ImageIO.write(rs, "png", new File(home + "/Downloads/" + fileName + "_upscaled.txt"));
        } catch (NullPointerException e) {
            System.out.println("No Input File");
        }

    }


    private static int[][] convertToPixels(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb -= 16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }
        return result;
    }

    public static BufferedImage cover(BufferedImage image, int range) {
        int[][] pixels = convertToPixels(image);
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage imageResult = new BufferedImage(width * range, height * range, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width * range; x++) {
            for (int y = 0; y < height * range; y++) {
                imageResult.setRGB(x, y, pixels[y / range][x / range]);
            }
        }
        return imageResult;
    }

    public static void startUpscale() {
        try {
            //Application ob = new Application();
            System.out.println(Application.imagePath);
            BufferedImage foo = ImageIO.read(new File(Application.imagePath));
            int width = foo.getWidth();
            int height = foo.getHeight();
            int mFactor;
            if (Math.max(width, height) >= 0 && Math.max(width, height) <= 100) {
                mFactor = 20;
            } else if (Math.max(width, height) > 100 && Math.max(width, height) <= 500) {
                mFactor = 5;
            } else if (Math.max(width, height) > 500 && Math.max(width, height) <= 1000) {
                mFactor = 2;
            } else {
                mFactor = 2;
            }
            rs = cover(foo, mFactor);// cover X2
            //ImageIO.write(rs, "png", new File("src/main/resources/com/tejasbhovad/upixel/images/tmp.png"));
        } catch (NullPointerException | IOException e) {
            System.out.println("No Input File");
        }
    }

    public static void writePNG() throws IOException {

        String home = System.getProperty("user.home");
        Path path = Paths.get(Application.imagePath);
        Path fileTMP = path.getFileName();
        String fileName = fileTMP.toString();
        if (fileName.indexOf(".") > 0)
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        ImageIO.write(rs, "png", new File(home + "/Downloads/" + fileName + "_upscaled.png"));

    }

}