package com.morrowind.java.tool.android;

import com.morrowind.java.util.DataReader;
import com.morrowind.java.util.Elf;
import com.morrowind.java.util.FileNamer;
import com.morrowind.java.util.FileNamer.FileDuplicateException;
import com.morrowind.java.util.Oat.DexFile;
import com.morrowind.java.util.Oat;

import java.io.IOException;
import java.util.UnknownFormatConversionException;

public class oat2dex {

    /** Convert Android ART odex file to dex file.
     * @param args oat2dex.jar xxx.odex [yyy.dex]
     */
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: \tjava -jar oat2dex.jar xxx.odex [yyy.dex]");
            System.out.println("\t[yyy.dex] is optional. If not specified, the output will be xxx.dex.");
            return;
        }
        
        // Do convertion.
        try {
            System.out.println("Converting oat file to dex file...");
            FileNamer dexFilenamer = new FileNamer(args, ".dex");
            Elf elf = new Elf(args[0]);
            DataReader reader = elf.getReader();
            reader.seek(0x1000); // oat offset in Elf
            Oat oat = new Oat(reader);
            DexFile[] dexFiles = oat.getDexFiles();
            for(int i = 0; i < dexFiles.length; i++) {
                dexFiles[i].saveTo(dexFilenamer.getFilename(i));
                System.out.printf("Create %s -- OK!\n", dexFilenamer.getFilename(i));
            }
            elf.close();
        } catch (FileDuplicateException e) {
            System.out.println("Error! The output filename is same as input!");
        } catch (UnknownFormatConversionException e) {
            System.out.println(e.getConversion());
            System.out.println("Error! The input file is not oat format.");
        } catch (IOException e) {
            System.out.println("Error! Cannot create output dex file!");
        } catch (SecurityException e) {
            System.out.println(e.toString());
        }
    }
}
