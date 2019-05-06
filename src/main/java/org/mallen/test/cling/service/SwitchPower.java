package org.mallen.test.cling.service;

import org.fourthline.cling.binding.annotations.*;

@UpnpService(
        serviceId = @UpnpServiceId("SwitchPower"),
        serviceType = @UpnpServiceType(value = "SwitchPower", version = 1)
)
public class SwitchPower {
    @UpnpStateVariable(defaultValue = "0", sendEvents = false)
    private boolean target;
    @UpnpStateVariable(defaultValue = "0")
    private boolean status;

    @UpnpAction
    public void setTarget(@UpnpInputArgument(name = "newTargetValue") boolean newTargetValue) {
        target = newTargetValue;
        status = newTargetValue;
        System.out.println("Switch is : " + status);
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "RetTargetValue"))
    public boolean getTarget() {
        return target;
    }

    @UpnpAction(out = @UpnpOutputArgument(name = "ResultStatus"))
    public boolean getStatus() {
        return status;
    }
}
