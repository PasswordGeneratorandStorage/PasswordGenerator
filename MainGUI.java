import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JPanel implements ActionListener{
    JTextField minField;
    JTextField maxField;
    JTextField digitField;
    JCheckBox genSpecial;
    JTextArea display;
    JButton submit;
    int min;
    int max;
    int numDigits;
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
        Font font = new Font("arial", Font.BOLD, 30);

        minField = new JTextField(20);
        minField.addActionListener(this);
        minField.setText("Min");
        minField.setActionCommand("minField");
        minField.setFont(font);
        add(minField);

        maxField = new JTextField(20);
        maxField.addActionListener(this);
        maxField.setText("Max");
        maxField.setActionCommand("maxField");
        maxField.setFont(font);
        add(maxField);

        digitField = new JTextField(20);
        digitField.addActionListener(this);
        digitField.setText(Integer.toString(generator.getNumWords()));
        digitField.setActionCommand("Digit Count");
        digitField.setFont(font);
        add(digitField);

        genSpecial = new JCheckBox();
        genSpecial.addActionListener(this);
        genSpecial.setText("Generate special character?");
        genSpecial.setFont(font);
        add(genSpecial);

        submit = new JButton();
        submit.addActionListener(this);
        submit.setText("Click me to generate");
        submit.setActionCommand("submit");
        submit.setFont(font);
        add(submit);

        display = new JTextArea("BLAH");
        display.setEditable(false);
        display.setFont(new Font("arial", Font.BOLD, 30));
        add(display);
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("submit")) {
            try {
                min = Integer.parseInt(minField.getText());
            } catch(NumberFormatException nfe) {
                minField.setBackground(Color.red);
                return;
            }
            try {
                max = Integer.parseInt(maxField.getText());
                numDigits = Integer.parseInt(digitField.getText());
                String tmp = generator.generatePass(min, max, numDigits, genSpecial.isSelected());
                display.setText(tmp);
                maxField.setBackground(Color.lightGray);
                minField.setBackground(Color.lightGray);
            } catch(NumberFormatException nf) {
                maxField.setBackground(Color.red);
                minField.setBackground(Color.red);
            }

        }


    }
}
