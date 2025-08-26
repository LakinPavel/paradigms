package search;

public class BinarySearchClosestI {
    /*
     * a = args[1:], x = Integer.parseInt(args[0])
     * Pred: args.length > 0 &&
     *       ∀ i ∈ [0, a.length - 1]: Integer.MIN_VALUE <= a[i] <= Integer.MAX_VALUE &&
     *       ∀ i,j ∈ [0, a.length - 1]: i < j => a[i] <= a[j]
     * Post: min i ∈ [0, a.length - 1]: ∀ j ∈ [0, a.length - 1] && j != i: Math.abs(x - a[i]) <= Math.abs(x - a[j])
     */
    public static void main(String[] args) {
        int r = args.length - 1;
        int x = Integer.parseInt(args[0]);
        int[] a = new int[r];
        for (int i = 1; i < r + 1; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(binarySearchClosestI(x, -1, r, a));
    }


    /*
     * Pred: l == -1 &&
     *       ∀ i ∈ [0, a.length - 1]: Integer.MIN_VALUE <= a[i] <= Integer.MAX_VALUE &&
     *       r == a.lenght &&
     *       ∀ i,j ∈ [0, a.length - 1]: i < j => a[i] <= a[j] &&
     *       a[l] = -inf &&
     *       a[r] = inf
     * Post: min i ∈ [0, a.length - 1]: a[i] <= x
     * пусть r, l - исходные (для нас константы)
     * r` , l` - изменяемые во время исполнения функции
     */
    public static int iterBinarySearch(int x, int l, int r, int[] a) {
        /*
         * Inv: a[l] < x &&
         *      a[r] >= x
         */
        while (l < r - 1) {
            /*
             * Pred: l` < r` - 1 &&
             *       (l` + r`) ∈ [Integer.MIN_VALUE; Integer.MAX_VALUE] &&
             *       (l` + r`)/2 >= Integer.MIN_VALUE &&
             *       (l` + r`)/2 <= Integer.MAX_VALUE
             * Post: m = (l` + r`)/2 &&
             *       l` < m < r` (m ∈ [0, a.length - 1])
             * Доказав предыдуший факт, мы докажем что с каждым таким действием длина отрезка уменьшается, и следовательно программа конечна:
             * Дано:
             *       m = (l` + r`) / 2
             * (!)   1) l` < m
             *       2) m < r`
             * Док-во:
             *       1) l` <(?) m = (l` + r`) / 2
             *          (1) l` % 2 == 0:
             *              l` <(?) l`/2 + r`/2
             *              l`/2 <(?) r`/2   | тк r` > l` + 1 чтд
             *          (2) l` % 2 == 1:
             *              l` <(?) l`/2 + r`/2
             *              r`/2 >= l`/2 + 1 чтд
             *       2) m <(?) r`
             *          аналогично
             */
            int m = (l + r) / 2;
            if (a[m] >= x) {
                /*
                 * Pred: m ∈ [0; a.lenght-1] &&
                 *       a[m] >= x
                 * Post: r` = m &&
                 *       a[l`] < x <= a[r`]
                 */
                r = m;
            } else {
                /*
                 * Pred: m ∈ [0; a.lenght-1] &&
                 *       a[m] < x
                 * Post: l` = m &&
                 *       a[l`] < x <= a[r`]
                 */
                l = m;
            }
        }
        return r;
    }


    /*
     * Pred: l == -1 &&
     *       ∀ i ∈ [0, a.length - 1]: Integer.MIN_VALUE <= a[i] <= Integer.MAX_VALUE &&
     *       r == a.lenght &&
     *       ∀ i,j ∈ [0, a.length - 1]: i < j => a[i] <= a[j] &&
     *       a[l] = -inf &&
     *       a[r] = inf
     * Post: i ∈ [0, a.length - 1]: ∀ j ∈ [0, a.length - 1] && j != i: Math.abs(x - a[i]) <= Math.abs(x - a[j])
     * пусть r, l - исходные (для нас константы)
     * r` , l` - изменяемые во время исполнения функции
     */
    public static int iterBinarySearchMode(int x, int l, int r, int[] a) {
        /*
         * Inv: a[l] < x &&
         *      a[r] >= x
         */
        while (l < r - 1) {
            /*
             * Pred: l` < r` - 1 &&
             *       (l` + r`) ∈ [Integer.MIN_VALUE; Integer.MAX_VALUE] &&
             *       (l` + r`)/2 >= Integer.MIN_VALUE &&
             *       (l` + r`)/2 <= Integer.MAX_VALUE
             * Post: m = (l` + r`)/2 &&
             *       l` < m < r` (m ∈ [0, a.length - 1])
             * Доказав предыдуший факт, мы докажем что с каждым таким действием длина отрезка уменьшается, и следовательно программа конечна:
             * Дано:
             *       m = (l` + r`) / 2
             * (!)   1) l` < m
             *       2) m < r`
             * Док-во:
             *       1) l` <(?) m = (l` + r`) / 2
             *          (1) l` % 2 == 0:
             *              l` <(?) l`/2 + r`/2
             *              l`/2 <(?) r`/2   | тк r` > l` + 1 чтд
             *          (2) l` % 2 == 1:
             *              l` <(?) l`/2 + r`/2
             *              r`/2 >= l`/2 + 1 чтд
             *       2) m <(?) r`
             *          аналогично
             */
            int m = (l + r) / 2;
            if (a[m] >= x) {
                /*
                 * Pred: m ∈ [0; a.lenght-1] &&
                 *       a[m] >= x
                 * Post: r` = m &&
                 *       a[l`] > x >= a[r`]
                 */
                r = m;
            } else {
                /*
                 * Pred: m ∈ [0; a.lenght-1] &&
                 *       a[m] < x
                 * Post: l` = m &&
                 *       a[l`] > x >= a[r`]
                 */
                l = m;
            }
        }

        if (a.length > 0 && l >= 0 && r < a.length) {
            /*
             * Pred: a.length > 0 &&
             *       l ∈ [0; a.length-1] &&
             *       r ∈ [0; a.length-1] &&
             * Post: i ∈ [0, a.length - 1]: ∀ j ∈ [0, a.length - 1] && j != i: Math.abs(x - a[i]) <= Math.abs(x - a[j])
             */
            if (Math.abs(x - a[l]) <= Math.abs(a[r] - x)) {
                return l;
            }
            return r;
        }
        /*
         * Pred: l >= 0 &&
         *       r = a.length
         * Post: i ∈ [0, a.length - 1]: ∀ j ∈ [0, a.length - 1] && j != i: Math.abs(x - a[i]) <= Math.abs(x - a[j])
         */
        if (l >= 0) {
            return r - 1;
        }
        return l + 1;
    }


    /*
     * Pred: l == -1 &&
     *       ∀ i ∈ [0, a.length - 1]: Integer.MIN_VALUE <= a[i] <= Integer.MAX_VALUE &&
     *       r == a.lenght &&
     *       ∀ i,j ∈ [0, a.length - 1]: i < j => a[i] <= a[j] &&
     *       a[l] = -inf &&
     *       a[r] = inf
     * Post: min i ∈ [0, a.length - 1]: ∀ j ∈ [0, a.length - 1] && j != i: Math.abs(x - a[i]) <= Math.abs(x - a[j])
     * пусть r, l - исходные (для нас константы)
     * r` , l` - изменяемые во время исполнения функции
     */
    public static int binarySearchClosestI(int x, int l, int r, int[] a) {
        if (a.length > 0) {
            /*
             * Pred: a.length > 0
             * Post: a[i] ∈ [0, a.length - 1]: ∀ j ∈ [0, a.length - 1] && j != i: Math.abs(x - a[i]) <= Math.abs(x - a[j])
             */
            int num = a[iterBinarySearchMode(x, l, r, a)];
            /*
             * Pred: a.length > 0
             * Post: min i ∈ [0, a.length - 1]: ∀ j ∈ [0, a.length - 1] && j != i: Math.abs(x - a[i]) <= Math.abs(x - a[j])
             */
            return iterBinarySearch(num, l, r, a);
        }
        return 0;
    }
}
