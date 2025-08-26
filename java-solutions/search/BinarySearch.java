package search;


public class BinarySearch {
    /*
     * a = args[1:], x = Integer.parseInt(args[0])
     * Pred: args.length > 0 &&
     *       ∀ i ∈ [0, a.length - 1]: Integer.MIN_VALUE <= a[i] <= Integer.MAX_VALUE &&
     *       ∀ i,j ∈ [0, a.length - 1]: i < j => a[i] >= a[j]
     * Post: min i ∈ [0, a.length - 1]: a[i] <= x
     */
    public static void main(String[] args) {
        int r = args.length - 1;
        int x = Integer.parseInt(args[0]);
        int[] a = new int[r];
        for (int i = 1; i < r + 1; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(recBinarySearch(x, -1, r, a));
    }


    /*
     * Pred: l == -1 &&
     *       ∀ i ∈ [0, a.length - 1]: Integer.MIN_VALUE <= a[i] <= Integer.MAX_VALUE &&
     *       r == a.lenght &&
     *       ∀ i,j ∈ [0, a.length - 1]: i < j => a[i] >= a[j] &&
     *       a[-1] = inf &&
     *       a[last] = -inf
     * Post: min i ∈ [0, a.length - 1]: a[i] <= x
     * пусть r, l - исходные (для нас константы)
     * r` , l` - изменяемые во время исполнения функции
     */
    public static int iterBinarySearch(int x, int l, int r, int[] a) {
        /*
         * Inv: a[l] > x &&
         *      a[r] <= x
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
             *          рассмотрим несколько вариантов, а в общем случае все значения будут отличатьс от них на четную константу, те условия будут сохр:
             *          (1) l` = 1  r` = 3 => m = 2
             *          (2) l` = 2  r` = 4 => m = 3
             *          (3) l` = 1  r` = 4 => m = 2
             *          (4) l` = 2  r` = 5 => m = 3
             *          мы знаем l` < r` -
             *          тогда чтд
             *   так как каждым "ходом" мы делаем m = (l + r) / 2, то всего программа будет работать за O(log(n)) так как изначально расстояние между l и r это n,
             *   и каждый ход мы уменьшаем данное расстояние в два раза, в конце же расстояние = 1 => всего программа работает за O(log(n))
             */
            int m = (l + r) / 2;
            if (a[m] <= x) {
                /*
                 * Pred: m ∈ [0; a.lenght-1] &&
                 *       a[m] <= x
                 * Post: r` = m &&
                 *       a[l`] > x >= a[r`]
                 */
                r = m;
            } else {
                /*
                 * Pred: m ∈ [0; a.lenght-1] &&
                 *       a[m] > x
                 * Post: l` = m &&
                 *       a[l`] > x >= a[r`]
                 */
                l = m;
            }
        }
        // l = r - 1 &&
        // a[l] > x && a[r] <= x   - Inv
        // => i = r, где i min ∈ [0, a.length - 1]: a[i] <= x
        return r;
    }


//    public static int recBinarySearch(int x, int[] a){

    /*
     * Pred: l == -1 &&
     * ∀ i ∈ [0, a.length - 1]: Integer.MIN_VALUE <= a[i] <= Integer.MAX_VALUE &&
     * r == a.lenght &&
     * ∀ i,j ∈ [0, a.length - 1]: i < j => a[i] >= a[j] &&
     * a[-1] = inf &&
     * a[last] = -inf
     * Post: min i ∈ [0, a.length - 1]: a[i] <= x
     * пусть r, l - исходные (для нас константы)
     * r` , l` - изменяемые во время исполнения функции
     */
    public static int recBinarySearch(int x, int l, int r, int[] a) {
        /*
         * Pred: (l` + r`) ∈ [Integer.MIN_VALUE; Integer.MAX_VALUE] &&
         *       (l` + r`)/2 >= Integer.MIN_VALUE &&
         *       (l` + r`)/2 <= Integer.MAX_VALUE
         * Post: m = (l` + r`)/2 &&
         *       l` < m < r` (m ∈ [0, a.length - 1])
         */
        int m = (l + r) / 2;
        if (l == r - 1) {
            /*
             * Pred: l` + 1 == r`
             * Post:min r` ∈ [0, a.length - 1]: a[r`] <= x
             */
            return r;
        }
        if (a[m] <= x) {
            /*
             * Pred: m ∈ [0; a.lenght-1] &&
             *       a[m] <= x
             * Post: a[l`] < x <= a[m]
             */
            /*
            * Inv: a[l] > x &&
            *      a[r] <= x
            *      доказательтство соблюдения совпадает с доказательсвом в iterBinarySearch выше
            */
            return recBinarySearch(x, l, m, a);
        } else {
            /*
             * Pred: m ∈ [0; a.lenght-1] &&
             *       a[m] > x
             * Post: a[m] < x <= a[r`]
             */
            /*
            * Inv: a[l] > x &&
            *      a[r] <= x
            *      доказательтство соблюдения совпадает с доказательсвом в iterBinarySearch выше
            */
            return recBinarySearch(x, m, r, a);
        }
    }
}
