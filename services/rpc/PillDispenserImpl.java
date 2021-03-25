package ro.tuc.ds2020.services.rpc;

import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dto.MedicationPillDispenserDto;
import ro.tuc.ds2020.dto.MedicationPlanPillDispenserDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class PillDispenserImpl implements PillDispenser{

    private final String JDBC_DRIVER = "org.hibernate.dialect.PostgreSQLDialect";

    //data for connection of the database from application.properties
    private final String db_ip = ResourceBundle.getBundle("application").getString("database.ip");
    private final String db_port = ResourceBundle.getBundle("application").getString("database.port");
    private final String db_user = ResourceBundle.getBundle("application").getString("database.user");
    private final String db_password = ResourceBundle.getBundle("application").getString("database.password");
    private final String db_name = ResourceBundle.getBundle("application").getString("database.name");

    //real data for connection of the database
    /*private final String DB_IP = db_ip.substring(db_ip.indexOf(':') + 1, db_ip.indexOf('}'));
    private final String DB_PORT = db_port.substring(db_port.indexOf(':') + 1, db_port.indexOf('}'));
    private final String DB_USER = db_user.substring(db_user.indexOf(':') + 1, db_user.indexOf('}'));
    private final String DB_PASSWORD = db_password.substring(db_password.indexOf(':') + 1, db_password.indexOf('}'));
    private final String DB_NAME = db_name.substring(db_name.indexOf(':') + 1, db_name.indexOf('}'));*/

    private final String DB_IP = "ec2-52-212-157-46.eu-west-1.compute.amazonaws.com";
    private final String DB_PORT = "5432";
    private final String DB_USER = "lepfepfpynwdwj";
    private final String DB_PASSWORD = "954cbb320638910d87a57b7208ffc62efc52d24e2de8220aaf7ff87221acda16";
    private final String DB_NAME = "dgf85ugc8pbnp";

    //final database url
    private final String DB_URL = "jdbc:postgresql://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;

    //connect to the db
    private Connection connection = null;
    private Statement statement = null;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private String editId(String id) {
        return id.replace("-", "");
    }

    @Override
    public List<MedicationPlanPillDispenserDto> getMedicationPlansForPatient(String patientId) throws Exception {
        //create a list with the medication plans which will be sent to the client
        List<MedicationPlanPillDispenserDto> medicationPlanDtos = new ArrayList<>();

        //connect to the db
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        statement = connection.createStatement();
        patientId = editId(patientId);

        //create and execute the query
        String query = "select encode(mp.medication_plan_id::bytea, 'hex') as medication_plan_id, period_start, period_end, intake_intervals from medication_plan as mp" +
                " join patient_medication_plans as pmp" +
                " on mp.medication_plan_id = pmp.medication_plans_medication_plan_id" +
                " where encode(pmp.patient_patient_id::bytea, 'hex') = '" + patientId + "'";

        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()) {
            //Create a new medication plan
            MedicationPlanPillDispenserDto medicationPlanDto = new MedicationPlanPillDispenserDto();

            //get all the data from the result set
            String medicationPlanId = resultSet.getString("medication_plan_id");
            String startDate = resultSet.getString("period_start");
            String endDate = resultSet.getString("period_end");
            int intakeIntervals = resultSet.getInt("intake_intervals");

            //save data to the medication plan dto object
            medicationPlanDto.setId(medicationPlanId);
            medicationPlanDto.setIntakeIntervals(intakeIntervals);
            medicationPlanDto.setPeriodStart(LocalDate.parse(startDate));
            medicationPlanDto.setPeriodEnd(LocalDate.parse(endDate));

            medicationPlanDtos.add(medicationPlanDto);
        }

        connection.close();

        return medicationPlanDtos;
    }

    private List<String> getSideEffectsForMedication(String medicationId) throws Exception{
        List<String> sideEffects = new ArrayList<>();

        //connect to the db
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        statement = connection.createStatement();
        medicationId = editId(medicationId);

        //Define the query to find all the side effects of a medication
        String query = "select se.name from side_effects as se " +
                "join medication_side_effects as mse " +
                "on se.side_effect_id = mse.side_effects_side_effect_id " +
                "join medication as med " +
                "on mse.medication_medication_id = med.medication_id " +
                "where encode(med.medication_id::bytea, 'hex') = '" + medicationId + "'";

        //execute query
        ResultSet resultSet = statement.executeQuery(query);

        //for each result compute the list with the names of the side effects
        while(resultSet.next()) {
            String sideEff = resultSet.getString("name");
            sideEffects.add(sideEff);
        }

        return sideEffects;
    }

    @Override
    public List<MedicationPillDispenserDto> getMedicationsForMedicationPlan(String patientId, String medicationPlanId) throws Exception {
        System.out.println("get medications");
        List<MedicationPillDispenserDto> medicationDtoList = new ArrayList<>();

        //connect to the db
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        statement = connection.createStatement();
        patientId = editId(patientId);
        medicationPlanId = editId(medicationPlanId);

        //define the query to get the medications of a medication plan for a patient
        String query = "select encode(med.medication_id::bytea, 'hex') as medication_id, med.dosage, med.name from medication as med " +
                "join medication_plan_medications as mpm " +
                "on mpm.medications_medication_id = med.medication_id " +
                "join medication_plan as mp " +
                "on mpm.medication_plan_medication_plan_id = mp.medication_plan_id " +
                "join patient_medication_plans pmp " +
                "on mp.medication_plan_id = pmp.medication_plans_medication_plan_id " +
                "where encode(mp.medication_plan_id::bytea, 'hex') = '" + medicationPlanId + "' " +
                "and encode(pmp.patient_patient_id::bytea, 'hex') = '" + patientId + "'";

        //execute the query
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()) {
            //create a new medication to send it to the client
            MedicationPillDispenserDto medicationDto = new MedicationPillDispenserDto();

            //get the data
            String medicationId = resultSet.getString("medication_id");
            String medicationName = resultSet.getString("name");
            String dosage = resultSet.getString("dosage");
            List<String> sideEffects = getSideEffectsForMedication(medicationId);

            //set the data to the medication and add it to the list
            medicationDto.setId(medicationId);
            medicationDto.setName(medicationName);
            medicationDto.setDosage(dosage);
            medicationDto.setSideEffects(sideEffects);

            medicationDtoList.add(medicationDto);
        }

        connection.close();

        return medicationDtoList;
    }

    @Override
    public void medicationTaken(String patientId, String medicationName) throws Exception {
        //connect to the db
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

        statement = connection.createStatement();
        patientId = editId(patientId);

        String query = "select * from users as u " +
                "join patient as pat " +
                "on u.user_id = pat.user_user_id " +
                "where encode(pat.patient_id::bytea, 'hex') = '" + patientId + "'";

        //execute the query
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()) {
            String patientName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
            System.out.println("Patient " + patientName + " took the medication " + medicationName + " at " + LocalTime.now());
        }

        connection.close();
    }

    @Override
    public void allMedicationTaken(String message) throws Exception {
        System.out.println(message);
    }
}
