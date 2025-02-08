package com.morizkraemer.services;

import java.util.Set;

import org.deepsymmetry.beatlink.DeviceAnnouncement;
import org.deepsymmetry.beatlink.DeviceAnnouncementListener;
import org.deepsymmetry.beatlink.DeviceFinder;
import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.DeviceUpdateListener;
import org.deepsymmetry.beatlink.VirtualCdj;
import org.deepsymmetry.beatlink.data.BeatGridFinder;
import org.deepsymmetry.beatlink.data.CrateDigger;
import org.deepsymmetry.beatlink.data.MetadataFinder;
import org.deepsymmetry.beatlink.data.TrackMetadataListener;
import org.deepsymmetry.beatlink.data.TrackMetadataUpdate;
import org.deepsymmetry.beatlink.data.WaveformFinder;

import com.morizkraemer.gui.ConsoleWindow;
import com.morizkraemer.gui.StatusBar;
import com.morizkraemer.state.PlayerState.AppStatus;
import com.morizkraemer.state.PlayerState;

public class DeviceFinderService {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    StatusBar statusBar = StatusBar.getInstance();
    PlayerState playerState = PlayerState.getInstance();

    private static DeviceFinderService instance;

    private DeviceFinder deviceFinder;
    private VirtualCdj virtualCdj;
    private MetadataFinder metadataFinder;
    private CrateDigger crateDigger;
    private WaveformFinder waveformFinder;
    private BeatGridFinder beatGridFinder;


    public void runDeviceFinder() {
        new Thread(() -> {
            try {
                playerState.setAppStatus(AppStatus.SERVICE_OFFLINE);
                startServices();
                discoverDevices();
                deviceFinder.addDeviceAnnouncementListener(deviceAnnouncementListener);
                virtualCdj.addUpdateListener(deviceUpdateListener);
                metadataFinder.addTrackMetadataListener(trackMetadataListener);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    DeviceAnnouncementListener deviceAnnouncementListener = new DeviceAnnouncementListener() {
        @Override
        public void deviceFound(DeviceAnnouncement device) {
            playerState.setAppStatus(AppStatus.DEVICES_FOUND);
            addDevice(device);
            consoleWindow.appendToConsole("Device Found: ", device.getDeviceNumber() + "-" + device.getDeviceName());
        }

        @Override
        public void deviceLost(DeviceAnnouncement device) {
            if (deviceFinder.getCurrentDevices().isEmpty()) {
                playerState.setAppStatus(AppStatus.NO_DEVICES_FOUND);
            }
            consoleWindow.appendToConsole("Device Lost: ", device.getDeviceNumber() + "-" + device.getDeviceName());
        }
    };

    DeviceUpdateListener deviceUpdateListener = new DeviceUpdateListener() {
        @Override
        public void received(DeviceUpdate update) {
            int playerNumber = update.getDeviceNumber();
            playerState.storePlayerUpdate(playerNumber, update);
        }
    };

    TrackMetadataListener trackMetadataListener = new TrackMetadataListener() {
        @Override
        public void metadataChanged(TrackMetadataUpdate update) {
            int playerNumber = update.player;
            if (playerNumber < 9) {
                consoleWindow.appendToConsole("track", update);
                playerState.storeTrackUpdate(playerNumber, update);
            }

        }
    };

    public void discoverDevices() throws InterruptedException {
        consoleWindow.appendToConsole("deviceManager", "Searching for devices on the network...");
        Thread.sleep(3000);
        Set<DeviceAnnouncement> devices = deviceFinder.getCurrentDevices();

        if (devices.isEmpty()) {
            playerState.setAppStatus(AppStatus.NO_DEVICES_FOUND);
            consoleWindow.appendToConsole("deviceManager", "No devices found.");
        } else {
            playerState.setAppStatus(AppStatus.DEVICES_FOUND);
            for (DeviceAnnouncement device : devices) {
                addDevice(device);
            };
        };
    };

    private void addDevice(DeviceAnnouncement device) {
        consoleWindow.appendToConsole("device", device);
        int deviceNumber = device.getDeviceNumber();
        if (deviceNumber < 9) {
            String deviceName = device.getDeviceName();
            if (deviceName.contains("CDJ") || deviceName.contains("XDJ")) {
                playerState.storeFoundPlayer(deviceNumber, device);
                metadataFinder.getLatestMetadataFor(deviceNumber);
            } else if (deviceName.contains("DJM")) {
                playerState.storeFoundMixer(device);
            } else {};
        };
    };

    public void startServices() throws Exception {
        deviceFinder = DeviceFinder.getInstance();
        virtualCdj = VirtualCdj.getInstance();
        crateDigger = CrateDigger.getInstance();
        metadataFinder = MetadataFinder.getInstance();
        waveformFinder = WaveformFinder.getInstance();
        beatGridFinder = BeatGridFinder.getInstance();
        playerState.setAppStatus(AppStatus.SEARCHING);

        deviceFinder.start();
        virtualCdj.start();
        metadataFinder.start();
        crateDigger.start();
        waveformFinder.start();
        beatGridFinder.start();

    };

    public void stopServices() {
        beatGridFinder.stop();
        waveformFinder.stop();
        crateDigger.stop();
        metadataFinder.stop();
        virtualCdj.stop();
    }

    public static DeviceFinderService getInstance() {
        if (instance == null) {
            instance = new DeviceFinderService();
        }
        return instance;
    }
}
