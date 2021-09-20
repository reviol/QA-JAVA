package com.example.contract.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserResponse {

    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("job")
    private String job;
    @JsonProperty("name")
    private String name;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "CreateUserResponse{" +
                "createdAt='" + updatedAt + '\'' +
                ", job='" + job + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
