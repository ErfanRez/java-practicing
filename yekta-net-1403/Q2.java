import java.util.Scanner;

public class Q2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();  // consume the newline
        
        String[] shots = new String[n];
        for (int i = 0; i < n; i++) {
            shots[i] = scanner.nextLine();
        }

        int[] scores = new int[2]; // scores[0] for First, scores[1] for Second
        int currentPlayer = 0; // 0 for First, 1 for Second
        int redBalls = 15;
        boolean mustPotRed = true;

        // Score mapping for colored balls
        java.util.Map<String, Integer> scoreMap = new java.util.HashMap<>();
        scoreMap.put("red", 1);
        scoreMap.put("yellow", 2);
        scoreMap.put("green", 3);
        scoreMap.put("brown", 4);
        scoreMap.put("blue", 5);
        scoreMap.put("pink", 6);
        scoreMap.put("black", 7);

        for (String shot : shots) {
            if (shot.equals("miss")) {
                currentPlayer = 1 - currentPlayer;
                mustPotRed = true;
                continue;
            }

            if (shot.equals("white")) {
                currentPlayer = 1 - currentPlayer;
                continue;
            }

            if (!scoreMap.containsKey(shot)) {
                System.out.println("Invalid");
                return;
            }

            if (mustPotRed) {
                if (!shot.equals("red")) {
                    System.out.println("Invalid");
                    return;
                }
                scores[currentPlayer] += scoreMap.get(shot);
                redBalls--;
                mustPotRed = false;
            } else {
                if (shot.equals("red")) {
                    if (redBalls == 0) {
                        System.out.println("Invalid");
                        return;
                    }
                    scores[currentPlayer] += scoreMap.get(shot);
                    redBalls--;
                    mustPotRed = false;
                } else {
                    scores[currentPlayer] += scoreMap.get(shot);
                    mustPotRed = true;
                }
            }
        }

        // Determine the game result
        if (redBalls < 0) {
            System.out.println("Invalid");
        } else if (scores[0] > scores[1]) {
            System.out.println("First");
        } else if (scores[1] > scores[0]) {
            System.out.println("Second");
        } else {
            System.out.println("Tie");
        }
    }
}
