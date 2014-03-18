package de.stetro.matin.dbw.prak1.gui;

import javax.swing.*;

public class MainWindow {
    private JTabbedPane tabbedPane1;
    public JPanel mainPanel;
    private JTextField XMLFilePathTextField;
    private JButton XMLFilePahtChooser;
    private JButton parseXMLFileButton;
    private JList elementlist;
    private JTextPane textPane1;
    private JComboBox entitySelection;

    public MainWindow() {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
