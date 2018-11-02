package dangerAgents;

import aima.core.logic.propositional.inference.DPLLSatisfiable;
import danger.CoveredSquareException;
import danger.DangerSquare;
import danger.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SATAgent extends Agent {
    DPLLSatisfiable dpllSatisfiable = new DPLLSatisfiable();
    public SATAgent(World world) {
        super(world);
    }

    public void execute() throws CoveredSquareException {
        String s = constructSAT(new Position(0, 0));
        System.out.println(s);
    }

    public String constructSAT(Position position) throws CoveredSquareException {
        DangerSquare square = dangerGame.getSquare(position);
        List<DangerSquare> neighbours = dangerGame.getNeighbours(position);
        int surroundingDaggers = square.getClue();
        for(DangerSquare neighbour : neighbours) {
            if(!neighbour.isCovered()) {
                if(neighbour.isDagger()) {
                    surroundingDaggers--;
                }
            }
        }

        ArrayList<String> toBuild = new ArrayList<>();
        for(DangerSquare neighbour : neighbours) {
            if(neighbour.isCovered()) {
                toBuild.add("D" + neighbour.getX() + "" + neighbour.getX());
            }
        }

        List<List<String>> combinations = getCombinations(toBuild, surroundingDaggers);
        return combineCombinations(combinations);
    }


    public static List<List<String>> getCombinations(List<String> input, int k) {
        List<List<String>> combinations = new ArrayList<>();
        int[] indices = new int[k];
        if (k <= input.size()) {
            for(int i = 0; i <= k -1; i++) {
                indices[i] = i;
            }

            combinations.add(getCombination(input, indices));

            while (true) {
                int j = k - 1;
                while (j >= 0 && indices[j] == input.size() - k + j) {
                    j--;
                }


                if (j < 0) {
                    break;
                }

                indices[j]++;
                j++;

                while (j < k) {
                    indices[j] = indices[j - 1] + 1;
                    j++;
                }

                combinations.add(getCombination(input, indices));
            }
        }

        return combinations;
    }

    private static List<String> getCombination(List<String> input, int[] indices) {
        String[] result = new String[input.size()];
        List<Integer> integers = new ArrayList<>();
        for (int aSubset : indices) {
            integers.add(aSubset);
        }

        for(int i = 0; i < input.size(); i++) {
            if(integers.contains(i)) {
                result[i] = input.get(i);
            } else {
                result[i] = "¬" + input.get(i);
            }
        }
        return Arrays.asList(result);
    }

    public static String combineCombinations(List<List<String>> combinations) {
        StringBuilder totalS = new StringBuilder();
        for(int i = 0; i < combinations.size(); i++) {
            List<String> strings = combinations.get(i);

            StringBuilder s = new StringBuilder("(");
            for(int j = 0; j < strings.size(); j++) {
                s.append(strings.get(j));
                if(j != strings.size()-1){
                    s.append("∧");
                }
            }
            s.append(")");
            totalS.append(s.toString());
            if(i != combinations.size()-1) {
                totalS.append("∨");
            }
        }
        return totalS.toString();
    }

    public static void main(String[] args) throws CoveredSquareException {
        SATAgent agent = new SATAgent(World.EASY1);
        agent.execute();
    }
}
