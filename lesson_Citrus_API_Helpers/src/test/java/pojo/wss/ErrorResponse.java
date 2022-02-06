package pojo.wss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    @JsonProperty("error")
    private WssError wssError;

    public WssError getError() {
        return wssError;
    }

    public void setError(WssError wssError) {
        this.wssError = wssError;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "error=" + wssError +
                '}';
    }
}
