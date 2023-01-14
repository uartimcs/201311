package comps311.a4.callblocker.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class CallLog {
    @PrimaryKey(autoGenerate = true)
    public long id; // auto-generate
    public String time;
    public String number;
    public boolean blocked;

    public CallLog(String number, boolean blocked) {
        this.time = new Date().toString();
        this.number = number;
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "CallLog{" + "id=" + id + ", time='" + time + '\'' +
                ", number='" + number + '\'' + ", blocked=" + blocked + '}';
    }
}
