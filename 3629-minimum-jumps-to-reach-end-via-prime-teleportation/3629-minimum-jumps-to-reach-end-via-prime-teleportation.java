import java.util.*;

class Solution {
    public int minJumps(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;

        int maxVal = 0;
        for (int x : nums) maxVal = Math.max(maxVal, x);

        // Smallest Prime Factor (SPF) Sieve
        int[] spf = new int[maxVal + 1];
        for (int i = 2; i <= maxVal; i++) {
            if (spf[i] == 0) {
                for (int j = i; j <= maxVal; j += i) {
                    if (spf[j] == 0) spf[j] = i;
                }
            }
        }

        // prime -> indices divisible by that prime
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int x = nums[i];
            Set<Integer> factors = new HashSet<>();

            while (x > 1) {
                int p = spf[x];
                factors.add(p);
                while (x % p == 0) x /= p;
            }

            for (int p : factors) {
                map.computeIfAbsent(p, k -> new ArrayList<>()).add(i);
            }
        }

        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];

        q.offer(0);
        visited[0] = true;

        int jumps = 0;

        while (!q.isEmpty()) {
            int size = q.size();

            while (size-- > 0) {
                int idx = q.poll();

                if (idx == n - 1) return jumps;

                // left
                if (idx - 1 >= 0 && !visited[idx - 1]) {
                    visited[idx - 1] = true;
                    q.offer(idx - 1);
                }

                // right
                if (idx + 1 < n && !visited[idx + 1]) {
                    visited[idx + 1] = true;
                    q.offer(idx + 1);
                }

                // teleport if nums[idx] is prime
                int val = nums[idx];

                if (val > 1 && spf[val] == val) {
                    List<Integer> next = map.getOrDefault(val, Collections.emptyList());

                    for (int ni : next) {
                        if (!visited[ni]) {
                            visited[ni] = true;
                            q.offer(ni);
                        }
                    }

                    map.remove(val); // optimization
                }
            }

            jumps++;
        }

        return -1;
    }
}