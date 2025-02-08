package com.morizkraemer.state;

import java.awt.Color;
import java.io.Console;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.deepsymmetry.beatlink.DeviceAnnouncement;
import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.data.TrackMetadata;
import org.deepsymmetry.beatlink.data.TrackMetadataUpdate;

import com.morizkraemer.AppConfig;
import com.morizkraemer.gui.ConsoleWindow;

public class PlayerState {
    ConsoleWindow consoleWindow = ConsoleWindow.getInstance();

    public enum AppStatus {
        SERVICE_OFFLINE(AppConfig.Colors.BACKGROUND_MEDIUM, "Service offline"),
        SEARCHING(AppConfig.Colors.LOADING_BLUE, "Searching for devices"),
        NO_DEVICES_FOUND(AppConfig.Colors.ALERT_RED, "No devices found"),
        DEVICES_FOUND(AppConfig.Colors.OK_GREEN, "Devices found");

        private final Color color;
        private final String message;

        AppStatus(Color color, String message) {
            this.color = color;
            this.message = message;
        }

        public Color getColor() {
            return color;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum PlayerStatus {
        ONLINE(3),
        CONNECTING(2),
        OFFLINE(1);

        private final int status;

        PlayerStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    private static PlayerState instance;

    private AppStatus appStatus;

    private Map<Integer, DeviceAnnouncement> foundPlayers = new ConcurrentHashMap<>();
    public int foundPlayersVersion = 0;
    @SuppressWarnings("unused")
    private DeviceAnnouncement foundMixer;
    public String foundMixerChanged = "changed";

    private final Map<Integer, DeviceUpdate> deviceUpdates = new ConcurrentHashMap<>();
    public String deviceUpdatesChanged = "changed";
    private final Map<Integer, TrackMetadata> trackUpdates = new ConcurrentHashMap<>();
    public String trackUpdatesChanged = "changed";

    public PlayerState() {

    }

    public void storePlayerUpdate(int playerNumber, DeviceUpdate update) {
        deviceUpdates.put(playerNumber, update);
    }

    public void storeTrackUpdate(int playerNumber, TrackMetadataUpdate update) {
        trackUpdates.put(playerNumber, update.metadata);
    }

    public void storeFoundPlayer(int playerNumber, DeviceAnnouncement device) {
        foundPlayers.put(playerNumber, device);
        foundPlayersVersion++;
    }

    public void storeFoundMixer(DeviceAnnouncement device) {
        foundMixer = device;
    }

    public void setAppStatus(AppStatus status) {
        appStatus = status;
    }

    public AppStatus getAppStatus() {
        return appStatus;
    }

    public Map<Integer, DeviceAnnouncement> getFoundPlayers() {
        return foundPlayers;
    }
    
    public int getFoundPlayersVersion() {
        return foundPlayersVersion;
    }

    public Map<Integer, DeviceUpdate> getDeviceUpdates() {
        return deviceUpdates;
    }

    public Map<Integer, TrackMetadata> getTrackUpdates() {
        return trackUpdates;
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

    public static PlayerState getInstance() {
        if (instance == null) {
            instance = new PlayerState();
        }
        return instance;
    }

}
