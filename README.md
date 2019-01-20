# Hitori-Java
PZ.P.3 - Hitori
Projekt realizowany w ramach przedmiotu „Podstawy sztucznej inteligencji”. 
Skład zespołu: Tomasz Suchodolski, Kacper Klimczuk, Mateusz Krawczyk

Opis funkcjonalości:
- możliwość rozwiązywania łamigłówki przez użytkownika, program na bieżąco 
będzie zaznaczać na czerwono pola które kolidują ze sobą, oraz na bieżąco będzie 
wyświetlać komunikaty w przypadku przekroczenia reguł - przecięcia białej figury, 
połączenia bokami czarnych pól
- generowanie łamigłówki o liczbie pól z zakresu 4-40 (przycisk Build Map wraz z choicebox grid size)
- wczytywanie łamigłówki z pliku "map.txt" w konwecji: pierwsza liczba oznacza wymiar planszy, 
następne oznaczają wartości dla kolejnych pól (przycisk Load Map)
- rozwiązywanie automatyczne bazujące na algorytmie A* (przycisk Solve)

