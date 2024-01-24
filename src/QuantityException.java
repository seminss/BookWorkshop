import java.io.IOException;

public class QuantityException extends IOException {
    public QuantityException() {
        super("수량이 부족합니다.");
    }
}
