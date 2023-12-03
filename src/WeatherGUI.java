import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;

public class WeatherGUI {
    JFrame frame;
    JLabel header;
    JTextField cityField;
    JButton button;

    JLabel logoLabel;

    JLabel degree;
    JLabel humidity;
    JLabel wSpeed;

    MyActionListener myActionListener = new MyActionListener();

    WeatherGUI(){
        frame = new JFrame("SogutWeather");
        frame.setSize(600,600);
        frame.setResizable(false);
        frame.setLayout(null);
        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("resources/bg.png")))));    //Frame background image
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        header = new JLabel("Enter City Name");
        header.setBounds(228,50,143,50);
        header.setFont(new Font("SERIF",Font.BOLD,20));

        cityField = new JTextField();
        cityField.setBounds(250,110,100,30);
        cityField.setHorizontalAlignment(SwingConstants.CENTER);
        cityField.getDocument().addDocumentListener(new DocumentListener() {    //To get the name of which was entered to the city field
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCity();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCity();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCity();
            }

            private void updateCity() {
                button.setActionCommand(cityField.getText());
            }
        });
        cityField.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));

        button = new JButton("BRING");
        button.setBounds(262,160,75,30);
        button.addActionListener(myActionListener);
        button.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
        button.setFocusable(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        logoLabel = new JLabel();
        logoLabel.setBounds(235,200,130,130);
        myActionListener.setLogoLabel(logoLabel);

        degree = new JLabel();
        degree.setBounds(195,350,210,30);
        degree.setFont(new Font("SERIF",Font.PLAIN,20));
        degree.setHorizontalAlignment(SwingConstants.CENTER);
        myActionListener.setDegreeLabel(degree);

        humidity = new JLabel();
        humidity.setBounds(195,400,210,30);
        humidity.setFont(new Font("SERIF",Font.PLAIN,20));
        humidity.setHorizontalAlignment(SwingConstants.CENTER);
        myActionListener.setHumidityLabel(humidity);

        wSpeed = new JLabel();
        wSpeed.setBounds(195,450,210,30);
        wSpeed.setFont(new Font("SERIF",Font.PLAIN,20));
        wSpeed.setHorizontalAlignment(SwingConstants.CENTER);
        myActionListener.setWSpeedLabel(wSpeed);

        frame.add(header);
        frame.add(cityField);
        frame.add(button);
        frame.add(logoLabel);
        frame.add(degree);
        frame.add(humidity);
        frame.add(wSpeed);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
