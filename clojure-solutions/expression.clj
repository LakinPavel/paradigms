(defn square [arg]
  (* arg arg))

(defn my-divide
  ([arg] (/ 1.0 arg))
  ([first-arg & args] (reduce (fn [x y] (/ (double x) (double y))) first-arg args)))

(defn my-mean [& args]
  (/ (apply + args) (count args)))

(defn normal-varn [& args]
  (-
    (apply my-mean (map square args))
    (square (/ (apply + args) (count args)))))

(defn my-phic [& args]
  (Math/atan2 (first args) (second args)))
(defn my-absc [& args]
  (Math/sqrt (+ (* (first args) (first args)) (* (second args) (second args)))))


(defn operations [sign]
  (fn [& args] (fn [map-v] (apply sign (mapv #(% map-v) args)))))

(defn constant [val] (constantly val))
(defn variable [var] (fn [map] (map var)))

(def add (operations +))
(def multiply (operations *))
(def subtract (operations -))
(def divide (operations my-divide))
(def negate (operations -))
(def mean (operations my-mean))
(def varn (operations normal-varn))

(def map-operations {'+      add
                     '-      subtract
                     '*      multiply
                     '/      divide
                     'negate negate
                     'mean   mean
                     'varn   varn
                     'const  constant
                     'var    variable})

(defn parse [expression map-name]
  (cond
    (number? expression) ((map-name 'const) expression)
    (symbol? expression) ((map-name 'var) (name expression))
    (list? expression) (apply (map-name (first expression)) (mapv #(parse % map-name) (rest expression)))))
; :NOTE: copy-paste
(defn parseFunction [string] (parse (read-string string) map-operations))






; ////// 11
(definterface Expression
  (^Number evaluate [map-v])
  (^String toString [])
  (^String toStringInfix [])
  (diff [respect]))

(declare const0)
; :NOTE: naming: arg
(deftype JConstant [arg]
  Expression
  (evaluate [this map-v] (.-arg this))
  (toString [this] (str (.-arg this)))
  (toStringInfix [this] (str (.-arg this)))
  (diff [this respect] const0))
(defn Constant [arg] (JConstant. arg))

(def const0 (Constant 0))
(def const1 (Constant 1))
(def const2 (Constant 2))

(deftype JVariable [arg]
  Expression
  (evaluate [this map-v] (map-v (str (Character/toLowerCase (first (.-arg this))))))
  (toString [this] (str (.-arg this)))
  (toStringInfix [this] (str (.-arg this)))
  (diff [this respect]
    (if (= (str (Character/toLowerCase (first (.-arg this)))) respect) const1 const0)))
(defn Variable [arg] (JVariable. arg))

; :NOTE: extra memory
(deftype JOperation [sign str-sign diff-op args inf-str]
  Expression
  (evaluate [this map-v] (apply sign (mapv #(.evaluate % map-v) args)))
  (toString [this] (let [str-args (map #(str (.toString %)) args)]
                     (str "(" str-sign " " (clojure.string/join " " str-args) ")")))
  (toStringInfix [_] (inf-str str-sign args))
  (diff [this respect] (diff-op (mapv #(.diff % respect) args))))

(defn evaluate [expr map-v] (.evaluate expr map-v))
(defn toString [expr] (.toString expr))
(defn toStringInfix [expr] (.toStringInfix expr))
(defn diff [expr diff-result] (.diff expr diff-result))
(defn inf-Str-un [str-sign arg] (str str-sign " " (toStringInfix (first arg))))
(defn inf-Str-not-un [str-sign args] (let [str-args (map #(str (toStringInfix %)) args)]
                                       (str "(" (clojure.string/join (str " " str-sign " ") str-args) ")")))


(defn Add [& args]
  (JOperation. + "+"
               (fn [args'] (apply Add args'))
               args inf-Str-not-un))
(defn Subtract [& args] (JOperation. - "-"
                                     (fn [args'] (apply Subtract args'))
                                     args inf-Str-not-un))
(declare Multiply)
(defn dif-mult [args args'] (apply Add
                                   ; :NOTE: square
                                   (mapv
                                     (fn [index]
                                       (apply Multiply
                                              (map-indexed
                                                (fn [i arg]
                                                  (if (= i index)
                                                    (nth args' i)
                                                    arg))
                                                args)))
                                     (range (count args)))))

; :NOTE: extra closure
(defn Multiply [& args]
  (JOperation. * "*"
               (fn [args']
                 (dif-mult args args'))
               args inf-Str-not-un))
(defn squareObj [x] (Multiply x x))
(declare Negate)
(defn Divide [& args] (JOperation. my-divide "/"
                                   (fn [args']
                                     (if (== 1 (count args))
                                       (Divide (Negate (first args')) (squareObj (first args)))
                                       (let [numerator (first args)
                                             numerator' (first args')
                                             denominator (apply Multiply (rest args))
                                             denominator' (dif-mult (rest args) (rest args'))]
                                         (Divide (Subtract (Multiply numerator' denominator)
                                                           (Multiply numerator denominator'))
                                                 (squareObj denominator)))
                                       ))
                                   args inf-Str-not-un))
(defn Negate [& args] (JOperation. - "negate"
                                   (fn [arg'] (apply Negate arg'))
                                   args inf-Str-un))
(defn rep [args' p] (reduce (fn [cur arg'] (Add cur (Multiply p arg')))
                            const0
                            args'))
(defn p-var [n] (Divide const1 (Constant n)))
; :NOTE: simplify
(defn Mean [& args] (JOperation. my-mean "mean"
                                 (fn [args'] (rep args' (p-var (count args))))
                                 args inf-Str-not-un))
(defn Varn [& args] (JOperation. normal-varn "varn"
                                 (fn [args']
                                   (let [mIfSquares (Multiply (Multiply
                                                                (reduce (fn [curIn arg] (
                                                                                          Add curIn (Multiply (p-var (count args)) arg)))
                                                                        const0
                                                                        args) (rep args' (p-var (count args)))) const2)
                                         mOfSquares (reduce
                                                      (fn [curOf arg']
                                                        (Add curOf (Multiply (p-var (count args))
                                                                             (Multiply (Multiply arg' (nth args (.indexOf args' arg'))) const2))))
                                                      const0
                                                      args')]
                                     (Subtract mOfSquares mIfSquares)
                                     ))
                                 args inf-Str-not-un))

(defn AbsC [& args] (JOperation. my-absc "absc" nil args inf-Str-not-un))
(defn PhiC [& args] (JOperation. my-phic "phic" nil args inf-Str-not-un))
(def operations-map
  {'+      Add
   '-      Subtract
   '*      Multiply

   '/      Divide
   'negate Negate
   'mean   Mean
   'varn   Varn
   'const  Constant
   'var    Variable
   'absc   AbsC
   'phic   PhiC})
(defn parseObject [string] (parse (read-string string) operations-map))





;////////////////12
(load-file "parser.clj")
(def *all-chars (mapv char (range 0 128)))
(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))
(def *space (+char (apply str (filter #(Character/isWhitespace %) *all-chars))))
(def *ws (+ignore (+star *space)))
(def *skip-ws (fn [p] (+seqn 0 *ws p *ws)))
(defn *string [s]
  (->> s
       seq
       (mapv (comp +char str))
       (apply (comp +str +seq))))
(def *digit (+char (apply str (filter #(Character/isDigit %) *all-chars))))
(def *number (+map read-string (+str (+seq (+opt (+char "-")) (+str (+plus *digit)) (+str (+opt (+seq (+char ".") (+plus *digit))))))))
(def *variable (+map Variable (+str (*skip-ws (+plus (+char "XYZxyz"))))))
(def *constant (+map Constant (*skip-ws *number)))
(declare *last *base)
(def *negate (+map Negate (+seqn 1 *ws (*string "negate") *ws (delay *base))))
(def *bracket (+seqn 1 (+char "(") *ws (delay *last) *ws (+char ")")))
(defn *operation-left [arg next]
  (+map
    (partial reduce (fn [left [sign right]] (let [op (get operations-map (->> sign str str -> symbol))]
                                              (op left right))))
    (+seqf cons next (+star (+seq *ws arg *ws next)))
    ))
(defn *operation-right [arg next]
  (+map
    (comp (partial reduce (fn [left [sign right]] (let [op (get operations-map (->> sign str str -> symbol))]
                                                    (op right left))))
          #(cons (first %) (partition 2 (rest %))) reverse flatten)
    (+seqf cons next (+star (+seq *ws arg *ws next)))
    ))
(def *base (+or *constant *negate *variable *bracket))
(def *absc-or-phic (*operation-right (+or (*string "absc") (*string "phic")) *base))
(def *multiply-or-divide (*operation-left (+char "*/") *absc-or-phic))
(def *add-or-subtract (*operation-left (+char "+-")
                                       *multiply-or-divide))
(def *last *add-or-subtract)
(def parseObjectInfix (+parser (*skip-ws *last)))
