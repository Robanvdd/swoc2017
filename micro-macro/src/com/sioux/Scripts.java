package com.sioux;

import sun.font.Script;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 29/06/2017.
 */
public class Scripts {
    private String working_dir;

    Scripts(String working_dir){
        this.working_dir = working_dir;
    }

    public File[] GetAllScripts(){
        File folder = new File(working_dir);
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".py");
            }
        });

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {

                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        return listOfFiles;
    }

    public List<Bot> StartAllBots() throws IOException {
        List<Bot> bots = new ArrayList<Bot>();

        for (File file: GetAllScripts()) {
            bots.add(new Bot(working_dir, "python" + " " + file.toString(),"1"));
        }
        return bots;
    }
}
