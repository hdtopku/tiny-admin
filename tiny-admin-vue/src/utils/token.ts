const tokenKey = 'token'

export const setToken = async (token: string) => {
    localStorage.setItem(tokenKey, token)
}

export const getToken = () => {
    return localStorage.getItem(tokenKey)
}
