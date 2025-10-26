package io.github.luminion.autoconfigure.aop.spi.signature;

import io.github.luminion.autoconfigure.util.ServletUtils;

import java.lang.reflect.Method;

/**
 * ip地址签名
 * @author luminion
 */
public class SpelIpAddressSignatureProvider extends SpelSignatureProvider {
    public SpelIpAddressSignatureProvider(String prefix) {
        super(prefix);
    }

    @Override
    public String signature(Object target, Method method, Object[] args, String expression) {
        String requestIp = ServletUtils.getRequestIp();
        if (requestIp == null) {
            requestIp = "unknow-ip";
        }
        String signature = super.signature(target, method, args, expression);
        return requestIp + "-" + signature;
    }
}
