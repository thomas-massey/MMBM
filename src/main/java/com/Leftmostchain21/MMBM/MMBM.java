package com.Leftmostchain21.MMBM;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Mod(modid = MMBM.MODID, name = MMBM.NAME, version = MMBM.VERSION)
public class MMBM {
    public static final String MODID = "mmbm";
    public static final String NAME = "MMBM";
    public static final String VERSION = "1.0";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Make sure that the mod is the latest release
        // If not, then update the mod using the latest GitHub release
        // If the mod is the latest release, then continue

        // We will use an info.json file to store the current version of the mod
        // First check if the info.json file exists
        // If it does not exist, then create it and write the current version of the mod to it
        try {
            // Get GitHub release info

            URL ReleaseURL = new URL("https://api.github.com/repos/thomas-massey/MMBM/releases/latest");
            HttpURLConnection connection = (HttpURLConnection) ReleaseURL.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            StringBuilder sb = new StringBuilder();
            while ((input = br.readLine()) != null) {
                sb.append(input);
            }
            br.close();

            System.out.println("Checking if info.json exists...");
            File infoFile = new File("config/info.json");
            // Print the working directory
            if (infoFile.createNewFile()) {
                System.out.println("Info file created: " + infoFile.getName());
                // Add some object to the info file
                FileWriter infoWriter = new FileWriter("config/info.json");
                infoWriter.write("{\n\t\"version\": \"" + "UNKNOWN" + "\"\n}");
                infoWriter.close();
            } else {
                System.out.println("Info file already exists.");
            }

            // Compare the current version of the mod to the latest version of the mod
            JsonParser parser = new JsonParser();
            JsonObject gitInfo = parser.parse(sb.toString()).getAsJsonObject();
            String latestRelease = gitInfo.get("id").getAsString();
            // Read the info.json file
            String currentVersion = null;
            try {
                // Read the info.json file
                Object obj = new JsonParser().parse(new FileReader("config/info.json"));
                JsonObject infoObject = (JsonObject) obj;
                currentVersion = infoObject.get("version").getAsString();
                System.out.println("Current version: " + currentVersion);
                System.out.println("Latest version: " + latestRelease);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (latestRelease.toString() != currentVersion.toString()) {
                // Now download the latest release of the mod from GitHub and set the info.txt file to the latest version
                System.out.println("Downloading latest release of the mod from GitHub");
                System.out.println(gitInfo.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("browser_download_url").getAsString());
                try {
                    URL downloadURL = new URL(gitInfo.get("assets").getAsJsonArray().get(0).getAsJsonObject().get("browser_download_url").getAsString());
                    File new_file = new File("MMBM-" + gitInfo.get("name").getAsString() + ".jar");
                    FileUtils.copyURLToFile(downloadURL, new_file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Now update the info.json file
                // Delete the old info.json version tag
                JsonObject versionObject = parser.parse(new FileReader("config/info.json")).getAsJsonObject();
                versionObject.remove("version");
                // Add the new version tag
                versionObject.addProperty("version", latestRelease);
                // Write the new version tag to the info.json file
                FileWriter infoWriter = new FileWriter("config/info.json");
                infoWriter.write(versionObject.toString());
                infoWriter.close();
                System.out.println("Updating info.json file");
                // Now detect the OS
                String OS = System.getProperty("os.name").toLowerCase();
                if (OS.contains("win")) {
                    // Windows
                    System.out.println("Windows detected");
                    // Now run the batch file
                    try {
                        Process p = Runtime.getRuntime().exec("cmd /c ping localhost -n 6 > nul && del MMBM-" + gitInfo.get("name").getAsString() + ".jar");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (OS.contains("mac")) {
                    // Mac
                    System.out.println("Mac detected");
                    // Now run the bash file
                    try {
                        Process p = Runtime.getRuntime().exec("bash -c \"sleep 6 && rm MMBM-" + gitInfo.get("name").getAsString() + ".jar\"");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
                    // Unix
                    System.out.println("Unix detected");
                    // Now run the bash file
                    try {
                        Process p = Runtime.getRuntime().exec("bash -c \"sleep 6 && rm MMBM-" + gitInfo.get("name").getAsString() + ".jar\"");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Unknown
                    System.out.println("Unknown OS detected, please manually delete the old mod jar file");
                }
                // Now exit the game
                System.exit(0);
            } else {
                // Continue
                System.out.println("The mod is up to date!");
            }




        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
