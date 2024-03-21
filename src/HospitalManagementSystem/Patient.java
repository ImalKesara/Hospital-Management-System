package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        System.out.print("Enter patient name");
        String pName = scanner.next();

        System.out.print("Enter patient age");
        int age = scanner.nextInt();

        System.out.print("Enter patient gender");
        String gender = scanner.next();

        try {
            String query =  "INSERT INTO patients(name,age,gender) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,pName);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0){
                System.out.println("patient added successfully");
            }else{
                System.out.println("Failed to add patient");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void viewPatients(){
        String query = "SELECT * FROM patients";
        try{
           PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients");
            System.out.println("+--------+---------------------+--------------+----------------+");
            System.out.println("|  P_id  |        Name         |      age     |     gender     |");
            System.out.println("+--------+---------------------+--------------+----------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-9s|%-20s|%-14s|%-15s|\n",id,name,age,gender);
                System.out.println("+--------+---------------------+--------------+----------------+");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deletePatients(){
        System.out.println("Enter Patient id you wish to delete");
        int patientID = scanner.nextInt();
        try {
            String query = "DELETE FROM patients WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,patientID);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0 ){
                System.out.println("Patient with id "+ patientID + "has been deleted successFully");
            }else{
                System.out.println("No patient found with ID " + patientID + ".");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientId(int id){
        String query = "SELECT * from patients WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return  true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;

    }



}
