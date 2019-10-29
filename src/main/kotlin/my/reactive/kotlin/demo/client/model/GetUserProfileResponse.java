package my.reactive.kotlin.demo.client.model;

public class GetUserProfileResponse {

    private Integer userId;

    private String name;

    public GetUserProfileResponse(Integer userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
