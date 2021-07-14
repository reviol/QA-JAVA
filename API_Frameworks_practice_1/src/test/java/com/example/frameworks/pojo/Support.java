
package com.example.frameworks.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Support {
    @Override
    public String toString() {
        return "Support{" +
                "url='" + url + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @JsonProperty("url")
    private String url;
    @JsonProperty("text")
    private String text;

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }



}
