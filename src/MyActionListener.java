import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class MyActionListener implements ActionListener {

    float degree;
    Object humidity;
    Object wSpeed;

    JLabel labelDegree;
    JLabel labelHumidity;
    JLabel labelWSpeed;

    JLabel logoLabel;

    @Override
    public void actionPerformed(ActionEvent e) {
        String API_KEY = APIKey.getAPI();   // Openweathermap api key goes here
        String LOC = e.getActionCommand();  // Location here
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOC + "&appid=" + API_KEY;

        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            Map<String, Object> respMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

            degree = (float) ((double) mainMap.get("temp") - 273.15);
            humidity = mainMap.get("humidity");
            wSpeed = windMap.get("speed");


            labelDegree.setText("Temperature = " + degree + " ℃");
            labelHumidity.setText("Humidity = " + humidity + " %");
            labelWSpeed.setText("Wind Speed = " + wSpeed + " km/h");

            double humidityInt = (double) humidity;
            double wSpeedInt = (double) wSpeed;

            if (degree >= 25 && wSpeedInt >= 0 && wSpeedInt <= 15) {
                logoLabel.setIcon(new ImageIcon("resources/sunny.png"));
            } else if (degree >= 19 && degree < 25 && humidityInt >= 20 && humidityInt <= 90 && wSpeedInt >= 0 && wSpeedInt <= 15) {
                logoLabel.setIcon(new ImageIcon("resources/cloudysunny.png"));
            } else if (degree >= 10 && degree < 19 && humidityInt >= 20 && humidityInt <= 70 && wSpeedInt >= 0 && wSpeedInt <= 20) {
                logoLabel.setIcon(new ImageIcon("resources/cloudy.png"));
            } else if (degree >= 0 && degree < 19 && humidityInt >= 71 && humidityInt <= 100 && wSpeedInt >= 0 && wSpeedInt <= 25) {
                logoLabel.setIcon(new ImageIcon("resources/rainy.png"));
            } else if (degree >= 0 && degree < 19 && humidityInt >= 80 && humidityInt <= 100 && wSpeedInt > 25) {
                logoLabel.setIcon(new ImageIcon("resources/thunder.png"));
            } else if (degree >= 0 && degree < 19 && humidityInt >= 0 && humidityInt <= 70) {
                logoLabel.setIcon(new ImageIcon("resources/cloudy.png"));
            } else if (degree < 0 && humidityInt >= 80 && humidityInt <= 100 && wSpeedInt >= 0 && wSpeedInt <= 15) {
                logoLabel.setIcon(new ImageIcon("resources/snowy.png"));
            } else {
                logoLabel.setIcon(new ImageIcon("resources/error.png"));
            }

        } catch (Exception exception) {
            labelDegree.setText("City NOT Found");
            labelHumidity.setText("City NOT Found");
            labelWSpeed.setText("City NOT Found");
            System.out.println(exception.getMessage());
        }
    }

    public static Map<String, Object> jsonToMap (String str){
        return new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );
    }

    public void setDegreeLabel(JLabel labelDegree) {
        this.labelDegree = labelDegree;
    }

    public void setHumidityLabel(JLabel labelHumidity) {
        this.labelHumidity = labelHumidity;
    }

    public void setWSpeedLabel(JLabel labelWSpeed) {
        this.labelWSpeed = labelWSpeed;
    }

    public void setLogoLabel(JLabel logoLabel) {
        this.logoLabel = logoLabel;
    }
}



