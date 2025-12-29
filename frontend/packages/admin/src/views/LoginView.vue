<script setup lang="ts">
  import { ref } from 'vue'
  import { useRouter, useRoute } from 'vue-router'
  import { NCard, NForm, NFormItem, NInput, NButton, useMessage } from 'naive-ui'
  import { useAuthStore } from '@/stores'

  const router = useRouter()
  const route = useRoute()
  const message = useMessage()
  const authStore = useAuthStore()

  const formRef = ref()
  const loading = ref(false)
  const formValue = ref({
    username: '',
    password: '',
  })

  const rules = {
    username: { required: true, message: '请输入用户名', trigger: 'blur' },
    password: { required: true, message: '请输入密码', trigger: 'blur' },
  }

  async function handleLogin() {
    try {
      await formRef.value?.validate()
    } catch {
      return
    }

    loading.value = true
    try {
      await authStore.login(formValue.value)

      if (!authStore.isAdmin) {
        authStore.logout()
        message.error('需要管理员权限')
        return
      }

      message.success('登录成功')
      const redirect = (route.query.redirect as string) || '/dashboard'
      router.push(redirect)
    } catch (err: unknown) {
      message.error(err instanceof Error ? err.message : '登录失败')
    } finally {
      loading.value = false
    }
  }
</script>

<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-100">
    <NCard class="w-96" title="管理后台登录">
      <NForm ref="formRef" :model="formValue" :rules="rules">
        <NFormItem label="用户名" path="username">
          <NInput v-model:value="formValue.username" placeholder="请输入用户名" />
        </NFormItem>
        <NFormItem label="密码" path="password">
          <NInput
            v-model:value="formValue.password"
            type="password"
            placeholder="请输入密码"
            @keyup.enter="handleLogin"
          />
        </NFormItem>
        <NButton type="primary" block :loading="loading" @click="handleLogin">
          登录
        </NButton>
      </NForm>
    </NCard>
  </div>
</template>
