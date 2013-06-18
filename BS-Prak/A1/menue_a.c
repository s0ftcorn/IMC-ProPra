#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

// Funktionskopf deklarieren
void error(char *str);

int main(int argc, char **argv){
	// Char für die getroffene Auswahl deklarieren
	char auswahl;
	while(1){
	printf("----------------------------------------------------------\n");
	printf("Bitte waehlen sie\nd fuer dolphin\np für ps\nx für xterm\n");
	/* Falls scanf einen Wert kleiner als Eins zurückgibt, dann ist etwas schief gegangen
	   weil wir einen Char erwarten. Kriegen wir einen, ist der Rückgabewert von scanf 1,
	   ging etwas schief, gibt scanf einen Wert kleiner als Null zurück, d.h. auch hier
	   greift die Fehlerbehandlung */
	if( scanf(" %c",&auswahl) < 1 ) error("Eingabe fehlgeschlagen!");
	// Gewählten Menüpunkt ausgeben
	if(auswahl == 'd') printf("-X-dolphin-X-\n");
	if(auswahl == 'p') printf("-X-ps-X-\n");
	if(auswahl == 'x') printf("-X-xterm-X-\n");
	printf("----------------------------------------------------------");
	}
	
	return 0;
}

void error(char *str){
	
	perror(str);
	exit(EXIT_FAILURE);
}