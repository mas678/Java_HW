(defn isVector? [item] (and (vector? item) (every? number? item)))
(defn tensor? [item]
  (or (number? item)
      (every? number? item)
      (and (every? (partial == (count (first item))) (mapv count item)) (every? tensor? item))))

(defn matrix? [matrix] (and (every? isVector? matrix) (tensor? matrix)))

(defn equalMatrix? [matrices]
  (and (every? matrix? matrices)
       (tensor? matrices)))

(defn mapWithConditions [function items conditions conclusion]
  {:pre [(conditions items)]
   :post [(conclusion %)]}
    (if (every? number? items)
      (apply function items)
      (apply mapv function items)))

(defn v+ [& vectors] (mapWithConditions + vectors matrix? isVector?))
(defn v- [& vectors] (mapWithConditions - vectors matrix? isVector?))
(defn v* [& vectors] (mapWithConditions * vectors matrix? isVector?))

(defn v*s [v & constants]
  {:pre [(isVector? v) (every? number? constants)]
   :post [(isVector? %)]}
  (reduce (fn [v s] (mapv (partial * s) v)) v constants))

(defn scalar [v1 v2]
  {:pre (matrix? [v1, v2])
   :post [(number? %)]}
  (reduce + (v* v1 v2)))

(defn vect [& args]
  {:pre [(every? isVector? args)
         (> (count args) 0)
         (every? (fn [x] (== (count x) 3)) args)]
   :post [(isVector? %)]}
  (letfn [(det [x y v1 v2] (- (* (nth v1 x) (nth v2 y)) (* (nth v1 y) (nth v2 x))))]
  (reduce (fn [v1 v2] (vector (det 1 2 v1 v2) (det 2 0 v1 v2) (det 0 1 v1 v2))) args)))

(defn m+ [& matrices] (mapWithConditions v+ matrices equalMatrix? matrix?))
(defn m- [& matrices] (mapWithConditions v- matrices equalMatrix? matrix?))
(defn m* [& matrices] (mapWithConditions v* matrices equalMatrix? matrix?))

(defn m*s [m & constants]
  {:pre [(matrix? m) (every? number? constants)]
   :post [(matrix? %)]}
  (reduce (fn [m s] (mapv (fn [v] (v*s v s)) m)) m constants))

(defn m*v [m v]
  {:pre [(matrix? m) (isVector? v)]
   :post [(isVector? %)]}
  (mapv (fn [line] (reduce + (v* line v))) m))

(defn transpose [m]
  {:pre [(matrix? m)]
   :post [(matrix? %)]}
  (apply mapv vector m))

(defn m*m [& matrices]
  {:pre [(> (count matrices) 0) (every? matrix? matrices)]
   :post [(matrix? %)]}
  (reduce (fn [m1 m2] (mapv (partial m*v (transpose m2)) m1)) matrices))

(defn tensorShape [tensor]
  {:pre [(tensor? tensor)]}
  (if (number? tensor)
    (vector)
    (apply vector (count tensor) (tensorShape (first tensor)))))

(defn isSuf? [arr1 arr2]
  {:pre [(vector? arr1) (vector? arr2)]}
  (if (== (* (count arr1) (count arr2)) 0)
    true
    (and (== (peek arr1) (peek arr2)) (isSuf? (pop arr1) (pop arr2)))))

(defn maxLength [vectors]
  (if (== (count (peek vectors)) (apply max (mapv (partial count) vectors)))
    (peek vectors)
    (recur (pop vectors))))

(defn broadcast [tensors]
  {:pre [(every? tensor? tensors)
         (every? (partial isSuf? (maxLength (mapv tensorShape tensors))) (mapv tensorShape tensors))]
   :post [(tensor? %)]}
  (letfn [(maxTensor [items]
            (if (== (count (tensorShape (peek items))) (count (maxLength (mapv tensorShape items))))
              (peek items)
              (recur (pop items))))
          (broadcast2 [t1 t2]
            (if (== (count (tensorShape t1)) (count (tensorShape t2)))
              t1
              (apply vector (repeat (count t2) (broadcast2 t1 (first t2))))))]
  (mapv (fn [tensor] (broadcast2 tensor (maxTensor (apply vector tensors)))) tensors)))

(defn tensorFunc [func tenFunc tensors]
  (if (every? number? tensors)
    (mapWithConditions func tensors tensor? tensor?)
    (mapWithConditions tenFunc tensors tensor? tensor?)))

(defn b+ [& args] (tensorFunc + b+ (broadcast args)))
(defn b- [& args] (tensorFunc - b- (broadcast args)))
(defn b* [& args] (tensorFunc * b* (broadcast args)))
(defn bd [& args] (tensorFunc / bd (broadcast args)))
(println (b+ 10 (vector 1 2)))
