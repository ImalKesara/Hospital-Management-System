package HospitalManagementSystem;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";

    private static final String username = "root";
    private  static final String password = "Wolf1234@";

    public static void main(String[] agrs){
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,scanner);
            Doctors doctors = new Doctors(connection);
            while (true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. Del Patient");
                System.out.println("4. View Doctors");
                System.out.println("5. Book Appointment");
                System.out.println("6. Exit");
                System.out.println("Enter Your Choice");
                int choice  = scanner.nextInt();
                switch (choice){
                    case 1:
                        patient.addPatient();
                        break;
                    case 2:
                        patient.viewPatients();
                        break;
                    case 3:
                        patient.deletePatients();
                        break;
                    case 4:
                        doctors.viewDoctors();
                        break;
                    case 5:
                        bookAppointment(patient,doctors,connection,scanner);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Wrong choice");
                        break;
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient,Doctors doctors,Connection connection , Scanner scanner){
        System.out.println("Enter patient id");
        int pID = scanner.nextInt();
        System.out.println("Enter Doctor id");
        int dID = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD):- ");
        String appointmentDate = scanner.next();

        if(patient.getPatientId(pID) && doctors.getDoctorId(dID)){
            if(checkDoctorAvailability(dID,appointmentDate,connection)){
                String appointmentQuery = "INSERT INTO appointments(patient_id,patient_id,patient_id) VALUES(?,?,?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,pID);
                    preparedStatement.setInt(2,dID);
                    preparedStatement.setString(3,appointmentDate);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected>0){
                        System.out.println("Appointment added success");
                    }else{
                        System.out.println("Failed to appointment");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

            }

        }else{
            System.out.println("patient or doctor doesn't exist");
        }

    }
    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate,Connection connection){
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                if(count == 0){
                    return true;
                }else {
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    return false;
    }

}
