package fr.mizore.core.configurator.configurator.interfaces;

import java.awt.event.ActionListener;

import javax.swing.JTextField;

import fr.mizore.core.configurator.IsComponent;
import fr.mizore.core.configurator.configurator.view.ComboColored;

public interface ConfiguratorDisplay extends IsComponent {
    void shows();

    JTextField addConfigOption(String text, String value);

    void addFile(ComboColored filename);

    void addFileChangedListener(ActionListener listener);

    Integer getFile();

    void clearConfig();

    void clearFiles();

    void setSelectedFile(Integer selectionFileIndex);

}
