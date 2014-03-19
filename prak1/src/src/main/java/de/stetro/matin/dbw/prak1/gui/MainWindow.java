package de.stetro.matin.dbw.prak1.gui;

import de.stetro.matin.dbw.prak1.daos.EmployeeDao;
import de.stetro.matin.dbw.prak1.daos.ProductDao;
import de.stetro.matin.dbw.prak1.entities.employees.Employee;
import de.stetro.matin.dbw.prak1.entities.employees.Employees;
import de.stetro.matin.dbw.prak1.entities.products.Product;
import de.stetro.matin.dbw.prak1.entities.products.Products;
import de.stetro.matin.dbw.prak1.util.XmlFactory;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MainWindow {
    public JPanel mainPanel;
    private XmlFactory xmlFactory;
    private ProductDao productDao;
    private EmployeeDao employeeDao;
    private JTabbedPane tabbedPane1;
    private JTextField xmlFilePathTextField;
    private JButton xmlFilePahtChooser;
    private JButton parseXMLFileButton;
    private JList elementlist;
    private JTextPane xmlTextField;
    private JComboBox entitySelection;
    private JButton insertElementToDBButton;
    private JLabel applicationStatus;
    private JButton refreshButton;
    private JComboBox entitySelection2;
    private JList databaseList;

    private JTextPane xmlTextPane;
    private JButton showAllButton;
    private Products products;
    private Employees employees;

    public MainWindow() {
        prepareJFrame();
        setupMouseListener();
        setupEntitySelection();
        instantiateParserAndDaos();
    }

    private void instantiateParserAndDaos() {
        try {
            xmlFactory = new XmlFactory();
            productDao = new ProductDao();
            employeeDao = new EmployeeDao();
        } catch (Exception e) {
            applicationStatus.setText(e.getMessage());
        }
    }

    private void setupEntitySelection() {
        entitySelection.addItem("Product");
        entitySelection.addItem("Employee");
        entitySelection2.addItem("Product");
        entitySelection2.addItem("Employee");
        entitySelection.setSelectedIndex(0);
    }

    private void setupMouseListener() {
        xmlFilePahtChooser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                selectXmlFilePath();
            }
        });
        parseXMLFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                parseSelectedXmlFile();
            }
        });
        elementlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                fillTextAreaWithCreateStatement();
            }
        });
        insertElementToDBButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                addSelectedObjectToDB();
            }
        });
        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                refreshDatabaseList();
            }
        });
        databaseList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                fillXmlStatementInTextArea();
            }
        });
        showAllButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String selectedItem = (String) entitySelection2.getSelectedItem();
                try {
                    if (selectedItem.equals("Product")) {
                        xmlTextPane.setText(xmlFactory.marshallProducts(products));
                    } else {
                        xmlTextPane.setText(xmlFactory.marshallEmployees(employees));
                    }
                } catch (Exception e) {
                    applicationStatus.setText(e.getMessage());
                }
            }
        });
    }

    private void fillXmlStatementInTextArea() {
        String selectedItem = (String) entitySelection2.getSelectedItem();
        try {
            if (selectedItem.equals("Product")) {
                Product product = products.getProduct().get(databaseList.getSelectedIndex());
                xmlTextPane.setText(xmlFactory.marshallProduct(product));
            } else {
                Employee employee = employees.getEmployee().get(databaseList.getSelectedIndex());
                xmlTextPane.setText(xmlFactory.marshallEmployee(employee));
            }
        } catch (Exception e) {
            applicationStatus.setText(e.getMessage());
        }
    }

    private void refreshDatabaseList() {
        String selectedItem = (String) entitySelection2.getSelectedItem();
        try {
            if (selectedItem.equals("Product")) {
                Products products = productDao.getAllProducts();
                DefaultListModel listModel = new DefaultListModel();
                for (Product p : products.getProduct()) {
                    listModel.addElement(p.getId() + " - " + p.getName());
                }
                databaseList.setModel(listModel);
            } else {
                Employees employees = employeeDao.getAllEmployees();
                DefaultListModel listModel = new DefaultListModel();
                for (Employee e : employees.getEmployee()) {
                    listModel.addElement(e.getId() + " - " + e.getName());
                }
                databaseList.setModel(listModel);
            }
        } catch (Exception e) {
            applicationStatus.setText(e.getMessage());
        }
    }

    private void addSelectedObjectToDB() {
        String selectedItem = (String) entitySelection.getSelectedItem();
        if (selectedItem.equals("Product")) {
            Product product = products.getProduct().get(elementlist.getSelectedIndex());
            try {
                productDao.createProduct(product);
                applicationStatus.setText(product.getName() + " added ...");
            } catch (Exception e) {
                applicationStatus.setText(e.getMessage());
            }

        } else {
            Employee employee = employees.getEmployee().get(elementlist.getSelectedIndex());
            try {
                employeeDao.createProduct(employee);
                applicationStatus.setText(employee.getName() + " added ...");
            } catch (Exception e) {
                applicationStatus.setText(e.getMessage());
            }
        }
    }

    private void fillTextAreaWithCreateStatement() {
        String selectedItem = (String) entitySelection.getSelectedItem();
        if (selectedItem.equals("Product")) {
            Product product = products.getProduct().get(elementlist.getSelectedIndex());
            xmlTextField.setText(productDao.getCreateSqlStatement(product));
        } else {
            Employee employee = employees.getEmployee().get(elementlist.getSelectedIndex());
            xmlTextField.setText(employeeDao.getCreateSqlStatement(employee));
        }
    }

    private void prepareJFrame() {
        JFrame frame = new JFrame("MainWindow");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(600, 500);
        frame.setVisible(true);
    }

    private void parseSelectedXmlFile() {
        String filePath = xmlFilePathTextField.getText();
        String selectedItem = (String) entitySelection.getSelectedItem();
        try {
            if (selectedItem.equals("Product")) {
                products = xmlFactory.parseProductsWithSelectedPath(filePath);
                DefaultListModel listModel = new DefaultListModel();
                for (Product p : products.getProduct()) {
                    listModel.addElement(p.getName());
                }
                elementlist.setModel(listModel);
            } else {
                employees = xmlFactory.parseEmployeesWithSelectedPath(filePath);
                DefaultListModel listModel = new DefaultListModel();
                for (Employee e : employees.getEmployee()) {
                    listModel.addElement(e.getName());
                }
                elementlist.setModel(listModel);
            }
        } catch (Exception e) {
            applicationStatus.setText(e.getMessage());
        }
    }

    private void selectXmlFilePath() {
        JFileChooser fc;
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        int returnVal = fc.showOpenDialog(mainPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            xmlFilePathTextField.setText(file.getAbsolutePath());
        } else {
            applicationStatus.setText("Error selecting xml file.");
        }
    }
}
