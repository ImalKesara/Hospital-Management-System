package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.println("Enter patient name");
        String pName = scanner.nextLine();
        System.out.println("Enter patient age");
        int age = scanner.nextInt();
        System.out.println("Enter patient gender");
        String gender = scanner.nextLine();

        try {
            String query =  "INSERT INTO patients(name,age,gender) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }



}
