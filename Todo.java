package hello;

public class Todo {

    private final long id;
    private final String content;
    private final String createdTime;

    public Todo(long id, String content, String createdTime) {
        this.id = id;
        this.content = content;
        this.createdTime = createdTime;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedTime() {
        return createdTime;
    }
}
