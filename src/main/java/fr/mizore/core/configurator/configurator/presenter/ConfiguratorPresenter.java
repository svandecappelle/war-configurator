package fr.mizore.core.configurator.configurator.presenter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.google.inject.Inject;
import com.google.inject.Provider;

import fr.mizore.core.configurator.SimpleConfiguratorPresenter;
import fr.mizore.core.configurator.app.presenter.PresenterApplication;
import fr.mizore.core.configurator.configurator.interfaces.ConfiguratorDisplay;
import fr.mizore.core.configurator.configurator.view.ComboColored;

public class ConfiguratorPresenter extends SimpleConfiguratorPresenter<ConfiguratorDisplay> {

    private static final String OUTPUT_FILE = "<FILENAME>-configured.war";
    protected static final Logger logger = Logger.getLogger("Executor");

    private File file;
    private boolean skipReloadingFile = true;
    private Map<String, Map<String, String>> modifiedValues;
    private String currentFileModification;
    private List<String> containsFilters;
    private LinkedHashSet<String> configurableFiles;

    private class Configurator implements Runnable {

        @Override
        public void run() {
            logger.info("Storing values into file");
            try {
                ZipFile zipFile = new ZipFile(file);
                final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(OUTPUT_FILE.replace("<FILENAME>", file.getName().split(".war")[0])));
                for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();) {
                    ZipEntry entryIn = e.nextElement();

                    if (!isAlreadyModifiedFile(entryIn.getName())) {
                        zos.putNextEntry(entryIn);
                        InputStream is = zipFile.getInputStream(entryIn);

                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = (is.read(buf))) > 0) {
                            zos.write(buf, 0, len);
                        }
                    } else {
                        zos.putNextEntry(new ZipEntry(entryIn.getName()));
                        InputStream is = zipFile.getInputStream(entryIn);

                        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String line;
                        while ((line = br.readLine()) != null) {
                            byte[] buf = getNewLine(entryIn.getName(), line).getBytes();
                            zos.write(buf, 0, buf.length);
                        }
                    }

                    zos.closeEntry();

                }
                zos.close();
                zipFile.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            applicationProvider.get().loadingScreen(false);
        }

    }

    @Inject
    private Provider<PresenterApplication> applicationProvider;

    @Inject
    public ConfiguratorPresenter(ConfiguratorDisplay display) {
        super(display);
        modifiedValues = new LinkedHashMap<String, Map<String, String>>();
        containsFilters = new ArrayList<String>();
        configurableFiles = new LinkedHashSet<String>();
        containsFilters.add("^((?!i18n).)*$");
    }

    @Override
    public void configure() {
        applicationProvider.get().loadingScreen(true);
        new Thread(new Configurator()).start();
    }

    private String getNewLine(String fileName, String line) {

        if (modifiedValues.containsKey(fileName)) {
            for (Entry<String, String> properties : modifiedValues.get(fileName).entrySet()) {
                String pattern = "^" + properties.getKey() + "=.*$";
                Pattern patternPropertyLine = Pattern.compile(pattern);
                Matcher matcherPropertyLine = patternPropertyLine.matcher(line);
                if (matcherPropertyLine.matches()) {
                    return line.replaceAll(pattern, properties.getKey() + "=" + properties.getValue()).concat("\n");
                }
            }
            return line.concat("\n");
        } else {
            return line.concat("\n");
        }
    }

    @Override
    public void onBind() {
        super.onBind();

        this.display.addFileChangedListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changeConfigFile();
            }
        });
    }

    private void changeConfigFile() {
        Integer selectionFileIndex = display.getFile();
        if (selectionFileIndex != -1 && !skipReloadingFile) {
            String filename = (new ArrayList<String>(configurableFiles)).get(selectionFileIndex);
            redrawFilesList();
            display.clearConfig();
            display.setSelectedFile(selectionFileIndex);

            this.currentFileModification = filename;
            try {
                ZipFile zipFile = new ZipFile(file);
                ZipEntry entryIn = new ZipEntry(filename);
                InputStream is = zipFile.getInputStream(entryIn);

                Properties prop = new Properties();
                prop.load(is);
                for (Entry<Object, Object> property : prop.entrySet()) {
                    Object key = property.getKey();

                    String defaultValue;
                    if (property.getValue() != null) {
                        defaultValue = property.getValue().toString();
                    } else {
                        defaultValue = "";
                    }
                    if (key != null) {
                        JTextField field = display.addConfigOption(key.toString(), property.getValue().toString());
                        registerOption(key.toString(), field, defaultValue);
                    }

                }
                zipFile.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void redrawFilesList() {
        display.clearFiles();
        System.out.println("Number of files: " + configurableFiles.size());
        for (String fileName : configurableFiles) {
            if (this.isAlreadyModifiedFile(fileName)) {
                System.out.println("add modified file: " + fileName);
                display.addFile(new ComboColored(fileName, Color.GREEN));
            } else {
                System.out.println("add initial file: " + fileName);
                display.addFile(new ComboColored(fileName));
            }
        }
    }

    private void registerOption(final String key, final JTextField configOption, final String defaultValue) {

        if (isAlreadyModified(currentFileModification, key)) {
            configOption.setBackground(Color.GREEN);
            configOption.setText(this.modifiedValues.get(currentFileModification).get(key));
            configOption.setToolTipText("Default value: ".concat(defaultValue));
        }

        configOption.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                editKey(key, configOption.getText(), configOption, defaultValue);
            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {
                editKey(key, configOption.getText(), configOption, defaultValue);
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                editKey(key, configOption.getText(), configOption, defaultValue);
            }
        });
    }

    private boolean isAlreadyModified(String currentFile, String key) {
        return this.modifiedValues.containsKey(currentFile) && this.modifiedValues.get(currentFile).containsKey(key);
    }

    private void editKey(final String key, final String text, final JTextField configOption, String defaultValue) {
        System.out.println("Editing key: " + key + "=" + text);
        boolean needRedrawFileList = false;

        if (!this.modifiedValues.containsKey(currentFileModification)) {
            this.modifiedValues.put(currentFileModification, new LinkedHashMap<String, String>());
            needRedrawFileList = true;
        }

        if (!isAlreadyModified(currentFileModification, key)) {
            configOption.setToolTipText("Default value: ".concat(defaultValue));
        }

        this.modifiedValues.get(currentFileModification).put(key, text);
        configOption.setBackground(Color.GREEN);

        if (needRedrawFileList) {
            skipReloadingFile = true;
            Integer selectionFileIndex = display.getFile();
            redrawFilesList();
            display.setSelectedFile(selectionFileIndex);
            skipReloadingFile = false;
        }
    }

    @Override
    public void onRevealDisplay() {
        modifiedValues.clear();
        this.display.shows();
    }

    public void init(File file) {
        this.file = file;
        this.launchConfiguration();
    }

    private void launchConfiguration() {
        skipReloadingFile = false;
        configurableFiles.clear();
        if (this.file != null) {
            try {

                ZipFile zipfile = new ZipFile(this.file);
                searchPropertiesFiles(zipfile);
                redrawFilesList();
                zipfile.close();
                skipReloadingFile = false;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private Collection<String> searchPropertiesFiles(ZipFile file) {
        Collection<String> propertiesFiles = new ArrayList<String>();
        Enumeration<? extends ZipEntry> files = file.entries();
        while (files.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) files.nextElement();
            String entryName = zipEntry.getName();

            if (!containsFilters.isEmpty()) {
                for (String filter : containsFilters) {
                    Pattern patternFilter = Pattern.compile(filter);
                    Matcher matcherFilter = patternFilter.matcher(entryName);
                    if (entryName.endsWith(".properties") && matcherFilter.find()) {
                        System.out.println("Add an available config file: " + zipEntry.getName());
                        configurableFiles.add(zipEntry.getName());
                        propertiesFiles.add(zipEntry.getName());
                    }
                }
            } else if (entryName.endsWith(".properties")) {
                configurableFiles.add(entryName);
                propertiesFiles.add(zipEntry.getName());
            }

        }
        return propertiesFiles;
    }

    private boolean isAlreadyModifiedFile(String file) {
        return this.modifiedValues.containsKey(file);
    }
}
