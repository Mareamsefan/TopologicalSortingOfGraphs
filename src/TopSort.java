import java.io.*;
import java.util.*;

// Topologisk sortering

public class TopSort
{
    int n;            // Antall noder i grafen
    boolean nabo[][]; // Nabomatrise
    String data[];    // Data i hver node

    // TopSort(): KonstruktÃ¸r
    // Leser inn grafen fra fil, ingen feilsjekking
    public TopSort(String filNavn)
    {
        // Filformat:
        //   ant.noder
        //   node# data ant.naboer nabo# nabo# ...
        //   node# data ant.naboer nabo# nabo# ...
        //   ...
        try
        {
            // Ã…pner datafil for lesing
            Scanner in = new Scanner(new File(filNavn));
            // Leser antall noder
            n = in.nextInt();
            // Oppretter nabomatrisen
            nabo = new boolean[n][n];
            // Setter hele nabomatrisen, untatt diagonalen, til false
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    nabo[i][j] = (i == j) ? true : false;
            // Oppretter arrayen med data (string) for hver node
            data = new String[n];
            // For hver node: Les data og alle naboene noden har
            for (int i = 0; i < n; i++)
            {
                int nodeNr = in.nextInt();
                data[nodeNr] = in.next();
                int antNaboer = in.nextInt();
                for (int j = 0; j < antNaboer; j++)
                {
                    int naboNr = in.nextInt();
                    nabo[nodeNr][naboNr] = true;
                }
            }
        }
        catch (Exception e)
        {
            System.err.println("Error reading file " + filNavn);
            System.exit(1);
        }
    }

    // findAndPrint(): Finner og skriver ut en topologisk sortering
    public void findAndPrint()
    {
        /*** Skal programmeres i oblig. 12 ***/

        /** Array for å lagre inngraden til hver node **/
        int[] inngrad = new int[n];
        /** Stack for å holde noder med inngrad 0 **/
        Stack<Integer> S = new Stack<>();

        /** Beregner inngraden til hver node**/
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                /**huske å ekskludere diagonalen**/
                if (i != j && nabo[i][j]) {
                    inngrad[j]++;
                }
            }
        }

        /** Legger noder med inngrad 0 til stacken**/
        for (int i = 0; i < n; i++) {
            if (inngrad[i] == 0) {
                S.push(i);
            }

        }
        /** Gjentar prosessen så lenge det er noder i stacken**/
        while (!S.isEmpty()) {
            /** Tar ut en node fra stacken**/
            int node = S.pop();
            /** Skriver ut noden (eller behandling av noden)**/
            System.out.print(data[node] + " ");

            /** Oppdater inngraden til naboene og legg til naboer med inngrad 0 til stacken**/
            for (int j = 0; j < n; j++) {
                if (nabo[node][j]) {
                    inngrad[j]--;
                    if (inngrad[j] == 0) {
                        S.push(j);
                    }
                }
            }
        }

    }

    // main(); Testprogram
    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("File? ");
        String filNavn = scan.next();
        new TopSort(filNavn).findAndPrint();

    }
}
