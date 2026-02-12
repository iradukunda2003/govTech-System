public class BirthCertificateService extends GovernmentService {

    public BirthCertificateService() {
        super("Birth Certificate Service", 20.0);
    }

    @Override
    public String getServiceType() {
        return "BirthCertificate";
    }
}