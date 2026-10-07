import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        // initialise arrays
        String[] allNames = {"dan", "john", "james", "alice", "maria", "alex"};
        int[] allStudentIDs = {12, 54, 32, 34, 65, 67, 78};
        int numMembers = 4 + new Random().nextInt(3); // randomly selecting between 4 and 6 members
        ExpeditionMember[] members = new ExpeditionMember[numMembers];

        // randomly creating objects of each member
        for (int i = 0; i < members.length; i++) {
            members[i] = new ExpeditionMember(allNames[i], allStudentIDs[i]);
        }
        /*
           TODO this solution uses the help I found online:
                  // https://www.digitalocean.com/community/tutorials/shuffle-array-java
         */
        List<ExpeditionMember> shuffledMembers = Arrays.asList(members);
        Collections.shuffle(shuffledMembers); // shuffle members

        // creating expeditions
        ResearchExpedition pyramidExpedition = new ResearchExpedition(members[0], members[1], members[2]);
        ResearchExpedition nileExpedition = new ResearchExpedition(
                members[members.length - 3],
                members[members.length - 2],
                members[members.length - 1]
        );
        // output Expedition Leader, Archivist, Field Researcher for Pyramid Expedition and Nile Expedition
        System.out.println(
                "Pyramid Expedition" + "\n" +
                "----------------------------" + "\n" +
                "Expedition Leader: " + pyramidExpedition.getExpeditionLeader().getName() + "\n" +
                "Archivist: " + pyramidExpedition.getArchivist().getName() + "\n" +
                "Field Researcher: " + pyramidExpedition.getFieldResearcher().getName() + "\n" +
                "----------------------------" );

        System.out.println(
                "\n" + "Nile Expedition" + "\n" +
                "----------------------------" + "\n" +
                "Expedition Leader: " + nileExpedition.getExpeditionLeader().getName() + "\n" +
                "Archivist: " + nileExpedition.getArchivist().getName() + "\n" +
                "Field Researcher: " + nileExpedition.getFieldResearcher().getName() + "\n" +
                "----------------------------" );
        // check for consistency between member_counter and the length of members
        if (ExpeditionMember.member_counter == members.length)
            System.out.println("\n"+ "SUCCESS, the amount of members are: " + members.length) ;
    }
}