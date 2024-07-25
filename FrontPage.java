package basic;
import javax.swing.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.sql.*;


public class FrontPage extends JFrame implements ActionListener {
    private JButton addPatientButton, addDoctorButton, displayPatientButton, displayDoctorButton,deleteDoctorButton,deletePatientButton;
    private Connection connection;

    public FrontPage() {
        setTitle("Hospital Management System");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        addPatientButton = new JButton("Add Patient");
        addPatientButton.addActionListener(this);
        add(addPatientButton);

        addDoctorButton = new JButton("Add Doctor");
        addDoctorButton.addActionListener(this);
        add(addDoctorButton);
        
        displayPatientButton = new JButton("Display Patient Records");
        displayPatientButton.addActionListener(this);
        add(displayPatientButton);
        
        displayDoctorButton = new JButton("Display Doctor Records");
        displayDoctorButton.addActionListener(this);
        add(displayDoctorButton);
        
        deleteDoctorButton = new JButton("Delete Doctor Records");
        deleteDoctorButton.addActionListener(this);
        add(deleteDoctorButton);
        
        deletePatientButton = new JButton("Delete Patient Records");
        deletePatientButton.addActionListener(this);
        add(deletePatientButton);


        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatientButton) {
            new AddPatientPage();
            dispose(); 
        } else if (e.getSource() == addDoctorButton) {
            new AddDoctorPage();
            dispose(); 
        }else if (e.getSource() == displayPatientButton) {
            displayPatientRecords();
        } else if (e.getSource() == displayDoctorButton) {
            displayDoctorRecords();
        }
        else if (e.getSource() == deleteDoctorButton) {
        	displayDoctorRecords();
        	new deleteDoctorPage ();
            dispose();
        }else if (e.getSource() == deletePatientButton) {
            displayPatientRecords();
            new deletePatientPage ();
            dispose();
        }
    }
    
    private void displayPatientRecords() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "vedu");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");
            
            JFrame patientFrame = new JFrame("Patient Records");
            patientFrame.setLayout(new BorderLayout());
            JPanel panel = new JPanel(new GridLayout(0, 6));
            panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
            patientFrame.getContentPane().add(panel, BorderLayout.CENTER);
            
            panel.add(new JLabel("Patient ID"));
            panel.add(new JLabel("Patient Name"));
            panel.add(new JLabel("Age"));
            panel.add(new JLabel("Gender"));
            panel.add(new JLabel("Disease"));
            panel.add(new JLabel("Mobile no."));
            
            
            while (rs.next()) {
                panel.add(new JLabel(rs.getString("patient_id")));
                panel.add(new JLabel(rs.getString("patient_name")));
                panel.add(new JLabel(rs.getString("age")));
                panel.add(new JLabel(rs.getString("gender")));
                panel.add(new JLabel(rs.getString("disease")));
                panel.add(new JLabel(rs.getString("mob_no")));
                
            }
            
            patientFrame.pack();
            patientFrame.setLocationRelativeTo(null); 
            patientFrame.setVisible(true);
            
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    private void displayDoctorRecords() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "vedu");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");
            
            JFrame doctorFrame = new JFrame("Doctor Records");
            JPanel panel = new JPanel(new GridLayout(0, 4));
            doctorFrame.getContentPane().add(panel);
            panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
            doctorFrame.getContentPane().add(panel, BorderLayout.CENTER);
            
            panel.add(new JLabel("Doctor ID"));
            panel.add(new JLabel("Doctor Name"));
            panel.add(new JLabel("Specialization"));
            panel.add(new JLabel("Qualification"));
            
            while (rs.next()) {
                panel.add(new JLabel(rs.getString("doctor_id")));
                panel.add(new JLabel(rs.getString("doctor_name")));
                panel.add(new JLabel(rs.getString("specialization")));
                panel.add(new JLabel(rs.getString("qualification")));
            }
            
            doctorFrame.pack();
            doctorFrame.setLocationRelativeTo(null);
            doctorFrame.setVisible(true);
            
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new FrontPage();
            }
        });
    }
}

class AddPatientPage extends JFrame implements ActionListener {
    private JTextField patientNameField, patientIDField, MobnoField, diseaseField;
    private JButton addButton, clearButton;
    private Connection connection;
    private JSpinner ageSpinner;
    private JComboBox<String> genderComboBox;
    private JDateChooser dobChooser;


    public AddPatientPage() {
        setTitle("Add Patient");
        setSize(500, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("Patient ID:"));
        patientIDField = new JTextField();
        inputPanel.add(patientIDField);
        inputPanel.add(new JLabel("Patient Name:"));
        patientNameField = new JTextField();
        inputPanel.add(patientNameField);
     
        inputPanel.add(new JLabel("Age:"));
        SpinnerModel ageModel = new SpinnerNumberModel(5, 0, 150, 1); 
        ageSpinner = new JSpinner(ageModel);
        inputPanel.add(ageSpinner);
        inputPanel.add(new JLabel("Disease:"));
        diseaseField = new JTextField();
        inputPanel.add(diseaseField);
        inputPanel.add(new JLabel("Gender:"));
        String[] genders = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genders);
        inputPanel.add(genderComboBox);
        inputPanel.add(new JLabel("DOB:"));
        dobChooser = new JDateChooser();
        inputPanel.add(dobChooser);
        inputPanel.add(new JLabel("Mob no:"));
        MobnoField = new JTextField();
        inputPanel.add(MobnoField);
        addButton = new JButton("Add Patient");
        addButton.addActionListener(this);
        inputPanel.add(addButton);
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        inputPanel.add(clearButton);
        
        JScrollPane scrollPane = new JScrollPane(inputPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.CENTER);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "vedu");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String patientID = patientIDField.getText();
            String patientName = patientNameField.getText();
            String age = ageSpinner.getValue().toString();
            String disease = diseaseField.getText();
            String gender =genderComboBox.getSelectedItem().toString();
            String dob = dobChooser.getDate().toString();
            String mobNo = MobnoField.getText();

            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO patients (patient_id, patient_name, age, disease, gender, dob, mob_no) VALUES (?, ?, ?, ?, ?, ?, ?)");
                statement.setString(1, patientID);
                statement.setString(2, patientName);
                statement.setString(3, age);
                statement.setString(4, disease);
                statement.setString(5, gender);
                statement.setString(6, dob);
                statement.setString(7, mobNo);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Patient added successfully!");
                clearFields();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == clearButton) {
            clearFields();
        }
    }

    private void clearFields() {
        patientIDField.setText("");
        patientNameField.setText("");
        ageSpinner.setValue("");
        diseaseField.setText("");
        genderComboBox.setSelectedItem("");
        dobChooser.setDate(null);
        MobnoField.setText("");
    }
}

class AddDoctorPage extends JFrame implements ActionListener {
    private JTextField DoctoridField,nameField, specializationField, qualificationField;
    private JButton addButton, clearButton;
    private Connection connection;

    public AddDoctorPage() {
        setTitle("Add Doctor");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Doctor Id:"));
        DoctoridField = new JTextField();
        inputPanel.add(DoctoridField);
        inputPanel.add(new JLabel("Doctor Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Specialization:"));
        specializationField = new JTextField();
        inputPanel.add(specializationField);
        inputPanel.add(new JLabel("Qualification:"));
        qualificationField = new JTextField();
        inputPanel.add(qualificationField);
        addButton = new JButton("Add Doctor");
        addButton.addActionListener(this);
        inputPanel.add(addButton);
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.CENTER);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "vedu");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
        	String ID  = DoctoridField.getText();
            String name = nameField.getText();
            String specialization = specializationField.getText();
            String qualification = qualificationField.getText();

            try {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO doctors (doctor_id, doctor_name, specialization, qualification) VALUES (?, ?, ?, ?)");
                statement.setString(1, ID);
                statement.setString(2, name);
                statement.setString(3, specialization);
                statement.setString(4, qualification);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Doctor added successfully!");
                clearFields();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == clearButton) {
            clearFields();
        }
    }

    private void clearFields() {
    	DoctoridField.setText("");
        nameField.setText("");
        specializationField.setText("");
        qualificationField.setText("");
    }
}

class deletePatientPage extends JFrame implements ActionListener {
    private JTextField patientIDFieldd;
    private JButton deleteButton, clearButton;
    private Connection connection;

    public deletePatientPage() {
        setTitle("Delete Patient");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Patient ID:"));
        patientIDFieldd = new JTextField();
        inputPanel.add(patientIDFieldd);
        
        deleteButton = new JButton("Delete Patient");
        deleteButton.addActionListener(this);
        inputPanel.add(deleteButton);
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.CENTER);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "vedu");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            String patientIDdelete = patientIDFieldd.getText();
            

            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM patients WHERE patient_id=?");
                statement.setString(1, patientIDdelete);
               
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Patient deleted successfully!");
                clearFields();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == clearButton) {
            clearFields();
        }
    }

    private void clearFields() {
        patientIDFieldd.setText("");
    }
}

class deleteDoctorPage extends JFrame implements ActionListener {
    private JTextField doctorIDFieldd;
    private JButton deleteButton, clearButton;
    private Connection connection;

    public deleteDoctorPage() {
        setTitle("Delete Doctor");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Doctor ID:"));
        doctorIDFieldd = new JTextField();
        inputPanel.add(doctorIDFieldd);
        
        deleteButton = new JButton("Delete doctor");
        deleteButton.addActionListener(this);
        inputPanel.add(deleteButton);
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.CENTER);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "vedu");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            String patientIDdelete = doctorIDFieldd.getText();
            

            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM doctors WHERE doctor_id=?");
                statement.setString(1, patientIDdelete);
               
                statement.executeUpdate();
                JOptionPane.showMessageDialog(this, "Doctor deleted successfully!");
                clearFields();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == clearButton) {
            clearFields();
        }
    }

    private void clearFields() {
        doctorIDFieldd.setText("");
    }
}