package org.mallen.test.cling;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.mallen.test.cling.service.SwitchPowerDevice;

public class BinaryLightServer implements Runnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new BinaryLightServer());
        thread.setDaemon(false);
        thread.start();
    }

    public void run() {
        final UpnpService upnpService = new UpnpServiceImpl();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                upnpService.shutdown();
            }
        });

        upnpService.getRegistry().addDevice(SwitchPowerDevice.getInstance());
    }
}
