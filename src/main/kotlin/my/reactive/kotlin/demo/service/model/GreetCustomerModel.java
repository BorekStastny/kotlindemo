package my.reactive.kotlin.demo.service.model;

public class GreetCustomerModel {

    private Integer userId;

    private String name;

    private String greeting;

    public GreetCustomerModel(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
