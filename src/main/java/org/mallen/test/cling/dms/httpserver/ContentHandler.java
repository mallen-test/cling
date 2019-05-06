package org.mallen.test.cling.dms.httpserver;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.mallen.test.cling.dms.ContentNode;
import org.mallen.test.cling.dms.ContentTree;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;

/**
 * 用于拦截http请求，将uri后面跟的id翻译成对应资源文件的路径，最终将文件输出到response
 * TODO mallen 实现断定续传
 * Created By mallen On 2018-04-02 23:57
 */
public class ContentHandler extends HandlerWrapper {
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (baseRequest.isHandled())
            return;
//        if (!HttpMethod.GET.is(request.getMethod()) || !HttpMethod.HEAD.is(request.getMethod())) {
//            super.handle(target, baseRequest, request, response);
//            return;
//        }
        Enumeration enumeration =request.getHeaders(HttpHeader.RANGE.asString());
        while (enumeration.hasMoreElements()) {
            System.out.print("r:" + enumeration.nextElement() + ";");
        }
        System.out.println();

        // 仅处理uri
        String uri = baseRequest.getRequestURI().replaceFirst("/", "");
        if (ContentTree.getNode(uri) != null) {
            OutputStream os = response.getOutputStream();
            ContentNode node = ContentTree.getNode(uri);
            if (node.isItem() && node.getFullPath() != null && node.getFullPath().length() > 0) {
                Files.copy(Paths.get(ContentTree.getNode(uri).getFullPath()), os);
            }else {
                writeError(os, "资源不存在");
            }


            os.close();
            baseRequest.setHandled(true);
        } else {
            super.handle(target, baseRequest, request, response);
        }
    }

    private void writeError(OutputStream os, String msg) throws IOException {
        byte[] bytes = msg.getBytes("UTF-8");
        os.write(bytes, 0, bytes.length);
    }
}
