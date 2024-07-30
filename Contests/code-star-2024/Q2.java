import java.util.*;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int sherlockCity = scanner.nextInt();
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        
        boolean[] visited = new boolean[n + 1];
        int[] parent = new int[n + 1];
        int[] entry = new int[n + 1];
        int[] lowest = new int[n + 1];
        Set<Integer> circleCities = new HashSet<>();
        int time = 0;

        findCircleCities(1, -1, visited, parent, entry, lowest, graph, circleCities, time);

        
        int[] distances = new int[n + 1];
        Arrays.fill(distances, -1);
        int[] previous = new int[n + 1];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(sherlockCity);
        distances[sherlockCity] = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (circleCities.contains(current)) {
                printPath(previous, current);
                return;
            }
            for (int neighbor : graph.get(current)) {
                if (distances[neighbor] == -1) {
                    distances[neighbor] = distances[current] + 1;
                    previous[neighbor] = current;
                    queue.add(neighbor);
                }
            }
        }
    }

    private static void findCircleCities(int u, int p, boolean[] visited, int[] parent, int[] entry, int[] lowest,
                                         List<List<Integer>> graph, Set<Integer> circleCities, int time) {
        visited[u] = true;
        entry[u] = lowest[u] = ++time;
        for (int v : graph.get(u)) {
            if (v == p) continue;
            if (!visited[v]) {
                parent[v] = u;
                findCircleCities(v, u, visited, parent, entry, lowest, graph, circleCities, time);
                lowest[u] = Math.min(lowest[u], lowest[v]);

                if (lowest[v] > entry[u]) {
                    
                } else {
                    circleCities.add(u);
                    circleCities.add(v);
                }
            } else {
                lowest[u] = Math.min(lowest[u], entry[v]);
            }
        }
    }

    private static void printPath(int[] previous, int current) {
        List<Integer> path = new ArrayList<>();
        while (current != 0) {
            path.add(current);
            current = previous[current];
        }
        Collections.reverse(path);
        for (int city : path) {
            System.out.print(city + " ");
        }
    }
}
