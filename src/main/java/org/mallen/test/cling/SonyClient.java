package org.mallen.test.cling;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.message.header.DeviceTypeHeader;
import org.fourthline.cling.model.message.header.ServiceTypeHeader;
import org.fourthline.cling.model.message.header.UDAServiceTypeHeader;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.*;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;
import org.fourthline.cling.support.avtransport.callback.Play;
import org.fourthline.cling.support.avtransport.callback.SetAVTransportURI;
import org.fourthline.cling.support.avtransport.callback.Stop;
import org.fourthline.cling.support.contentdirectory.DIDLParser;
import org.fourthline.cling.support.model.DIDLContent;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.ProtocolInfo;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.item.Item;
import org.fourthline.cling.support.model.item.VideoItem;
import org.mallen.test.cling.dms.ContentTree;
import org.mallen.test.cling.dms.MediaServer;
import org.mallen.test.cling.utils.mediainfo.MediaInfo;
import org.seamless.util.MimeType;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created By mallen on 2018/4/10 22:45
 **/
public class SonyClient implements Runnable {
    private static boolean executed = false;

    public static void main(String[] args) {
        // Optionally remove existing handlers attached to j.u.l root logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)// mallen:jul在使用时如果不在程序中配置，则或默认读取JAVA_HOME/jre/lib下的配置文件logging.properties。classpath下的logging.properties不会生效

        // add SLF4JBridgeHandler to j.u.l's root logger, should be done once during
        // the initialization phase of your application
        SLF4JBridgeHandler.install();

        Thread thread = new Thread(new SonyClient());
        thread.setDaemon(false);
        thread.start();
    }

    @Override
    public void run() {
        final UpnpService service = new UpnpServiceImpl();
        service.getRegistry().addListener(createListener(service));

//        UDADeviceType deviceType = new UDADeviceType("MediaRenderer");
        UDAServiceType serviceType = new UDAServiceType("AVTransport");
        service.getControlPoint().search(new UDAServiceTypeHeader(serviceType));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                service.shutdown();
            }
        });
    }

    private RegistryListener createListener(final UpnpService service) {
        final Item item = buildVideoItem(1, new File("E:\\t\\1.Android 5.0 新特性.mp4"));
        return new DefaultRegistryListener() {
            ServiceId serviceId = new UDAServiceId("AVTransport");

            @Override
            public void deviceAdded(Registry registry, Device device) {
                Service avservice;
                String fiendlyName = "KD-65X9000E";
                if (device.getDetails().getFriendlyName().equals(fiendlyName)) {
                    if ((avservice = device.findService(serviceId)) != null)
                        executeAction(service, avservice);
                }
            }

            @Override
            public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
                super.remoteDeviceUpdated(registry, device);

            }

            private void executeAction(final UpnpService service, final Service avservice) {
                if (executed) return;
//                ActionCallback stopAction = new Stop(avservice) {
//                    @Override
//                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
//                        System.out.println("stop出错啦！！！！" + defaultMsg);
//                        DIDLParser parser = new DIDLParser();
//                        DIDLContent content = new DIDLContent();
//                        content.addItem(item);
//                        String md = "";
//                        try {
//                            md = parser.generate(content);
//                            System.out.println("md ==== " + md);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
////                        String md = createItemMetadata(item);
////                        System.out.println("md ==== " + md);
//                        String videoURL = "http://192.168.31.32:8192/video-item-1.mp4";
//                        ActionCallback setAVTransportURIAction =
//                                new SetAVTransportURI(avservice, videoURL,
//                                        md) {
//                                    @Override
//                                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
//                                        System.out.println("set uri出错啦！！！！\n" + defaultMsg);
//                                    }
//
//                                    @Override
//                                    public void success(ActionInvocation invocation) {
//                                        super.success(invocation);
//                                    }
//                                };
//                        service.getControlPoint().execute(setAVTransportURIAction);
//                    }
//
//                    @Override
//                    public void success(ActionInvocation invocation) {
//                        super.success(invocation);
//                        DIDLParser parser = new DIDLParser();
//                        DIDLContent content = new DIDLContent();
//                        content.addItem(item);
//                        String md = "";
//                        try {
//                            md = parser.generate(content);
//                            System.out.println("md ==== " + md);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
////                        String md = createItemMetadata(item);
////                        System.out.println("md ==== " + md);
//                        String videoURL = "http://192.168.31.32:8192/video-item-1.mp4";
//                        ActionCallback setAVTransportURIAction =
//                                new SetAVTransportURI(avservice, videoURL,
//                                        md) {
//                                    @Override
//                                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
//                                        System.out.println("set uri出错啦！！！！\n" + defaultMsg);
//                                    }
//
//                                    @Override
//                                    public void success(ActionInvocation invocation) {
//                                        super.success(invocation);
//                                    }
//                                };
//                        service.getControlPoint().execute(setAVTransportURIAction);
//                    }
//                };
//                service.getControlPoint().execute(stopAction);

                DIDLParser parser = new DIDLParser();
                DIDLContent content = new DIDLContent();
                content.addItem(item);
                String md = "";
                try {
                    md = parser.generate(content);
                    System.out.println("md ==== " + md);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                        String md = createItemMetadata(item);
//                        System.out.println("md ==== " + md);
                String videoURL = "http://192.168.31.32:8192/video-item-1.mp4";
                ActionCallback setAVTransportURIAction =
                        new SetAVTransportURI(avservice, videoURL,
                                md) {
                            @Override
                            public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                                System.out.println("set uri出错啦！！！！\n" + defaultMsg);
                            }

                            @Override
                            public void success(ActionInvocation invocation) {
                                super.success(invocation);
                                ActionCallback playAction = new Play(avservice) {
                                    @Override
                                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                                        System.out.println("plat出错啦！！！！\n" + defaultMsg);
                                    }
                                };
                                service.getControlPoint().execute(playAction);
                            }
                        };
                service.getControlPoint().execute(setAVTransportURIAction);


                executed = true;
            }

            @Override
            public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
                super.remoteDeviceRemoved(registry, device);
            }
        };
    }

    private static VideoItem buildVideoItem(int index, File video) {
        String id = ContentTree.VIDEO_PREFIX + index + ".mp4";
        String filePath = video.getPath();
        MediaInfo mediaInfo = MediaInfo.build(filePath);
        String title = mediaInfo.getFileName();
        String creator = mediaInfo.getCreator();
        String mimeType = mediaInfo.getMimeType();
        long size = mediaInfo.getSize();
        long duration = mediaInfo.getDuration();
        String resolution = mediaInfo.getWidthHeight();
        String desc = mediaInfo.getDescription();
        Res res = new Res(new MimeType(mimeType.substring(0,
                mimeType.indexOf('/')), mimeType.substring(mimeType
                .indexOf('/') + 1)), size, "http://192.168.31.32:8192/video-item-1.mp4");

        res.setDuration(duration / (1000 * 60 * 60) + ":"
                + (duration % (1000 * 60 * 60)) / (1000 * 60) + ":"
                + (duration % (1000 * 60)) / 1000);
        res.setResolution(resolution);
        VideoItem videoItem = new VideoItem(id, ContentTree.VIDEO_ID, title, creator, res);
        videoItem.setDescription(desc);

        return videoItem;
    }

    public static final String DIDL_LITE_FOOTER = "</DIDL-Lite>";
    public static final String DIDL_LITE_HEADER = "<?xml version=\"1.0\"?>"
            + "<DIDL-Lite "
            + "xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\" "
            + "xmlns:dc=\"http://purl.org/dc/elements/1.1/\" "
            + "xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\" "
            + "xmlns:dlna=\"urn:schemas-dlna-org:metadata-1-0/\">";

    public String createItemMetadata(DIDLObject item) {
        StringBuilder metadata = new StringBuilder();
        metadata.append(DIDL_LITE_HEADER);

        metadata.append(String.format(
                "<item id=\"%s\" parentID=\"%s\" restricted=\"%s\">", item
                        .getId(), item.getParentID(), item.isRestricted() ? "1"
                        : "0"));

        metadata.append(String.format("<dc:title>%s</dc:title>",
                item.getTitle()));
        String creator = item.getCreator();
        if (creator != null) {
            creator = creator.replaceAll("<", "_");
            creator = creator.replaceAll(">", "_");
        }
        metadata.append(String.format("<upnp:artist>%s</upnp:artist>", creator));

        metadata.append(String.format("<upnp:class>%s</upnp:class>", item
                .getClazz().getValue()));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date now = new Date();
        String time = sdf.format(now);
        metadata.append(String.format("<dc:date>%s</dc:date>", time));

        // metadata.append(String.format("<upnp:album>%s</upnp:album>",
        // item.get);

        // <res protocolInfo="http-get:*:audio/mpeg:*"
        // resolution="640x478">http://192.168.1.104:8088/Music/07.我醒著做夢.mp3</res>

        Res res = item.getFirstResource();
        if (res != null) {
            // protocol info
            String protocolinfo = "";
            ProtocolInfo pi = res.getProtocolInfo();
            if (pi != null) {
                protocolinfo = String.format("protocolInfo=\"%s:%s:%s:%s\"",
                        pi.getProtocol(), pi.getNetwork(),
                        pi.getContentFormatMimeType(), pi.getAdditionalInfo());
            }

            // resolution, extra info, not adding yet
            String resolution = "";
            if (res.getResolution() != null && res.getResolution().length() > 0) {
                resolution = String.format("resolution=\"%s\"",
                        res.getResolution());
            }

            // duration
            String duration = "";
            if (res.getDuration() != null && res.getDuration().length() > 0) {
                duration = String.format("duration=\"%s\"", res.getDuration());
            }

            // res begin
            //            metadata.append(String.format("<res %s>", protocolinfo)); // no resolution & duration yet
            metadata.append(String.format("<res %s %s %s>", protocolinfo, resolution, duration));

            // url
            String url = res.getValue();
            metadata.append(url);

            // res end
            metadata.append("</res>");
        }
        metadata.append("</item>");

        metadata.append(DIDL_LITE_FOOTER);

        return metadata.toString();
    }
}
