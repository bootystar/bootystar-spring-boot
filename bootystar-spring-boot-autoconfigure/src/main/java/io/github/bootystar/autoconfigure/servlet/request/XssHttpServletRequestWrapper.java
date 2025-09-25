package io.github.bootystar.autoconfigure.servlet.request;

import io.github.bootystar.autoconfigure.servlet.utils.html.EscapeUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * XSS过滤处理
 * <p>
 * <b>警告:</b> 这个实现会将整个JSON请求体读入内存进行XSS清理。
 * 这可能导致应用程序在处理大型请求时出现内存溢出 (OutOfMemoryError) 错误。
 * 强烈建议在生产环境中配置请求体大小限制 (例如, spring.servlet.multipart.max-request-size)。
 * </p>
 *
 * @author ruoyi
 * @author bootystar
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            String[] escapedValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                escapedValues[i] = EscapeUtil.clean(values[i]).trim();
            }
            return escapedValues;
        }
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 只处理JSON请求
        if (!isJsonRequest()) {
            return super.getInputStream();
        }

        byte[] originalBody = IOUtils.toByteArray(super.getInputStream());

        // 如果请求体为空，则返回一个空的输入流
        if (originalBody == null || originalBody.length == 0) {
            return new EmptyServletInputStream();
        }

        // 对请求体进行XSS清理
        String json = new String(originalBody, StandardCharsets.UTF_8);
        String cleanedJson = EscapeUtil.clean(json).trim();
        byte[] cleanedBody = cleanedJson.getBytes(StandardCharsets.UTF_8);

        final ByteArrayInputStream bis = new ByteArrayInputStream(cleanedBody);

        return new ServletInputStream() {
            @Override
            public int read() {
                return bis.read();
            }

            @Override
            public boolean isFinished() {
                return bis.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // Not implemented
            }
        };
    }

    private boolean isJsonRequest() {
        String contentType = getHeader(HttpHeaders.CONTENT_TYPE);
        return contentType != null && contentType.toLowerCase().startsWith(MediaType.APPLICATION_JSON_VALUE);
    }

    private static class EmptyServletInputStream extends ServletInputStream {
        @Override
        public int read() {
            return -1; // End of stream
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            // Not implemented
        }
    }
}