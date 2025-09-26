package io.github.bootystar.autoconfigure.servlet.filter;

import io.github.bootystar.autoconfigure.servlet.request.CachedBodyRequestWrapper;
import io.github.bootystar.autoconfigure.servlet.request.XssRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author bootystar
 */
public class XssAndRepeatableFilter implements Filter {
    @Setter
    private Function<String, String> func = s -> s;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        String ct = Optional.ofNullable(request.getContentType()).orElse("");
        boolean bodyRewritable = ct.contains("application/json")
                || ct.contains("text/plain")
                || ct.contains("xml");
        byte[] bodyBytes = null;
        if (bodyRewritable) {
            // 读原始体
            bodyBytes = readAll(request.getInputStream());
            String enc = Optional.ofNullable(request.getCharacterEncoding())
                    .orElse(StandardCharsets.UTF_8.name());
            String body = new String(bodyBytes, enc);
            String sanitized = sanitizeBody(body, ct); // 实现你自己的 JSON/XML 安全净化
            bodyBytes = sanitized.getBytes(enc);
        }

        HttpServletRequest wrapped =
                bodyBytes != null
                        ? new CachedBodyRequestWrapper(request, bodyBytes)
                        : new CachedBodyRequestWrapper(request); // 仅缓存，不修改

        XssRequestWrapper xssWrapper = new XssRequestWrapper(wrapped, func);

        chain.doFilter(xssWrapper, res);
    }

    private static byte[] readAll(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int n;
        while ((n = is.read(buf)) != -1) bos.write(buf, 0, n);
        return bos.toByteArray();
    }

    private String sanitizeBody(String body, String contentType) {
        // 注意：不要破坏 JSON 结构。可以仅转义字符串字段中的危险字符，或使用 JSON-aware 的转义。
        return func.apply(body);
    }
}
