<script setup lang="ts">
  import { ref, onMounted, h } from 'vue'
  import { NCard, NDataTable, NButton, NSpace, NTag, NPopconfirm, useMessage } from 'naive-ui'
  import type { DataTableColumns } from 'naive-ui'
  import { userApi, type UserDTO, type PageResult, formatDate, USER_ROLE } from '@blog/shared'

  const message = useMessage()
  const loading = ref(false)
  const users = ref<UserDTO[]>([])
  const pagination = ref({
    page: 1,
    pageSize: 10,
    itemCount: 0,
    showSizePicker: true,
    pageSizes: [10, 20, 50],
  })

  const columns: DataTableColumns<UserDTO> = [
    { title: 'ID', key: 'id', width: 60 },
    { title: '用户名', key: 'username' },
    { title: '邮箱', key: 'email' },
    { title: '昵称', key: 'nickname' },
    {
      title: '角色',
      key: 'role',
      width: 90,
      render: (row) =>
        h(
          NTag,
          { type: row.role === 'ADMIN' ? 'success' : 'default', size: 'small' },
          () => USER_ROLE[row.role]
        ),
    },
    {
      title: '状态',
      key: 'enabled',
      width: 80,
      render: (row) =>
        h(NTag, { type: row.enabled ? 'success' : 'error', size: 'small' }, () =>
          row.enabled ? '正常' : '禁用'
        ),
    },
    {
      title: '注册时间',
      key: 'createdAt',
      width: 120,
      render: (row) => formatDate(row.createdAt),
    },
    {
      title: '操作',
      key: 'actions',
      width: 150,
      render: (row) =>
        h(NSpace, null, () => [
          h(
            NButton,
            { size: 'small', onClick: () => handleToggleStatus(row.id) },
            () => (row.enabled ? '禁用' : '启用')
          ),
          h(
            NPopconfirm,
            { onPositiveClick: () => handleDelete(row.id) },
            {
              trigger: () => h(NButton, { size: 'small', type: 'error' }, () => '删除'),
              default: () => '确定删除该用户？',
            }
          ),
        ]),
    },
  ]

  async function loadData() {
    loading.value = true
    try {
      const res = (await userApi.admin.getAll({
        page: pagination.value.page - 1,
        size: pagination.value.pageSize,
      })) as unknown as { data: PageResult<UserDTO> }
      users.value = res.data.content
      pagination.value.itemCount = res.data.totalElements
    } catch (err) {
      message.error('加载失败')
    } finally {
      loading.value = false
    }
  }

  function handlePageChange(page: number) {
    pagination.value.page = page
    loadData()
  }

  function handlePageSizeChange(pageSize: number) {
    pagination.value.pageSize = pageSize
    pagination.value.page = 1
    loadData()
  }

  async function handleToggleStatus(id: number) {
    try {
      await userApi.admin.toggleStatus(id)
      message.success('操作成功')
      loadData()
    } catch (err) {
      message.error('操作失败')
    }
  }

  async function handleDelete(id: number) {
    try {
      await userApi.admin.delete(id)
      message.success('删除成功')
      loadData()
    } catch (err) {
      message.error('删除失败')
    }
  }

  onMounted(loadData)
</script>

<template>
  <div class="space-y-4">
    <h1 class="text-2xl font-bold">用户管理</h1>

    <NCard>
      <NDataTable
        :columns="columns"
        :data="users"
        :loading="loading"
        :pagination="pagination"
        :row-key="(row: UserDTO) => row.id"
        @update:page="handlePageChange"
        @update:page-size="handlePageSizeChange"
      />
    </NCard>
  </div>
</template>
