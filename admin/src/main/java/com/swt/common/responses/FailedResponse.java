package com.swt.common.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 失败返回
 *
 * @author Caratacus
 */
@Data
@Builder
public class FailedResponse{

    private static final long serialVersionUID = 1L;
    /**
     * http 状态码
     */
    private Integer status;
    /**
     * 错误描述
     */
    private String message;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 客户端是否展示
     */
    private Boolean show;
    /**
     * 当前时间戳
     */
    private LocalDateTime time;

}
