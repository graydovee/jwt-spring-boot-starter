package cn.graydove.security.utils;

import org.apache.commons.lang3.StringUtils;

public final class PathUtils {
    public static final String ROOT_URI = "/";

    private PathUtils() {

    }

    public static String join(String uri, String path) {
        uri = formatUri(uri);
        path = formatUri(path);
        if (ROOT_URI.equals(uri)) {
            return path;
        }
        return uri + path;
    }

    public static String formatUri(String uri) {
        if (StringUtils.isBlank(uri)) {
            return ROOT_URI;
        }
        if (!uri.startsWith("/")) {
            uri = "/" + uri;
        }
        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }
        return uri;
    }
}
