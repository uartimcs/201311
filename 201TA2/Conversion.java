import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Conversion extends JFrame implements ActionListener {
    
    //GUI units
    private JTextField audAmount = new JTextField(10); // reasonable size
    private JLabel usdAmount = new JLabel("          "); // 10 spacing question requested
    private JButton calculateButton = new JButton("Calculate");
    
    //set the constructor
    public Conversion() {
        add(audAmount, BorderLayout.WEST);
        add(usdAmount, BorderLayout.EAST);
        add(calculateButton, BorderLayout.SOUTH);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        
        //Action Listener
        calculateButton.addActionListener(this);  
    }
    
    public void actionPerformed(ActionEvent e) {
        double audValue = Double.parseDouble(audAmount.getText());
        double usdValue = audValue * 0.75;
        
        usdAmount.setText(usdValue + "");
    }
    
    public static void main(String[] args) {
        Conversion exchange = new Conversion();
        exchange.setVisible(true);
    }
    
}
