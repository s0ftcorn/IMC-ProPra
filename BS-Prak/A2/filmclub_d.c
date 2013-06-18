#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

int kontostand;
pthread_mutex_t lock;
/*Conditionvariable deklarieren*/
pthread_cond_t cond;

void* filmebestellen(void *pv);
void* getraenkeundspeisen(void *pv);
void* ueberweisen(void *pv);

int main(int argc, char **argv){
	int status_film;
	pthread_t thread_film;
	int status_nahrung;
	pthread_t thread_nahrung;
	int status_uberweiung;
	pthread_t thread_uberweisung;
	
	kontostand = 1000;
	if( pthread_mutex_init(&lock, NULL) != 0 ){
		printf("Fehler beim initialisieren des Mutex\n");
	}
	/*Conditionvariable initialisieren*/
	if(pthread_cond_init(&cond, NULL) != 0){
		printf("Fehler beim initialisieren der Condition-Variable\n");
	}
	
	status_film = pthread_create(&thread_film, NULL, &filmebestellen, NULL);
	if(status_film) printf("Fehler beim Filmthread\n");
	
	status_nahrung = pthread_create(&thread_nahrung, NULL, &getraenkeundspeisen, NULL);
	if(status_nahrung) printf("Fehler beim Getraenke- und Speisenthread\n");
	
	status_uberweiung = pthread_create(&thread_uberweisung, NULL, &ueberweisen, NULL);
	if(status_uberweiung) printf("Fehler beim Uberweisungsthread\n");
	
	status_film = pthread_join(thread_film, NULL);
	if(status_uberweiung) printf("Fehler beim aufsammeln des Filmthreads\n");
	
	status_nahrung = pthread_join(thread_nahrung, NULL);
	if(status_nahrung) printf("Fehler beim aufsammeln des Nahrungsthreads\n");
	
	status_uberweiung = pthread_join(thread_uberweisung, NULL);
	if(status_uberweiung) printf("Fehler beim aufsammeln des Uberweisungsthread\n");
	
	printf("Endkontostand: %d\n",kontostand);
	
	if( pthread_mutex_destroy(&lock) != 0) printf("Konnte Mutex nicht zerstören\n");
	/*Conditionvariable wieder freigeben*/
	if( pthread_cond_destroy(&cond) != 0 ) printf("Konnte Cond-Variable nicht zerstören\n");
	
	return 0;
}

void* filmebestellen(void *pv){
	int localkonto;
	int counter = 0;
	while(counter < 5){
		sleep(1);
		if( pthread_mutex_trylock(&lock)  != 0){
			printf("FILME Mutex konnte nicht gelockt werden\n");
			printf("warte...\n");
			sleep(2);
		}else{
			/*Falls wir durch die aktuelle Transaktion ins Minus kommen, warten wir*/
			if( kontostand - 200 < 0){
			pthread_cond_wait(&cond, &lock);
			}
			/* printf("Gelockter Mutex %d\n",lock); */
			printf("Bestelle Filme\n");
			localkonto = kontostand;
			printf("Kontostand vorher: %d\n",localkonto);
			localkonto -= 200;
			kontostand = localkonto;
			printf("Kontostand nachher: %d\n",localkonto);
			if( pthread_mutex_unlock(&lock) != 0) printf("Mutex konnte nicht geunlockt werden\n");
			counter++;
		}
	}
	
	pthread_exit(NULL);
}

void* getraenkeundspeisen(void *pv){
	int localkonto;
	int counter = 0;
	while(counter < 5){
		sleep(1);
		if( pthread_mutex_trylock(&lock) != 0 ){
			printf("NAHRNUNG Mutex konnte nicht gelockt werden\n");
			printf("warte...\n");
			sleep(3);
		}else{
			/*Falls wir durch die aktuelle Transaktion ins Minus kommen, warten wir*/
			if( kontostand -400 < 0){
			pthread_cond_wait(&cond, &lock);
			}
			/* printf("Gelockter Mutex %d\n",lock); */
			printf("Kaufe Getraenke und Speisen\n");
			localkonto = kontostand;
			printf("Kontostand vorher %d\n",localkonto);
			localkonto -= 400;
			kontostand = localkonto;
			printf("Kontostand nachher: %d\n",localkonto);
			if( pthread_mutex_unlock(&lock) != 0 ){
				printf("Mutex konnte nicht entsperrt werden\n");
			}
			counter++;
		}
	}
	
	pthread_exit(NULL);
}

void* ueberweisen(void *pv){
	int localkonto;
	int counter = 0;
	while(counter < 2){
		sleep(1);
		if( pthread_mutex_trylock(&lock) != 0){
			printf("UEBERWEISEN Mutex konnte nicht gelockt werden\n");
			printf("warte...\n");
			sleep(8);
		}else{
			/* printf("Gelockter Mutex %d\n",lock); */
			printf("Ueberweise Geld\n");
			localkonto = kontostand;
			printf("Kontostand vorher %d\n",localkonto);
			localkonto += 2000;
			kontostand = localkonto;
			printf("Kontostand nachher %d\n",localkonto);
			if( pthread_mutex_unlock(&lock) != 0 ){
				printf("Mutex konnte nicht geunlockt werden\n");
			}
			counter++;
		}
		/*Falls wir nach der Transaktion im Plus sind, senden wir ein Signal an alle wartenden Threads
		  und das Betriebssystem entscheidet Thread dann wirklich weiterarbeitet*/
		if(kontostand > 0){
			pthread_cond_signal(&cond);
		}
	}
	
	pthread_exit(NULL);
}