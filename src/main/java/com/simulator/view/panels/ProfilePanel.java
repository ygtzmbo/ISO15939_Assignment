package com.simulator.view.panels;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends StepPanel {
    private JTextField txtUserName;
    private JTextField txtSchool;
    private JTextField txtSessionName;

    public ProfilePanel() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        formPanel.setOpaque(false);

        JLabel lblUser = new JLabel("User Name:");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUserName = new JTextField();
        txtUserName.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblSchool = new JLabel("School / Institution:");
        lblSchool.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSchool = new JTextField();
        txtSchool.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblSession = new JLabel("Session Name:");
        lblSession.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSessionName = new JTextField();
        txtSessionName.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        formPanel.add(lblUser);
        formPanel.add(txtUserName);
        formPanel.add(lblSchool);
        formPanel.add(txtSchool);
        formPanel.add(lblSession);
        formPanel.add(txtSessionName);

        add(formPanel, BorderLayout.NORTH);
        
        JLabel lblHint = new JLabel("Please enter your details to begin the measurement process.");
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        add(lblHint, BorderLayout.SOUTH);
    }

    @Override
    public String getStepTitle() { return "Step 1: Measurement Profile"; }

    @Override
    public boolean validateInput() {
        if (txtUserName.getText().trim().isEmpty() || 
            txtSchool.getText().trim().isEmpty() || 
            txtSessionName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public String getUserName() { return txtUserName.getText(); }
    public String getSchool() { return txtSchool.getText(); }
    public String getSessionName() { return txtSessionName.getText(); }
}
