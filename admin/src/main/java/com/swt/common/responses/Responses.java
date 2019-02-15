package com.swt.common.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * 成功返回
 *
 * @author Caratacus
 */
@Data
@Builder
public class Responses<T> {
    private static final long serialVersionUID = 1L;

    /**
     * http 状态码
     */
    @ApiModelProperty(value = "状态码", example = "0", hidden = false, required = true)
    private Integer status;
    /**
     * 结果集返回
     */
    private T result;

    /**
     * 不需要返回结果
     *
     * @param status
     */
    public static Responses<Void> success(HttpServletResponse response, HttpStatus status) {
        response.setStatus(status.value());
        return Responses.<Void>builder().status(status.value()).build();

    }

    /**
     * 成功返回
     *
     * @param object
     */
    public static <T> Responses<T> success(HttpServletResponse response, T object) {
        return success(response, HttpStatus.OK, object);

    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     */
    public static <T> Responses<T> success(HttpServletResponse response, HttpStatus status, T object) {
        response.setStatus(status.value());
        return Responses.<T>builder().status(status.value()).result(object).build();
    }

}
