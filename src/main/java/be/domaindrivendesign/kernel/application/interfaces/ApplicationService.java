package be.domaindrivendesign.kernel.application.interfaces;

import be.domaindrivendesign.kernel.data.interfaces.UnitOfWork;

/**
 * Created by eddie on 20/11/15.
 */
public class ApplicationService {

    private UnitOfWork unitOfWork;

    public UnitOfWork getUnitOfWork() {
        return unitOfWork;
    }

    public void setUnitOfWork(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
}
