package org.mallen.test.cling.service;

import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;

public class SwitchPowerDevice {
    private static volatile LocalDevice device;

    public static LocalDevice getInstance() {
        if (device == null) {
            synchronized (SwitchPowerDevice.class) {
                if (device == null) {
                    try {
                        device = new SwitchPowerDevice().createDevice();
                    } catch (ValidationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return device;
    }

    private LocalDevice createDevice() throws ValidationException {
        DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier("Binary Light Demo"));

        DeviceType deviceType = new UDADeviceType("BinaryLight", 1);

        DeviceDetails deviceDetails = new DeviceDetails("Friendly Binary Light",
                new ManufacturerDetails("mallen company"),
                new ModelDetails("Binary Light001",
                        "第一个Binary Light测试版本", "v0.0.1"));
//        icon图标可选
//        Icon icon =
//                new Icon(
//                        "image/png", 48, 48, 8,
//                        getClass().getResource("icon.png")
//                );
        LocalService<SwitchPower> switchPowerLocalService = new AnnotationLocalServiceBinder().read(SwitchPower.class);
        switchPowerLocalService.setManager(new DefaultServiceManager<SwitchPower>(switchPowerLocalService, SwitchPower.class));

        return new LocalDevice(identity, deviceType, deviceDetails, switchPowerLocalService);
        /* Several services can be bound to the same device:
        return new LocalDevice(
                identity, type, details, icon,
                new LocalService[] {switchPowerService, myOtherService}
        );
        */
    }
}
