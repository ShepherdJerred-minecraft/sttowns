package trackers;

import com.shepherdjerred.sttowns.exceptions.TownException;
import com.shepherdjerred.sttowns.exceptions.TrackerException;
import com.shepherdjerred.sttowns.objects.AbstractEntity;
import com.shepherdjerred.sttowns.objects.trackers.Entities;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EntitiesTest {

    private Entities<AbstractEntity> tracker;

    @Before
    public void setup() {
        tracker = new Entities<>();
    }

    @Test
    public void testAdding() throws TownException, TrackerException {
        UUID entityUuid = UUID.randomUUID();
        String entityName = "Test";
        AbstractEntity entity = mock(AbstractEntity.class);
        when(entity.getUuid()).thenReturn(entityUuid);
        when(entity.getName()).thenReturn(entityName);

        tracker.add(entity);

        assert tracker.contains(entity);
        assert tracker.getAsList().size() == 1;
    }

    @Test
    public void testContains() throws TownException, TrackerException {
        UUID entityUuid = UUID.randomUUID();
        String entityName = "Test";
        AbstractEntity entity = mock(AbstractEntity.class);
        when(entity.getUuid()).thenReturn(entityUuid);
        when(entity.getName()).thenReturn(entityName);

        tracker.add(entity);

        assert tracker.contains(entity);
        assert tracker.contains(entityName);
        assert tracker.contains(entityUuid);
    }

    @Test(expected = TrackerException.class)
    public void testDuplicateNames() throws TownException, TrackerException {
        UUID entityUuid = UUID.randomUUID();
        String entityName = "Test";
        AbstractEntity entity = mock(AbstractEntity.class);
        when(entity.getUuid()).thenReturn(entityUuid);
        when(entity.getName()).thenReturn(entityName);

        UUID entityUuid2 = UUID.randomUUID();
        String entityName2 = "Test";
        AbstractEntity entity2 = mock(AbstractEntity.class);
        when(entity2.getUuid()).thenReturn(entityUuid2);
        when(entity2.getName()).thenReturn(entityName2);

        tracker.add(entity);
        tracker.add(entity2);

        assert tracker.getAsList().size() == 1;
        assert tracker.contains(entity);
    }

    @Test(expected = TrackerException.class)
    public void testLongName() throws TownException, TrackerException {
        UUID entityUuid = UUID.randomUUID();
        String entityName = "SomeReallyLongTownName";
        AbstractEntity entity = mock(AbstractEntity.class);
        when(entity.getUuid()).thenReturn(entityUuid);
        when(entity.getName()).thenReturn(entityName);

        tracker.add(entity);
    }

    @Test(expected = TrackerException.class)
    public void testShortName() throws TownException, TrackerException {
        UUID entityUuid = UUID.randomUUID();
        String entityName = "12";
        AbstractEntity entity = mock(AbstractEntity.class);
        when(entity.getUuid()).thenReturn(entityUuid);
        when(entity.getName()).thenReturn(entityName);

        tracker.add(entity);
    }

    @Test
    public void testGetting() throws TownException, TrackerException {
        UUID entityUuid = UUID.randomUUID();
        String entityName = "Test";
        AbstractEntity entity = mock(AbstractEntity.class);
        when(entity.getUuid()).thenReturn(entityUuid);
        when(entity.getName()).thenReturn(entityName);

        tracker.add(entity);

        assert tracker.get(entity) == entity;
        assert tracker.get(entityName) == entity;
    }

    @Test
    public void testRemoving() throws TownException, TrackerException {
        UUID entityUuid = UUID.randomUUID();
        String entityName = "Test";
        AbstractEntity entity = mock(AbstractEntity.class);
        when(entity.getUuid()).thenReturn(entityUuid);
        when(entity.getName()).thenReturn(entityName);

        tracker.add(entity);
        tracker.remove(entity);
        assert tracker.get(entity) == null;
        assert tracker.getAsList().size() == 0;

        tracker.add(entity);
        tracker.remove(entityUuid);
        assert tracker.get(entity) == null;
        assert tracker.getAsList().size() == 0;


        tracker.add(entity);
        tracker.remove(entityName);
        assert tracker.get(entity) == null;
        assert tracker.getAsList().size() == 0;

    }

    @Test
    public void testGettingList() throws TownException, TrackerException {
        UUID entityUuid = UUID.randomUUID();
        String entityName = "Test";
        AbstractEntity entity = mock(AbstractEntity.class);
        when(entity.getUuid()).thenReturn(entityUuid);
        when(entity.getName()).thenReturn(entityName);

        UUID entityUuid2 = UUID.randomUUID();
        String entityName2 = "Test2";
        AbstractEntity entity2 = mock(AbstractEntity.class);
        when(entity2.getUuid()).thenReturn(entityUuid2);
        when(entity2.getName()).thenReturn(entityName2);

        tracker.add(entity);
        tracker.add(entity2);

        assert tracker.getAsList().contains(entity);
        assert tracker.getAsList().contains(entity2);
        assert tracker.getAsList().size() == 2;
    }
}
