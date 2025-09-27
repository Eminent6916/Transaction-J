package com.UserService.Dto.Response;

public class ApiResponse<any> {
    private final boolean success;
    private final String message;
    private final any data;

    public ApiResponse(boolean success, String message, any data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {return success;}

    public String getMessage() {return message;}

    public any getData() {return data;}

    public static <any> ApiResponse<any> success(String message, any data){
        return new ApiResponse<>(true,message,data);
    }

    public static <any> ApiResponse<any> error(String message,any data){
        return new ApiResponse<>(false, message, data);
    }

}