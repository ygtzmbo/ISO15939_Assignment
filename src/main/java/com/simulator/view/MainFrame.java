package com.simulator.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout stackLayout;
    private final JPanel centerStage;
    private final JButton forwardBtn;
    private final JButton backwardBtn;
    private final JLabel titleDisplay;
    private final JLabel progressDisplay;

    public MainFrame() {
        setTitle("ISO 15939 Measurement Strategy Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Header Section
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(31, 58, 147));
        topBar.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        titleDisplay = new JLabel("Measurement Lifecycle");
        titleDisplay.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleDisplay.setForeground(Color.WHITE);
        topBar.add(titleDisplay, BorderLayout.WEST);

        progressDisplay = new JLabel("Phase 1 / 5");
        progressDisplay.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        progressDisplay.setForeground(new Color(236, 240, 241));
        topBar.add(progressDisplay, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // Main Stage Section
        stackLayout = new CardLayout();
        centerStage = new JPanel(stackLayout);
        centerStage.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        centerStage.setBackground(new Color(245, 246, 250));
        add(centerStage, BorderLayout.CENTER);

        // Navigation Bar
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        navBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        backwardBtn = new JButton("Previous");
        backwardBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backwardBtn.setPreferredSize(new java.awt.Dimension(100, 35));
        backwardBtn.setEnabled(false);

        forwardBtn = new JButton("Proceed");
        forwardBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        forwardBtn.setPreferredSize(new java.awt.Dimension(120, 35));
        forwardBtn.setBackground(new Color(39, 174, 96));
        forwardBtn.setForeground(Color.WHITE);
        forwardBtn.setFocusPainted(false);

        navBar.add(backwardBtn);
        navBar.add(forwardBtn);
        add(navBar, BorderLayout.SOUTH);
    }

    public void addStepPanel(JPanel component, String identifier) {
        centerStage.add(component, identifier);
    }

    public void showStep(String identifier) {
        stackLayout.show(centerStage, identifier);
    }

    public JButton getBtnNext() { return forwardBtn; }
    public JButton getBtnBack() { return backwardBtn; }
    public void setStepTitle(String text) { titleDisplay.setText(text); }
    public void setStepIndicator(int current, int max) {
        progressDisplay.setText("Phase " + current + " of " + max);
    }
}
