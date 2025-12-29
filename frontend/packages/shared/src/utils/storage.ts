const TOKEN_KEY = 'blog_access_token'
const REFRESH_KEY = 'blog_refresh_token'
const USER_KEY = 'blog_user'

export const tokenStorage = {
  getAccessToken: (): string | null => localStorage.getItem(TOKEN_KEY),

  setAccessToken: (token: string): void => localStorage.setItem(TOKEN_KEY, token),

  getRefreshToken: (): string | null => localStorage.getItem(REFRESH_KEY),

  setRefreshToken: (token: string): void => localStorage.setItem(REFRESH_KEY, token),

  getUser: <T>(): T | null => {
    const user = localStorage.getItem(USER_KEY)
    return user ? JSON.parse(user) : null
  },

  setUser: <T>(user: T): void => localStorage.setItem(USER_KEY, JSON.stringify(user)),

  clear: (): void => {
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(REFRESH_KEY)
    localStorage.removeItem(USER_KEY)
  },
}
