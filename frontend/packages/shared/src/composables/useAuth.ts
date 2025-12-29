import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import type { UserInfo, LoginRequest, RegisterRequest } from '../types'
import { authApi } from '../api'
import { tokenStorage } from '../utils/storage'

export const useAuthStore = defineStore('auth', () => {
  const user = ref<UserInfo | null>(tokenStorage.getUser<UserInfo>())
  const accessToken = ref<string | null>(tokenStorage.getAccessToken())

  const isAuthenticated = computed(() => !!accessToken.value)
  const isAdmin = computed(() => user.value?.role === 'ADMIN')

  async function login(data: LoginRequest) {
    const res = await authApi.login(data)
    const { accessToken: token, refreshToken, user: userInfo } = res.data
    accessToken.value = token
    user.value = userInfo
    tokenStorage.setAccessToken(token)
    tokenStorage.setRefreshToken(refreshToken)
    tokenStorage.setUser(userInfo)
    return res.data
  }

  async function register(data: RegisterRequest) {
    const res = await authApi.register(data)
    const { accessToken: token, refreshToken, user: userInfo } = res.data
    accessToken.value = token
    user.value = userInfo
    tokenStorage.setAccessToken(token)
    tokenStorage.setRefreshToken(refreshToken)
    tokenStorage.setUser(userInfo)
    return res.data
  }

  function logout() {
    accessToken.value = null
    user.value = null
    tokenStorage.clear()
  }

  function initAuth() {
    const token = tokenStorage.getAccessToken()
    const savedUser = tokenStorage.getUser<UserInfo>()
    if (token && savedUser) {
      accessToken.value = token
      user.value = savedUser
    }
  }

  return {
    user,
    accessToken,
    isAuthenticated,
    isAdmin,
    login,
    register,
    logout,
    initAuth,
  }
})
