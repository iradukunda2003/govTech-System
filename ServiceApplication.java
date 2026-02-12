public class ServiceApplication {

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    private String applicationId;
    private Citizen citizen;
    private GovernmentService service;
    private Status status;

    public ServiceApplication(String applicationId, Citizen citizen, GovernmentService service) {
        this.applicationId = applicationId;
        this.citizen = citizen;
        this.service = service;
        this.status = Status.PENDING;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Status getStatus() {
        return status;
    }

    public GovernmentService getService() {
        return service;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void approve() throws InvalidStatusTransitionException {
        if (status != Status.PENDING) {
            throw new InvalidStatusTransitionException("Invalid status transition.");
        }
        status = Status.APPROVED;
    }

    public void reject() throws InvalidStatusTransitionException {
        if (status != Status.PENDING) {
            throw new InvalidStatusTransitionException("Invalid status transition.");
        }
        status = Status.REJECTED;
    }

    public String toFileString() {
        return applicationId + "," +
                citizen.getCitizenId() + "," +
                citizen.getName() + "," +
                service.getServiceType() + "," +
                status;
    }

    @Override
    public String toString() {
        return "ID: " + applicationId +
                " | Citizen: " + citizen.getName() +
                " | Service: " + service.getServiceName() +
                " | Status: " + status;
    }
}
