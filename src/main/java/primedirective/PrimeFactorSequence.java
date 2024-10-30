package primedirective;

import java.util.*;

public class PrimeFactorSequence {
    private List<Integer> primes;
    private int upperBound;

    /**
     * Create a PrimeFactorSequence object with a defined upperbound.
     *
     * @param _upperBound the upper bound for sequences and primes in this instance,
     * {@code upperBound > 0}.
     */
    public PrimeFactorSequence(int _upperBound) {
        upperBound = _upperBound;
        primes = Primes.getPrimes(upperBound);
    }

    /**
     * Obtain a sequence L[0 .. upper bound] where L[i] represents the number of prime factors i
     * has, including repeated factors
     *
     * @return sequence L[0 .. upper bound] where L[i] represents the number of prime factors i
     * has, including repeated factors
     */
    public List<Integer> primeFactorSequence() {
        List<Integer> seq = new ArrayList<>();
        // TODO: Implement this method
        seq.add(0);
        if (upperBound > 0) {
            seq.add(0);
        }
        for (int i = 2; i <= upperBound; i++) {
            int thisNum = i;
            int pFactorNum = 0;
                for (int j = 0; j < primes.size(); j++) {
                    if (thisNum % primes.get(j) == 0) {
                        thisNum = thisNum /primes.get(j);
                        pFactorNum++;
                        j--;
                    }
                }

            seq.add(pFactorNum);
        }
        return seq;
    }

    /**
     * Obtain a sequence L that is sorted in ascending order and where L[i] has exactly m
     * prime factors (including repeated factors) and L[i] <= upper bound
     *
     * @param m the number of prime factors that each element of the output sequence has
     * @return a sequence L that is sorted in ascending order and where L[i] has exactly
     * m prime factors (including repeated factors) and L[i] <= upper bound
     */
    public List<Integer> numbersWithMPrimeFactors(int m) {
        List<Integer> seq = new ArrayList<>();
        List<Integer> primeSeq = primeFactorSequence();

        for (int i = 0; i < primeSeq.size(); i++) {
            if (primeSeq.get(i) == m) {
                seq.add(i);
            }
        }

        return seq;
    }

    /**
     * Obtain a sequence of integer pairs [(Sa, Sb)] where Sa and Sb have exactly m prime factors
     * (including repeated factors), and where |Sa - Sb| <= gap and where Sa and Sb are
     * adjacent each other in the output of {@code numbersWithMPrimeFactors(m)}
     *
     * @param m   the number of prime factors that each element in the output sequence has
     * @param gap the maximum gap between two adjacent entries of interest in the output
     *            of {@code numbersWithMPrimeFactors(m)}
     * @return a sequence of integer pairs [(Sa, Sb)] where Sa and Sb have exactly
     * m prime factors (including repeated factors), and where |Sa - Sb| <= gap and where
     * Sa and Sb are adjacent each other in the output of {@code numbersWithMPrimeFactors(m)}
     */
    public List<IntPair> numbersWithMPrimeFactorsAndSmallGap(int m, int gap) {
        List<IntPair> listOfPairs = new ArrayList<>();
        List<Integer> primeSeq = numbersWithMPrimeFactors(m);

        for (int i = 1; i < primeSeq.size(); i++) {
            if (Math.abs(primeSeq.get(i - 1) - primeSeq.get(i)) <= gap) {
                listOfPairs.add(new IntPair(primeSeq.get(i - 1), primeSeq.get(i)));
            }
        }
        // TODO: Implement this method
        return listOfPairs;
    }

    /**
     * Transform n to a larger prime (unless n is already prime) using the following steps:
     * <p>
     *     <ul>
     *         <li>A 0-step where we obtain 2 * n + 1</li>,
     *         <li>or a 1-step where we obtain n + 1</li>.
     *     </ul>
     *      We can apply these steps repeatedly, with more details in the problem statement.
     * </p>
     * @param n the number to transform
     * @return a string representation of the smallest transformation sequence
     */
    public String changeToPrime(int n) {
        // Early check if no primes above n can be found within bounds
        if (n > upperBound) {
            return "-";
        }
        if (primes.get(primes.size() - 1) < n) {
            return "-";
        }
        if (primes.contains(n)) {
            return "";
        }

        Queue<TransformationStep> queue = new LinkedList<>();
        List<Integer> visited = new ArrayList<>();
        queue.add(new TransformationStep(n, ""));

        while (!queue.isEmpty()) {
            TransformationStep current = queue.poll();
            int currentNum = current.currentNumber;
            String currentSteps = current.steps;
            // 0-step transformation
            int zeroStepNum = 2 * currentNum + 1;
            if (zeroStepNum <= upperBound && !visited.contains(zeroStepNum)) {
                if (primes.contains(zeroStepNum)) {
                    return currentSteps + "0";
                }
                queue.add(new TransformationStep(zeroStepNum, currentSteps + "0"));
                visited.add(zeroStepNum);
            }
            // 1-step transformation
            int oneStepNum = currentNum + 1;
            if (oneStepNum <= upperBound && !visited.contains(oneStepNum)) {
                if (primes.contains(oneStepNum)) {
                    return currentSteps + "1";
                }
                queue.add(new TransformationStep(oneStepNum, currentSteps + "1"));
                visited.add(oneStepNum);
            }



        }
        return "-";
    }

    private static class TransformationStep {
        int currentNumber;
        String steps;

        public TransformationStep(int currentNumber, String steps) {
            this.currentNumber = currentNumber;
            this.steps = steps;
        }
    }
}
