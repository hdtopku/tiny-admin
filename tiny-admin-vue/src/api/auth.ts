import http from "@/utils/http.ts";
import {UserType} from "@/store/userStore.ts";

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
export const postLogin = (data?: object): Promise<{token: string, userInfo: UserType}> => {
    return http.post("/login", data)
};

export const getSelfInfo = () => {
    return http.get("/getSelfInfo")
}
