#!/bin/bash
for folder in randoop"$@"/StmtReport randoop"$@"/BranchReport randoop"$@"/LineReport
do
  output_file="$folder/overview.txt"
  
  if [[ -e "$output_file" ]]; then
    rm "$output_file"
  fi

  for file in $folder/*; do
    last_line=$(tail -n 1 "$file" | tr -cd '[:digit:].' | sed -E 's/([0-9]+\.[0-9]{1})[0-9]*/\1/g')
    echo "$(basename "$file"): $last_line" >> "$output_file"
  done
done