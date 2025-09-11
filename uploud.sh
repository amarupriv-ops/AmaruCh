#!/bin/bash
# Script upload project AmaruCh ke GitHub
# Directory: /root/aplikasi/

REPO_URL="https://github.com/amarupriv-ops/AmaruCh.git"
PROJECT_DIR="/root/aplikasi"

cd $PROJECT_DIR || exit 1

# Inisialisasi repo git
git init
git remote add origin $REPO_URL

# Tambahkan semua file
git add .

# Commit
git commit -m "Initial commit: Upload AmaruCh project"

# Push ke GitHub
git branch -M main
git push -u origin main
