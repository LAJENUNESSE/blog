import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@blog/shared'

const router = createRouter({
  history: createWebHistory('/admin/'),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
    },
    {
      path: '/',
      component: () => import('@/components/layout/AdminLayout.vue'),
      meta: { requiresAuth: true },
      children: [
        {
          path: '',
          redirect: '/dashboard',
        },
        {
          path: 'dashboard',
          name: 'dashboard',
          component: () => import('@/views/DashboardView.vue'),
        },
        {
          path: 'articles',
          name: 'articles',
          component: () => import('@/views/article/ArticleListView.vue'),
        },
        {
          path: 'articles/new',
          name: 'article-new',
          component: () => import('@/views/article/ArticleEditView.vue'),
        },
        {
          path: 'articles/:id/edit',
          name: 'article-edit',
          component: () => import('@/views/article/ArticleEditView.vue'),
        },
        {
          path: 'categories',
          name: 'categories',
          component: () => import('@/views/category/CategoryView.vue'),
        },
        {
          path: 'tags',
          name: 'tags',
          component: () => import('@/views/tag/TagView.vue'),
        },
        {
          path: 'comments',
          name: 'comments',
          component: () => import('@/views/comment/CommentView.vue'),
        },
        {
          path: 'users',
          name: 'users',
          component: () => import('@/views/user/UserView.vue'),
        },
        {
          path: 'settings',
          name: 'settings',
          component: () => import('@/views/setting/SettingView.vue'),
        },
      ],
    },
  ],
})

router.beforeEach((to, _from, next) => {
  const authStore = useAuthStore()

  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next({ name: 'login', query: { redirect: to.fullPath } })
  } else if (to.name === 'login' && authStore.isAuthenticated) {
    next({ name: 'dashboard' })
  } else {
    next()
  }
})

export default router
