package com.morizkraemer.state;

import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.deepsymmetry.beatlink.DeviceAnnouncement;
import org.deepsymmetry.beatlink.DeviceUpdate;
import org.deepsymmetry.beatlink.data.TrackMetadata;
import org.deepsymmetry.beatlink.data.TrackMetadataUpdate;

import com.morizkraemer.AppConfig;

public class PlayerState {
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
    }

    private static PlayerState instance;

    private AppStatus appStatus;

    private Map<Integer, DeviceAnnouncement> foundPlayers = new ConcurrentHashMap<>();
    public Boolean foundPlayersChanged = false;
    @SuppressWarnings("unused")
    private DeviceAnnouncement foundMixer;
    public Boolean foundMixerChanged = false;


    private final Map<Integer, DeviceUpdate> deviceUpdates = new ConcurrentHashMap<>();
    public Boolean deviceUpdatesChanged = false;
    private final Map<Integer, TrackMetadata> trackUpdates = new ConcurrentHashMap<>();
    public Boolean trackUpdatesChanged = false;

    public PlayerState() {

    }

    public void storePlayerUpdate(int playerNumber, DeviceUpdate update) {
        deviceUpdates.put(playerNumber, update);
        deviceUpdatesChanged = true;
    }

    public void storeTrackUpdate(int playerNumber, TrackMetadataUpdate update) {
        trackUpdates.put(playerNumber, update.metadata);
        trackUpdatesChanged = true;
    }

    public void storeFoundPlayer(int playerNumber, DeviceAnnouncement device) {
        foundPlayers.put(playerNumber, device);
        foundPlayersChanged = true;
    }

    public void setAppStatus(AppStatus status) {
        appStatus = status;
    }

    public AppStatus getAppStatus() {
        return appStatus;
    }

    public void storeFoundMixer(DeviceAnnouncement device) {
        foundMixer = device;
        foundMixerChanged = true;
    }
    
    public Map<Integer, DeviceAnnouncement> getFoundPlayers() {
        foundPlayersChanged = false;
        return foundPlayers;
    }

    public Map<Integer, DeviceUpdate> getDeviceUpdates() {
        deviceUpdatesChanged = false;
        return deviceUpdates;
    }

    public Map<Integer, TrackMetadata> getTrackUpdates() {
        trackUpdatesChanged = false;
        return trackUpdates;
    }

    public Map<Integer, DeviceUpdate> getAllDeviceUpdates() {
        deviceUpdatesChanged = false;
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
