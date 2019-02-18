package com.swt.common.responses;

import java.time.LocalDateTime;

/**
 * 失败返回
 *
 * @author Caratacus
 */
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

    public FailedResponse() {
    }

    public FailedResponse(Integer status, String message, String exception, Boolean show, LocalDateTime time) {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "FailedResponse(status=" + this.getStatus() + ", message=" + this.getMessage() + ", exception=" + this.getException() + ", show=" + this.getShow() + ", time=" + this.getTime() + ")";
    }

    public static FailedResponseBuilder builder() {
        return new FailedResponseBuilder();
    }

    public static class FailedResponseBuilder {
        private Integer status;
        private String message;
        private String exception;
        private Boolean show;
        private LocalDateTime time;

        FailedResponseBuilder() {
        }

        public FailedResponse.FailedResponseBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public FailedResponse.FailedResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public FailedResponse.FailedResponseBuilder exception(String exception) {
            this.exception = exception;
            return this;
        }

        public FailedResponse.FailedResponseBuilder show(Boolean show) {
            this.show = show;
            return this;
        }

        public FailedResponse.FailedResponseBuilder time(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public FailedResponse build() {
            return new FailedResponse(this.status, this.message, this.exception, this.show, this.time);
        }

        @Override
        public String toString() {
            return "FailedResponse.FailedResponseBuilder(status=" + this.status + ", message=" + this.message + ", exception=" + this.exception + ", show=" + this.show + ", time=" + this.time + ")";
        }
    }
}
