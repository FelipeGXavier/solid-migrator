package migrator.module.bar.tables;

public class Dashboard {

    private Long id;
    private String context;
    private String extra;
    private boolean migrated;

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

    public boolean isMigrated() {
        return migrated;
    }

    public void setMigrated(boolean migrated) {
        this.migrated = migrated;
    }
}

