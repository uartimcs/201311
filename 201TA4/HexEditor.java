import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.*;

public class HexEditor extends JFrame implements ActionListener {
    //Q4(a) items
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fileMenu = new JMenu("File");
    private JMenuItem loadMenuItem = new JMenuItem("Load");
    private JTextArea textArea1 = new JTextArea("", 5, 10);
    //Q4(c)
    private static final int EOF = -1;
    private String fileName = null;
    private boolean loadStatus = false;
    
    //Q4(d) items
    private JLabel label1 = new JLabel(" ");
    private JTextArea textArea2 = new JTextArea("", 5, 20);
    private JButton button1 = new JButton("Update hex");
    
    //Q4(f) items
    private JMenuItem saveMenuItem = new JMenuItem("Save");
    
    public HexEditor() {
        //Q4(a) Items
//        add(textArea1);
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        fileMenu.add(loadMenuItem);
        
        //Q4(f)
        fileMenu.add(saveMenuItem);

        //Q4(d)
        add(textArea1, BorderLayout.WEST);
        add(label1, BorderLayout.CENTER);
        add(textArea2, BorderLayout.EAST);
        add(button1, BorderLayout.SOUTH);
        
        //actionListener Q4(c)
        loadMenuItem.addActionListener(this);
        //actionListener Q4(e)
        button1.addActionListener(this);
        //actionListener Q4(f)
        saveMenuItem.addActionListener(this);
                
        //Routine Operations
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }
    
    public void actionPerformed(ActionEvent e) {
        Object r = e.getSource();
        if(r == loadMenuItem) {
            fileName = JOptionPane.showInputDialog("Please input the file name");
            if(fileName == null) {
                JOptionPane.showMessageDialog(null, "Please specify the file");
            }
            else {
                File file = new File(fileName);
                String messageLoad = "";
                int byteRead;
                try {
                    InputStream is = new FileInputStream(file);
                    while((byteRead = is.read()) != EOF) {
                        messageLoad += (char) byteRead;
                    }
                    textArea1.setText(messageLoad);
                    loadStatus = true;
                    pack();
                    is.close();                    
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        else if(r == button1) {
            String messageStandBy = textArea1.getText();
            byte[] conversion = messageStandBy.getBytes();
            
            String hexMessage = "";
            for (int i = 0; i < conversion.length; i++) {
                String result = Integer.toHexString(conversion[i]).toUpperCase();
                if(result.length() == 1) {
                    result = "0" + result;
                }
                hexMessage += result + " ";
            }
            textArea2.setText(hexMessage);
            pack();            
        }
        
        else if (r == saveMenuItem) {
            if(!loadStatus) {
                fileName = JOptionPane.showInputDialog("Please enter the file name");
                
                if(fileName == null) {
                    JOptionPane.showMessageDialog(null, "Please specify the file");
                }
                else {
                    loadStatus = true;
                }                
            }            
            try {
                File file = new File(fileName);
                String messagePresent = textArea1.getText();
                OutputStream os = new FileOutputStream(file);
                byte[] writeOut = messagePresent.getBytes();
                os.write(writeOut);
                os.close();
                pack(); 
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }            
        }
    }
}