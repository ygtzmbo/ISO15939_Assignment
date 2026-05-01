package com.simulator;

import com.simulator.controller.SimulatorController;
import com.simulator.model.MeasurementModel;
import com.simulator.view.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set Look and Feel to System default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MeasurementModel model = new MeasurementModel();
            MainFrame view = new MainFrame();
            new SimulatorController(view, model);
            view.setVisible(true);
        });
    }
}
