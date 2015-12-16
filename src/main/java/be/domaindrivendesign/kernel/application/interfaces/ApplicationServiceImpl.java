package be.domaindrivendesign.kernel.application.interfaces;

import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;

public class ApplicationServiceImpl implements ApplicationService {

    private UnitOfWork unitOfWork;

    @Override
    public UnitOfWork getUnitOfWork() {
        return unitOfWork;
    }

    @Override
    public void setUnitOfWork(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
}
