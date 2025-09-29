package io.github.bootystar.autoconfigure.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Servlet工具类
 * <p>
 * 该工具类封装了常用的request和response操作，旨在隔离javax和jakarta的包名冲突，
 * 以便当前项目在不同Servlet API版本间切换时，只需修改此类即可。
 * <p>
 * 提供了对Servlet API的便捷访问和封装，包括:
 * <ul>
 *   <li>获取当前请求相关的对象，如HttpServletRequest、HttpServletResponse等</li>
 *   <li>获取请求参数、请求头、URL等信息，方法名以request开头</li>
 *   <li>常用的响应操作方法，简化Web开发中的响应处理，方法名以response开头</li>
 * </ul>
 *
 * @author bootystar
 */
public class ServletUtils {

    /**
     * 获取当前请求的属性对象
     *
     * @return ServletRequestAttributes对象
     */
    public static ServletRequestAttributes getServletRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取当前请求的ServletContext对象
     *
     * @return ServletContext对象
     */
    public static ServletContext getServletContext() {
        return getServletRequestAttributes().getRequest().getServletContext();
    }

    /**
     * 获取Web应用程序的上下文路径
     *
     * @return 上下文路径
     */
    public static String getServletContextPath() {
        return getServletContext().getContextPath();
    }

    /**
     * 获取当前请求的HttpServletRequest对象
     *
     * @return HttpServletRequest对象
     */
    public static HttpServletRequest getRequest() {
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取当前请求的HttpServletResponse对象
     *
     * @return HttpServletResponse对象
     */
    public static HttpServletResponse getResponse() {
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 获取当前请求的HttpSession对象
     *
     * @return HttpSession对象
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 返回此请求的URL的URI路径部分，该部分在authority(如果有)之后开始，在查询字符串分隔符(？)(如果有)之前结束。
     * web容器不会解码此字符串。
     *
     * <table>
     * <caption>返回值示例</caption>
     * <tr>
     * <th>HTTP请求的第一行</th>
     * <th>返回值</th>
     * </tr>
     * <tr>
     * <td>POST /some/path.html HTTP/1.1</td>
     * <td>/some/path.html</td>
     * </tr>
     * <tr>
     * <td>GET http://foo.bar/a.html HTTP/1.0</td>
     * <td>/a.html</td>
     * </tr>
     * <tr>
     * <td>HEAD /xyz?a=b HTTP/1.1</td>
     * <td>/xyz</td>
     * </tr>
     * </table>
     *
     * <p>要使用scheme和host重建URL，请使用{@link #getRequestUrl}。</p>
     *
     * @return 一个包含从authority之后到查询字符串之前的URL路径部分的字符串
     * @see #getRequestUrl
     */
    public static String getRequestURI() {
        return getRequest().getRequestURI();
    }

    /**
     * 获取客户端用于发起请求的完整URL。返回的URL包含协议、服务器名、端口号和服务器路径，但不包含查询字符串参数。
     * <p>
     * 此方法返回一个字符串，可以方便地用于重定向消息和错误报告。
     * <p>
     * 注意：如果需要构建包含查询参数的完整URL，可以在返回结果后手动拼接查询字符串。
     *
     * @return 包含重构URL的字符串对象
     */
    public static String getRequestUrl() {
        return getRequest().getRequestURL().toString();
    }

    /**
     * 获取请求的HTTP方法名称
     *
     * @return 请求方法 (GET, POST, etc.)
     */
    public static String getRequestMethod() {
        return getRequest().getMethod();
    }

    /**
     * 获取请求的查询字符串
     *
     * @return 查询字符串
     */
    public static String getRequestQueryString() {
        return getRequest().getQueryString();
    }

    /**
     * 获取请求的内容类型
     *
     * @return 请求的内容类型
     */
    public static String getRequestContentType() {
        return getRequest().getContentType();
    }

    /**
     * 获取请求体的长度
     *
     * @return 请求体的长度
     */
    public static int getRequestContentLength() {
        return getRequest().getContentLength();
    }

    /**
     * 获取客户端真实IP地址
     * <p>
     * 此方法会尝试从常见的HTTP代理头中获取真实的客户端IP地址，如 'x-forwarded-for', 'Proxy-Client-IP', 'WL-Proxy-Client-IP', 'X-Real-IP'等。
     * 如果无法从代理头中获取，则返回直接连接的客户端IP地址。
     *
     * @return 客户端IP地址
     */
    public static String getRequestIp() {
        HttpServletRequest request = getRequest();
        String ip = null;
        String[] headers = {"x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "X-Real-IP"};
        for (String header : headers) {
            ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                break;
            }
        }

        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 转换localhost
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }

        // 处理多级代理，获取第一个有效IP
        if (ip != null && ip.indexOf(',') > 0) {
            final String[] ips = ip.trim().split(",");
            for (String subIp : ips) {
                String trimmedIp = subIp.trim();
                if (!trimmedIp.isEmpty() && !"unknown".equalsIgnoreCase(trimmedIp)) {
                    ip = trimmedIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 获取请求的协议 (http, https)
     *
     * @return 请求的协议
     */
    public static String getRequestScheme() {
        return getRequest().getScheme();
    }

    /**
     * 获取服务器的主机名
     *
     * @return 服务器的主机名
     */
    public static String getRequestServerName() {
        return getRequest().getServerName();
    }

    /**
     * 获取服务器的端口号
     *
     * @return 服务器的端口号
     */
    public static int getRequestServerPort() {
        return getRequest().getServerPort();
    }

    /**
     * 获取请求头信息
     *
     * @param key 请求头的键
     * @return 对应键的请求头值
     */
    public static String getRequestHeader(String key) {
        return getRequest().getHeader(key);
    }

    /**
     * 获取请求参数值
     *
     * @param name 参数名称
     * @return 参数值
     */
    public static String getRequestParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取所有请求参数
     *
     * @return 包含所有请求参数的Map
     */
    public static Map<String, String[]> getRequestParameterMap() {
        return getRequest().getParameterMap();
    }

    /**
     * 获取请求的输入流。
     *
     * @return 请求的输入流
     */
    @SneakyThrows
    public static InputStream getRequestInputStream() {
        return getRequest().getInputStream();
    }

    /**
     * 设置响应头
     *
     * @param name  响应头的名称
     * @param value 响应头的值
     */
    public static void setResponseHeader(String name, String value) {
        getResponse().setHeader(name, value);
    }

    /**
     * 设置响应状态码
     *
     * @param sc 状态码
     */
    public static void setResponseStatus(int sc) {
        getResponse().setStatus(sc);
    }

    /**
     * 设置响应的内容类型
     *
     * @param type 内容类型
     */
    public static void setResponseContentType(String type) {
        getResponse().setContentType(type);
    }

    /**
     * 获取用于向客户端发送二进制数据的ServletOutputStream对象。
     * <b>注意：</b>不应手动关闭此流，Servlet容器会负责管理它的生命周期。
     *
     * @return OutputStream
     */
    @SneakyThrows
    public static OutputStream getResponseOutputStream() {
        return getResponse().getOutputStream();
    }

    /**
     * 获取用于向客户端发送字符文本的PrintWriter对象。
     * <b>注意：</b>不应手动关闭此Writer，Servlet容器会负责管理它的生命周期。
     *
     * @return PrintWriter对象
     */
    @SneakyThrows
    public static PrintWriter getResponseWriter() {
        return getResponse().getWriter();
    }

    /**
     * 将JSON字符串写入响应。此方法会设置ContentType为application/json;charset=UTF-8。
     *
     * @param jsonString 要写入的JSON字符串
     */
    @SneakyThrows
    public static void writeResponseJson(String jsonString) {
        setResponseContentType("application/json;charset=UTF-8");
        getResponseWriter().write(jsonString);
    }

    /**
     * 将文本字符串写入响应。此方法会设置ContentType为text/plain;charset=UTF-8。
     *
     * @param textString 要写入的文本字符串
     */
    @SneakyThrows
    public static void writeResponseText(String textString) {
        setResponseContentType("text/plain;charset=UTF-8");
        getResponseWriter().write(textString);
    }

    /**
     * 将XML字符串写入响应。此方法会设置ContentType为application/xml;charset=UTF-8。
     *
     * @param xmlString 要写入的XML字符串
     */
    @SneakyThrows
    public static void writeResponseXml(String xmlString) {
        setResponseContentType("application/xml;charset=UTF-8");
        getResponseWriter().write(xmlString);
    }
}
