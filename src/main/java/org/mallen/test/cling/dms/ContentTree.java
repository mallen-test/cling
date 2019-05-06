package org.mallen.test.cling.dms;

import org.fourthline.cling.support.model.WriteStatus;
import org.fourthline.cling.support.model.container.Container;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

public class ContentTree {
    public final static String CREATOR = "happy family member : mallen";
    public final static String ROOT_ID = "0";
    public final static String VIDEO_ID = "1";
    public final static String AUDIO_ID = "2";
    public final static String IMAGE_ID = "3";
    public final static String IMAGE_FOLD_ID = "4";
    public final static String VIDEO_PREFIX = "video-item-";
    public final static String AUDIO_PREFIX = "audio-item-";
    public final static String IMAGE_PREFIX = "image-item-";

    private static Map<String, ContentNode> contentMap = new HashMap<String, ContentNode>();
    private static ContentNode rootNode = createRootNode();

    protected static ContentNode createRootNode() {
        Container root = new Container();
        root.setId(ROOT_ID);
        root.setParentID("-1");
        root.setTitle("HAPPY FAMILY");
        root.setCreator(CREATOR);
        root.setRestricted(true);
        root.setSearchable(false);
        root.setWriteStatus(WriteStatus.NOT_WRITABLE);
        root.setChildCount(0);
        ContentNode rootNode = new ContentNode(ROOT_ID, root);

        return rootNode;
    }

    public static ContentNode getRootNode() {
        return rootNode;
    }

    public static ContentNode getNode(String id) {
        return contentMap.get(id);
    }

    public static boolean hasNode(String id) {return contentMap.containsKey(id);}

    public static void addNode(String id, ContentNode node) {contentMap.put(id, node);}
}
