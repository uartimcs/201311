import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HKD2EUR extends JFrame implements ActionListener {
    // Attributes used in the GUI
    private JMenuBar menuBar = new JMenuBar();
    private JMenu actionMenu = new JMenu("Action");
    private JMenuItem convertMenuItem = new JMenuItem("Convert");
    private JMenuItem exitMenuItem = new JMenuItem("Exit");
    
    private JTextField textField = new JTextField("HKD amount");
    private JLabel resultLabel = new JLabel();
    
    public HKD2EUR() {
        // Set the status bar details
        setJMenuBar(menuBar);
        menuBar.add(actionMenu);
        actionMenu.add(convertMenuItem);
        actionMenu.add(exitMenuItem);
        
        // Layout and add items
        setLayout(new GridLayout(2,1));
        add(textField);
        add(resultLabel);
        
        // Add ActionListener to MenuItems
        convertMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
    
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        // calculate the Euro when convert menu is clicked.
        if (source == convertMenuItem) {
            double amount = Double.parseDouble(textField.getText());
            double euroAmount = amount * 0.109;
            resultLabel.setText(euroAmount + "");
        }
        // exit when exit menu is clicked
        else if (source == exitMenuItem) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        HKD2EUR exchange = new HKD2EUR();
        exchange.setVisible(true);
    }
}
