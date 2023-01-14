package comps311.a4.callblocker.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CallLogDao {
    @Query("SELECT * FROM calllog")
    LiveData<List<CallLog>> getAll();

    @Insert
    void insert(CallLog callLog);
}
