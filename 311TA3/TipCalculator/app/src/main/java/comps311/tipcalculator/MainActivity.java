package comps311.tipcalculator;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private EditText billAmount, tipPercent, numPeople;
    private TextView totalAmount, totalPerPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setupButtons();
        setupTextChanges();
        billAmount.setText("");
        billAmount.requestFocus();
        tipPercent.setText("10");
        numPeople.setText("1");
    }

    private void findViews() {
        billAmount = findViewById(R.id.bill_amount);
        tipPercent = findViewById(R.id.tip);
        numPeople = findViewById(R.id.people);
        totalAmount = findViewById(R.id.total_amount);
        totalPerPerson = findViewById(R.id.total_per_person);
    }

    private void setupButtons() {
        //clearButtons
        Button clear =  findViewById(R.id.bill_amount_clear);
        clear.setOnClickListener(v -> billAmount.setText(""));

        //keyButtons
        View.OnClickListener keyButtonListener = v -> {
            //Convert the text in Button and input field to String
            String keyButton = ((Button) v).getText().toString();
            String setAmount = billAmount.getText().toString();

            // Clicked Button = BackSpace
            if (keyButton.equals("x")) {
                if (setAmount.length() > 0) {
                    setAmount = setAmount.substring(0, setAmount.length() -1);
                    billAmount.setText(setAmount);
                    billAmount.setSelection(setAmount.length());
                }
            }

            // Carry out If it is not the case that the field contains a dot and keyButton clicked is also a dot
            else if (!(keyButton.equals(".") && setAmount.contains("."))) {
                //Concatenate the numbers, set the text in field and cursor pointer to the last appended position.
                setAmount += keyButton;
                billAmount.setText(setAmount);
                billAmount.setSelection(setAmount.length());
            }
        };

        //set an array of keyPad buttons (0 - 9, decimal and backspace)
        int[] digitIDs =    {R.id.digit_7, R.id.digit_8, R.id.digit_9,
                            R.id.digit_4, R.id.digit_5, R.id.digit_6,
                            R.id.digit_1, R.id.digit_2, R.id.digit_3,
                            R.id.digit_0, R.id.dot, R.id.backspace};

        // Add keyButtonListener for each keyButton
        for (int id: digitIDs) {
            Button button = findViewById(id);
            button.setOnClickListener(keyButtonListener);
        }

        //tipButtons
        View.OnClickListener tipButtonListener = v -> {
            Button button = (Button) v;
            String percent = button.getText().toString()
                    .replace("%", "");
            tipPercent.setText(percent);
        };

        int[] tipIDs = {R.id.tip_5, R.id.tip_10, R.id.tip_15, R.id.tip_20};
        for (int id : tipIDs) {
            Button button = findViewById(id);
            button.setOnClickListener(tipButtonListener);
        }

        //peopleButtons
        View.OnClickListener peopleButtonListener = v -> {
            String s = ((Button) v).getText().toString();
            numPeople.setText(s);
        };
        int[] peopleIDs = {R.id.people_1, R.id.people_2, R.id.people_3,
                R.id.people_4, R.id.people_5};
        for (int id : peopleIDs) {
            Button button = findViewById(id);
            button.setOnClickListener(peopleButtonListener);
        }
    }

    private void setupTextChanges() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculate();
            }
        };
        billAmount.addTextChangedListener(watcher);
        tipPercent.addTextChangedListener(watcher);
        numPeople.addTextChangedListener(watcher);
    }

    private void calculate() {
        try {
            double bill = Double.parseDouble(billAmount.getText().toString());
            int pc = Integer.parseInt(tipPercent.getText().toString());
            int people = Integer.parseInt(numPeople.getText().toString());
            double amount = bill * (1 + pc / 100.0);
            NumberFormat format = NumberFormat.getCurrencyInstance();
            totalAmount.setText(format.format(amount));
            totalPerPerson.setText(format.format(amount / people));
        } catch (NumberFormatException e) {
            totalAmount.setText("");
            totalPerPerson.setText("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    public static class AboutDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("About")
                        .setMessage("Chan Chi Hung\nID: s12650050")
                        .setPositiveButton(android.R.string.ok, null)
                        .create();
            return dialog;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isCheckable()) {
            item.setChecked(true);
        }

        switch (item.getItemId()) {
            case R.id.about:
                AboutDialogFragment dialog = new AboutDialogFragment();
                dialog.show(getSupportFragmentManager(), "dialog");
                return true;

            case R.id.blue_color:
                findViewById(R.id.constraint_layout).setBackgroundResource(android.R.color.holo_blue_light);
                return true;

            case R.id.green_color:
                findViewById(R.id.constraint_layout).setBackgroundResource(android.R.color.holo_green_light);
                return true;

            case R.id.white_color:
                findViewById(R.id.constraint_layout).setBackgroundResource(android.R.color.white);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
