#include <stdio.h>
#include <stdlib.h>

struct Veiculo
{
    int carga;
    char * email;
    int idVeiculo;
    int idParque;
    double capacidadeVeiculo;
    char * data;
}; // Define o nome do novo tipo criado

int size;
struct Veiculo * veiculos;

int estimar(int capacidade,int carga,int potenciaCarregador);
void readFile();
