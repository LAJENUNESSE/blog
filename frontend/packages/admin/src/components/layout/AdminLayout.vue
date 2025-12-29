<script setup lang="ts">
  import { ref, h, computed } from 'vue'
  import { useRouter, useRoute, RouterLink } from 'vue-router'
  import {
    NLayout,
    NLayoutSider,
    NLayoutHeader,
    NLayoutContent,
    NMenu,
    NDropdown,
    NAvatar,
    NIcon,
    NBadge,
  } from 'naive-ui'
  import type { MenuOption } from 'naive-ui'
  import { useAuthStore } from '@/stores'

  const router = useRouter()
  const route = useRoute()
  const authStore = useAuthStore()
  const collapsed = ref(false)

  const menuOptions: MenuOption[] = [
    {
      label: () => h(RouterLink, { to: '/dashboard' }, { default: () => '仪表盘' }),
      key: 'dashboard',
      icon: () =>
        h(NIcon, null, {
          default: () =>
            h('svg', { viewBox: '0 0 24 24', fill: 'currentColor', width: '1em', height: '1em' }, [
              h('path', {
                d: 'M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z',
              }),
            ]),
        }),
    },
    {
      label: () => h(RouterLink, { to: '/articles' }, { default: () => '文章管理' }),
      key: 'articles',
      icon: () =>
        h(NIcon, null, {
          default: () =>
            h('svg', { viewBox: '0 0 24 24', fill: 'currentColor', width: '1em', height: '1em' }, [
              h('path', {
                d: 'M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z',
              }),
            ]),
        }),
    },
    {
      label: () => h(RouterLink, { to: '/categories' }, { default: () => '分类管理' }),
      key: 'categories',
      icon: () =>
        h(NIcon, null, {
          default: () =>
            h('svg', { viewBox: '0 0 24 24', fill: 'currentColor', width: '1em', height: '1em' }, [
              h('path', { d: 'M10 4H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V8c0-1.1-.9-2-2-2h-8l-2-2z' }),
            ]),
        }),
    },
    {
      label: () => h(RouterLink, { to: '/tags' }, { default: () => '标签管理' }),
      key: 'tags',
      icon: () =>
        h(NIcon, null, {
          default: () =>
            h('svg', { viewBox: '0 0 24 24', fill: 'currentColor', width: '1em', height: '1em' }, [
              h('path', {
                d: 'M21.41 11.58l-9-9C12.05 2.22 11.55 2 11 2H4c-1.1 0-2 .9-2 2v7c0 .55.22 1.05.59 1.42l9 9c.36.36.86.58 1.41.58.55 0 1.05-.22 1.41-.59l7-7c.37-.36.59-.86.59-1.41 0-.55-.23-1.06-.59-1.42zM5.5 7C4.67 7 4 6.33 4 5.5S4.67 4 5.5 4 7 4.67 7 5.5 6.33 7 5.5 7z',
              }),
            ]),
        }),
    },
    {
      label: () => h(RouterLink, { to: '/comments' }, { default: () => '评论管理' }),
      key: 'comments',
      icon: () =>
        h(NIcon, null, {
          default: () =>
            h('svg', { viewBox: '0 0 24 24', fill: 'currentColor', width: '1em', height: '1em' }, [
              h('path', {
                d: 'M21.99 4c0-1.1-.89-2-1.99-2H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h14l4 4-.01-18z',
              }),
            ]),
        }),
    },
    {
      label: () => h(RouterLink, { to: '/users' }, { default: () => '用户管理' }),
      key: 'users',
      icon: () =>
        h(NIcon, null, {
          default: () =>
            h('svg', { viewBox: '0 0 24 24', fill: 'currentColor', width: '1em', height: '1em' }, [
              h('path', {
                d: 'M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z',
              }),
            ]),
        }),
    },
    {
      label: () => h(RouterLink, { to: '/settings' }, { default: () => '系统设置' }),
      key: 'settings',
      icon: () =>
        h(NIcon, null, {
          default: () =>
            h('svg', { viewBox: '0 0 24 24', fill: 'currentColor', width: '1em', height: '1em' }, [
              h('path', {
                d: 'M19.14 12.94c.04-.31.06-.63.06-.94 0-.31-.02-.63-.06-.94l2.03-1.58c.18-.14.23-.41.12-.61l-1.92-3.32c-.12-.22-.37-.29-.59-.22l-2.39.96c-.5-.38-1.03-.7-1.62-.94l-.36-2.54c-.04-.24-.24-.41-.48-.41h-3.84c-.24 0-.43.17-.47.41l-.36 2.54c-.59.24-1.13.57-1.62.94l-2.39-.96c-.22-.08-.47 0-.59.22L2.74 8.87c-.12.21-.08.47.12.61l2.03 1.58c-.04.31-.06.63-.06.94s.02.63.06.94l-2.03 1.58c-.18.14-.23.41-.12.61l1.92 3.32c.12.22.37.29.59.22l2.39-.96c.5.38 1.03.7 1.62.94l.36 2.54c.05.24.24.41.48.41h3.84c.24 0 .44-.17.47-.41l.36-2.54c.59-.24 1.13-.56 1.62-.94l2.39.96c.22.08.47 0 .59-.22l1.92-3.32c.12-.22.07-.47-.12-.61l-2.01-1.58zM12 15.6c-1.98 0-3.6-1.62-3.6-3.6s1.62-3.6 3.6-3.6 3.6 1.62 3.6 3.6-1.62 3.6-3.6 3.6z',
              }),
            ]),
        }),
    },
  ]

  const activeKey = computed(() => {
    const path = route.path
    if (path.startsWith('/articles')) return 'articles'
    if (path.startsWith('/categories')) return 'categories'
    if (path.startsWith('/tags')) return 'tags'
    if (path.startsWith('/comments')) return 'comments'
    if (path.startsWith('/users')) return 'users'
    if (path.startsWith('/settings')) return 'settings'
    return 'dashboard'
  })

  const userOptions = [
    { label: '退出登录', key: 'logout' },
  ]

  function handleUserSelect(key: string) {
    if (key === 'logout') {
      authStore.logout()
      router.push('/login')
    }
  }
</script>

<template>
  <NLayout has-sider class="h-screen">
    <NLayoutSider
      bordered
      collapse-mode="width"
      :collapsed-width="64"
      :width="220"
      :collapsed="collapsed"
      show-trigger
      @collapse="collapsed = true"
      @expand="collapsed = false"
    >
      <div class="h-16 flex items-center justify-center border-b border-gray-200">
        <span v-if="!collapsed" class="text-lg font-bold">Blog Admin</span>
        <span v-else class="text-lg font-bold">B</span>
      </div>
      <NMenu
        :collapsed="collapsed"
        :collapsed-width="64"
        :collapsed-icon-size="22"
        :options="menuOptions"
        :value="activeKey"
      />
    </NLayoutSider>
    <NLayout>
      <NLayoutHeader bordered class="h-16 px-6 flex items-center justify-between">
        <div></div>
        <NDropdown :options="userOptions" @select="handleUserSelect">
          <div class="flex items-center gap-2 cursor-pointer">
            <NAvatar round size="small">
              {{ authStore.user?.username?.charAt(0).toUpperCase() }}
            </NAvatar>
            <span>{{ authStore.user?.nickname || authStore.user?.username }}</span>
          </div>
        </NDropdown>
      </NLayoutHeader>
      <NLayoutContent class="p-6 bg-gray-50" content-style="height: calc(100vh - 64px); overflow: auto;">
        <RouterView />
      </NLayoutContent>
    </NLayout>
  </NLayout>
</template>
