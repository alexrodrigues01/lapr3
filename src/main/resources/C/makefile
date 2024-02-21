 # A simple Makefile
prog: asm.o main.o Estimativa.o
	gcc asm.o Estimativa.o main.o -o prog -lm
	
asm.o: asm.s
	gcc -g -Wall -c asm.s -o asm.o 

Estimativa.o: Estimativa.c
	gcc -g -Wall -c Estimativa.c -o Estimativa.o -lm

main.o: main.c
	gcc -g -Wall -c main.c -o main.o -lm
	
clean:
	rm -f *.o prog
	
run: prog
	./prog

