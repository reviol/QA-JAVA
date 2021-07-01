package com.example.apiHelpers.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {

    @JsonProperty("job")
    private String job;
    @JsonProperty("name")
    private String name;

//    public String getJob() {
//        return job;
//    }

//    public String getName() {
//        return name;
//    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "job='" + job + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
