## Induction
This service will be deployed on Centos 7.6. This article assumes that you have already purchased a server and installed the Centos operating system and also assumes your server's IP address is 0.0.0.0 (you should replace 0.0.0.0 with your server's IP address in real-world scenarios).

## Step 1: Connect to your server
```bash
> ssh root@0.0.0.0
root@0.0.0.0's password: 
Last login: Mon Aug 10 10:55:29 2021 from 192.168.127.12
```
Attention: First time connecting to a server, If you received a warning to forbid your connection like below

Your should add the server's IP address to the known hosts list by running the following command:
```bash
> ssh-keygen 0.0.0.0
```

## Step 2: Install Some Required Packages
Because our backend service will be running on docker (openjdk:22-ea-16-jdk-slim, maven:latest) and our frontend will be compiled by nodejs and pnpm in docker, we need to install some required packages first.
And exempt from openjdk:22-ea-16-jdk-slim, maven:latest, nodejs and pnpm, we also need to install some other packages for development and deployment.

### github
```bash
> yum install -y git
> git config --global color.ui true
> git config --global user.name "your_username"
> git config --global user.email "your_email"
```

### github client
```bash
> sudo yum-config-manager --add-repo https://cli.github.com/packages/rpm/gh-cli.repo
> yum install -y gh
```


### zsh
```bash
> yum install -y zsh
> chsh -s /bin/zsh
> sh -c "$(curl -fsSL https://raw.github.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
```

### vim
```bash
> yum install -y vim

> vim ~/.zshrc # add the following lines to the end of the file
alias c="cd /root"
alias dp="docker ps -a"
alias di="docker images -a"
alias g="git pull --rebase origin main"

> source ~/.zshrc
```

### docker and docker-compose
```bash
> yum install -y yum-utils device-mapper-persistent-data lvm2
> yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
> yum install -y docker-ce
> systemctl start docker
> systemctl enable docker

> curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
> chmod +x /usr/local/bin/docker-compose
```

## Step 3: Deployment
We will deploy our backend service and frontend on docker containers.
### Use GitHub Client to Clone the repository
```bash
> gh auth login

? Where do you use GitHub? GitHub.com
? What is your preferred protocol for Git operations on this host? SSH
? Upload your SSH public key to your GitHub account? /root/.ssh/id_ed25519.pub
? Title for your SSH key: GitHub CLI
? How would you like to authenticate GitHub CLI? Login with a web browser

! First copy your one-time code: 916C-82CF
Press Enter to open https://github.com/login/device in your browser...
! Failed opening a web browser at https://github.com/login/device
  exec: "xdg-open,x-www-browser,www-browser,wslview": executable file not found in $PATH
  Please try entering the URL in your browser manually
✓ Authentication complete.
- gh config set -h github.com git_protocol ssh
✓ Configured git protocol
! Authentication credentials saved in plain text
✓ Uploaded the SSH key to your GitHub account: /root/.ssh/id_ed25519.pub
✓ Logged in as hdtopku
```

### GitHub Client or Use SSH to Clone the repository
```bash
> gh repo clone hdtopku/tiny-admin
> git clone git@github.com:hdtopku/tiny-admin.git
```

## Step 3: Set your MySQL and Redis password
Because we have already installed docker and docker-compose, we can deploy our backend service and frontend on docker containers.
First, you need to set your own password for the MySQL database and redis cache.
```bash
> cd /root/tiny-admin
> cp ./tiny-boot/env/.env.example ./tiny-boot/env/.env
> cp ./tiny-boot/tiny-admin/tiny-admin-starter/src/main/resources/.env.example ./tiny-boot/tiny-admin/tiny-admin-starter/src/main/resources/.env.prod
> cp ./tiny-boot/tiny-admin/tiny-admin-starter/src/main/resources/.env.example ./tiny-boot/tiny-admin/tiny-admin-starter/src/main/resources/.env.dev
> cp ./tiny-boot/tiny-admin/tiny-admin-starter/src/main/resources/firebase-example.json ./tiny-boot/tiny-admin/tiny-admin-starter/src/main/resources/firebase.json
```

Change your mysql and redis password in the.env, .env.prod and.env.dev file.
```bash
> vim ./tiny-boot/env/.env
> vim ./tiny-boot/tiny-admin/tiny-admin-starter/src/main/resources/.env.prod
> vim ./tiny-boot/tiny-admin/tiny-admin-starter/src/main/resources/.env.dev
```

## Step 4: Deploy the backend and the frontend services on docker containers
Deploy mysql, redis and nginx services on docker containers.
```bash
> sh ./tiny-boot/env/deploy_environment.sh
```

Deploy the backend Springboot service on docker containers.
```bash
> sh ./tiny-boot/deploy.sh
```

Compile the frontend width nodejs and pnpm in docker containers.
```bash
> sh ./tiny-boot/build.sh
```

