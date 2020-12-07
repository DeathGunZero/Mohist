package com.mohistmc;

import com.mohistmc.config.MohistConfigUtil;
import com.mohistmc.network.download.DownloadLibraries;
import com.mohistmc.network.download.UpdateUtils;
import com.mohistmc.utils.JarLoader;
import com.mohistmc.utils.i18n.i18n;

import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.jar.Manifest;

import static com.mohistmc.network.download.DownloadLibraries.loadLibs;
import static com.mohistmc.utils.EulaUtil.hasAcceptedEULA;
import static com.mohistmc.utils.EulaUtil.writeInfos;
import static com.mohistmc.utils.InstallUtils.startInstallation;
import static net.minecraftforge.server.ServerMain.startMohistServer;

public class MohistMCStart {

    public static String getVersion() {
        return (MohistMCStart.class.getPackage().getImplementationVersion() != null) ? MohistMCStart.class.getPackage().getImplementationVersion() : "unknown";
    }

    public static String getForgeVersion() {
        return (MohistMCStart.class.getPackage().getSpecificationVersion() != null) ? MohistMCStart.class.getPackage().getSpecificationVersion() : "unknown";
    }

    public static String getMCPVersion() {
        return (MohistMCStart.class.getPackage().getSpecificationTitle() != null) ? MohistMCStart.class.getPackage().getSpecificationTitle() : "unknown";
    }

    public static void main(String[] args) throws Exception {
        MohistConfigUtil.copyMohistConfig();
        if(MohistConfigUtil.bMohist("show_logo", "true"))
            System.out.println("\n" + "\n" +
                " __    __   ______   __  __   __   ______   ______  \n" +
                "/\\ \"-./  \\ /\\  __ \\ /\\ \\_\\ \\ /\\ \\ /\\  ___\\ /\\__  _\\ \n" +
                "\\ \\ \\-./\\ \\\\ \\ \\/\\ \\\\ \\  __ \\\\ \\ \\\\ \\___  \\\\/_/\\ \\/ \n" +
                " \\ \\_\\ \\ \\_\\\\ \\_____\\\\ \\_\\ \\_\\\\ \\_\\\\/\\_____\\  \\ \\_\\ \n" +
                "  \\/_/  \\/_/ \\/_____/ \\/_/\\/_/ \\/_/ \\/_____/   \\/_/ \n" +
                "                                                    \n" + "\n" +
                "                                      " + i18n.getString("mohist.launch.welcomemessage"));
        if (MohistConfigUtil.bMohist("check_libraries", "true")) DownloadLibraries.run();
        startInstallation();
        loadLibs();
        if (MohistConfigUtil.bMohist("check_update", "true")) UpdateUtils.versionCheck();
        if(!hasAcceptedEULA()) {
            System.out.println(i18n.getString("eula"));
            while (!"true".equals(new Scanner(System.in).next())) ;
            writeInfos();
        }
        startMohistServer(args);
    }
}