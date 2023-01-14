package comps311.mesapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageAdapter
        extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private static final int MAX_IMAGE_WIDTH = 1024;
    private static final int TYPE_MY_MESSAGE = 1;
    private static final int TYPE_OTHER_MESSAGE = 2;



    private static final SimpleDateFormat formatDate = new SimpleDateFormat("h:mm a");

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView authorView;
        public TextView textView;
        public ImageView imageView;
        public TextView timeView;

        public ViewHolder(View view) {
            super(view);
            authorView = view.findViewById(R.id.message_author);
            textView = view.findViewById(R.id.message_text);
            imageView = view.findViewById(R.id.message_image);
            timeView = view.findViewById(R.id.message_time);
        }
    }

    private List<Message> messages;
    private String displayName;

    public MessageAdapter(List<Message> messages, String displayName) {
        this.messages = messages;
        this.displayName = displayName;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (message.getAuthor().equals(displayName)) {
            return TYPE_MY_MESSAGE;
        } else {
            return TYPE_OTHER_MESSAGE;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = viewType == TYPE_MY_MESSAGE ? R.layout.view_my_message
                : R.layout.view_other_message;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder.authorView != null) {
            holder.authorView.setText(message.getAuthor());
        }
        holder.timeView.setText(formatDate.format(message.getTime()));
        byte[] image = message.getImage();
        if (image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0,
                    image.length);
            bitmap = resizeBitmap(bitmap, MAX_IMAGE_WIDTH);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageBitmap(bitmap);
            holder.textView.setVisibility(View.GONE);
            holder.textView.setText(null);
        } else {
            holder.imageView.setVisibility(View.GONE);
            holder.imageView.setImageBitmap(null);
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setText(message.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private static Bitmap resizeBitmap(Bitmap bitmap, int maxWidth) {
        if (bitmap == null || bitmap.getWidth() <= maxWidth) {
            return bitmap;
        }
        float scale = (float) maxWidth / bitmap.getWidth();
        int newWidth = maxWidth;
        int newHeight = (int) (bitmap.getHeight() * scale);
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, newWidth,
                newHeight, false);
        bitmap.recycle();
        return newBitmap;
    }
}