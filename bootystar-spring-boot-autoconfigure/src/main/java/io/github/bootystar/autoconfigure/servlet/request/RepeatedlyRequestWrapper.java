package io.github.bootystar.autoconfigure.servlet.request;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 构建可重复读取inputStream的request
 * <p>
 * <b>警告:</b> 这个实现会将整个请求体读入内存。
 * 这可能导致应用程序在处理大型请求时出现内存溢出 (OutOfMemoryError) 错误。
 * 强烈建议在生产环境中配置请求体大小限制 (例如, spring.servlet.multipart.max-request-size)。
 * </p>
 *
 * @author ruoyi
 * @author bootystar
 */
public class RepeatedlyRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public RepeatedlyRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 从输入流中读取请求体并存储在字节数组中
        this.body = IOUtils.toByteArray(request.getInputStream());
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream bis = new ByteArrayInputStream(this.body);
        return new ServletInputStream() {
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

            @Override
            public int read() {
                return bis.read();
            }
        };
    }
}