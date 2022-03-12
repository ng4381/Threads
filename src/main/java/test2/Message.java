package test2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class Message {
    private final int id;
    private final String data;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", data='" + data + '\'' +
                '}';
    }
}
