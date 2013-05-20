#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>

void error(char *str);

void dieGabel(char *c_ptr);

void killzombies(void);

int main(int argc, char **argv){
	char auswahl;
	while(1){
	/* PID des Elternprozesses */
	printf("Main-PID: %d\n",getpid());
	/* Ausgeben des "Menüs" */
	printf("Bitte waehlen sie\nd fuer dolphin\np für ps\nx für xterm\n");
	/* Falls scanf einen Wert kleiner als Eins zurückgibt, dann ist etwas schief gegangen
	   weil wir einen Char erwarten. Kriegen wir einen, ist der Rückgabewert von scanf 1,
	   ging etwas schief, gibt scanf einen Wert kleiner als Null zurück, d.h. auch hier
	   greift die Fehlerbehandlgun */
	if( scanf(" %c",&auswahl) < 1 ) error("Eingabe fehlgeschlagen!");
	/* Das "Menü" verarbeiten */
	if(auswahl == 'd') dieGabel("dolphin");
	else if(auswahl == 'p') dieGabel("ps");
	else if(auswahl == 'x') dieGabel("xterm");
	else printf("Falsche Eingabe");
	/* Nach Zombies suchen und diese abschießen */
	killzombies();
	}
	/* Nach der while-Schleife nochmal nach Zombies suchen, falls irgendwas, warum auch immer,
	   schief gegangen ist.*/
	killzombies();
	exit(EXIT_SUCCESS);
}

void dieGabel(char *c_ptr){
	
	int pid = fork();
	printf("Die Gabel sagt: %d\n",pid);
	if(pid > 0){
		printf("Eltern-Prozess, PID des Kinds: %d\n",pid);
	}else if(pid == 0){
		printf("Im Kind-Prozess, PID %d, PPID %d\n",getpid(),getppid());
		execlp(c_ptr,c_ptr,NULL);
		// Eigentlich sollten wir hier nicht hinkommen, da exec den Prozess ersetzt
		exit(EXIT_SUCCESS);
	}else if(pid < 0){
		error("Die Gabel scheiterte!");
	}else{
		printf("Universe check failed.");
	}
}

void error(char *str){
	
	perror(str);
	exit(EXIT_FAILURE);
}

/* Methode für das Aufsammeln von Zombieprozessen*/
void killzombies(void){
	int pid_res;
	/* Solange es Zombies gibt, werden diese aufgesammelt */
	while((pid_res=waitpid(-1,NULL,WNOHANG)) > 0){
		/*Wenn waitpid eine -1 zurückgibt, ging irgendetwas schief*/
		printf("PID des Zombies: %d\n",pid_res);
		if(pid_res == -1){
			error("Fehler beim sammeln der Zombies");
		}
	}
}