#!/bin/bash

# Comprobamos si translate-shell está instalado
if ! command -v trans &> /dev/null
then
    echo "translate-shell no está instalado. Por favor, instálalo e inténtalo de nuevo."
    exit 1
fi

# Comprobamos si se ha proporcionado un argumento para el idioma
if [ $# -eq 0 ]; then
    echo "Uso: $0 -en|-it|-fr"
    exit 1
fi

# Determinamos el idioma según el argumento
case $1 in
    -en) lang="en" ;;  # Inglés
    -it) lang="it" ;;  # Italiano
    -fr) lang="fr" ;;  # Francés
    *)
        echo "Idioma no válido. Usa -en para inglés, -it para italiano, o -fr para francés."
        exit 1
        ;;
esac

# Verificamos si el texto se ha pasado a través de un pipe o como entrada manual
if [ -t 0 ]; then
    # Si no hay pipe, solicitamos el texto manualmente
    read -p "Introduce el texto en español para traducir: " text_to_translate
else
    # Si hay pipe, leemos el texto desde la entrada estándar
    text_to_translate=$(cat)
fi

# Realizar la traducción y extraer solo el texto traducido
translated_text=$(trans -brief :$lang "$text_to_translate")

# Mostrar el texto traducido
echo "$translated_text"

