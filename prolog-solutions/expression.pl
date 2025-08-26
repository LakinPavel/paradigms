:- load_library('alice.tuprolog.lib.DCGLibrary').

lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

operation(op_add, A, B, R) :- R is A + B.
operation(op_subtract, A, B, R) :- R is A - B.
operation(op_multiply, A, B, R) :- R is A * B.
operation(op_divide, A, B, R) :- R is A / B.

operation(op_negate, A, R) :- R is -A.

operation(op_bitnot, A, R) :- R is \A.
operation(op_bitand, A, B, R) :- R is A /\ B.
operation(op_bitor, A, B, R) :- R is A \/ B.
operation(op_bitxor, A, B, R) :- R is (A \/ B) /\ \(A /\ B).


to_lower_case('Z', 'z').
to_lower_case('z', 'z').
to_lower_case('X', 'x').
to_lower_case('x', 'x').
to_lower_case('Y', 'y').
to_lower_case('y', 'y').
evaluate(variable(Name), Vars, R) :- atom_chars(Name, [N | _]), to_lower_case(N, Na), lookup(Na, Vars, R).
evaluate(const(Value), _, Value).
evaluate(operation(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    operation(Op, AV, BV, R).
evaluate(operation(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    operation(Op, AV, R).


expr_term(variable(Name), Name) :- atom(Name).
expr_term(const(Value), Value) :- number(Value).
expr_term(operation(Op, A, B), R) :- R =.. [Op, AT, BT], expr_term(A, AT), expr_term(B, BT).
expr_term(operation(Op, A), R) :- R =.. [Op, AT], expr_term(A, AT).

expr_text(E, S) :- ground(E), expr_term(E, T), text_term(S, T).
expr_text(E, S) :-   atom(S), text_term(S, T), expr_term(E, T).

expr_chars_(variable(Name), [Char]) :- atom_chars(Name, [Char]).
expr_chars_(const(Value), Chars) :- number_chars(Value, Chars).

flatten([], []).
flatten([H | T], R) :- append(H, FT, R), flatten(T, FT).

expr_chars_(operation(Op, A, B), C) :-
  op_chars(Op, OpC),
  expr_chars_(A, AC),
  expr_chars_(B, BC),
  flatten([OpC, ['('], AC, [','], BC, [')']], C).
expr_chars_(operation(Op, A), C) :-
  op_chars(Op, OpC),
  expr_chars_(A, AC),
  flatten([OpC, ['('], AC, [')']], C).

expr_atom_(E, A) :- expr_chars_(E, C), atom_chars(A, C).

all_member([], _).
all_member([H | T], Values) :- member(H, Values), all_member(T, Values).

nonvar(V, T) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).

expr_p(variable(Name)) -->
  { nonvar(Name, atom_chars(Name, Chars)) },
  var_p(Chars),
  { Chars = [_ | _], atom_chars(Name, Chars) }.

var_p([]) --> [].
var_p([H | T]) -->
  { member(H, ['x', 'y', 'z', 'X', 'Y', 'Z'])},
  [H],
  var_p(T).

expr_p(const(Value)) -->
  { nonvar(Value, number_chars(Value, Chars)) },
  digits_p(Chars),
  { Chars = [_ | _], Chars \= ['-'], number_chars(Value, Chars) }.

digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '-'])},
  [H],
  digits_p(T).

op_p(op_add) --> ['+'].
op_p(op_subtract) --> ['-'].
op_p(op_multiply) --> ['*'].
op_p(op_divide) --> ['/'].
op_p(op_negate) --> ['negate'].
op_p(op_bitnot) --> ['~'].
op_p(op_bitand) --> ['&'].
op_p(op_bitor) --> ['|'].
op_p(op_bitxor) --> ['^'].

expr_p(operation(Op, A, B)) --> ['('], expr_p(A), [' '], op_p(Op), [' '], expr_p(B), [')'].
expr_p(operation(Op, A)) --> op_p(Op), [' '], expr_p(A).

skip_ws([], Current, Current).
skip_ws([H | T], Work, NC) :-
		eq(H, ['(', ')', 'x', 'y', 'z', 'X', 'Y', 'Z',
		       '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.']),
		append(Work, [H], Current),
		skip_ws(T, Current, NC).
skip_ws([H1, H2 | T], Work, NC) :-
		eq(H1, ['-']),
		\+ eq(H2, [' ']),
		append(Work, [H1], Current),
		append(Current, [H2], H1_Current),
		skip_ws(T, H1_Current, NC).
skip_ws([H | T], Work, NC) :-
		eq(H, ['+', '-', '/', '*', '&', '|', '^']),
		append(Work, [' '], Current),
		append(Current, [H], H1_Current),
		append(H1_Current, [' '], H2_Current),
		skip_ws(T, H2_Current, NC).
skip_ws([H1, H2, H3, H4, H5, H6 | T], Work, NC) :-
		eq(H1, ['n']), eq(H2, ['e']), eq(H3, ['g']),
		eq(H4, ['a']), eq(H5, ['t']), eq(H6, ['e']),
		append(Work, ['negate'], H1_Current),
		append(H1_Current, [' '], H2_Current),
		skip_ws(T, H2_Current, NC).
skip_ws([H| T], Work, NC) :-
		eq(H, ['~']),
		append(Work, ['~'], H1_Current),
		append(H1_Current, [' '], H2_Current),
		skip_ws(T, H2_Current, NC).
skip_ws([H | T], Work, NC) :-
		eq(H, [' ']),
		skip_ws(T, Work, NC).
eq(A, [A | T]).
eq(A, [H | T]) :- eq(A, T).

infix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C).
infix_str(E, A) :- atom(A), atom_chars(A, C),
		skip_ws(C, [], NC),
		phrase(expr_p(E), NC), !.