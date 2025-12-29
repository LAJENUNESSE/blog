<script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { NCard, NForm, NFormItem, NInput, NButton, useMessage } from 'naive-ui'
  import { settingApi, type SettingDTO } from '@blog/shared'

  const message = useMessage()
  const loading = ref(false)
  const saving = ref(false)
  const settings = ref<Record<string, string>>({
    site_name: '',
    site_description: '',
    site_keywords: '',
    site_footer: '',
  })

  async function loadData() {
    loading.value = true
    try {
      const res = (await settingApi.admin.getAll()) as unknown as { data: SettingDTO[] }
      res.data.forEach((s) => {
        if (s.key in settings.value) {
          settings.value[s.key] = s.value || ''
        }
      })
    } catch (err) {
      message.error('加载失败')
    } finally {
      loading.value = false
    }
  }

  async function handleSave() {
    saving.value = true
    try {
      await settingApi.admin.batchUpdate(settings.value)
      message.success('保存成功')
    } catch (err) {
      message.error('保存失败')
    } finally {
      saving.value = false
    }
  }

  onMounted(loadData)
</script>

<template>
  <div class="space-y-4">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-bold">系统设置</h1>
      <NButton type="primary" :loading="saving" @click="handleSave">保存设置</NButton>
    </div>

    <NCard title="站点信息" :loading="loading">
      <NForm :model="settings" label-placement="left" label-width="120">
        <NFormItem label="站点名称">
          <NInput v-model:value="settings.site_name" placeholder="博客名称" />
        </NFormItem>
        <NFormItem label="站点描述">
          <NInput
            v-model:value="settings.site_description"
            type="textarea"
            :rows="2"
            placeholder="站点描述"
          />
        </NFormItem>
        <NFormItem label="SEO 关键词">
          <NInput v-model:value="settings.site_keywords" placeholder="关键词，用逗号分隔" />
        </NFormItem>
        <NFormItem label="页脚信息">
          <NInput
            v-model:value="settings.site_footer"
            type="textarea"
            :rows="2"
            placeholder="页脚版权信息"
          />
        </NFormItem>
      </NForm>
    </NCard>
  </div>
</template>
