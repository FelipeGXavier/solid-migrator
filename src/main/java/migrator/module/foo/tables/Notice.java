package migrator.module.bar.tables;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Notice {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("code")
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("opening_date")
    private Timestamp openingDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("final_date")
    private Timestamp finalDate;
    @JsonProperty("object")
    private String object;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Timestamp openingDate) {
        this.openingDate = openingDate;
    }

    public Timestamp getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Timestamp finalDate) {
        this.finalDate = finalDate;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
