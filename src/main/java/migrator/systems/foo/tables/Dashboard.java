package migrator.systems.foo.tables;

import com.fasterxml.jackson.annotation.JsonProperty;
import core.contracts.TableRefer;

public class Dashboard implements TableRefer {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("context")
    private String context;
    @JsonProperty("extra")
    private String extra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String getRefer() {
        return String.valueOf(this.id);
    }
}

