package com.simulator.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton btnNext;
    private JButton btnBack;
    private JLabel lblStepTitle;
    private JLabel lblStepIndicator;

    public MainFrame() {
        setTitle("ISO 15939 Measurement Process Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblStepTitle = new JLabel("ISO 15939 Simulator");
        lblStepTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblStepTitle.setForeground(Color.WHITE);
        headerPanel.add(lblStepTitle, BorderLayout.WEST);

        lblStepIndicator = new JLabel("Step 1 of 5");
        lblStepIndicator.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblStepIndicator.setForeground(Color.WHITE);
        headerPanel.add(lblStepIndicator, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Content
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentPanel, BorderLayout.CENTER);

        // Footer / Navigation
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setEnabled(false);

        btnNext = new JButton("Next");
        btnNext.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnNext.setBackground(new Color(39, 174, 96));
        btnNext.setForeground(Color.WHITE);
        btnNext.setFocusPainted(false);

        footerPanel.add(btnBack);
        footerPanel.add(btnNext);
        add(footerPanel, BorderLayout.SOUTH);
    }

    public void addStepPanel(JPanel panel, String name) {
        contentPanel.add(panel, name);
    }

    public void showStep(String name) {
        cardLayout.show(contentPanel, name);
    }

    public JButton getBtnNext() { return btnNext; }
    public JButton getBtnBack() { return btnBack; }
    public void setStepTitle(String title) { lblStepTitle.setText(title); }
    public void setStepIndicator(int current, int total) {
        lblStepIndicator.setText("Step " + current + " of " + total);
    }
}
