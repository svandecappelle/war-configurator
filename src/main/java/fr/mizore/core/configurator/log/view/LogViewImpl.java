package fr.mizore.core.configurator.log.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import fr.mizore.core.configurator.log.interfaces.LogDisplay;

public class LogViewImpl extends JFrame implements LogDisplay {

    private static final long serialVersionUID = -2906446819712641700L;

    private JTextArea messageTextArea;

    public LogViewImpl() {
        super("Logs");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new GridLayout(1, 0));
        Box leftPanel = new Box(BoxLayout.Y_AXIS);

        JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BorderFactory.createTitledBorder(null, "Status Console", TitledBorder.LEFT, TitledBorder.TOP, new Font("null", Font.BOLD, 12), Color.BLUE));
        final JScrollPane sp = new JScrollPane(getMessageTextArea());
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messagePanel.add(sp);

        leftPanel.add(messagePanel);
        mainPanel.add(leftPanel);
        this.add(mainPanel);
    }

    @Override
    public Component asComponent() {
        return this;
    }

    protected JTextArea getMessageTextArea() {
        this.messageTextArea = new JTextArea("", 10, 19);
        this.messageTextArea.setEditable(false);
        this.messageTextArea.setFont(new Font(null, Font.PLAIN, 20));
        this.messageTextArea.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        DefaultCaret caret = (DefaultCaret) messageTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        return this.messageTextArea;
    }

    @Override
    public void shows() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void addLog(String message) {
        this.messageTextArea.append(message + "\n");
    }

}
