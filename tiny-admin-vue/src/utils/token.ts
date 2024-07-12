const tokenKey = 'token'

export const setToken = (token: string) => {
    localStorage.setItem(tokenKey, token)
}

export const getToken = () => {
    return localStorage.getItem(tokenKey)
}

export const removeToken = () => {
    localStorage.removeItem(tokenKey)
}
