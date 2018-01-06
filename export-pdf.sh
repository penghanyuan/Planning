#!/bin/bash

asciidoctor-pdf -a pdf-style=default-theme.yml manuel.adoc

echo "Et voila, le manuel est généré en pdf !"
