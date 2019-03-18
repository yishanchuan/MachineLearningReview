package model;

public class Example{

    // 第一个标签是类型，第二个才是文本
    private String type;//类型分为spam垃圾，ham非垃圾
    private String message;

    public Example() {
    }

    public Example(String type, String message) {
        this.type = type;
        this.message = message;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Example{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}