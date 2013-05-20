#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <signal.h>

void error(char *str);

void dieGabel(char *c_ptr);

void sc_handler(int signum){
	int pid_res;
	/* Solange es Zombies gibt, werden diese aufgesammelt */
	while((pid_res=waitpid(-1,NULL,WNOHANG)) > 0){
		/*Wenn waitpid eine -1 zurückgibt, ging irgendetwas schief*/
		printf("PID des Zombies: %d\n",pid_res);
		if(pid_res == -1){
		error("Fehler beim sammeln der Zombies");
		}
	printf("SIGCHLD gefangen: %d!\n", signum);
	}
}

int main(int argc, char **argv){
	char auswahl;
	
	// Setzen der Variablen in unserer sigaction-Struct
	struct sigaction action;
	action.sa_handler = &sc_handler; // Pointer auf unseren Signal-Handler
	action.sa_flags = SA_RESTART; // Der Flag ist notwending, damit scanf() nicht unterbrochen wird
	sigemptyset(&action.sa_mask); // Keine Signale ignorieren
	
	// Falls sigaction einen Wert ungleich null hat, ging etwas schief
	if(sigaction(SIGCHLD,&action,NULL) != 0){
		error("Signalfehler im Kindprozess");
	}
	
	while(1){
	/* PID des Elternprozesses */
	printf("Main-PID: %d\n",getpid());
	
	/* Ausgeben des "Menüs" */
	printf("Bitte waehlen sie\nd fuer dolphin\np für ps\nx für xterm\n");
	
	/* Falls scanf einen Wert kleiner als Eins zurückgibt, dann ist etwas schief gegangen
	   weil wir einen Char erwarten. Kriegen wir einen, ist der Rückgabewert von scanf 1,
	   ging etwas schief, gibt scanf einen Wert kleiner als Null zurück, d.h. auch hier
	   greift die Fehlerbehandlung */
	
	if( scanf(" %c",&auswahl) < 1 ) error("Eingabe fehlgeschlagen!");
	/* Das "Menü" verarbeiten */
	if(auswahl == 'd') dieGabel("dolphin");
	else if(auswahl == 'p') dieGabel("ps");
	else if(auswahl == 'x') dieGabel("xterm");
	else printf("Falsche Eingabe");
	
	}
	
	exit(EXIT_SUCCESS);
}

void dieGabel(char *c_ptr){
	int pid = fork();
	printf("Die Gabel sagt: %d\n",pid);
	// Ist die pid größer als 0, sind wir im Elternprozesss 
	if(pid > 0){
		printf("Eltern-Prozess, PID des Kinds: %d\n",pid);
	// Wenn die pid gleich 0 ist, dann sind wir im Kindprozess und wir führen das gewünschte Programm aus
	}else if(pid == 0){
		printf("Im Kind-Prozess, PID %d, PPID %d\n",getpid(),getppid());
		execlp(c_ptr,c_ptr,NULL);
		exit(EXIT_FAILURE);
	// Falls fork() einen Wert kleiner 0 zurückgibt, ist etwas schiefgegangen
	}else if(pid < 0){
		error("Die Gabel scheiterte!");
	// Hierhin sollten wir eigentlich nicht kommen
	}else{
		printf("Universe check failed.");
	}
}

void error(char *str){
	// perror kriegt als Meldung einen String von der aufrufenden Funktion
	perror(str);
	exit(EXIT_FAILURE);
}