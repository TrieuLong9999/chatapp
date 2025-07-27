package com.chatapp.project.utils;

public class BaseResponse<T> {
    private int status;        // Ví dụ: 200
    private String message;    // Ví dụ: "Success" hoặc "Error"
    private Integer code;       // Ví dụ: "ERR-001"
    private String messageCode;// Ví dụ: "USER_NOT_FOUND"
    private T data;            // Dữ liệu trả về tùy ý (generic)

    // Constructors
    public BaseResponse() {}

    public BaseResponse(int status, String message, Integer code, String messageCode, T data) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.messageCode = messageCode;
        this.data = data;
    }

    // Getters and Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, "Success", ErrorCode.OK.getCode(), ErrorCode.OK.getMessage(), data);
    }
    // Method tiện ích cho error dùng ErrorCode enum
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(
                400,                               // Hoặc bạn có thể thêm `getHttpStatus()` vào enum nếu cần tuỳ biến
                errorCode.getMessage(),            // Ví dụ: "User not found"
                errorCode.getCode(), // Ví dụ: "1001"
                errorCode.name(),                  // Ví dụ: "USER_NOT_FOUND"
                null                               // Không có dữ liệu khi xảy ra lỗi
        );
    }



}