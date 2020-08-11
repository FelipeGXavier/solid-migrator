package migrator.module.foo.tables;


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

    public Notice setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Notice setCode(String code) {
        this.code = code;
        return this;
    }

    public Timestamp getOpeningDate() {
        return openingDate;
    }

    public Notice setOpeningDate(Timestamp openingDate) {
        this.openingDate = openingDate;
        return this;
    }

    public Timestamp getFinalDate() {
        return finalDate;
    }

    public Notice setFinalDate(Timestamp finalDate) {
        this.finalDate = finalDate;
        return this;
    }

    public String getObject() {
        return object;
    }

    public Notice setObject(String object) {
        this.object = object;
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
