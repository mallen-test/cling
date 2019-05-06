package org.mallen.test.cling.dms;

import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.WriteStatus;
import org.fourthline.cling.support.model.container.Container;
import org.fourthline.cling.support.model.item.VideoItem;
import org.mallen.test.cling.utils.mediainfo.MediaInfo;
import org.seamless.util.MimeType;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Created By mallen on 2018/4/8 21:04
 **/
public class StartDms {
    private static final String path = "E://t";

    public static void main(String[] args) throws ValidationException {
        prepareMedia();
        startMediaServer();
    }

    private static void startMediaServer() throws ValidationException {
        new MediaServer();
    }

    private static void prepareMedia() {
        ContentNode rootNode = ContentTree.getRootNode();
        Container videoContainer = new Container();
        videoContainer.setClazz(new DIDLObject.Class("object.container"));
        videoContainer.setId(ContentTree.VIDEO_ID);
        videoContainer.setParentID(rootNode.getId());
        videoContainer.setTitle("Videos");
        videoContainer.setRestricted(true);
        videoContainer.setWriteStatus(WriteStatus.NOT_WRITABLE);
        videoContainer.setChildCount(0);

        rootNode.getContainer().addContainer(videoContainer);
        rootNode.getContainer().setChildCount(rootNode.getContainer().getChildCount() + 1);
        ContentTree.addNode(ContentTree.VIDEO_ID, new ContentNode(ContentTree.VIDEO_ID, videoContainer));

        List<File> videos = getVideos(path);
        for (int i = 1; i <= videos.size(); i++) {
           VideoItem item = buildVideoItem(i, videos.get(i - 1));
           videoContainer.addItem(item);
           videoContainer.setChildCount(videoContainer.getChildCount() + 1);
           ContentTree.addNode(item.getId(), new ContentNode(item.getId(), item, videos.get(i - 1).getPath()));
        }
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
                .indexOf('/') + 1)), size, "http://" + hostAddress() + MediaServer.PORT);

        res.setDuration(duration / (1000 * 60 * 60) + ":"
                + (duration % (1000 * 60 * 60)) / (1000 * 60) + ":"
                + (duration % (1000 * 60)) / 1000);
        res.setResolution(resolution);
        VideoItem videoItem = new VideoItem(id, ContentTree.VIDEO_ID, title, creator, res);
        videoItem.setDescription(desc);

        return videoItem;
    }

    private static List<File> getVideos(String path) {
        File folder = new File(path);
        if (null == folder)
            throw new RuntimeException("文件夹不存在:" + path);
        if (!folder.isDirectory())
            throw new RuntimeException("路径:" + path + "不是一个文件夹");
        return Arrays.asList(folder.listFiles());
    }

    private static String hostAddress() {
        return getLocalHostLANAddress().getHostAddress();
    }

    private static InetAddress getLocalHostLANAddress() {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements(); ) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements(); ) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            if (inetAddr.getHostAddress().matches("192.168.31.*"))
                                return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
