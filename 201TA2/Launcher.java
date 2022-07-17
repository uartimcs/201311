import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Launcher extends JFrame implements ActionListener {
    //GUI units
    private JMenuBar menuBar = new JMenuBar();
    
    private JMenu launchMenu = new JMenu("Launch");
    private JMenu exitMenu = new JMenu("Exit");
    
    private JMenuItem televisionItem = new JMenuItem("Launch Television");
    private JMenuItem dialogBoxItem = new JMenuItem("Launch DialogBox");
    private JMenuItem conversionItem = new JMenuItem("Launch Conversion");
    
    private JMenuItem exitItem = new JMenuItem("exit");
    
    private JTextArea textArea = new JTextArea();
    
    //Constructor Setup
    public Launcher() {
        setJMenuBar(menuBar);
        
        menuBar.add(launchMenu);
        menuBar.add(exitMenu);
        
        launchMenu.add(televisionItem);
        launchMenu.add(dialogBoxItem);
        launchMenu.add(conversionItem);
        
        exitMenu.add(exitItem);
        
        add(textArea);
        
        televisionItem.addActionListener(this);
        dialogBoxItem.addActionListener(this);
        conversionItem.addActionListener(this);
        exitItem.addActionListener(this);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
    
    //Action Performed
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        if(source == televisionItem) 
        {
            textArea.setText(textArea.getText() + "Televison Launched\n");
            TestTelevision.main(null);
        }
        else if(source == dialogBoxItem) 
        {
            textArea.setText(textArea.getText() + "DialogBox Launched\n");
            DialogBox.main(null);
        }
        else if(source == conversionItem) 
        {
            textArea.setText(textArea.getText() + "Conversion Launched\n");
            Conversion.main(null);
        }
        
        else if (source == exitItem)
        {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        Launcher progLaunch = new Launcher();
        progLaunch.setSize(400,300); //for easier review, no required
        progLaunch.setVisible(true);
    }
    
    
}
