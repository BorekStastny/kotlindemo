package my.reactive.kotlin.demo.client.model;

public class GetUserProfileRequest {

    private Integer userId;

    public GetUserProfileRequest(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }
}
