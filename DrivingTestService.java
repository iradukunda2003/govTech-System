public class DrivingTestService extends GovernmentService {

    public DrivingTestService() {
        super("Driving Test Service", 50.0);
    }

    @Override
    public String getServiceType() {
        return "DrivingTest";
    }
}