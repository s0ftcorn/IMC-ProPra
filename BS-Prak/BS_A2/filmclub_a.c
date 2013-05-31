#include <stdio.h>

int kontostand;

void filmebestellen(void);
void getraenkeundspeisen(void);
void ueberweisen(void);

int main(int argc, char **argv){
	/* Kontostand mit 1000 "initialisieren" */
	kontostand = 1000;
	/* Aufruf der Funktionen */
	filmebestellen();
	ueberweisen();
	getraenkeundspeisen();
	/* Ausgeben des Kontostandes */
	printf("Endkontostand: %d\n",kontostand);
	
	return 0;
}

void filmebestellen(void){
	/* Lokalen Kontostand initialisieren */
	int localkonto = kontostand;
	int i;
	for(i = 0; i < 5; i++){
		/* 5 mal Filme "bestellen" */
		printf("Bestelle Filme\n");
		printf("Kontostand vorher: %d\n",localkonto);
		localkonto -= 200;
		kontostand = localkonto;
		printf("Kontostand nachher: %d\n",localkonto);
	}
}

void getraenkeundspeisen(void){
	int localkonto = kontostand;
	int i;
	for(i = 0; i < 5; i++){
		/*5 mal Getraenke und Speisen "bestellen"*/
		printf("Kaufe Getraenke und Speisen\n");
		printf("Kontostand vorher %d\n",localkonto);
		localkonto -= 400;
		kontostand = localkonto;
		printf("Kontostand nachher: %d\n",localkonto);
	}
}

void ueberweisen(void){
	int localkonto = kontostand;
	int i;
	for(i = 0; i < 2; i++){
		/*2 mal Geld "ueberweisen"*/
		printf("Ueberweise Geld\n");
		printf("Kontostand vorher %d\n",localkonto);
		localkonto += 2000;
		kontostand = localkonto;
		printf("Kontostand nachher %d\n",localkonto);
	}
}