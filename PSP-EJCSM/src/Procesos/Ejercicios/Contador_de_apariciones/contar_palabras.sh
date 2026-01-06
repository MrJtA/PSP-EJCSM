#!/bin/bash

# Verificar si se proporcionó al menos la palabra
if [ "$#" -lt 1 ] || [ "$#" -gt 2 ]; then
    echo "Uso: $0 palabra [archivo]"
    exit 1
fi

# Asignar el primer argumento a la variable palabra
palabra=$1

# Si hay un segundo argumento, se asume que es un archivo
if [ "$#" -eq 2 ]; then
    archivo=$2
    
    # Verificar si el archivo existe
    if [ ! -f "$archivo" ]; then
        echo "Error: El archivo '$archivo' no existe."
        exit 1
    fi

    # Contar las apariciones de la palabra en el archivo (insensible a mayúsculas/minúsculas)
    conteo=$(grep -o -i "\b$palabra\b" "$archivo" | wc -l)

else
    # Si no se proporciona archivo, se lee de la entrada estándar
    if [ -t 0 ]; then
        echo "Error: No se proporcionó ni un archivo ni datos por redirección de entrada."
        echo "Uso: $0 palabra [archivo] o cat archivo.txt | $0 palabra"
        exit 1
    fi

    # Contar las apariciones de la palabra en los datos de la entrada estándar
    conteo=$(grep -o -i "\b$palabra\b" /dev/stdin | wc -l)
fi

# Mostrar el resultado
echo $conteo

