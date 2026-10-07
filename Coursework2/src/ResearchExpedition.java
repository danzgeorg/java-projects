public class ResearchExpedition {
    private ExpeditionMember expeditionLeader;
    private ExpeditionMember archivist;
    private ExpeditionMember fieldResearcher;

    public ResearchExpedition(ExpeditionMember expeditionLeader, ExpeditionMember archivist, ExpeditionMember fieldResearcher) {
        this.expeditionLeader = expeditionLeader;
        this.archivist = archivist;
        this.fieldResearcher = fieldResearcher;
    }

    public ExpeditionMember getExpeditionLeader() {
        return expeditionLeader;
    }

    public ExpeditionMember getArchivist() {
        return archivist;
    }

    public ExpeditionMember getFieldResearcher() {
        return fieldResearcher;
    }
}
