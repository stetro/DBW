package de.stetro.matin.dbw.prak1.gui;

import de.stetro.matin.dbw.prak1.entities.employees.Employees;
import de.stetro.matin.dbw.prak1.entities.products.Products;
import de.stetro.matin.dbw.prak1.parser.XmlParser;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MainWindow {
    private final XmlParser xmlParser;
    private JTabbedPane tabbedPane1;
    public JPanel mainPanel;
    private JTextField xmlFilePathTextField;
    private JButton xmlFilePahtChooser;
    private JButton parseXMLFileButton;
    private JList elementlist;
    private JTextPane xmlTextField;
    private JComboBox entitySelection;
    private JButton button1;
    private JLabel applicationStatus;

    private Products products;
    private Employees employees;

    public MainWindow() {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 450);
        frame.setVisible(true);

        xmlParser = new XmlParser();


        xmlFilePahtChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                loadXmlFilePathIntoXmlFilePathTextField();
            }
        });

        parseXMLFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

                String filePath = xmlFilePathTextField.getText();
                String selectedItem = (String) entitySelection.getSelectedItem();
                try {
                    if (selectedItem == "Product") {
                        products = xmlParser.parseProductsWithSelectedPath(filePath);
                        DefaultListModel listModel = new DefaultListModel();
                        for (Products.Product p : products.getProduct()) {
                            listModel.addElement(p.getName());
                        }
                        elementlist.setModel(listModel);
                    } else {

                        employees = xmlParser.parseEmployeesWithSelectedPath(filePath);
                        DefaultListModel listModel = new DefaultListModel();
                        for (Employees.Employee e : employees.getEmployee()) {
                            listModel.addElement(e.getName());
                        }
                        elementlist.setModel(listModel);
                    }
                } catch (Exception e) {
                    applicationStatus.setText(e.getMessage());
                }
            }
        });
        entitySelection.addItem("Product");
        entitySelection.addItem("Employee");
        entitySelection.setSelectedIndex(0);
    }

    private void loadXmlFilePathIntoXmlFilePathTextField() {
        JFileChooser fc;
        fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(mainPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            xmlFilePathTextField.setText(file.getAbsolutePath());
        } else {
            applicationStatus.setText("Error selecting xml file.");
        }
    }


}
