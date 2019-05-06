package org.mallen.test.cling.dms;

import org.fourthline.cling.support.contentdirectory.AbstractContentDirectoryService;
import org.fourthline.cling.support.contentdirectory.ContentDirectoryErrorCode;
import org.fourthline.cling.support.contentdirectory.ContentDirectoryException;
import org.fourthline.cling.support.contentdirectory.DIDLParser;
import org.fourthline.cling.support.model.BrowseFlag;
import org.fourthline.cling.support.model.BrowseResult;
import org.fourthline.cling.support.model.DIDLContent;
import org.fourthline.cling.support.model.SortCriterion;
import org.fourthline.cling.support.model.container.Container;
import org.fourthline.cling.support.model.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentDirectoryService extends AbstractContentDirectoryService {
    private static final Logger logger = LoggerFactory.getLogger(ContentDirectoryService.class);

    public BrowseResult browse(String objectID, BrowseFlag browseFlag,
                               String filter, long firstResult, long maxResults,
                               SortCriterion[] orderBy) throws ContentDirectoryException {
        try {
            DIDLContent didlContent = new DIDLContent();

            ContentNode contentNode = ContentTree.getNode(objectID);
            if (null == contentNode) {
                logger.info("资源id不存在:{}", objectID);
                return new BrowseResult("", 0, 0);
            }
            if (contentNode.isItem()) {
                didlContent.addItem(contentNode.getItem());
                logger.info("找到了id:{}，对应的资源:{}", objectID, contentNode.getItem().getTitle());

                return new BrowseResult(new DIDLParser().generate(didlContent), 1, 1);
            } else {
                if (browseFlag == BrowseFlag.METADATA) {
                    didlContent.addContainer(contentNode.getContainer());
                    logger.info("找到了id:{},对应的METADATA:{}", objectID, contentNode.getContainer().getTitle());

                    return new BrowseResult(new DIDLParser().generate(didlContent), 1, 1);
                }
                // 查找容器子层信息
                for (Container container : contentNode.getContainer().getContainers()) {
                    didlContent.addContainer(container);
                }
                for (Item item : contentNode.getContainer().getItems()) {
                    didlContent.addItem(item);
                }

                return new BrowseResult(new DIDLParser().generate(didlContent),
                        contentNode.getContainer().getChildCount(),
                        contentNode.getContainer().getChildCount());
            }
        } catch (Exception e) {
            logger.error("browse资源出错:\n", e);
            throw new ContentDirectoryException(ContentDirectoryErrorCode.CANNOT_PROCESS, e.toString());
        }
    }
}