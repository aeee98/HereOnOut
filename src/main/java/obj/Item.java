package obj;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Item {

    @SerializedName("update_timestamp")
    @Expose
    private String updateTimestamp;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("valid_period")
    @Expose
    private ValidPeriod validPeriod;
    @SerializedName("forecasts")
    @Expose
    private List<Forecast> forecasts = null;

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public ValidPeriod getValidPeriod() {
        return validPeriod;
    }

    public void setValidPeriod(ValidPeriod validPeriod) {
        this.validPeriod = validPeriod;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

}
