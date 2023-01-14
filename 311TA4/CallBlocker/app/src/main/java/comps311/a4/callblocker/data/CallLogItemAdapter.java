package comps311.a4.callblocker.data;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comps311.a4.callblocker.R;

public class CallLogItemAdapter
        extends RecyclerView.Adapter<CallLogItemAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeView, numberView;

        public ViewHolder(View itemView) {
            super(itemView);
            timeView = itemView.findViewById(R.id.time);
            numberView = itemView.findViewById(R.id.number);
        }
    }

    private List<CallLog> callLogs;

    public void setCallLogs(List<CallLog> callLogs) {
        this.callLogs = callLogs;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.call_log_item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CallLog callLog = callLogs.get(position);
        holder.timeView.setText(String.valueOf(callLog.time));
        Resources res = holder.numberView.getContext().getResources();
        if (callLog.blocked) {
            int color = res.getColor(android.R.color.holo_red_dark);
            holder.numberView.setTextColor(color);
            holder.numberView.setText(callLog.number + " - Blocked!");
        } else {
            int color = res.getColor(android.R.color.holo_green_dark);
            holder.numberView.setTextColor(color);
            holder.numberView.setText(callLog.number);
        }
    }

    @Override
    public int getItemCount() {
        return callLogs == null ? 0 : callLogs.size();
    }
}