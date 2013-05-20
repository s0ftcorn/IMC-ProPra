#include <stdio.h>

/* Anlegen einiger Variablen zum Testen der Addressen */
int globalIntInit = 10;
char globalCharInit = 'a';
const int globalConstIntInit = 15;
const char globalConstCharInit = 'b';
int globalInt;
char globalChar;
const int globalConstInt;
const char globalConstChar;

/*Funktion für das "Ausrechnen" der Schaltjahre */
void schaltjahre (int startjahr, int endjahr) {
    int count = 0;
    int i;
    for (i=startjahr; i<= endjahr; i++) {
		/* Alle bedingungen für ein Schaltjahr in einer If-Abfrage */
        if (i >= 1582 && (i%400==0 || (i%100!=0 && i%4==0)))
			/* Hochzählen der Anzahl der Schaltjahre */
            count++;
    }
    
    printf("Im Zeitraum von %d  bis %d gab es %d Schaltjahre\n", startjahr, endjahr, count );
}

int main (int argc, char *argv[]) {
	// Wieder Variablen zum Testen der Addressen
    int localInt;
    char localChar;
    const int localConstInt;
    const char localConstChar;
    const char localConstCharInit = 'v';
    const int localConstIntInit = 124;
    char localCharInit = 'a';
    
    int startjahr = 1853;
    int endjahr = 2013;
    schaltjahre(startjahr, endjahr);
    
	// Ausgabe der ganzen Adressen
    printf("\nGlobale initialisierte int Variable: %p\n", (void*)&globalIntInit);
    printf("Globale initialisierte char Variable: %p\n", (void*)&globalCharInit);
    printf("Globale initialisierte const int Variable: %p\n", (void*)&globalConstIntInit);
    printf("Globale initialisierte const char Variable: %p\n", (void*)&globalConstCharInit);
    printf("Globale uninitialisierte int Variable: %p\n", (void*)&globalInt);
    printf("Globale uninitialisierte char Variable: %p\n", (void*)&globalChar);
    printf("Globale uninitialisierte const int Variable: %p\n", (void*)&globalConstInt);
    printf("Globale uninitialisierte const char Variable: %p\n\n", (void*)&globalConstChar);
    printf("Lokale initialisierte int Variable: %p\n", (void*)&startjahr);
    printf("Lokale initialisierte char Variable: %p\n", (void*)&localCharInit);
    printf("Lokale initialisierte const int Variable: %p\n", (void*)&localConstIntInit);
    printf("Lokale initialisierte const char Variable: %p\n", (void*)&localConstCharInit);
    printf("Lokale uninitialisierte int Variable: %p\n", (void*)&localInt);
    printf("Lokale uninitialisierte char Variable: %p\n", (void*)&localChar);
    printf("Lokale uninitialisierte const int Variable: %p\n", (void*)&localConstInt);
    printf("Lokale uninitialisierte const char Variable: %p\n", (void*)&localConstChar);
    
    return 0;
}