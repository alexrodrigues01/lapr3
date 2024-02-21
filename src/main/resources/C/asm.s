.section .data

.section .text

.global estimar 
estimar:
     # Prologue
    pushl %ebp
	movl %esp, %ebp

    pushl %ebx
    pushl %edi
    pushl %esi
    

    movl 12(%ebp),%edi      # carga
    movl 16(%ebp),%esi      # potencia do carregador

    movl $100,%ebx
    subl %edi,%ebx          # 100-carga

    movl %ebx,%eax          # mover (100-carga) para o registo %eax


    movl 8(%ebp),%edx       # capacidade da scooter
    imull %edx,%eax         #  multiplicar pela capacidade da scooter ( (100-carga)*capacidadeScooter )

    cdq

    idivl %esi              # dividir pelo carregador                 (100-carga)*capacidadeScooter/potenciaCarregador )


    imull $60,%eax          # determinar horas em minutos
    cdq
    movl $100,%ecx
    idivl %ecx              # dividir por 100                         ( (100-carga)*capacidadeScooter*60/potenciaCarregador )/100


    popl %esi
    popl %edi
    popl %ebx

    # Epilogue
	movl %ebp, %esp
	popl %ebp
    ret