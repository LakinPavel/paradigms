:- load_library('alice.tuprolog.lib.DCGLibrary').


example(bin(add,bin(mul,variable(x),bin(sub,variable(y),variable(z))),un(negate, const(100)))).


lookup(K, [(K, V) | _], V).
lookup(K, [_ | T], V) :- lookup(K, T, V).

bin(add, A, B, R) :- R is A + B.
bin(sub, A, B, R) :- R is A - B.
bin(mul, A, B, R) :- R is A * B.
bin(dvd, A, B, R) :- R is A / B.

un(negate, A, R) :- R is -A.


evaluate(variable(Name), Vars, R) :- lookup(Name, Vars, R).
evaluate(const(Value), _, Value).
evaluate(bin(Op, A, B), Vars, R) :-
    evaluate(A, Vars, AV),
    evaluate(B, Vars, BV),
    bin(Op, AV, BV, R).
evaluate(un(Op, A), Vars, R) :-
    evaluate(A, Vars, AV),
    un(Op, AV, R).


expr_term(variable(Name), Name) :- atom(Name).
expr_term(const(Value), Value) :- number(Value).
expr_term(bin(Op, A, B), R) :- R =.. [Op, AT, BT], expr_term(A, AT), expr_term(B, BT).
expr_term(un(Op, A), R) :- R =.. [Op, AT], expr_term(A, AT).



expr_text(E, S) :- ground(E), expr_term(E, T), text_term(S, T).
expr_text(E, S) :-   atom(S), text_term(S, T), expr_term(E, T).


%//////3.2

expr_chars_(variable(Name), [Char]) :- atom_chars(Name, [Char]).
expr_chars_(const(Value), Chars) :- number_chars(Value, Chars).

flatten([], []).
flatten([H | T], R) :- append(H, FT, R), flatten(T, FT).

op_chars(add, ['+']).
op_chars(sub, ['-']).
op_chars(mul, ['*']).
op_chars(dvd, ['/']).
op_chars(negate, ['negate']).


expr_chars_(bin(Op, A, B), C) :-
  op_chars(Op, OpC),
  expr_chars_(A, AC),
  expr_chars_(B, BC),
  flatten([OpC, ['('], AC, [','], BC, [')']], C).
expr_chars_(un(Op, A), C) :-
  op_chars(Op, OpC),
  expr_chars_(A, AC),
  flatten([OpC, ['('], AC, [')']], C).

expr_atom_(E, A) :- expr_chars_(E, C), atom_chars(A, C).

expr_chars(variable(Name), [Char]) :-
    atom_chars(Name, [Char]),
    member(Name, [x, y, z]).

all_member([], _).
all_member([H | T], Values) :- member(H, Values), all_member(T, Values).

nonvar(V, T) :- var(V).
nonvar(V, T) :- nonvar(V), call(T).


expr_p(variable(Name)) --> [Name], { member(Name, [x, y, z]) }.

expr_p(const(Value)) -->
  { nonvar(Value, number_chars(Value, Chars)) },
  digits_p(Chars),
  { Chars = [_ | _], number_chars(Value, Chars) }.

digits_p([]) --> [].
digits_p([H | T]) -->
  { member(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'])},
  [H],
  digits_p(T).

op_p(add) --> ['+'].
op_p(sub) --> ['-'].
op_p(mul) --> ['*'].
op_p(dvd) --> ['/'].
op_p(negate) --> ['negate'].


expr_p(bin(Op, A, B)) --> ['('], expr_p(A), [' '], op_p(Op), [' '], expr_p(B), [')'].
expr_p(un(Op, A)) --> ['('], op_p(Op), [' '], expr_p(A), [')'].

skip_ws([], Current, Current).
skip_ws([H | T], Work, NC) :-
		eq(H, ['(', ')', 'x', 'y', 'z']),
		append(Work, [H], Current),
		print(T), nl,
		print(Current), nl, nl,
		skip_ws(T, Current, NC).
skip_ws([H | T], Work, NC) :-
		eq(H, ['+', '-', '/', '*']),
		append(Work, [' '], Current),
		append(Current, [H], H1_Current),
		append(H1_Current, [' '], H2_Current),
		print(T), nl,
		print(H2_Current), nl, nl,
		skip_ws(T, H2_Current, NC).
skip_ws([H | T], Work, NC) :-
		eq(H, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.']),
		append(Work, ['1'], Current),
		print(T), nl,
		print(Current), nl, nl,
		skip_ws(T, Current, NC).
skip_ws([H | T], Work, NC) :-
		eq(H, [' ']),
		print(T), nl,
		print(Work), nl, nl,
		skip_ws(T, Work, NC).
eq(A, [A | T]).
eq(A, [H | T]) :- eq(A, T).

infix_str(E, A) :- ground(E), phrase(expr_p(E), C), atom_chars(A, C).
infix_str(E, A) :- atom(A), atom_chars(A, C),
		skip_ws(C, [], NC),
		phrase(expr_p(E), NC).