## ☘️ Abstract
Tiny Admin is a full-stack web application
- 🍁 Front-end framework: [Vue3.5](https://cn.vuejs.org/), [TailwindCSS](https://tailwindcss.com/), [Pinia](https://pinia.vuejs.org/)
- 🍂 Back-end framework: [SpringBoot3.2.5](https://spring.io/), [Mysql](https://www.mysql.com/), [Mybatis](https://mybatis.org/mybatis-3/)
- - 🍃 Username: **admin**，Password: **123456**
- [🪹 github repository](https://github.com/hdtopku/tiny-admin)

## System Design
1. User Management: User login, logout, user information modification, user role management, user permission management
2. Role Management: Role creation, role modification, role deletion, role permission management
3. Permission Management: Permission creation, permission modification, permission deletion
4. Menu Management: Menu creation, menu modification, menu deletion, menu permission management

## Environment and Deployment
1. [pnpm](https://github.com/pnpm/pnpm/) (>= 8.7.0, recommended. It is used as the package manager)
2. [Node.js](https://nodejs.org/) (Node.js version >= 18.12.0，npm version >= 6.14.13)
3. [MySQL](https://www.mysql.com/) (MySQL version >= 8.0.27)
4. [Git](https://git-scm.com/) (Git version >= 2.34.1)
5. [Nginx](https://nginx.org/) (Nginx version >= 1.21.6)
6. [OpenJDK](https://openjdk.java.net/) (OpenJDK version >= 17.0.2)
7. [Maven](https://maven.apache.org/) (Maven version >= 3.8.4)
8. [Docker](https://www.docker.com/) (Docker version >= 20.10.12)

## Quick Start

> More detailed deployment instructions: [AWS Deployment in Docker](https://i7u0jn0r7j0.jp.larksuite.com/wiki/FT6owCCYRiTFj0kRQBKjWJtypac)

1. Clone the repository to local
```
git clone https://github.com/hdtopku/tiny-admin.git
```

2. Deploy mysql, redis and nginx services on docker containers.
```
sh ./tiny-boot/env/deploy_environment.sh
```

3. Deploy the backend Springboot service on docker containers.
```
sh ./tiny-boot/deploy.sh
```

4. Compile the frontend width nodejs and pnpm in docker containers.
```
sh ./tiny-boot/build.sh
```


