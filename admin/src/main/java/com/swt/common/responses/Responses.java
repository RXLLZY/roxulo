package com.swt.common.responses;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * 成功返回
 *
 * @author Caratacus
 */
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

    public Responses() {
    }

    public Responses(Integer status) {
        this.status = status;
    }

    public Responses(Integer status, T result) {
        this.status = status;
        this.result = result;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Responses(status=" + this.getStatus() + ", result=" + this.getResult() + ")";
    }

    public static class ResponsesBuilder<T>{

        private Integer status;
        private T result;

        ResponsesBuilder() {
        }

        public Responses.ResponsesBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public Responses.ResponsesBuilder result(T object) {
            this.result = result;
            return this;
        }

        public Responses <T>build() {
            return new Responses(this.status, this.result);
        }

    }

    public static ResponsesBuilder builder() {
        return new ResponsesBuilder();
    }

}
