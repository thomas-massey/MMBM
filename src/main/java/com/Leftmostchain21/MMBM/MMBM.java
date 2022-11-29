package com.Leftmostchain21.MMBM;

import com.google.gson.JsonArray;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Mod(modid = MMBM.MODID, name = MMBM.NAME, version = MMBM.VERSION)
public class MMBM {
    public static final String MODID = "mmbm";
    public static final String NAME = "MMBM";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Make sure that the mod is the latest release
        // If not, then update the mod using the latest GitHub release
        // If the mod is the latest release, then continue

        // We will use a info.txt file to store the current version of the mod
        // First check if the info.txt file exists
        // If it does not exist, then create it and write the current version of the mod to it
        try {
            System.out.println("Checking if info.txt exists...");
            File infoFile = new File("config/info.txt");
            // Print the working directory
            if (infoFile.createNewFile()) {
                System.out.println("Info file created: " + infoFile.getName());
                // Now download the latest release of the mod from GitHub and set the info.txt file to the latest version
                System.out.println("Downloading latest release of the mod from GitHub");
                URL ReleaseURL = new URL("https://api.github.com/repos/thomas-massey/MMBM/releases/latest");
                HttpURLConnection connection = (HttpURLConnection) ReleaseURL.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String input;
                StringBuilder sb = new StringBuilder();
                while ((input = br.readLine()) != null) {
                    sb.append(input);
                }
                br.close();
                JsonArray json = new JsonArray();
                System.out.println("Hello");


                // Now parse the JSON file to get the download URL

            } else {
                System.out.println("Info file already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
