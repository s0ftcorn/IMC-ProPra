#include <stdio.h>
#include <pthread.h>

int kontostand;

void* filmebestellen(void *pv);
void* getraenkeundspeisen(void *pv);
void* ueberweisen(void *pv);

int main(int argc, char **argv){
	kontostand = 1000;
	
	int status_film;
	pthread_t thread_film;
	int status_nahrung;
	pthread_t thread_nahrung;
	int status_uberweiung;
	pthread_t thread_uberweisung;
	
	status_film = pthread_create(&thread_film, NULL, &filmebestellen, NULL);
	if(status_film) printf("Fehler beim Filmthread");
	
	status_nahrung = pthread_create(&thread_nahrung, NULL, &getraenkeundspeisen, NULL);
	if(status_nahrung) printf("Fehler beim Getraenke- und Speisenthread");
	
	status_uberweiung = pthread_create(&thread_uberweisung, NULL, &ueberweisen, NULL);
	if(status_uberweiung) printf("Fehler beim Uberweisungsthread");
	
	status_film = pthread_join(thread_film, NULL);
	if(status_uberweiung) printf("Fehler beim aufsammeln des Filmthreads");
	
	status_nahrung = pthread_join(thread_nahrung, NULL);
	if(status_nahrung) printf("Fehler beim aufsammeln des Nahrungsthreads");
	
	status_uberweiung = pthread_join(thread_uberweisung, NULL);
	if(status_uberweiung) printf("Fehler beim aufsammeln des Uberweisungsthread");
	
	printf("Endkontostand: %d\n",kontostand);
	
	pthread_exit(NULL);
}

void* filmebestellen(void *pv){
	int localkonto = kontostand;
	for(int i = 0; i < 5; i++){
		printf("Bestelle Filme\n");
		printf("Kontostand vorher: %d\n",localkonto);
		localkonto -= 200;
		kontostand = localkonto;
		printf("Kontostand nachher: %d\n",localkonto);
	}
	
	pthread_exit(NULL);
}

void* getraenkeundspeisen(void *pv){
	int localkonto = kontostand;
	for(int i = 0; i < 5; i++){
		printf("Kaufe Getraenke und Speisen\n");
		printf("Kontostand vorher %d\n",localkonto);
		localkonto -= 400;
		kontostand = localkonto;
		printf("Kontostand nachher: %d\n",localkonto);
	}
	
	pthread_exit(NULL);
}

void* ueberweisen(void *pv){
	int localkonto = kontostand;
	for(int i = 0; i < 2; i++){
		printf("Ueberweise Geld\n");
		printf("Kontostand vorher %d\n",localkonto);
		localkonto += 2000;
		kontostand = localkonto;
		printf("Kontostand nachher %d\n",localkonto);
	}
	
	pthread_exit(NULL);
}