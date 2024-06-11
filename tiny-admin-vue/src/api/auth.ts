import http from "@/utils/http.ts";

// 这里定义返回值类型，使接口拥有良好的类型推导
export type UserResult = {
    code: number,
    data: {
        /** 用户名 */
        username: string;
        /** 当前登陆用户的角色 */
        roles: Array<string>;
        /** `token` */
        accessToken: string;
        /** 用于调用刷新`accessToken`的接口时所需的`token` */
        refreshToken: string;
        /** `accessToken`的过期时间（格式'xxxx/xx/xx xx:xx:xx'） */
        expires: Date;
    };
};

/** 登录接口 */
// export const postLogin = (data?: object) => {
//     return new Promise<userType>((resolve) => {
//         setTimeout(async () => {
//             let res: userType = await http.post("/login", data)
//             resolve(res)
//         }, 1000)
//     })
// };

/** 登录接口 */
export const postLogin = (data?: object): Promise<string> => {
    return http.post("/login", data)
};
