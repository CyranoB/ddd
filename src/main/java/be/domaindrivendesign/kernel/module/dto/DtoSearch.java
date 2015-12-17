package be.domaindrivendesign.kernel.module.dto;


public class DtoSearch {

    protected int take;
    protected int skip;

    public DtoSearch() {
        this.take = 200;
        this.skip = 0;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }
}
