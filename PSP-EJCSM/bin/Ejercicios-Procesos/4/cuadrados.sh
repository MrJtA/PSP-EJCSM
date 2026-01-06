#!/bin/bash

# Comprobar que se han pasado argumentos
if [ "$#" -eq 0 ]; then
    exit 1
fi

# Iterar sobre los argumentos y elevar al cuadrado
for numero in "$@"; do
    echo $((numero * numero))
done
