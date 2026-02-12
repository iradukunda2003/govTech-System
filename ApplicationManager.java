import java.util.*;
import java.io.*;

public class ApplicationManager {

    private List<ServiceApplication> applications = new ArrayList<>();

    public void addApplication(ServiceApplication app) {
        applications.add(app);
    }

    public ServiceApplication findById(String id) throws ApplicationNotFoundException {
        for (ServiceApplication app : applications) {
            if (app.getApplicationId().equals(id)) {
                return app;
            }
        }
        throw new ApplicationNotFoundException("Application not found.");
    }
    public void approveApplication(String id)
            throws ApplicationNotFoundException, InvalidStatusTransitionException {
        ServiceApplication app = findById(id);
        app.approve();
    }

    public void displayAll() {
        for (ServiceApplication app : applications) {
            System.out.println(app);
        }
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (ServiceApplication app : applications) {
                writer.write(app.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("File saving error: " + e.getMessage());
        }
    }
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");

                String appId = parts[0];
                String citizenId = parts[1];
                String citizenName = parts[2];
                String serviceType = parts[3];
                ServiceApplication.Status status =
                        ServiceApplication.Status.valueOf(parts[4]);

                Citizen citizen = new Citizen(citizenId, citizenName);

                GovernmentService service;
                if (serviceType.equals("BirthCertificate")) {
                    service = new BirthCertificateService();
                } else {
                    service = new DrivingTestService();
                }

                ServiceApplication app =
                        new ServiceApplication(appId, citizen, service);

                if (status == ServiceApplication.Status.APPROVED)
                    app.approve();
                if (status == ServiceApplication.Status.REJECTED)
                    app.reject();

                applications.add(app);
            }

        } catch (IOException | InvalidStatusTransitionException e) {
            System.out.println("File loading error: " + e.getMessage());
        }
    }

    public double calculateTotalRevenue() {
        double total = 0;
        for (ServiceApplication app : applications) {
            if (app.getStatus() == ServiceApplication.Status.APPROVED) {
                total += app.getService().getFee();
            }
        }
        return total;
    }

    public Map<String, Double> calculateRevenueByService() {
        Map<String, Double> revenueMap = new HashMap<>();

        for (ServiceApplication app : applications) {
            if (app.getStatus() == ServiceApplication.Status.APPROVED) {

                String type = app.getService().getServiceType();
                double fee = app.getService().getFee();

                revenueMap.put(type,
                        revenueMap.getOrDefault(type, 0.0) + fee);
            }
        }
        return revenueMap;
    }

    public void generateRevenueReport(String filename) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            writer.write(" GOVTECH REVENUE REPORT \n");

            writer.write("Total Applications: " + applications.size() + "\n");

            long approvedCount = applications.stream()
                    .filter(a -> a.getStatus() == ServiceApplication.Status.APPROVED)
                    .count();

            writer.write("Approved Applications: " + approvedCount + "\n");

            writer.write("Total Revenue: " + calculateTotalRevenue() + "\n");

            writer.write("\nRevenue by Service:\n");

            Map<String, Double> revenueMap = calculateRevenueByService();

            for (String key : revenueMap.keySet()) {
                writer.write(key + ": " + revenueMap.get(key) + "\n");
            }

        } catch (IOException e) {
            System.out.println("Report generation error: " + e.getMessage());
        }
    }
}