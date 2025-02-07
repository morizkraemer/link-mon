package com.morizkraemer.services;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.SwingUtilities;

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
import org.deepsymmetry.beatlink.data.TrackMetadata;
import org.deepsymmetry.beatlink.data.TrackMetadataListener;
import org.deepsymmetry.beatlink.data.TrackMetadataUpdate;
//import org.deepsymmetry.beatlink.data.TrackMetadata;
//import org.deepsymmetry.beatlink.data.WaveformDetail;
import org.deepsymmetry.beatlink.data.WaveformFinder;

import com.morizkraemer.gui.ConsoleWindow;
import com.morizkraemer.gui.StatusBar;

import com.morizkraemer.gui.StatusBar.AppStatus;

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

    private Map<Integer, DeviceAnnouncement> foundPlayers = new ConcurrentHashMap<>();
    @SuppressWarnings("unused")
    private DeviceAnnouncement foundMixer;

    private final Map<Integer, DeviceUpdate> deviceUpdates = new ConcurrentHashMap<>();
    private final Map<Integer, TrackMetadata> trackUpdates = new ConcurrentHashMap<>();

    public void runDeviceFinder() {
        new Thread(() -> {
            try {
                SwingUtilities.invokeLater(() -> statusBar.setStatus(AppStatus.SERVICE_OFFLINE));
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
            SwingUtilities.invokeLater(() -> statusBar.setStatus(AppStatus.DEVICES_FOUND));
            addDevice(device);
            consoleWindow.appendToConsole("Device Found: ", device.getDeviceNumber() + "-" + device.getDeviceName());
        }

        @Override
        public void deviceLost(DeviceAnnouncement device) {
            if (deviceFinder.getCurrentDevices().isEmpty()) {
                SwingUtilities.invokeLater(() -> statusBar.setStatus(AppStatus.NO_DEVICES_FOUND));
            }
            consoleWindow.appendToConsole("Device Lost: ", device.getDeviceNumber() + "-" + device.getDeviceName());
        }
    };

    DeviceUpdateListener deviceUpdateListener = new DeviceUpdateListener() {
        @Override
        public void received(DeviceUpdate update) {
            int playerNumber = update.getDeviceNumber();
            if (playerNumber < 9) {
                deviceUpdates.put(playerNumber, update);
            }
        }
    };

    TrackMetadataListener trackMetadataListener = new TrackMetadataListener() {
        @Override
        public void metadataChanged(TrackMetadataUpdate update) {
            int playerNumber = update.player;
            if (playerNumber < 9) {
                trackUpdates.put(playerNumber, update.metadata);
                consoleWindow.appendToConsole("track", update);
            }

        }
    };

    public void discoverDevices() throws InterruptedException {
        consoleWindow.appendToConsole("deviceManager", "Searching for devices on the network...");
        Thread.sleep(3000);
        Set<DeviceAnnouncement> devices = deviceFinder.getCurrentDevices();

        if (devices.isEmpty()) {
            SwingUtilities.invokeLater(() -> statusBar.setStatus(AppStatus.NO_DEVICES_FOUND));
            consoleWindow.appendToConsole("deviceManager", "No devices found.");
        } else {
            SwingUtilities.invokeLater(() -> statusBar.setStatus(AppStatus.DEVICES_FOUND));
            for (DeviceAnnouncement device : devices) {
                addDevice(device);
            }
        }
    }

    private void addDevice(DeviceAnnouncement device) {
        consoleWindow.appendToConsole("device", device);
        int deviceNumber = device.getDeviceNumber();
        if (deviceNumber < 9) {
            String deviceName = device.getDeviceName();
            if (deviceName.contains("CDJ") || deviceName.contains("XDJ")) {
                foundPlayers.put(deviceNumber, device);
                metadataFinder.getLatestMetadataFor(deviceNumber);
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
        SwingUtilities.invokeLater(() -> statusBar.setStatus(AppStatus.SEARCHING));

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

    public TrackMetadata getTrackUpdate(int playerNumber) {
        return trackUpdates.get(playerNumber);
    }

    private DeviceFinderUpdateListener deviceFinderUpdateListener;

    public interface DeviceFinderUpdateListener {
        void onPlayerFound(Map<Integer, DeviceAnnouncement> devices);
    }

    public void setDeviceFinderUpdateListener(DeviceFinderUpdateListener deviceFinderUpdateListener) {
        this.deviceFinderUpdateListener = deviceFinderUpdateListener;
    }

    public static DeviceFinderService getInstance() {
        if (instance == null) {
            instance = new DeviceFinderService();
        }
        return instance;
    }
}
