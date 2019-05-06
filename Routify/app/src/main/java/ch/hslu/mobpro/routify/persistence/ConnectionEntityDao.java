package ch.hslu.mobpro.routify.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ConnectionEntityDao {

    @Query("SELECT * FROM connectionentity")
    List<ConnectionEntity> loadAll();

    @Query("SELECT * FROM connectionentity WHERE id = :id")
    ConnectionEntity loadOne(int id);

    @Insert
    void insert(ConnectionEntity connectionEntity);

    @Delete
    void delete(ConnectionEntity connectionEntity);
}
