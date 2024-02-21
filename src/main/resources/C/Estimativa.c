#include <sys/stat.h>

#include "asm.h"

#include <stdbool.h>

#include <unistd.h>

#include <string.h>

#include <math.h>

#include <errno.h>


// Este metodo escreve o ficheiro estimate e o ficheiro flag dado algumas informações do veiculos e a estimativa

 void writerFile ( char * fileName, int idVeiculo, int qtdBateria, double capacidadeScooter, char * email, int potenciaCarregador, int idParque) {

  char * name = (char * ) malloc(100);
  name = strcpy(name, fileName);
  strcat(fileName, ".txt");

  // creating file pointer to work with files
  FILE * fptr;

  // opening file in writing mode
  fptr = fopen(fileName, "w+");
  if (fptr == NULL) {
    perror(fileName);
  } else {
    printf("Passei Por Aqui: Potencia%d\n", potenciaCarregador);
    fprintf(fptr, "%d;%d;%s\n", idVeiculo, estimar(round(capacidadeScooter), qtdBateria, round(potenciaCarregador)), email);

    fclose(fptr);

    // criar o ficheiro flag
    strcat(name, ".flag.txt");
    fptr = fopen(name, "w");
    fprintf(fptr, "done");
    fclose(fptr);
  }
   free(name);

}

   //Este metodo cria o nome do ficheiro estimate para cada veiculo do parque idParque
   //e invoca a função para escrever o ficheiro
void criarEstimativa(int idParque, int potenciaCarregador) {
  int i = 0, nrVeiculos=0;
  printf("size:%d\n", size);
  while (i != size) {
    if (idParque == veiculos[i].idParque) {
      printf("veiculos[%d]=%d\n", i, veiculos[i].idVeiculo);
      nrVeiculos++;
    }
    i++;
  }
  if (nrVeiculos != 0) {
    potenciaCarregador = potenciaCarregador / nrVeiculos;
    i = 0;
    printf("%d\n", nrVeiculos);
    while (i != size) {
      if (idParque == veiculos[i].idParque) {
        char * fileName = malloc(100);
        strcpy(fileName,"");
//        printf("Antes FileName:%s\n",fileName);
        strcat(fileName, "../VeiculoInfo/estimate_");
//         printf("Depois FileName:%s\n",fileName);
        strcat(fileName, veiculos[i].data);
        strcat(fileName, ".data");

        writerFile(fileName, veiculos[i].idVeiculo, veiculos[i].carga, veiculos[i].capacidadeVeiculo, veiculos[i].email, potenciaCarregador, idParque);
        free(fileName);
      }

      i++;
    }
  }
}

void removeFile() {
  remove("info.txt");
}



   //Adicionar o veiculo ao apontador de Veiculo
void adicionarVeiculo(int idVeiculo, char * email, int carga, int idParque, double capacidadeVeiculo, char * data) {
  veiculos = realloc(veiculos, sizeof(struct Veiculo) * (size + 1));

  veiculos[size].carga = carga;
  veiculos[size].email = malloc(strlen(email) + 1);
  strcpy(veiculos[size].email, email);
  veiculos[size].idVeiculo = idVeiculo;
  veiculos[size].idParque = idParque;
  veiculos[size].capacidadeVeiculo = capacidadeVeiculo;
  veiculos[size].data = malloc(strlen(data) + 1);
  strcpy(veiculos[size].data, data);

//   printf("Veiculo adicionado-----------\n");
//   printf("Id:%d\n",veiculos->idVeiculo);
//   printf("Carga:%d\n",veiculos->carga);
//    printf("Id:%d\n",veiculos[size].idVeiculo);
//      printf("Carga:%d\n",veiculos[size].carga);
//   printf("Fim Veiculo adicionado-----------\n");
  size++;
}



  //Este metodo tem a função de ler o ficheiro info.txt, adiconar o veiculo ao array de veiculos e
  //iniciar as estimativas para todos os veiculos do parque do veiculo adicionado

void fileReader() {

  char * line = (char * ) calloc(1, 100);

  FILE * fptr;

  if ((fptr = fopen("info.txt", "r")) == NULL) {

    printf("Error! opening file");

  } else {

    fscanf(fptr, "%s", line);
    fclose(fptr);

    removeFile();

    int cont = 0, idVeiculo, carga, potenciaCarregador, idParque;

    double capacidadeVeiculo;

    char * token = strtok(line, ";");

    char * data = (char * ) malloc(100);

    char * email = (char * ) malloc(100);

    while (token != NULL) {

      if (cont == 0) {
        idVeiculo = atoi(token);
      }

      if (cont == 1) {
        carga = atoi(token);
      }

      if (cont == 2) {
        sscanf(token, "%lf", & capacidadeVeiculo);
      }

      if (cont == 3) {
        strcpy(data, token);
      }

      if (cont == 4) {
        strcpy(email, token);
      }
      if (cont == 5) {
        potenciaCarregador = atoi(token);
      }

      if (cont == 6) {
        idParque = atoi(token);
      }

      token = strtok(NULL, ";");
      fprintf(fptr, "%s;", token);

      cont++;

    }
    // adicionar veiculo ao array de veiculos
    adicionarVeiculo(idVeiculo, email, carga, idParque, capacidadeVeiculo, data);
    // escrever o ficheiro lock com a estimativa
    criarEstimativa(idParque, potenciaCarregador);
    free(line);
    free(data);
    free(email);
  }

}

 // Check if a file exist using stat() function
 // return 1 if the file exist otherwise return 0

int cfileexists(const char * filename) {
  struct stat buffer;
  int exist = stat(filename, & buffer);
  if (exist == 0)
    return 1;
  else // -1
    return 0;
}

void readFile() {
  while (true) {
    int flagg = cfileexists("info.txt");
    if (flagg == 1) {
      sleep(1);
      fileReader();
    }
  }
  free(veiculos);
}