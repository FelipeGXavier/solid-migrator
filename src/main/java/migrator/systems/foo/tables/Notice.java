package migrator.systems.foo.tables;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import core.contracts.TableRefer;

import java.sql.Timestamp;

public class Notice implements TableRefer {

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

    public String getCode() {
        return code;
    }

    public Timestamp getOpeningDate() {
        return openingDate;
    }

    public Timestamp getFinalDate() {
        return finalDate;
    }

    public String getObject() {
        return object;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOpeningDate(Timestamp openingDate) {
        this.openingDate = openingDate;
    }

    public void setFinalDate(Timestamp finalDate) {
        this.finalDate = finalDate;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Notice withCode(String code) {
        this.code = code;
        return this;
    }

    public Notice withObject(String object) {
        this.object = object;
        return this;
    }

    public Notice withOpeningDate(Timestamp openingDate) {
        this.openingDate = openingDate;
        return this;
    }

    public Notice withFinalDate(Timestamp finalDate) {
        this.finalDate = finalDate;
        return this;
    }

    public Notice withId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    @JsonIgnore
    public String getRefer() {
        if(this.id == null){
            return "";
        }
        return String.valueOf(this.id);
    }


}
