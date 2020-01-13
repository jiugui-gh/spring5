package chapter2.part7.payport;

public class PayState {

    private int code;
    private Object data;
    private String msg;
    
    public PayState(int code, Object data, String msg) {
        super();
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "PayState [code=" + code + ", data=" + data + ", msg=" + msg + "]";
    }
    
}
