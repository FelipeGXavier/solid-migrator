package unit;

import core.ElasticConnectionImpl;
import core.MalformedDocumentException;
import core.contracts.IDatabaseHandler;
import migrator.Migrator;
import migrator.systems.foo.rows.FooNoticeRows;
import migrator.systems.foo.tables.Notice;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;


public class MigratorTest {

    private ElasticConnectionImpl elasticConnection;

    public MigratorTest(){
        this.elasticConnection = Mockito.mock(ElasticConnectionImpl.class);
    }

    @Test
    public void emptyDatabaseHandlerShouldNotRunMigrator() throws IOException {
        Migrator migrator = new Migrator(new LinkedHashSet<>(), this.elasticConnection);
        migrator.run();
        Mockito.verify(this.elasticConnection, Mockito.times(0)).put(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void databaseHandlerShouldRunMigrator() throws IOException, SQLException {
        FooNoticeRows fooNoticeRows = Mockito.mock(FooNoticeRows.class);
        Mockito.when(fooNoticeRows.getOriginTable()).thenReturn("mock");
        Mockito.when(fooNoticeRows.getDestinationTable()).thenReturn("mock");
        Set<Notice> rows = new LinkedHashSet<>();
        rows.add(new Notice()
                .withCode("1")
                .withFinalDate(new Timestamp(System.currentTimeMillis()))
                .withObject("Mock")
                .withId(1L));
        rows.add(new Notice()
                .withCode("2")
                .withFinalDate(new Timestamp(System.currentTimeMillis()))
                .withObject("Mock")
                .withId(2L));
        Mockito.doReturn(rows).when(fooNoticeRows).getDatabaseRows();
        Set<IDatabaseHandler> instances = new LinkedHashSet<>();
        instances.add(fooNoticeRows);
        Assertions.assertDoesNotThrow(() -> new Migrator(instances, this.elasticConnection).run());
        Mockito.verify(this.elasticConnection, Mockito.times(2)).put(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void invalidReferenceMustThrowMalformedDocumentException() throws SQLException {
        FooNoticeRows fooNoticeRows = Mockito.mock(FooNoticeRows.class);
        Mockito.when(fooNoticeRows.getOriginTable()).thenReturn("mock");
        Mockito.when(fooNoticeRows.getDestinationTable()).thenReturn("mock");
        Set<Notice> rows = new LinkedHashSet<>();
        rows.add(new Notice()
                .withCode("1")
                .withFinalDate(new Timestamp(System.currentTimeMillis()))
                .withObject("Mock")
                .withId(null));
        Mockito.doReturn(rows).when(fooNoticeRows).getDatabaseRows();
        Set<IDatabaseHandler> instances = new LinkedHashSet<>();
        instances.add(fooNoticeRows);
        Migrator migrator = new Migrator(instances, this.elasticConnection);
        MalformedDocumentException exception = assertThrows(MalformedDocumentException.class, migrator::run);
        Assertions.assertTrue(exception.getMessage().contains("Malformed document"));
    }

    @Test
    public void emptyDestinationMustThrowMalformedDocumentException() throws SQLException {
        IDatabaseHandler mockRows = Mockito.mock(IDatabaseHandler.class);
        Mockito.when(mockRows.getDestinationTable()).thenReturn("");
        Mockito.when(mockRows.getOriginTable()).thenReturn("mock");
        Set<Notice> rows = new LinkedHashSet<>();
        rows.add(new Notice()
                .withCode("1")
                .withFinalDate(new Timestamp(System.currentTimeMillis()))
                .withObject("Mock")
                .withId(1L));
        Mockito.doReturn(rows).when(mockRows).getDatabaseRows();
        Set<IDatabaseHandler> instances = new LinkedHashSet<>();
        instances.add(mockRows);
        Migrator migrator = new Migrator(instances, this.elasticConnection);
        MalformedDocumentException exception = assertThrows(MalformedDocumentException.class, migrator::run);
        Assertions.assertTrue(exception.getMessage().contains("Malformed document"));
    }

    @Test
    public void emptyOriginMustThrowMalformedDocumentException() throws SQLException {
        IDatabaseHandler mockRows = Mockito.mock(IDatabaseHandler.class);
        Mockito.when(mockRows.getDestinationTable()).thenReturn("mock");
        Mockito.when(mockRows.getOriginTable()).thenReturn("");
        Set<Notice> rows = new LinkedHashSet<>();
        rows.add(new Notice()
                .withCode("1")
                .withFinalDate(new Timestamp(System.currentTimeMillis()))
                .withObject("Mock")
                .withId(1L));
        Mockito.doReturn(rows).when(mockRows).getDatabaseRows();
        Set<IDatabaseHandler> instances = new LinkedHashSet<>();
        instances.add(mockRows);
        Migrator migrator = new Migrator(instances, this.elasticConnection);
        MalformedDocumentException exception = assertThrows(MalformedDocumentException.class, migrator::run);
        Assertions.assertTrue(exception.getMessage().contains("Malformed document"));
    }

}
