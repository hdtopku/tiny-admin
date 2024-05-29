import {MockMethod} from "vite-plugin-mock";
import {faker} from '@faker-js/faker'

export default [
    {
        url: "/mock/login",
        method: "post",
        response: () => {
            return {
                code: 200,
                data: {
                    token: faker.string.uuid(),
                }
            }
        }
    },
    {
        url: "/mock/getSelfInfo",
        method: "get",
        response: () => {
            return {
                code: 200,
                data: {

                    avatar: faker.image.url(),
                    username: faker.word.noun(),
                    nickname: faker.person.fullName(),
                    // 一个用户可能有多个角色
                    roles: ["admin", "developer", "user"],
                    accessToken: faker.string.uuid(),
                    refreshToken: faker.string.uuid(),
                    expires: "2030/10/30 00:00:00"
                }
            }
        }
    }
] as MockMethod[];
