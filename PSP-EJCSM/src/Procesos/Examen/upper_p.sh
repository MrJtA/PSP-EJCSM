#!/bin/bash

# Caso 1: Se pasa 1 argumento
if [ "$#" -eq 1 ]; then
    entrada=$1

    # Si el argumento es un archivo, lo procesamos como archivo
    if [ -f "$entrada" ]; then
        tr '[:lower:]' '[:upper:]' < "$entrada"
        exit 0
    fi

    # Si NO es un archivo, entonces es una palabra → convertirla
    echo "$entrada" | tr '[:lower:]' '[:upper:]'
    exit 0
fi


# Caso 2: No se pasan argumentos → usar stdin
if [ -t 0 ]; then
    echo "Uso: $0 [palabra] [archivo] o cat archivo.txt | $0"
    exit 1
fi

# Convertir la entrada estándar
tr '[:lower:]' '[:upper:]' < /dev/stdin
