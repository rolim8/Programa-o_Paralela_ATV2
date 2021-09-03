package main;
import image.mapping.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnalisarFotosPasta extends Thread {

    String path;
    Image comparPhoto;

    public AnalisarFotosPasta(Image comparPhoto, String path){
        this.path = path;
        this.comparPhoto = comparPhoto;
    }


    @Override
    public void run() {

        Image image01 = null;
        double similarityCosine;
  
        try {
        	
        	File tiffFile = new File(path);  //input path to tif file
            image01 = new Image(Image.componentExtract(this.path), 255);
            
            String outputPath = ("D:\\images\\processamentoPesado\\"); //output path to write file
            
            String convertExt = "png";  // convert file

            converterTiff(tiffFile, outputPath, convertExt);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        similarityCosine = image01.compareCosineSimilarity(comparPhoto);
        System.out.printf("%s %.2f%%\n", path, (similarityCosine * 100));

    }


    public String getFileExtension(String fileName) {

        int lastIndexOf = fileName.lastIndexOf(".");
        return fileName.substring(lastIndexOf).toLowerCase();
    }

    public File converterTiff(File tiffFile, String outputPath, String convertFormato) throws IOException {
    	
        String fileName = tiffFile.getName();
        String ext = getFileExtension(fileName);
        
        fileName = fileName.replace(ext, "."+convertFormato);
        BufferedImage tiff = ImageIO.read(tiffFile);
        
        File output = new File(outputPath + fileName);
        ImageIO.write(tiff, convertFormato, output);
        
        return output;
    }

  
    public static void main(String[] args) throws IOException {

        String filename01 = "images/skin.tif";
        Image comparavel = new Image(Image.componentExtract(filename01), 255);
        
        
        File file = new File("D:\\images\\processamentoPesado");

        for(File foto: file.listFiles()){
            new AnalisarFotosPasta(comparavel, foto.getPath()).start();
        }
    }
}