package com.school.onlinemuseums.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存状态码
 */
public interface HttpStatus {
    /**
     * 操作成功
     */
    public static final int SUCCESS = 200;

    /**
     * 对象创建成功
     */
    public static final int CREATED = 201;

    /**
     * 请求已经被接受
     */
    public static final int ACCEPTED = 202;

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    public static final int NO_CONTENT = 204;

    /**
     * 资源已被移除
     */
    public static final int MOVED_PERM = 301;

    /**
     * 重定向
     */
    public static final int SEE_OTHER = 303;

    /**
     * 资源没有被修改
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 未授权
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 访问受限，授权过期
     */
    public static final int FORBIDDEN = 403;

    /**
     * 资源，服务未找到
     */
    public static final int NOT_FOUND = 404;

    /**
     * 不允许的http方法
     */
    public static final int BAD_METHOD = 405;

    /**
     * 资源冲突，或者资源被锁
     */
    public static final int CONFLICT = 409;

    /**
     * 不支持的数据，媒体类型
     */
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;

    /**
     * 接口未实现
     */
    public static final int NOT_IMPLEMENTED = 501;

    /**
     * 系统警告消息
     */
    public static final int WARN = 601;

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    static Map<String, Object> success(String msg) {
        return result(SUCCESS, msg);
    }
    public static HttpStatus success() {
        return (HttpStatus) HttpStatus.success("操作成功");
    }
    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 错误消息
     */
    static Map<String, Object> error(int code, String msg) {
        return result(code, msg);
    }

    /**
     * 返回通用结果
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 通用结果
     */
    static Map<String, Object> result(int code, String msg) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("msg", msg);
        return resultMap;
    }



}
