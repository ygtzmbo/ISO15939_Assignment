package com.simulator.view.panels;

import javax.swing.*;
import java.awt.*;

public abstract class StepPanel extends JPanel {
    public StepPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
    }

    public abstract String getStepTitle();
    public abstract boolean validateInput();
}
