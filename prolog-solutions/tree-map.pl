max(A, B, A) :- A > B.
max(A, B, B) :- A < B.
max(A, A, A).
node(Key, Value, Left, Right, Height).
get_key(node(Key, _, _, _, _), Key).
get_value(node(_, Value, _, _, _), Value).
get_left(node(_, _, Left, _, _), Left).
get_right(node(_, _, _, Right, _), Right).
get_height(node(_, _, _, _, Height), Height).
get_height(empty_node, 0).
count_height(node(_, _, Left, Right, _), Height) :-
        get_height(Left, Height_left),
        get_height(Right, Height_right),
        max(Height_left, Height_right, M),
        Height is M + 1.

diff(node(_, _, Left, Right, _), D) :- get_height(Left, H_l), get_height(Right, H_r), D is H_l - H_r.

balance(node(Key, Value, Left, Right, Height), Result) :-
        Cur = node(Key, Value, Left, Right, Height),
        diff(Cur, -2),
        (diff(Right, -1) ; diff(Right, 0)),
        rotate_left(Cur, Result).
balance(node(Key, Value, Left, Right, Height), Result) :-
        Cur = node(Key, Value, Left, Right, Height),
        diff(Cur, -2),
        diff(Right, 1),
        Right = node(R_k, R_v, R_l, R_r, R_h),
        ((diff(R_l, 1) ; diff(R_l, 0)) ; diff(R_l, -1)),
        big_rotate_left(Cur, Result).

balance(node(Key, Value, Left, Right, Height), Result) :-
    Cur = node(Key, Value, Left, Right, Height),
    diff(Cur, 2),
    (diff(Left, 0) ; diff(Left, 1)),
    rotate_right(Cur, Result).

balance(node(Key, Value, Left, Right, Height), Result) :-
    Cur = node(Key, Value, Left, Right, Height),
    diff(Cur, 2),
    diff(Left, -1),
    Left = node(L_k, L_v, L_l, L_r, L_h),
    ((diff(L_r, -1) ; diff(L_r, 0)) ; diff(L_r, 1)),
    big_rotate_right(Cur, Result).

balance(X, X) :- !.


map_put(empty_node, Key, Value, node(Key, Value, empty_node, empty_node, 1)) :- !.
map_put(node(Key, _, Left, Right, Height), Key, Value_new, Result) :-
        Result = node(Key, Value_new, Left, Right, Height), !.
map_put(node(Key, Value, empty_node, Right, Height), Key_new, Value_new, Result) :-
        Key > Key_new,
        Ans = node(Key, Value, node(Key_new, Value_new, empty_node, empty_node, 1), Right, Height_new),
        count_height(Ans, Height_new),
        balance(Ans, Result), !.
map_put(node(Key, Value, Left, empty_node, Height), Key_new, Value_new, Result) :-
        Key < Key_new,
        Ans = node(Key, Value, Left, node(Key_new, Value_new, empty_node, empty_node, 1), Height_new),
        count_height(Ans, Height_new),
        balance(Ans, Result), !.
map_put(node(Key, Value, Left, Right, Height), Key_new, Value_new, Result) :-
        Left \= empty_node,
        Key > Key_new,
        map_put(Left, Key_new, Value_new, L),
        Ans = node(Key, Value, L, Right, Height_ans),
        count_height(Ans, Height_ans),
        balance(Ans, Result), !.
map_put(node(Key, Value, Left, Right, Height), Key_new, Value_new, Result) :-
        Right \= empty_node,
        Key < Key_new,
        map_put(Right, Key_new, Value_new, R),
        Ans = node(Key, Value, Left, R, Height_ans),
        count_height(Ans, Height_ans),
        balance(Ans, Result), !.


rotate_left(node(A_Key, A_Value, A_Left, A_Right, A_Height), Result) :-
        A_Right = node(R_k, R_v, R_l, R_r, R_h),
        A = node(A_Key, A_Value, A_Left, R_l, A_Height_new),
        count_height(A, A_Height_new),
        Result = node(R_k, R_v, A, R_r, Height_new),
        count_height(Result, Height_new).

big_rotate_left(node(A_Key, A_Value, A_Left, A_Right, A_Height), Result) :-
        A = node(A_Key, A_Value, A_Left, A_Right, A_Height),
        rotate_right(A_Right, C),
        B = node(A_Key, A_Value, A_Left, C, A_Height),
        rotate_left(B, Result).

rotate_right(node(A_Key, A_Value, A_Left, A_Right, A_Height), Result) :-
        A_Left = node(L_k, L_v, L_l, L_r, L_h),
        A = node(A_Key, A_Value, L_r, A_Right, A_Height_new),
        count_height(A, A_Height_new),
        Result = node(L_k, L_v, L_l, A, Height_new),
        count_height(Result, Height_new).
big_rotate_right(node(A_Key, A_Value, A_Left, A_Right, A_Height), Result) :-
        A = node(A_Key, A_Value, A_Left, A_Right, A_Height),
        rotate_left(A_Left, C),
        B = node(A_Key, A_Value, C, A_Right, A_Height),
        rotate_right(B, Result).


map_get(node(Key, Value, Left, Right, _), Key, Value).
map_get(node(Key, Value, Left, Right, _), Key_find, Result) :-
        Key_find < Key,
        map_get(Left, Key_find, Result).
map_get(node(Key, Value, Left, Right, Height), Key_find, Result) :-
        A = node(Key, Value, Left, Right, Height),
        Key_find > Key,
        map_get(Right, Key_find, Result).


remove_to_min(empty_node, _, _, empty_node) :- !.
remove_to_min(node(Key, Value, empty_node, Right, Height), Key, Value, Right) :- !.
remove_to_min(node(Key, Value, Left, Right, Height), K, V, Result) :-
        remove_to_min(Left, K, V, Ans),
        A = node(Key, Value, Ans, Right, Height_new),
        count_height(A, Height_new),
        balance(A, Result).


map_remove(empty_node, _, empty_node) :- !.
map_remove(node(Key, _, Left, empty_node, _), Key, Result) :-
        balance(Left, Result), !.
map_remove(node(Key, _, empty_node, Right, _), Key, Result) :- balance(Right, Result), !.
map_remove(node(Key, Value, Left, Right, Height), KeyToRemove, node(Key, Value, Left_new, Right, Height)) :-
        KeyToRemove < Key,
        map_remove(Left, KeyToRemove, Left_new).
map_remove(node(Key, Value, Left, Right, Height), KeyToRemove, node(Key, Value, Left, Right_new, Height)) :-
        KeyToRemove > Key,
        map_remove(Right, KeyToRemove, Right_new).
map_remove(node(Key, Value, Left, Right, Height), Key, Result) :-
        remove_to_min(Right, K, V, Current),
        A = node(K, V, Left, Current, Height_new),
        count_height(A, Height_new),
        balance(A, Result), !.


map_build([], empty_node) :- !.
map_build([(Key, Value) | T], TreeMap) :-
        map_build(T, TempTree),
        map_put(TempTree, Key, Value, TreeMap).


find_m_key(empty_node, _, Current, Current) :- !.
find_m_key(node(Key, _, Left, Right, _), Key_i, Current, Ans) :-
    Key >= Key_i, !,
    find_m_key(Left, Key_i, Key, Ans).
find_m_key(node(Key, _, Left, Right, _), Key_i, Current, Ans) :-
    Key < Key_i,
    find_m_key(Right, Key_i, Current, Ans).


map_ceilingEntry(node(Key, Value, Left, Right, Height), Key_i, (Key_find, Value_find)) :-
        find_m_key(node(Key, Value, Left, Right, Height), Key_i, Key_i, Key_find),
        map_get(node(Key, Value, Left, Right, Height), Key_find, Value_find).


map_removeCeiling(empty_node, _, empty_node) :- !.
map_removeCeiling(node(Key, Value, Left, Right, Height), Key_i, Result) :-
        find_m_key(node(Key, Value, Left, Right, Height), Key_i, Key_i, Key_find),
        map_remove(node(Key, Value, Left, Right, Height), Key_find, Result).


