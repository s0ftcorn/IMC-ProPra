#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdlib.h>

struct id3tag {
        char title[31];
        char artist[31];
        char album[31];
        char year[5];
        char comment[30];
        unsigned char tracknumber;
        unsigned char genre;
};

/*
 * Funktionsköpfe deklarieren
 */
static void metadaten_einabe(char *, char *);
static void metadaten_ausgabe(char *);
static void id3tag_ausgabe(struct id3tag *);

static char* get_genre(unsigned char);

int main(int argc, char **argv) {
        int i;
        /*
         * Uebergebene Parameter verarbeiten
         */
        if(argc <= 1) {
                printf("Es muss mindestens ein Parameter angegeben werden\n");
                return 1;
        }else if( strcmp(argv[1],"--modify") == 0){
               for(i = 3; i < argc; i++)
                        metadaten_einabe(argv[2],argv[i]);
        }else{
                for(i = 1; i < argc; ++i)
                        metadaten_ausgabe(argv[i]);
        }
        
        return 0;
}

static void metadaten_einabe(char *kommentar, char *dateiname){
        /*
         * Variablen deklarieren, in diesem Fall brauchen wir auch Variablen
         * für ein Auslesen der Datei, da wir erst gucken ob die Datei schon einen id3 v1.1 Bereich
         * hat
         */
        int w_fd, r_fd = 0;
        ssize_t w_bytes, r_bytes = 0;
        /* Wir brauchen einen Buffer in den nur "TAG", mit Nullbyte reinpasst
         */
        char buf[3] = {0};
        int offset = 0;
        int i = 0;
        struct stat fileinfo;
        
        printf("== Datei: %s ==\n\n", dateiname);
        
        int retval = stat(dateiname, &fileinfo);
        if(retval == -1){
                perror("Fehler bei stat\n");
                exit(0);
        }
        
        if( !S_ISREG(fileinfo.st_mode) ){
                printf("Argument ist keine Datei\n");
                exit(0);
        }
        
        r_fd = open(dateiname,O_RDONLY);
        if(r_fd == -1){
                perror("Fehler beim oeffnen der Datei\n");
                exit(0);
        }
        
        retval = lseek(r_fd,-128L,SEEK_END);
        if(retval == -1){
                perror("Fehler beim setzen des Filedeskriptor\n");
                exit(0);
        }
        
        r_bytes = read(r_fd,buf,3);
        if(r_bytes < 3 && r_bytes != -1){
                printf("Evtl. ging etwas schief\n");
        }else if(r_bytes == -1){
                perror("Fehler beim lesen.\n");
                exit(0);
        }
        
        if( close(r_fd) == -1 ){
                perror("Fehler beim schließen der Datei.\n");
                exit(0);
        }
        
        char temp[3] = {0};
        for(i = offset; i < offset+3; i++){
                temp[i] = buf[i];
        }
        temp[3] = '\0';
        
        if(strcmp("TAG",temp) == 0){
                printf("id3-Bereich gefunden\nUeberschreibe\n");
                w_fd = open(dateiname, O_WRONLY);
                
                if(w_fd == -1){
                        perror("Fehler beim öffnen der Datei\n");
                        exit(0);
                }
                
                int retval = lseek(w_fd,-31L,SEEK_END);
                if(retval == -1){
                        perror("Fehler beim setzen des Filedeskriptor\n");
                        exit(0);
                }
                
                w_bytes = write(w_fd, kommentar, 29);
                
                if( close(w_fd) == -1 ){
                        perror("Fehler beim schließen der Datei.\n");
                        exit(0);
                }
                
        }else{
                
                printf("id3-Bereich nicht gefunden\nHaenge an\n");
                w_fd = open(dateiname, O_APPEND);
                
                if(w_fd == -1){
                        perror("Fehler beim öffnen der Datei\n");
                        exit(0);
                }
                
                int retval = lseek(w_fd,0, SEEK_END);
                if(retval == -1){
                        perror("Fehler beim setzen des Filedeskriptors\n");
                        exit(0);
                }
                
                if( close(w_fd) == -1){
                        perror("Fehler beim schließen der Datei\n");
                        exit(0);
                }
        }
}

static void metadaten_ausgabe(char *dateiname) {
        struct id3tag tag;
        int r_fd = 0;
        ssize_t r_bytes = 0;
        char buf[128] = {0};
        int offset = 0;
        int i = 0;
        struct stat fileinfo;
        
        printf("== Datei: %s ==\n\n", dateiname);
        
        int retval = stat(dateiname, &fileinfo);
        if(retval == -1){
                perror("Fehler bei stat\n");
                exit(0);
        }
        
        if( !S_ISREG(fileinfo.st_mode) ){
                printf("Argument ist keine Datei\n");
                exit(0);
        }
        
        r_fd = open(dateiname,O_RDONLY);
        if(r_fd == -1){
                perror("Fehler beim oeffnen der Datei\n");
                exit(0);
        }
        
        retval = lseek(r_fd,-128L,SEEK_END);
        if(retval == -1){
                perror("Fehler beim setzen des Filedeskriptor\n");
                exit(0);
        }
        
        r_bytes = read(r_fd,buf,128);
        if(r_bytes < 128 && r_bytes != -1){
                printf("Evtl. ging etwas schief\n");
        }else if(r_bytes == -1){
                perror("Fehler beim lesen.\n");
                exit(0);
        }
        
        if( close(r_fd) == -1 ){
                perror("Fehler beim schließen der Datei.\n");
                exit(0);
        }
        
        char temp[3] = {0};
        for(i = offset; i < offset+3; i++){
                temp[i] = buf[i];
        }
        temp[3] = '\0';
        
        if(strcmp("TAG",temp) != 0){
                printf("Konnte \"TAG\" nicht erkennen.\n");
                exit(0);
        }
        
        offset = 3;
        for(i = 0; i < 31; i++){
                tag.title[i] = buf[offset+i];
        }
        tag.title[31] = '\0';
        
        offset= 33;
        for(i = 0; i < 31; i++){
                tag.artist[i] = buf[offset+i];
        }
        tag.artist[31] = '\0';
        
        offset = 63;
        for(i = 0; i < 031; i++){
                tag.album[i] = buf[offset+i];
        }
        tag.album[31] = '\0';
        
        offset = 93;
        for(i = 0; i < 4; i++){
                tag.year[i] = buf[offset+i];
        }
        tag.year[4] = '\0';

        offset = 97;
        for(i = 0; i < 30; i++){
                tag.comment[i] = buf[offset+i];
        }
        tag.comment[30] = '\0';
        
        tag.tracknumber = buf[126];
        tag.genre = buf[127];
        
        /* AUSGABE */
        id3tag_ausgabe(&tag);
}

static void id3tag_ausgabe(struct id3tag *tag) {
        printf(
                "Titel:     %s\n"
                "Künstler:  %s\n"
                "Album:     %s\n"
                "Jahr:      %s\n"
                "Kommentar: %s\n"
                "Nummer:    %d\n"
                "Genre:     %s\n\n",
                tag->title,
                tag->artist,
                tag->album,
                tag->year,
                tag->comment,
                tag->tracknumber,
                get_genre(tag->genre)
        );
}

static char *genres[80];

/* SUCHT DAS GENRE AUS FOLGENDER TABELLE HERAUS, PARAMETER IST DAS GENRE-BYTE */
static char *get_genre(unsigned char index) {
        if(index >= sizeof(genres) / sizeof(genres[0]))
                return "undefined";
        else
                return genres[index];
}

static char *genres[80] = {
        "Blues",
        "Classic Rock",
        "Country",
        "Dance",
        "Disco",
        "Funk",
        "Grunge",
        "Hip-Hop",
        "Jazz",
        "Metal",
        "New Age",
        "Oldies",
        "Other",
        "Pop",
        "Rhythm and Blues",
        "Rap",
        "Reggae",
        "Rock",
        "Techno",
        "Industrial",
        "Alternative",
        "Ska",
        "Death Metal",
        "Pranks",
        "Soundtrack",
        "Euro-Techno",
        "Ambient",
        "Trip-Hop",
        "Vocal",
        "Jazz&Funk",
        "Fusion",
        "Trance",
        "Classical",
        "Instrumental",
        "Acid",
        "House",
        "Game",
        "Sound Clip",
        "Gospel",
        "Noise",
        "Alternative Rock",
        "Bass",
        "Soul",
        "Punk",
        "Space",
        "Meditative",
        "Instrumental Pop",
        "Instrumental Rock",
        "Ethnic",
        "Gothic",
        "Darkwave",
        "Techno-Industrial",
        "Electronic",
        "Pop-Folk",
        "Eurodance",
        "Dream",
        "Southern Rock",
        "Comedy",
        "Cult",
        "Gangsta",
        "Top 40",
        "Christian Rap",
        "Pop/Funk",
        "Jungle",
        "Native US",
        "Cabaret",
        "New Wave",
        "Psychedelic",
        "Rave",
        "Showtunes",
        "Trailer",
        "Lo-Fi",
        "Tribal",
        "Acid Punk",
        "Acid Jazz",
        "Polka",
        "Retro",
        "Musical",
        "Rock & Roll",
        "Hard Rock"
};