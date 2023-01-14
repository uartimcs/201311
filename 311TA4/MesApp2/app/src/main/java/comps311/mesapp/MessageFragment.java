package comps311.mesapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MessageFragment extends BaseFragment {
    private static final int RC_PICK_IMAGE = 1;

    private RecyclerView recyclerView;
    private EditText editText;
    private MessageAdapter messageAdapter;
    private PrintWriter printWriter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        recyclerView = view.findViewById(R.id.messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new MessageAdapter(viewModel.messages,
                viewModel.displayName);
        recyclerView.setAdapter(messageAdapter);
        viewModel.recyclerView = recyclerView;
        refreshRecyclerView(recyclerView);

        try {
            if (savedInstanceState == null) {
                startThreadToReceiveMessages(viewModel.socket.getInputStream());
            }
            OutputStream os = viewModel.socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            printWriter = new PrintWriter(bos);
        } catch (IOException e) {
            toast(e.getMessage());
        }
        editText = view.findViewById(R.id.text);
        view.findViewById(R.id.send).setOnClickListener(v -> handleSend());
        view.findViewById(R.id.image).setOnClickListener(v -> handleImage());
        return view;
    }

    private Executor sendingExecutor = Executors.newSingleThreadExecutor();

    private void handleSend() {
        String text = editText.getText().toString();
        editText.setText(null);
        editText.requestFocus();
        if (!text.isEmpty()) {
            sendingExecutor.execute(() -> {
                Message message = new Message();
                message.setAuthor(viewModel.displayName);
                message.setText(text);
                message.setTime(new Date());
                String jsonString = JSONUtil.fromMessage(message);
                printWriter.println(jsonString);
                printWriter.flush();
            });
        }
    }

    private void startThreadToReceiveMessages(InputStream inputStream) {
        new Thread(() -> {
            try (InputStreamReader isr = new InputStreamReader(inputStream);
                 BufferedReader in = new BufferedReader(isr)) {
                String line;
                while ((line = in.readLine()) != null) {
                    Message message = JSONUtil.toMessage(line);
                    if (message != null) {
                        viewModel.messages.add(message);
                        refreshRecyclerView(viewModel.recyclerView);
                    }
                }
            } catch (Exception e) {
                toast(e.getMessage());
            }
        }).start();
    }

    private void refreshRecyclerView(RecyclerView recyclerView) {
        recyclerView.post(() -> {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            adapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        });
    }

    private void handleImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, RC_PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    sendingExecutor.execute(() -> sendImage(uri));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendImage(Uri uri) {
        ContentResolver resolver = getContext().getContentResolver();
        try (InputStream is = resolver.openInputStream(uri)) {
            byte[] image = readAllBytes(is);
            Message message = new Message();
            message.setAuthor(viewModel.displayName);
            message.setImage(image);
            message.setTime(new Date());
            String jsonString = JSONUtil.fromMessage(message);
            printWriter.println(jsonString);
            printWriter.flush();
        } catch (IOException e) {
            toast(e.getMessage());
        }
    }

    private static byte[] readAllBytes(InputStream is) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n = bis.read(buffer)) != -1) {
            baos.write(buffer, 0, n);
        }
        return baos.toByteArray();
    }
}
