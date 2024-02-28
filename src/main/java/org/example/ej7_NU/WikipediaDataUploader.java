package org.example.ej7_NU;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WikipediaDataUploader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input from user
        System.out.print("Enter the country code: ");
        String countryCode = scanner.nextLine();
        System.out.print("Enter the date (YYYY/MM/DD): ");
        String date = scanner.nextLine();

        // Get most viewed articles data
        String data = getMostViewedArticles(countryCode, date);

        if (data != null) {
            // Save to JSON file
            String filePath = saveToJson(data, countryCode, date);

            // FTP Configuration
            String ftpHost = "192.156.68.1";
            String ftpUser = "angel";
            String ftpPassword = "angel";

            // Upload to FTP server
            uploadToFtp(filePath, ftpHost, ftpUser, ftpPassword);
        }
    }

    // https://wikimedia.org/api/rest_v1/metrics/pageviews/top-per-country/JP/all-access/2021/01/02
    private static String getMostViewedArticles(String countryCode, String date) {
        try {
            // Wikipedia API endpoint for most viewed articles
            String apiUrl = "https://wikimedia.org/api/rest_v1/metrics/pageviews/top-per-country/" + countryCode.toUpperCase() + "/all-access/" + date;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Check if the request was successful (HTTP status code 200)
            if (connection.getResponseCode() == 200) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                return response.toString();
            } else {
                System.out.println("Error: Unable to fetch data. Status code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String saveToJson(String data, String countryCode, String date) {
        String filePath = countryCode + "_" + date + ".json";

        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.write(data);
            System.out.println("Data saved to " + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filePath;
    }

    private static void uploadToFtp(String filePath, String ftpHost, String ftpUser, String ftpPassword) {
        FTPClient ftpClient = new FTPClient();

        try {
            // Connect to the FTP server
            ftpClient.connect(ftpHost,21);
            ftpClient.login(ftpUser, ftpPassword);

            // Set file type to binary
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Upload the file to the FTP server
            try (InputStream inputStream = new FileInputStream(filePath)) {
                ftpClient.storeFile(filePath, inputStream);
            }

            System.out.println("File uploaded to FTP server");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Disconnect from the FTP server
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
