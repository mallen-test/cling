package org.mallen.test.cling.dms;

import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;
import org.mallen.test.cling.dms.httpserver.JettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created By mallen on 2018-04-02 23:30
 */
public class MediaServer {
    private static final Logger logger = LoggerFactory.getLogger(MediaServer.class);

    private final static String deviceType = "MediaServer";
    private final static int version = 1;
    private final static String modelNumer = "0.0.1-SNAPSHOT";
    private final static String udnSalt = "MallenDMS";
    public final static int PORT = 8192;
    private UDN udn;
    private LocalDevice localDevice;

    public MediaServer() throws ValidationException {
        DeviceType type = new UDADeviceType(deviceType, version);

        DeviceDetails details = new DeviceDetails(
               "mallen media server", new ManufacturerDetails("Mallen Huang"),
               new ModelDetails("smart media server", "第一版本的媒体服务器", modelNumer)
        );
        LocalService service = new AnnotationLocalServiceBinder().read(ContentDirectoryService.class);
        service.setManager(new DefaultServiceManager(service, ContentDirectoryService.class));

        udn = UDN.uniqueSystemIdentifier(udnSalt);

        localDevice = new LocalDevice(new DeviceIdentity(udn), type, details, service);
        // 启动http服务器，开始接收资源获取请求
        try {
            JettyServer server = new JettyServer(PORT);
            logger.info("成功在端口{}启动Http服务器", PORT);
        }catch (Exception ex) {
            logger.error("启动HTTP服务器失败：\n", ex);
        }
    }
}
