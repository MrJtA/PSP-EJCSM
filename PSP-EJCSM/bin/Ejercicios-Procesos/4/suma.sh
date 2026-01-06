#!/bin/bash

# Comprobar que se han pasado argumentos
if [ "$#" -eq 0 ]; then
    exit 1
fi

# Inicializar suma
suma=0

# Iterar sobre los argumentos y sumar
for numero in "$@"; do
    suma=$((suma + numero))
done

# Imprimir la suma
echo $suma
