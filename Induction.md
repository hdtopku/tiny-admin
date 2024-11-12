## Induction
This service will be deployed on Centos 7.6. This article assumes that you have already purchased a server and installed the Centos operating system and also assumes your server's IP address is 0.0.0.0 (you should replace 0.0.0.0 with your server's IP address in real-world scenarios).

### Step 1: Connect to your server
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

### Step 2: Install Some Required Packages


