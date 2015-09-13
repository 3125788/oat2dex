package com.morrowind.java.util;

public class FileNamer {
    
    public class FileDuplicateException extends Exception {
        private static final long serialVersionUID = 2900417852936485863L;
    }
    
    private String inputFilepath = null;
    private String name = null;
    private String suffix = null;
    
    public FileNamer(String[] args, String defaultOutputFileSuffix) throws FileDuplicateException {
        inputFilepath = args[0];
        String outputFilepath = null;
        int index = 0;
        if(args.length > 1) { // User specified output filename.
            outputFilepath = args[1];
            index = outputFilepath.lastIndexOf('.');
            if(index > 0) {
                name = outputFilepath.substring(0, index);
                suffix = outputFilepath.substring(index);
            } else {
                name = outputFilepath;
                suffix = "";
            }
        } else { // Generate output filename according input.
            index = inputFilepath.lastIndexOf('.');
            if(index > 0) {
                name = inputFilepath.substring(0, index);
            } else {
                name = inputFilepath;
            }
            suffix = defaultOutputFileSuffix;
        }
        
        if(name.concat(suffix).equalsIgnoreCase(inputFilepath)) {
            throw new FileDuplicateException();
        }
    }
    
    public String getFilename(int index) {
        if(index < 1) {
            return name.concat(suffix);
        } else {
            return name.concat("-"+(index+1)).concat(suffix);
        }
    }
}
