#!/bin/bash

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${GREEN}=== Workflow Checker ===${NC}\n"

if [ ! -f ".github/workflows/build.yml" ]; then
    echo -e "${RED}Workflow file not found!${NC}"
    exit 1
fi

echo -e "${YELLOW}Checking syntax...${NC}"

if python3 -c "import yaml; yaml.safe_load(open('.github/workflows/build.yml'))" 2>/dev/null; then
    echo -e "${GREEN}✓ Syntax is valid!${NC}"
else
    echo -e "${RED}✗ Syntax error found!${NC}"
    nano .github/workflows/build.yml
    exit 1
fi

echo -e "\n${GREEN}Quick actions:${NC}"
echo "1) View file"
echo "2) Edit file"
echo "3) Commit & push"
echo "4) Exit"
read -p "Choose [1-4]: " choice

case $choice in
    1) cat .github/workflows/build.yml ;;
    2) nano .github/workflows/build.yml ;;
    3)
        git add .github/workflows/build.yml
        git commit -m "Update workflow"
        git push
        echo -e "${GREEN}Pushed!${NC}"
        ;;
    4) exit 0 ;;
esac
