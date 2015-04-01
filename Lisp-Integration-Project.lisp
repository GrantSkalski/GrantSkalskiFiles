#LISP Integration Project


;;; Project 1
;;; Grant Skalski
;;; Due Oct 16th 2014
;;;====================================================================
;;; INTEGRATE FUNCTIONS

(defun integrate (F V &optional lo hi)
  "The integrate function uses calls to two other functions,
   indef-integrate and def-integrate.
   It has 4 parameters, 2 of which are optional.
   If 4 are present and 2 of them are numbers, 
   the definite integral is constructed.
   If there are only 2 passed then the 
   indefinite integral is constructed instead"
  (cond ((and (null lo)                 ;;; if both lo
	      (null hi))                      ;;; and hi are null
	 (indef-integrate F V))               ;;; then do indef-integrate          
	(t (eval(def-integrate F V lo hi))))) ;;; otherwise eval def-integrate 
;;;-------------------------------------------------------------------

(defun indef-integrate (F V)
"Handles wide range of cases which support the integrals
 of numbers, negatives, addition, subtraction, and powers 
 of -1, and any other exponent"
  (cond ((number-p F)                   ;;; if F is a number
	 (make-product F V))                  ;;; make-product of F and V
;;;-------------------------------------------------------------------

	((equal F 0) 0)                   ;;; handles when the V value is 0
;;;-------------------------------------------------------------------

	((variable-p F)                   ;;; if F is a variable
	 (make-product                    ;;; makes the product
	  (make-quotient 1 2)             ;;; of (/ 1 2)
	  (make-power F 2)))              ;;; then (expt F 2)
;;;-------------------------------------------------------------------
	
	((and                                         ;;; if
	  (negation-p F)                              ;;; F is a negation   
	  (= (length F) 3))                           ;;; of length 3
	 (indef-integrate (negation-operand F) V))    ;;; integrate the negation-operand                   
                                                ;;; of F and V
;;;-------------------------------------------------------------------

	((and                                     ;;; if
	  (negation-p F)                          ;;; F is a negation
	  (< (length F) 2))                       ;;; and has length less than 2 
	 (indef-integrate (make-negation (rest F)) V))


;;;-------------------------------------------------------------------

	((and (power-p F)                            ;;; if F is a power
	      (or                                    ;;; then if power-
                                               ;;; operand-2 
	       (negation-p (power-operand-2 F))      ;;; of F is a negation
	       (minusp (power-operand-2 F)))         ;;; or a negative
	      (or                                    ;;; or
	       (equal (power-operand-2 F)            ;;; or the power-
                                               ;;; operand-2 
		      -1)                                  ;;; is a -1
	       (equal (power-operand-2 F)            ;;; or the power-
                                               ;;; operand-2 is
		      (make-negation 1))))                 ;;; the negation of 1
	 (make-log (power-operand-1 F)))             ;;; make the log
;;;-------------------------------------------------------------------
	      
	((and (power-p F)             ;;; power != -1
	      (function-p 
	       (power-operand-2 F)))  ;;; if power-operand-2 of F is a
                                ;;; function
	 (make-product                ;;; make-product of
	  (make-quotient              ;;; the quotient of
	   1                          ;;; 1 
	   (make-sum                  ;;; and the sum of
	    1                         ;;; 1
	    (power-operand-2 F)))     ;;; and the power-operand-2 of F
	  (make-power                 ;;; then makes-power of
	   (power-operand-1 F)        ;;; power-operand-1
	   (make-sum                  ;;; and the sum of 
	    1                         ;;; 1 
	    (power-operand-2 F)))))   ;;; and power-operand-2 of F
	((sum-p F)                    ;;; if F is a sum   
	 (make-sum (indef-integrate   ;;; add the integral of
		    (sum-operand-1 F)       ;;; sum-operand-1 of F
		    V)                      ;;; and V 
		   (indef-integrate         ;;; to the integral of
		    (sum-operand-2 F)       ;;; sum-operand-2
		    V)))                    ;;; and V
;;;-------------------------------------------------------------------	

	((difference-p F)               ;;; if F is a difference
	 (make-difference               ;;; subtraction of the 
	  (indef-integrate              ;;; integral of
	   (difference-operand-1 F) V)  ;;; difference-operand-1 of F and V
	  (indef-integrate              ;;; and
	   (difference-operand-2 F) V)));;; difference-operand-2 of F and V
;;;-------------------------------------------------------------------
	
	((negation-p F)                 ;;; is F is a negation
	 (make-negation                 ;;; make negation of 
	  (indef-integrate              ;;; the integral of
	   (negation-operand F) V)))))  ;;; negation-operand of F and V


;;;-------------------------------------------------------------------

(defun def-integrate  (F V x y)
"The def-integrate function accepts 4 parameters.
 It uses them to make make the difference of the hi and lo values
 to evaluate definite integrals. 
 It uses the subst method to substitute the real values
 into the indef-intergate answer."
  (eval(make-difference           ;;; subtracts
   (subst y V                     ;;; subst hi into the   
	  (indef-integrate F V))        ;;; indefinite integral of F and V
   (subst x V                     ;;; from subst lo into the
	  (indef-integrate F V)))))     ;;; indefinite integral of F and V  

;;;===================================================================
;;; SYMBOLS
;;; If there is no docstring then the definition was considered trivial
(defconstant *variable-symbols*       '(U V W X Y Z))
(defconstant *negation-symbol*        '-)
(defconstant *product-symbol*         '*)
(defconstant *quotient-symbol*        '/)
(defconstant *power-symbol*           'expt)
(defconstant *sum-symbol*             '+)
(defconstant *log-symbol*             'log)
(defconstant *difference-symbol*      '-)

;;;===================================================================
;;; READERS/SELECTORS
;;; If there is no docstring then the definition was considered trivial
;;;-------------------------------------------------------------------
;;; OPERATORS
(defun negation-operator (F) (first F))
(defun sum-operator (F) (first F))
(defun difference-operator (F) (first F))
(defun log-operator (F) (first F))
(defun power-operator (F) (first F))
(defun quotient-operator (F) (first F))
(defun product-operator (F) (first F))
;;;-------------------------------------------------------------------
;;; OPERANDS  
(defun negation-operand (F)
" recursively works down F in order
  to find the operand that is being negated"
  (cond ((null F) F)
	((and (< (length F) 3) 
	      (not (eql (first F) *negation-symbol*)) 
	      (function-p (first F))) (first F)) 
	(t (negation-operand (rest F)))))
;;;-------------------------------------------------------------------
(defun sum-operand-1 (F) (second F))
(defun sum-operand-2 (F) (third F))
(defun difference-operand-1 (F) (second F))
(defun difference-operand-2 (F) (third F))
(defun product-operand-1 (F) (second F))
(defun product-operand-2 (F) (third F))
(defun quotient-operand-1 (F) (second F))
(defun quotient-operand-2 (F) (third F))
(defun power-operand-1 (F) (second F))
(defun power-operand-2 (F) (third F))
(defun log-operand-1 (F) (second F)) 



 
;;;===================================================================
;;; PREDICATES

(defun variable-p (F)
“ variable-p checks if F is a variable” 
  (not (null (member F *variable-symbols*))))
;;;-------------------------------------------------------------------

(defun number-p (F)
“ number-p check if F is a number”
  (numberp F))
;;;-------------------------------------------------------------------

(defun negation-p (F)
" negation-p checks if F is a list
  then if the list is larger than 2 it
  recurses down the list until the length
  is only 2 then negation-p returns
  the symbol and second F" 
(and                                        ;;; T
 (listp F)                                  ;;; if F is a list
 (or (and                                   ;;; and its either 
      (> (length F) 2)                      ;;; of length greater than
              ;;; 2
      (negation-p (rest F)))                ;;; negation-p on rest F
     (and                                   ;;; or its
      (= (length F) 2)                      ;;; of length = 2
      (eql (first F)                        ;;; and the first of F
	   *negation-symbol*)                     ;;; is *negation-symbol*
      (function-p (second F))))))           ;;; call function-p on second F 
;;;-------------------------------------------------------------------	    

(defun sum-p (F)
" sum-p checks if the F is a sum"           ;;; T             
  (and (listp F)                            ;;; if F is a list
       (= (length F) 3)                     ;;; of length 3
       (equal (sum-operator F)              ;;; and the sum-operator of F
	      *sum-symbol*)                       ;;; is *sum-symbol*
       (function-p                          ;;; and sum-operand-1 of F
	(sum-operand-1 F))                        ;;; is a function
       (function-p                          ;;; and sum-operand-2 of F   
	(sum-operand-2 F))))                      ;;; is a function
;;;-------------------------------------------------------------------

(defun difference-p (F)
" difference-p checks is F is a difference" ;;; T
  (and (listp F)                            ;;; if F is list            
       (= (length F) 3)                     ;;; of length 3
       (equal (difference-operator F)       ;;; and the difference-operator of F  
	      *difference-symbol*)                ;;; is *difference-symbol*
       (function-p                          ;;; and difference-operand-1 of F
	(difference-operand-1 F))                 ;;; is a function
       (function-p                          ;;; and difference-operand-2 of F
	(difference-operand-2 F))))               ;;; is a function

;;;-------------------------------------------------------------------

(defun product-p (F)
" product-p checks if F is a product"     ;;; T
  (and (listp F)                          ;;; if F is a list
       (= (length F) 3)                   ;;; of length 3
       (equal (product-operator F)        ;;; and the product-operator of F
	      *product-symbol*)                 ;;; is *product-symbol*
       (function-p                        ;;; and product-operand-1 of F 
	(product-operand-1 F))                  ;;; is a function
       (function-p                        ;;; and product-operand-2 of F 
	(product-operand-2 F))))                ;;; is a function
;;;-------------------------------------------------------------------
       
(defun quotient-p (F)
" quotient-p checks if F is a quotient"   ;;; T
  (and (listp F)                          ;;; if F is a list
       (= (length F) 3)                   ;;; of length 3
       (equal (quotient-operator F)       ;;; and the quotient-operator of F
	      *quotient-symbol*)                ;;; is *quotient-symbol*
       (function-p                        ;;; and quotient-operand-1 of  F 
	(quotient-operand-1 F))                 ;;; is a function
       (function-p                        ;;; and quotient-operand-2 of F 
	(quotient-operand-2 F))))               ;;; is a function
;;;-------------------------------------------------------------------

(defun power-p (F)
" power-p checks if F is a power"         ;;; T
  (and (listp F)                          ;;; if F is a list  
       (= (length F) 3)                   ;;; of length 3
       (equal (power-operator F)          ;;; and the power-operator of F
	      *power-symbol*)                   ;;; is *power-symbol*
       (function-p                        ;;; and the power-operand-1 of F 
	(power-operand-1 F))                    ;;; is a function
       (function-p                        ;;; and the power-operand-2 of F 
	(power-operand-2 F))))                  ;;; is a function
;;;-------------------------------------------------------------------

(defun log-p (F)
" log-p checks if F is a log"             ;;; T
  (and (listp F)                          ;;; if F is a list 
       (= (length F) 2)                   ;;; of length 2
       (equal (log-operator F)            ;;; and the log-operator of F  
	      *log-symbol*)                     ;;; is *log-symbol*
       (function-p                        ;;; and the log-operand-1 of F 
	(log-operand-1 F))))                    ;;; is a function

 
;;;-------------------------------------------------------------------

(defun function-p (F)
" function-p checks if F is a function"      ;;; T
  (or (number-p F)                           ;;; if F is a number
      (variable-p F)                         ;;; or F is a variable
      (sum-p F)                              ;;; or F is a sum
      (negation-p F)                         ;;; or F is a negation
      (difference-p F)                       ;;; or F is a difference
      (product-p F)                          ;;; or F is a product
      (quotient-p F)                         ;;; or F is a quotient
      (power-p F)                            ;;; or F is a power
      (log-p F)))                            ;;; or F is a log


 
;;;===================================================================
;;; CONSTRUCTORS
;;;-------------------------------------------------------------------

(defun make-variable (V)
" make-variable should be included incase
  what constitutes a varible changes, or the
  interface changes. 
  This constructor relieves those problems if make-variable
  is used throughout coding"  
V)
;;;-------------------------------------------------------------------

(defun make-sum (F G)
" make-sum takes 2 arguments and makes a sum"  
  (cond ((equal 0 F) G)                 ;;; 0 + G = G
	((equal 0 G) F)                       ;;; F + 0 = F
	((or                                  ;;; if
	  (equal (make-negation F)            ;;; (- F) = G
		 G)                                 ;;; or
	  (equal F                            ;;; (- G) = F
		 (make-negation G)))                ;;; make sum returns 0
	 0)
	((and (numberp F)                    ;;; if both F
	      (numberp G))                   ;;; and G are numbers
	 (+ F G))                            ;;; add normally
	(t (list *sum-symbol* F G))))        ;;; else make a list with the form (+ F G)
;;;-------------------------------------------------------------------

(defun make-difference (F G)
" make-difference takes 2 arguments and makes a difference"
  (cond ((equal 0 F) (make-negation G)) ;;; 0 - G = (- G)
	((equal 0 G) F)                       ;;; F- 0 = F
	((and (numberp F)                     ;;; if both F
	      (numberp G))                    ;;; and G are numbers
	 (- F G))                             ;;; subtract normally
 	(t (list *negation-symbol* F G))))    ;;; else make a list with the form (- F G)

;;;-------------------------------------------------------------------

(defun make-product (F G)
" make-product takes 2 arguments and makes a product"
  (cond ((equal 1 F) G)                 ;;; 1 * G = G
	((equal 1 G) F)                       ;;; F * 1 = F
	((equal 0 G) 0)                       ;;; F * 0 = 0
	((equal 0 F) 0)                       ;;; 0 * G = 0
	((and (numberp F)                     ;;; if both F
 	      (numberp G))                    ;;; and G are numbers
	 (* F G))                             ;;; multiply normally
	( t (list *product-symbol* F G))))    ;;; else make a list with the form (* F G)
;;;-------------------------------------------------------------------

(defun make-quotient (F G)
" make-qoutient takes 2 arguments and makes a quotient"
  (cond ((equal F 0) 0)                  ;;; 0 / G = 0
	((equal G 0) nil)                      ;;; F / 0 = nil
	((and (numberp F)                      ;;; if both F
	      (numberp G))                     ;;; and G are numbers
	 (/ F G))                              ;;; divide normally
	( t (list *quotient-symbol* F G))))    ;;; else make a list with form (/ F G)
;;;-------------------------------------------------------------------

 (defun make-negation (F)
" make-negation takes 1 argument and makes a negation"
  (cond ((and                         ;;; if
	  (negation-p F)                    ;;; F is a negation
	  (listp F)                         ;;; and F is a list
	  (>= (length F) 2)                 ;;; of length 2 or greater
	  (eql (mod (length F) 2) 0)        ;;; and list has an even number of elts 
	 (negation-operand F))              ;;; return the negation-operand of F
	((and                               ;;; if
	  (listp F)                         ;;; F is a list
	  (negation-p F)                    ;;; and F is a negation
	  (eql (mod (length F) 2) 1))       ;;; and list has an odd number of elts
	 (list *negation-symbol*            ;;; make a list with the form
	       (negation-operand F)))       ;;; (- (negation-operand F)
	(t (list *negation-symbol* F))))    ;;; else make a list with form (- F)
;;;-------------------------------------------------------------------

(defun make-power (B E)
  "make-power takes 2 arguments and makes a power"
  (cond ((equal B 0) 0)               ;;; 0 ^ E = 0
	((equal B 1) 1)                     ;;; 1 ^ E = 1
	((and (numberp B)                   ;;; if both B
	      (numberp E))                  ;;; and E are numbers
	 (expt B E))                        ;;; do exponent normally
	(t (list *power-symbol* B E))))     ;;; else make a list with form (expt B E)

;;;-------------------------------------------------------------------

(defun make-log (F)
" make-log takes 1 argument and makes a log"
  (cond ((equal 1 F) 0)               ;;; log 1 = 0
	((numberp F)(log F))                ;;; If F is a number then (log F)
	(t (list *log-symbol* F))))         ;;; else make a list with form
                                      ;;; (log F)



