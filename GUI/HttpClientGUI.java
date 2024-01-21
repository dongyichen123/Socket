import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClientGUI extends JFrame {
    private JTextField methodField, serverIpField, pathField, postDataField;
    private JTextArea responseArea;

    public HttpClientGUI() {
        initUI();
    }

    private void initUI() {
        setTitle("HTTP Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Request Method:"));
        methodField = new JTextField();
        panel.add(methodField);

        panel.add(new JLabel("Server IP:"));
        serverIpField = new JTextField();
        panel.add(serverIpField);

        panel.add(new JLabel("Request Path:"));
        pathField = new JTextField();
        panel.add(pathField);

        panel.add(new JLabel("POST Data (if applicable):"));
        postDataField = new JTextField();
        panel.add(postDataField);

        JButton sendButton = new JButton("Send Request");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendHttpRequest();
            }
        });
        panel.add(sendButton);

        responseArea = new JTextArea();
        responseArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(responseArea);
        panel.add(scrollPane);

        add(panel);
    }

    private void sendHttpRequest() {
        try {
            String method = methodField.getText().toUpperCase();
            String serverIp = serverIpField.getText();
            String path = pathField.getText();
            String postData = postDataField.getText();

            String urlString = "http://" + serverIp + path;
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            // 如果是 POST 请求，设置请求头和发送 POST 数据
            if (method.equals("POST")) {
                connection.setDoOutput(true);
                connection.getOutputStream().write(postData.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();

            response.append("Response Code: ").append(responseCode).append("\n");

            // 读取响应数据
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append("\n");
                }
            }

            responseArea.setText(response.toString());
        } catch (IOException ex) {
            responseArea.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HttpClientGUI httpClientGUI = new HttpClientGUI();
            httpClientGUI.setVisible(true);
        });
    }
}
