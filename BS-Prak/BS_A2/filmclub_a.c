#include <stdio.h>

int kontostand;

void filmebestellen(void);
void getraenkeundspeisen(void);
void ueberweisen(void);

int main(int argc, char **argv){
	kontostand = 1000;
	filmebestellen();
	ueberweisen();
	getraenkeundspeisen();
	
	printf("Endkontostand: %d\n",kontostand);
	
	return 0;
}

void filmebestellen(void){
	int localkonto = kontostand;
	for(int i = 0; i < 5; i++){
		printf("Bestelle Filme\n");
		printf("Kontostand vorher: %d\n",localkonto);
		localkonto -= 200;
		kontostand = localkonto;
		printf("Kontostand nachher: %d\n",localkonto);
	}
}

void getraenkeundspeisen(void){
	int localkonto = kontostand;
	for(int i = 0; i < 5; i++){
		printf("Kaufe Getraenke und Speisen\n");
		printf("Kontostand vorher %d\n",localkonto);
		localkonto -= 400;
		kontostand = localkonto;
		printf("Kontostand nachher: %d\n",localkonto);
	}
}

void ueberweisen(void){
	int localkonto = kontostand;
	for(int i = 0; i < 2; i++){
		printf("Ueberweise Geld\n");
		printf("Kontostand vorher %d\n",localkonto);
		localkonto += 2000;
		kontostand = localkonto;
		printf("Kontostand nachher %d\n",localkonto);
	}
}