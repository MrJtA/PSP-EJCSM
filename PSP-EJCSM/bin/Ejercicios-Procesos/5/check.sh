#!/bin/bash

# Comprobamos si hunspell está instalado
if ! command -v hunspell &> /dev/null
then
    echo "hunspell no está instalado. Por favor, instálalo e inténtalo de nuevo."
    exit 1
fi

# Verificamos si el texto se ha pasado a través de un pipe o como entrada manual
if [ -t 0 ]; then
    # Si no hay pipe, solicitamos el texto manualmente
    read -p "Introduce el texto en español para corregir: " text_to_check
else
    # Si hay pipe, leemos el texto desde la entrada estándar
    text_to_check=$(cat)
fi

# Guardamos el texto en un archivo temporal
temp_file=$(mktemp)
echo "$text_to_check" > "$temp_file"

# Usamos hunspell para corregir el texto, buscamos palabras incorrectas
incorrect_words=$(hunspell -d es_ES -l "$temp_file")

# Para cada palabra incorrecta, buscamos la mejor sugerencia y reemplazamos
for word in $incorrect_words; do
    # Obtenemos la primera sugerencia para la palabra incorrecta
    suggestion=$(echo "$word" | hunspell -d es_ES | sed -n 's/^&.*: \([^,]*\).*/\1/p' | head -n 1)
    
    # Si hay una sugerencia, reemplazamos la palabra incorrecta con la sugerencia
    if [ ! -z "$suggestion" ]; then
        text_to_check=$(echo "$text_to_check" | sed "s/\b$word\b/$suggestion/g")
    fi
done

# Mostramos el texto corregido
echo "$text_to_check"

# Limpiamos el archivo temporal
rm "$temp_file"

