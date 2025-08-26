(defn is-vector? [something] (and (vector? something) (every? number? something)))
(defn all-op-v [operation, & vectors]
  {:pre  [(and (every? vector? vectors)
               (not-any? #(vector? %) (apply concat vectors))
               (apply = (mapv count vectors)))]
   :post [#(vector? %)]}
  (apply mapv operation vectors)
  )

(defn v+ [& vectors] (apply all-op-v + vectors))
(defn v- [& vectors] (apply all-op-v - vectors))
(defn v* [& vectors] (apply all-op-v * vectors))
(defn vd [& vectors] (apply all-op-v / vectors))
(defn v*s [vector, & s] (
                          let [scalar (reduce * s)]
                          (mapv #(* % scalar) vector)
                          )
  )
(defn scalar [& vectors]
  {:pre  [(every? vector? vectors)]
   :post [#(number? %)]}
  (reduce + (reduce v* vectors)))

(defn vect [& vectors]
  {:pre  [(every? is-vector? vectors)
          (apply = 3 (mapv count vectors))]
   :post [#(is-vector? %)]}
  (if (= 1 (count vectors))
    (first vectors)
    (reduce (fn [result vector]
              (let [[ax ay az] result
                    [bx by bz] vector]
                [(- (* ay bz) (* az by))
                 (- (* az bx) (* ax bz))
                 (- (* ax by) (* ay bx))]))
            (first vectors)
            (rest vectors))))



(defn is-matrix-size? [something] (and (vector? something)
                                       (every? is-vector? something)
                                       ))
(defn equals-sizes-m? [matrices]
  (and (every? (fn [matrix] (= (count (first matrices)) (count matrix))) matrices)
       (every? (fn [matrix] (= (count (first (first matrices))) (count (first matrix)))) matrices)
       (every? (fn [matrix] (every? #(= (count (first matrix)) (count %)) (rest matrix))) matrices)))
(defn is-matrix? [something] (and (vector? something)
                                  (every? is-vector? something)
                                  ))
(defn all-op-m [operation, & matrices]
  {:pre  [(and (every? is-matrix-size? matrices) (equals-sizes-m? matrices))]
   :post [#(is-matrix? %)]}
  (if (= 1 (count matrices))
    (mapv (fn [row] (mapv operation row)) (first matrices))
    (reduce (fn [m1 m2] (mapv (fn [v1 v2] (mapv operation v1 v2)) m1 m2)) matrices))
  )
(defn m+ [& matrices] (apply all-op-m + matrices))
(defn m- [& matrices] (apply all-op-m - matrices))
(defn m* [& matrices] (apply all-op-m * matrices))
(defn md [& matrices] (apply all-op-m / matrices))
(defn m*s [matrix, & s]
  {:pre  [(and (is-matrix? matrix) (every? number? s))]
   :post [#(is-matrix? %)]}
  (let [pr-s (reduce * s)]
    (mapv (fn [vector] (v*s vector pr-s)) matrix))
  )
(defn m*v [matrix, & v]
  {:pre  [(and (is-matrix? matrix) (every? is-vector? v)
               (apply = (map count v))
               (every? #(= (count (first matrix)) (count %)) (rest matrix))
               (= (count (first matrix)) (count (first v)))
               )]
   :post [#(vector? %)]}
  (let [pr-v (reduce v* v)]
    (mapv (fn [vector]
            (reduce + (map * vector pr-v))
            ) matrix)
    )
  )
(defn transpose [matrix]
  {:pre  [(is-matrix? matrix)]
   :post [#(is-matrix? %)]}
  (apply mapv vector matrix))

(defn matrix-compatible? [matrices]
  (every?
    (fn [[a b]]
      (= (count (first a)) (count b)))
    (partition 2 1 matrices)))
(defn m*m [& matrices]
  {:pre  [(every? is-matrix? matrices)
          (matrix-compatible? matrices)
          ]
   :post [#(is-matrix? %)]}
  (reduce (fn [work-matrix current-matrix]
            (let [trans-matrix (transpose current-matrix)]
              (mapv #(mapv (partial scalar %) trans-matrix) work-matrix)))
          (first matrices)
          (rest matrices)))



(defn help-check [v]
  (if (vector? v)
    (conj (help-check (first v)) (count v))
    []))

(defn is-size-s? [& something]
  (and
    (every? vector? something)
    (apply = (mapv help-check something))
    (every? (partial mapv is-size-s?) something)))

(defn is-simplex? [x]
  (or
    (number? x)
    (is-vector? x)
    (and
      (= (range (count x) 0 -1) (mapv count x))
      (every? is-simplex? x))
    ))

(defn all-op-s [operation, & simplex]
  {:pre [(and
           (every? is-simplex? simplex)
           (apply = (mapv help-check simplex)))]}
  (if (vector? (first simplex))
    (apply (partial mapv (fn [& x] (apply all-op-s operation x))) simplex)
    (apply operation simplex)))

(defn x+ [& simplex] (apply all-op-s + simplex))
(defn x- [& simplex] (apply all-op-s - simplex))
(defn x* [& simplex] (apply all-op-s * simplex))
(defn xd [& simplex] (apply all-op-s / simplex))