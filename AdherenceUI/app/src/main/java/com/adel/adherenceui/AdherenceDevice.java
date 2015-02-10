package com.adel.adherenceui;

import java.util.Date;

public class AdherenceDevice {

    private String deviceName;
    private Date expectedNextRefill;
    private Date lastRefilled;
    private Date lastConnected;

    //empty constructor
    public  AdherenceDevice(){}

    public AdherenceDevice(String _deviceName,
                           Date _expectedNextRefill,
                           Date _lastRefilled,
                           Date _lastConnected )  {
        deviceName = _deviceName;
        expectedNextRefill = _expectedNextRefill;
        lastConnected = _lastConnected;
        lastRefilled = _lastRefilled;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setExpectedNextRefill(Date expectedNextRefill) {
        this.expectedNextRefill = expectedNextRefill;
    }

    public void setLastRefilled(Date lastRefilled) {
        this.lastRefilled = lastRefilled;
    }

    public void setLastConnected(Date lastConnected) {
        this.lastConnected = lastConnected;
    }

    public Date getLastConnected() {

        return lastConnected;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public Date getExpectedNextRefill() {
        return expectedNextRefill;
    }

    public Date getLastRefilled() {
        return lastRefilled;
    }
}
