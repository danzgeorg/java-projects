package SingerTask;

public class Duet {
    private Singer singer1;
    private Singer singer2;

    public Duet(Singer singer1, Singer singer2) {
        this.singer1 = singer1;
        this.singer2 = singer2;
    }

    public Singer getSinger1() {return singer1;}

    public Singer getSinger2() {return singer2;}

    public boolean bothProfessional(Singer singer1, Singer singer2)
    {return singer1.isProfessional() && singer2.isProfessional();}

    public boolean bothAdult(Singer singer1, Singer singer2) {
        return singer1.isAdult() && singer2.isAdult();
    }
}
