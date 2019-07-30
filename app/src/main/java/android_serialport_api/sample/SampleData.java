package android_serialport_api.sample;

import java.io.Serializable;

public class SampleData implements Serializable {
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getGPSLocation() {
        return GPSLocation;
    }

    public void setGPSLocation(String GPSLocation) {
        this.GPSLocation = GPSLocation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getTiSerialNumber() {
        return tiSerialNumber;
    }

    public void setTiSerialNumber(String tiSerialNumber) {
        this.tiSerialNumber = tiSerialNumber;
    }

    public String getSiSerialNumber() {
        return siSerialNumber;
    }

    public void setSiSerialNumber(String siSerialNumber) {
        this.siSerialNumber = siSerialNumber;
    }

    public String getTiPoint() {
        return tiPoint;
    }

    public void setTiPoint(String tiPoint) {
        this.tiPoint = tiPoint;
    }

    public String getSiPoint() {
        return siPoint;
    }

    public void setSiPoint(String siPoint) {
        this.siPoint = siPoint;
    }

    public String getDetectorTemperature() {
        return detectorTemperature;
    }

    public void setDetectorTemperature(String detectorTemperature) {
        this.detectorTemperature = detectorTemperature;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getSampleLocation() {
        return sampleLocation;
    }

    public void setSampleLocation(String sampleLocation) {
        this.sampleLocation = sampleLocation;
    }

    public String getTargetChineseName() {
        return targetChineseName;
    }

    public void setTargetChineseName(String targetChineseName) {
        this.targetChineseName = targetChineseName;
    }

    public String getTargetEnglishName() {
        return targetEnglishName;
    }

    public void setTargetEnglishName(String targetEnglishName) {
        this.targetEnglishName = targetEnglishName;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getTargetPurity() {
        return targetPurity;
    }

    public void setTargetPurity(String targetPurity) {
        this.targetPurity = targetPurity;
    }

    public String getRowTi() {
        return rowTi;
    }

    public void setRowTi(String rowTi) {
        this.rowTi = rowTi;
    }

    public String getRowSi() {
        return rowSi;
    }

    public void setRowSi(String rowSi) {
        this.rowSi = rowSi;
    }

    public String getInterpolationTiSi() {
        return interpolationTiSi;
    }

    public void setInterpolationTiSi(String interpolationTiSi) {
        this.interpolationTiSi = interpolationTiSi;
    }

    private String userID;
    private String deviceCode;
    private String nationalCode;
    private String modelName;
    private String GPSLocation;
    private String address;
    private String softwareVersion;
    private String firmwareVersion;
    private String tiSerialNumber;
    private String siSerialNumber;
    private String tiPoint;
    private String siPoint;
    private String detectorTemperature;
    private String temperature;
    private String humidity;
    private String sampleLocation;
    private String targetChineseName;
    private String targetEnglishName;
    private String commonName;
    private String targetPurity;
    private String rowTi;
    private String rowSi;
    private String interpolationTiSi;
}


