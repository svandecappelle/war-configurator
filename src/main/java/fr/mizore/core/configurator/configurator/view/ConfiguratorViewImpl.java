package fr.mizore.core.configurator.configurator.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import fr.mizore.core.configurator.configurator.interfaces.ConfiguratorDisplay;

public class ConfiguratorViewImpl extends JPanel implements ConfiguratorDisplay {

    private static final long serialVersionUID = -1277481901018736538L;
    private JPanel scrollableContent;

    private ComboBoxColor<ComboColored> availableFiles;
    private List<String> alreadyAddedFilesItem;

    public ConfiguratorViewImpl() {
        this.setLayout(new MigLayout());
        this.setBorder(BorderFactory.createTitledBorder("Configuration"));

        this.availableFiles = new ComboBoxColor<ComboColored>();
        this.add(availableFiles, "width 100%, newline");

        this.scrollableContent = new JPanel();
        scrollableContent.setLayout(new MigLayout("fillx"));

        JScrollPane scrollPanel = new JScrollPane(scrollableContent);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(16);

        this.add(scrollPanel, "height 100%, width 100%, newline");
        alreadyAddedFilesItem = new ArrayList<String>();
    }

    @Override
    public JTextField addConfigOption(String text, String value) {
        JPanel oneConfigPanel = new JPanel();
        oneConfigPanel.setLayout(new MigLayout("fillx"));
        JLabel textLabel = new JLabel(text);
        textLabel.setAlignmentX(LEFT_ALIGNMENT);
        JTextField valueLabel = new JTextField(value);
        valueLabel.setBackground(Color.WHITE);
        oneConfigPanel.add(textLabel, "width 40%");
        oneConfigPanel.add(valueLabel, "width 40%");
        this.scrollableContent.add(oneConfigPanel, "width 80%, newline");
        return valueLabel;
    }

    @Override
    public void addFile(ComboColored filename) {
        String fullName = filename.getText();

        if (alreadyAddedFilesItem.contains(fullName)) {
            return;
        }
        alreadyAddedFilesItem.add(fullName);

        StringBuilder croppedFileName = new StringBuilder();
        if (filename.getText().length() > 50) {
            String[] filesEntries = filename.getText().split("/");
            for (String fileEntry : filesEntries) {
                if (croppedFileName.length() > 20) {
                    croppedFileName.append("/...");
                    break;
                } else {
                    croppedFileName.append("/");
                    croppedFileName.append(fileEntry);
                }
            }
            croppedFileName.append("/");
            croppedFileName.append(filesEntries[filesEntries.length - 1]);

            filename.setText(croppedFileName.toString());
            this.availableFiles.addItem(filename);
        } else {
            this.availableFiles.addItem(filename);
        }
    }

    @Override
    public void addFileChangedListener(ActionListener listener) {
        this.availableFiles.addActionListener(listener);
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void shows() {
        this.setVisible(true);
    }

    @Override
    public Integer getFile() {
        return this.availableFiles.getSelectedIndex();
    }

    @Override
    public void clearConfig() {
        this.scrollableContent.removeAll();
        this.scrollableContent.invalidate();
        this.scrollableContent.revalidate();
        this.scrollableContent.repaint();
    }

    @Override
    public void clearFiles() {
        alreadyAddedFilesItem.clear();
        availableFiles.removeAllItems();
    }

    @Override
    public void setSelectedFile(Integer selectionFileIndex) {
        availableFiles.setSelectedIndex(selectionFileIndex);
    }
}