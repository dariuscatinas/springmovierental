package common.message;

import java.io.Serializable;

public class Message<T> implements Serializable {

    String header;
    T body;

    public Message(String header, T body) {
        this.header = header;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "header='" + header + '\'' +
                ", body=" + body +
                '}';
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
