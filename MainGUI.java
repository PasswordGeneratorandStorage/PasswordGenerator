import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JPanel implements ActionListener{
    GridBagLayout gbl;
    GridBagConstraints c;
    JTextField minField;
    JTextField maxField;
    JCheckBox genSpecial;
    JTextArea display;
    JButton submit;
    int min;
    int max;
    Generator generator;

    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.add(new MainGUI());
        jf.pack();
        jf.setVisible(true);
    }

    public MainGUI() {

        generator = new Generator();

        minField = new JTextField(20);
        minField.addActionListener(this);
        minField.setText("Min");
        minField.setActionCommand("minField");
        add(minField);

        maxField = new JTextField(20);
        maxField.addActionListener(this);
        maxField.setText("Max");
        maxField.setActionCommand("maxField");
        add(maxField);

        genSpecial = new JCheckBox();
        genSpecial.addActionListener(this);
        genSpecial.setText("Generate special character?");
        add(genSpecial);

        submit = new JButton();
        submit.addActionListener(this);
        submit.setText("Click me to generate");
        submit.setActionCommand("submit");
        add(submit);

        display = new JTextArea("BLAH");
        display.setEditable(false);
        display.setFont(new Font("arial", Font.BOLD, 30));
        add(display);
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("max")) {

        }
        if(e.getActionCommand().equalsIgnoreCase("min")) {

        }
        if(e.getActionCommand().equalsIgnoreCase("submit")) {
            try {
                min = Integer.parseInt(minField.getText());
            } catch(NumberFormatException nfe) {
                minField.setBackground(Color.red);
                return;
            }
            try {
                max = Integer.parseInt(maxField.getText());
            } catch(NumberFormatException nf) {
                maxField.setBackground(Color.red);
                return;
            }
            String tmp = generator.generatePass(min, max, genSpecial.isSelected());
            display.setText(tmp);
        }


    }
}
