<script setup lang="ts">
  import { ref } from 'vue'
  import { RouterLink } from 'vue-router'
  import { useThemeStore, useAuthStore } from '@/stores'

  const themeStore = useThemeStore()
  const authStore = useAuthStore()

  const searchKeyword = ref('')

  function handleSearch() {
    if (searchKeyword.value.trim()) {
      window.location.href = `/search?keyword=${encodeURIComponent(searchKeyword.value.trim())}`
    }
  }
</script>

<template>
  <header class="border-b border-gray-200 dark:border-gray-700 bg-white dark:bg-gray-900">
    <div class="container mx-auto px-4 max-w-4xl">
      <div class="flex items-center justify-between h-16">
        <!-- Logo -->
        <RouterLink to="/" class="text-xl font-bold text-gray-900 dark:text-white">
          Blog
        </RouterLink>

        <!-- Navigation -->
        <nav class="hidden md:flex items-center space-x-6">
          <RouterLink
            to="/"
            class="text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white"
          >
            首页
          </RouterLink>
          <RouterLink
            to="/about"
            class="text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white"
          >
            关于
          </RouterLink>
        </nav>

        <!-- Right Actions -->
        <div class="flex items-center space-x-4">
          <!-- Search -->
          <form @submit.prevent="handleSearch" class="hidden sm:block">
            <input
              v-model="searchKeyword"
              type="text"
              placeholder="搜索文章..."
              class="w-40 px-3 py-1.5 text-sm border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-800 text-gray-900 dark:text-white focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </form>

          <!-- Theme Toggle -->
          <button
            @click="themeStore.toggleTheme()"
            class="p-2 text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white"
            :title="themeStore.theme === 'dark' ? '切换到浅色' : '切换到深色'"
          >
            <svg
              v-if="themeStore.theme === 'dark'"
              class="w-5 h-5"
              fill="currentColor"
              viewBox="0 0 20 20"
            >
              <path
                d="M10 2a1 1 0 011 1v1a1 1 0 11-2 0V3a1 1 0 011-1zm4 8a4 4 0 11-8 0 4 4 0 018 0zm-.464 4.95l.707.707a1 1 0 001.414-1.414l-.707-.707a1 1 0 00-1.414 1.414zm2.12-10.607a1 1 0 010 1.414l-.706.707a1 1 0 11-1.414-1.414l.707-.707a1 1 0 011.414 0zM17 11a1 1 0 100-2h-1a1 1 0 100 2h1zm-7 4a1 1 0 011 1v1a1 1 0 11-2 0v-1a1 1 0 011-1zM5.05 6.464A1 1 0 106.465 5.05l-.708-.707a1 1 0 00-1.414 1.414l.707.707zm1.414 8.486l-.707.707a1 1 0 01-1.414-1.414l.707-.707a1 1 0 011.414 1.414zM4 11a1 1 0 100-2H3a1 1 0 000 2h1z"
              />
            </svg>
            <svg v-else class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
              <path d="M17.293 13.293A8 8 0 016.707 2.707a8.001 8.001 0 1010.586 10.586z" />
            </svg>
          </button>

          <!-- Auth -->
          <template v-if="authStore.isAuthenticated">
            <span class="text-sm text-gray-600 dark:text-gray-300">
              {{ authStore.user?.nickname || authStore.user?.username }}
            </span>
            <button
              @click="authStore.logout()"
              class="text-sm text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white"
            >
              退出
            </button>
          </template>
          <template v-else>
            <RouterLink
              to="/login"
              class="text-sm text-gray-600 dark:text-gray-300 hover:text-gray-900 dark:hover:text-white"
            >
              登录
            </RouterLink>
          </template>
        </div>
      </div>
    </div>
  </header>
</template>
