package unit;

import core.ElasticConnectionImpl;
import core.IteratorWrapper;
import core.MalformedDocumentException;
import migrator.Migrator;
import migrator.systems.foo.postgres.notice.FooNoticeIterator;
import migrator.systems.foo.postgres.notice.FooNoticeRows;
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
    public void givenEmptyMigratorShouldMigrateAnything() throws IOException {
        Migrator migrator = new Migrator(new LinkedHashSet<>(), this.elasticConnection);
        migrator.run();
        Mockito.verify(this.elasticConnection, Mockito.times(0)).put(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void givenValidIteratorMigratorShouldMigrateToElastic() throws IOException, SQLException {
        FooNoticeRows fooNoticeRows = Mockito.mock(FooNoticeRows.class);
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
        FooNoticeIterator fooNoticeIterator = new FooNoticeIterator(fooNoticeRows);
        Set<IteratorWrapper> iterators = new LinkedHashSet<>();
        iterators.add(fooNoticeIterator);
        Assertions.assertDoesNotThrow(() -> new Migrator(iterators, this.elasticConnection).run());
        Mockito.verify(this.elasticConnection, Mockito.times(2)).put(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void givenDocumentWithInvalidReferenceMustThrowMalformedDocumentException() throws SQLException {
        FooNoticeRows fooNoticeRows = Mockito.mock(FooNoticeRows.class);
        Set<Notice> rows = new LinkedHashSet<>();
        rows.add(new Notice()
                .withCode("1")
                .withFinalDate(new Timestamp(System.currentTimeMillis()))
                .withObject("Mock")
                .withId(null));
        Mockito.doReturn(rows).when(fooNoticeRows).getDatabaseRows();
        FooNoticeIterator fooNoticeIterator = new FooNoticeIterator(fooNoticeRows);
        Set<IteratorWrapper> iterators = new LinkedHashSet<>();
        iterators.add(fooNoticeIterator);
        Migrator migrator = new Migrator(iterators, this.elasticConnection);
        MalformedDocumentException exception = assertThrows(MalformedDocumentException.class, migrator::run);
        Assertions.assertTrue(exception.getMessage().contains("Malformed document"));
    }

    @Test
    public void givenEmptyDestinationForIteratorMustThrowMalformedDocumentException() throws SQLException {
        FooNoticeIterator iterator = Mockito.mock(FooNoticeIterator.class);
        Mockito.when(iterator.getDestination()).thenReturn("");
        Set<Notice> rows = new LinkedHashSet<>();
        rows.add(new Notice()
                .withCode("1")
                .withFinalDate(new Timestamp(System.currentTimeMillis()))
                .withObject("Mock")
                .withId(1L));
        Mockito.doReturn(rows.iterator()).when(iterator).createIterator();
        Set<IteratorWrapper> iterators = new LinkedHashSet<>();
        iterators.add(iterator);
        Migrator migrator = new Migrator(iterators, this.elasticConnection);
        MalformedDocumentException exception = assertThrows(MalformedDocumentException.class, migrator::run);
        Assertions.assertTrue(exception.getMessage().contains("Malformed document"));
    }

    @Test
    public void givenEmptyOriginForIteratorMustThrowMalformedDocumentException() throws SQLException {
        FooNoticeIterator iterator = Mockito.mock(FooNoticeIterator.class);
        Mockito.when(iterator.getOrigin()).thenReturn("");
        Set<Notice> rows = new LinkedHashSet<>();
        rows.add(new Notice()
                .withCode("1")
                .withFinalDate(new Timestamp(System.currentTimeMillis()))
                .withObject("Mock")
                .withId(1L));
        Mockito.doReturn(rows.iterator()).when(iterator).createIterator();
        Set<IteratorWrapper> iterators = new LinkedHashSet<>();
        iterators.add(iterator);
        Migrator migrator = new Migrator(iterators, this.elasticConnection);
        MalformedDocumentException exception = assertThrows(MalformedDocumentException.class, migrator::run);
        Assertions.assertTrue(exception.getMessage().contains("Malformed document"));
    }

}
