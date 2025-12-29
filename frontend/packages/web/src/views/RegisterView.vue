<script setup lang="ts">
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { useAuthStore } from '@/stores'

  const router = useRouter()
  const authStore = useAuthStore()

  const username = ref('')
  const email = ref('')
  const password = ref('')
  const nickname = ref('')
  const loading = ref(false)
  const error = ref('')

  async function handleRegister() {
    if (!username.value || !email.value || !password.value) {
      error.value = '请填写必填字段'
      return
    }

    loading.value = true
    error.value = ''

    try {
      await authStore.register({
        username: username.value,
        email: email.value,
        password: password.value,
        nickname: nickname.value || undefined,
      })
      router.push('/')
    } catch (err: unknown) {
      error.value = err instanceof Error ? err.message : '注册失败'
    } finally {
      loading.value = false
    }
  }
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-900 py-12 px-4">
    <div class="max-w-md w-full">
      <h1 class="text-3xl font-bold text-center text-gray-900 dark:text-white mb-8">注册</h1>

      <form @submit.prevent="handleRegister" class="space-y-6">
        <div v-if="error" class="p-3 bg-red-50 dark:bg-red-900/20 text-red-600 dark:text-red-400 rounded-lg text-sm">
          {{ error }}
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            用户名 <span class="text-red-500">*</span>
          </label>
          <input
            v-model="username"
            type="text"
            required
            class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-800 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            邮箱 <span class="text-red-500">*</span>
          </label>
          <input
            v-model="email"
            type="email"
            required
            class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-800 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            密码 <span class="text-red-500">*</span>
          </label>
          <input
            v-model="password"
            type="password"
            required
            minlength="6"
            class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-800 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-700 dark:text-gray-300 mb-1">
            昵称
          </label>
          <input
            v-model="nickname"
            type="text"
            class="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-800 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
        </div>

        <button
          type="submit"
          :disabled="loading"
          class="w-full py-2 px-4 bg-blue-600 hover:bg-blue-700 text-white font-medium rounded-lg disabled:opacity-50"
        >
          {{ loading ? '注册中...' : '注册' }}
        </button>
      </form>

      <p class="mt-6 text-center text-sm text-gray-600 dark:text-gray-400">
        已有账号?
        <RouterLink to="/login" class="text-blue-600 hover:text-blue-500">
          登录
        </RouterLink>
      </p>
    </div>
  </div>
</template>
