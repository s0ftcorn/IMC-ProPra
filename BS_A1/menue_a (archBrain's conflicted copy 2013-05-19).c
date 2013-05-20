#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

void error(char *str);

int main(int argc, char **argv){
	char auswahl;
	// Endlosschleife bauen
	while(1){
	printf("----------------------------------------------------------\n");
	// Ausgeben des "Menüs"
	printf("Bitte waehlen sie\nd fuer dolphin\np für ps\nx für xterm\n");
	scanf(" %c",&auswahl);
	// Ausgabe abhängig von der Eingabe
	if(auswahl == 'd') printf("-X-dolphin-X-\n");
	else if(auswahl == 'p') printf("-X-ps-X-\n");
	else if(auswahl == 'x') printf("-X-xterm-X-\n");
	else printf("Falsche Eingabe!");
	printf("----------------------------------------------------------");
	}
	
	return 0;
}

void error(char *str){
	
	perror(str);
	EXIT_FAILURE;
}