package Provider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lev
 */
public class FileListProvider {
    
    public static List<File> getFiles(ServletContext context) throws IOException {
        List<File> files = new ArrayList<>(); 
        
        File filepath = getFilepath(context);
        
        if(!filepath.exists())
            return files;
        
        File[] fileArray = filepath.listFiles();
        
        if(filepath == null)
            throw new IOException("Cannot open filepath!");
       
        
        for(File file : fileArray) {
            if(file.isFile())
                files.add(file);
        }
        
        return files;
    }
    
    public static File getFilepath(ServletContext context) {
        String saveDir = context.getInitParameter("FilePath");
        return new File(saveDir);
    } 
    
    public static boolean ensureFilePath(File filepath) throws IOException {
        if(!filepath.exists()) {
            if (!filepath.mkdirs()) {
                throw new IOException("Cannot create FilePath");
            }
            return true;
        } else if(!filepath.isDirectory()) {
            throw new IOException("FilePath isn't a directory!");
        } else {
            return false;
        }
    }
}
