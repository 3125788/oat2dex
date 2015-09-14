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
            System.exit(0);
        }
        
        // Do convertion.
        int dexFileNumber = 0;
        try {
            System.out.println("Converting oat file to dex file...");
            
            FileNamer dexFilenamer = new FileNamer(args, ".dex");
            Elf elf = new Elf(args[0]);
            DataReader reader = elf.getReader();
            reader.seek(0x1000); // oat data offset in elf
            Oat oat = new Oat(reader);
            DexFile[] dexFiles = oat.getDexFiles();
            dexFileNumber = dexFiles.length;
            String dexFilename = null;
            for(int i = 0; i < dexFileNumber; i++) {
                dexFilename = dexFilenamer.getFilename(i);
                dexFiles[i].saveTo(dexFilename);
                System.out.printf("Extracting %s\t -- OK!\n", dexFilename);
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
        } finally {
            /* Return extracted dex file number back to caller, ex. cmd shell */
            System.exit(dexFileNumber);            
        }
    }
}
