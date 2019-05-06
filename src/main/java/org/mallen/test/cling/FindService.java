package org.mallen.test.cling;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.model.message.header.DeviceTypeHeader;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by mallen on 3/23/18.
 */
public class FindService {
    public static void main(String[] args) throws InterruptedException {
        RegistryListener listener = new RegistryListener() {
            public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice remoteDevice) {
                System.out.println("Discovery started:" + remoteDevice.getDetails().getFriendlyName()  + "===" + remoteDevice.getType().getType());
            }

            public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice remoteDevice, Exception e) {
                System.out.println("Discovery failed: " + remoteDevice.getDetails().getFriendlyName() + "=>" + e);
            }

            public void remoteDeviceAdded(Registry registry, RemoteDevice remoteDevice) {
                System.out.println("Remote device available:" + remoteDevice.getDetails().getFriendlyName() + "===" + remoteDevice.getType().getType());
                if (remoteDevice.getDetails().getFriendlyName().indexOf("KD-65X9000E") != -1)
                    System.out.println(remoteDevice);
            }

            public void remoteDeviceUpdated(Registry registry, RemoteDevice remoteDevice) {
                System.out.println("Remote device updated:" + remoteDevice.getDisplayString());
            }

            public void remoteDeviceRemoved(Registry registry, RemoteDevice remoteDevice) {
                System.out.println("Remote device removed:" + remoteDevice.getDisplayString());
            }

            public void localDeviceAdded(Registry registry, LocalDevice localDevice) {
                System.out.println("Local device added:" + localDevice.getDisplayString());
            }

            public void localDeviceRemoved(Registry registry, LocalDevice localDevice) {
                System.out.println("Remote device removed:" + localDevice.getDisplayString());
            }

            public void beforeShutdown(Registry registry) {
                System.out.println("Before shutdown. The registry has devices: " + registry.getDevices().size());
            }

            public void afterShutdown() {
                System.out.println("Shutdown of registry complete!");
            }
        };

        System.out.println("Starting Cling");
        UpnpService service = new UpnpServiceImpl(listener);

        UDADeviceType deviceType = new UDADeviceType("MediaRenderer");
        service.getControlPoint().search(new DeviceTypeHeader(deviceType));
//        service.getControlPoint().search(new STAllHeader());
        System.out.println("Waiting 10 seconds before shutdown");
        TimeUnit.SECONDS.sleep(1000);

        service.shutdown();
    }
}
