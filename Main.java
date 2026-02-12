public class Main {

    public static void main(String[] args) {

        ApplicationManager manager = new ApplicationManager();

        manager.loadFromFile("applications.txt");

        Citizen c1 = new Citizen("C001", "Claudine");
        Citizen c2 = new Citizen("C002", "Beatrice");

        GovernmentService birth = new BirthCertificateService();
        GovernmentService driving = new DrivingTestService();

        ServiceApplication app1 =
                new ServiceApplication("A001", c1, birth);

        ServiceApplication app2 =
                new ServiceApplication("A002", c2, driving);

        manager.addApplication(app1);
        manager.addApplication(app2);

        try {
            manager.approveApplication("A001");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        manager.displayAll();

        manager.saveToFile("applications.txt");

        manager.generateRevenueReport("revenue_report.txt");

        System.out.println("System executed successfully.");
    }
}