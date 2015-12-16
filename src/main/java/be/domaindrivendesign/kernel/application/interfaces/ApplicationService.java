package be.domaindrivendesign.kernel.application.interfaces;

import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;

public interface ApplicationService {
    UnitOfWork getUnitOfWork();

    void setUnitOfWork(UnitOfWork unitOfWork);
}
