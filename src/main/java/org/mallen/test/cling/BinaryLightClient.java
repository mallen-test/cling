package org.mallen.test.cling;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.model.types.ServiceId;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class BinaryLightClient implements Runnable {
    public static void main(String[] args) {
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)// mallen:jul在使用时如果不在程序中配置，则或默认读取JAVA_HOME/jre/lib下的配置文件logging.properties。classpath下的logging.properties不会生效

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();

        Thread thread = new Thread(new BinaryLightClient());
        thread.setDaemon(false);
        thread.start();
    }
    public void run() {
        final UpnpService service = new UpnpServiceImpl();
        service.getRegistry().addListener(createListener(service));
        // Broadcast a search message for all devices
        service.getControlPoint().search(
                new STAllHeader()
        );
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                service.shutdown();
            }
        });
    }

    private RegistryListener createListener(final UpnpService service) {
        return new DefaultRegistryListener() {
            ServiceId serviceId = new UDAServiceId("SwitchPower");
//            ServiceId serviceId = new UDAServiceId("Layer3Forwarding1");// urn:upnp-org:serviceId:
            @Override
            public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
                super.remoteDeviceAdded(registry, device);
                Service switchPower;
                if ((switchPower = device.findService(serviceId)) != null) {
                    System.out.println("Service discovered: " + switchPower);
                    executeAction(service, switchPower);
                }
            }

            @Override
            public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
                Service switchPower;
                if ((switchPower = device.findService(serviceId)) != null) {
                    System.out.println("Service disappeared: " + switchPower);
                }
            }
        };


    }

    private void executeAction(UpnpService service, Service switchPower) {
        ActionInvocation setTargetInvocation =
                new SetTargetActionInvocation(switchPower);
        // Executes asynchronous in the background
        service.getControlPoint().execute(
                new ActionCallback(setTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation,
                                        UpnpResponse operation,
                                        String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );

    }

    class SetTargetActionInvocation extends ActionInvocation {

        SetTargetActionInvocation(Service service) {
            super(service.getAction("SetTarget"));
            try {

                // Throws InvalidValueException if the value is of wrong type
                setInput("NewTargetValue", true);

            } catch (InvalidValueException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }
    }
}
