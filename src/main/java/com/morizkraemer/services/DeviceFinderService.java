package com.morizkraemer.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.deepsymmetry.beatlink.DeviceAnnouncement;
import org.deepsymmetry.beatlink.DeviceAnnouncementListener;
import org.deepsymmetry.beatlink.DeviceFinder;
import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.DeviceUpdateListener;
import org.deepsymmetry.beatlink.VirtualCdj;
//import org.deepsymmetry.beatlink.data.BeatGrid;
import org.deepsymmetry.beatlink.data.BeatGridFinder;
import org.deepsymmetry.beatlink.data.CrateDigger;
import org.deepsymmetry.beatlink.data.MetadataFinder;
//import org.deepsymmetry.beatlink.data.TrackMetadata;
//import org.deepsymmetry.beatlink.data.WaveformDetail;
import org.deepsymmetry.beatlink.data.WaveformFinder;

import com.morizkraemer.gui.ConsoleWindow;
import com.morizkraemer.gui.StatusBar;

import com.morizkraemer.gui.StatusBar.Status;

public class DeviceFinderService {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();
    StatusBar statusBar = StatusBar.getInstance();

    private static DeviceFinderService instance;

    private DeviceFinder deviceFinder;
    private VirtualCdj virtualCdj;
    private MetadataFinder metadataFinder;
    private CrateDigger crateDigger;
    private WaveformFinder waveformFinder;
    private BeatGridFinder beatGridFinder;

    private Set<DeviceAnnouncement> foundPlayers = new HashSet<>();
    @SuppressWarnings("unused")
    private DeviceAnnouncement foundMixer;

    private final Map<Integer, DeviceUpdate> deviceUpdates = new ConcurrentHashMap<>();

    public void runDeviceFinder() {
        new Thread(() -> {
            try {
                startServices();
                discoverDevices();
                deviceFinder.addDeviceAnnouncementListener(deviceAnnouncementListener);
                virtualCdj.addUpdateListener(deviceUpdateListener);

            } catch (InterruptedException e) {
                e.printStackTrace();
                // TODO implement error handling
            } catch (Exception e) {
                e.printStackTrace();
                // TODO implement error handling
            }

        }).start();
    }

    DeviceAnnouncementListener deviceAnnouncementListener = new DeviceAnnouncementListener() {
        @Override
        public void deviceFound(DeviceAnnouncement device) {
            addDevice(device);
            consoleWindow.appendToConsole("listener", device);
        }

        @Override
        public void deviceLost(DeviceAnnouncement device) {
            consoleWindow.appendToConsole("listener", device);
        }
    };

    DeviceUpdateListener deviceUpdateListener = new DeviceUpdateListener() {
        @Override
        public void received(DeviceUpdate update) {
            int playerNumber = update.getDeviceNumber();
            if (playerNumber < 9) {
                deviceUpdates.put(playerNumber, update);
            }
            //consoleWindow.appendToConsole("update", update);
        }
    };

    public void discoverDevices() throws InterruptedException {
        statusBar.setStatus(Status.SEARCHING);
        consoleWindow.appendToConsole("deviceManager", "Searching for devices on the network...");
        Thread.sleep(3000);
        Set<DeviceAnnouncement> devices = deviceFinder.getCurrentDevices();
        if (devices.isEmpty()) {
            statusBar.setStatus(Status.NO_DEVICES_FOUND);
            consoleWindow.appendToConsole("deviceManager", "No devices found.");
        } else {
            statusBar.setStatus(Status.DEVICES_FOUND);
            for (DeviceAnnouncement device : devices) {
                addDevice(device);
            }
        }
    }

    private void addDevice(DeviceAnnouncement device) {
        consoleWindow.appendToConsole("device", device);
        if (device.getDeviceNumber() < 9) {
            String deviceName = device.getDeviceName();
            if (deviceName.contains("CDJ") || deviceName.contains("XDJ")) {
                foundPlayers.add(device);
            } else if (deviceName.contains("DJM")) {
                foundMixer = device;
            } else {
            }
            if (deviceFinderUpdateListener != null) {
                deviceFinderUpdateListener.onPlayerFound(foundPlayers);
            }
        }
    }

    public void startServices() throws Exception {
        deviceFinder = DeviceFinder.getInstance();
        virtualCdj = VirtualCdj.getInstance();
        crateDigger = CrateDigger.getInstance();
        metadataFinder = MetadataFinder.getInstance();
        waveformFinder = WaveformFinder.getInstance();
        beatGridFinder = BeatGridFinder.getInstance();

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

    public Map<Integer, DeviceUpdate> getAllDeviceUpdates() {
        return deviceUpdates;
    }

    public DeviceUpdate getDeviceUpdate(int playerNumber) {
        return deviceUpdates.get(playerNumber);
    }

    public static DeviceFinderService getInstance() {
        if (instance == null) {
            instance = new DeviceFinderService();
        }
        return instance;
    }

    private DeviceFinderUpdateListener deviceFinderUpdateListener;

    public interface DeviceFinderUpdateListener {
        void onPlayerFound(Set<DeviceAnnouncement> devices);
    }

    public void setDeviceFinderUpdateListener(DeviceFinderUpdateListener deviceFinderUpdateListener) {
        this.deviceFinderUpdateListener = deviceFinderUpdateListener;
    }

}
