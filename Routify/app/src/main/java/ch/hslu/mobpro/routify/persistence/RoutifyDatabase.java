package ch.hslu.mobpro.routify.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ConnectionEntity.class}, version = 1, exportSchema = false)
public abstract class RoutifyDatabase extends RoomDatabase {
    public abstract ConnectionEntityDao connectionEntityDao();
}
