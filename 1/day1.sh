#!/bin/bash

cat input.txt | gawk 'BEGIN { i=0 } { if ($1=="") i++; else t[i] += $1 } END { n = asort(t, s); print s[n] }'
cat input.txt | gawk 'BEGIN { i=0 } { if ($1=="") i++; else t[i] += $1 } END { n = asort(t, s); print s[n]+s[n-1]+s[n-2] }'

