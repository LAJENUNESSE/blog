<script setup lang="ts">
  import { ref, onMounted, h } from 'vue'
  import {
    NCard,
    NDataTable,
    NButton,
    NSpace,
    NTag,
    NSelect,
    NPopconfirm,
    useMessage,
  } from 'naive-ui'
  import type { DataTableColumns } from 'naive-ui'
  import { commentApi, type CommentDTO, type PageResult, formatDate, COMMENT_STATUS } from '@blog/shared'

  const message = useMessage()
  const loading = ref(false)
  const comments = ref<CommentDTO[]>([])
  const statusFilter = ref<string>('')
  const pagination = ref({
    page: 1,
    pageSize: 10,
    itemCount: 0,
    showSizePicker: true,
    pageSizes: [10, 20, 50],
  })

  const statusOptions = [
    { label: '全部', value: '' },
    { label: '待审核', value: 'PENDING' },
    { label: '已通过', value: 'APPROVED' },
    { label: '已拒绝', value: 'REJECTED' },
  ]

  const columns: DataTableColumns<CommentDTO> = [
    { title: 'ID', key: 'id', width: 60 },
    {
      title: '内容',
      key: 'content',
      ellipsis: { tooltip: true },
    },
    {
      title: '文章',
      key: 'articleTitle',
      width: 150,
      ellipsis: { tooltip: true },
    },
    {
      title: '作者',
      key: 'authorName',
      width: 100,
      render: (row) => row.authorName || '匿名',
    },
    {
      title: '状态',
      key: 'status',
      width: 90,
      render: (row) => {
        const types: Record<string, 'success' | 'warning' | 'error'> = {
          APPROVED: 'success',
          PENDING: 'warning',
          REJECTED: 'error',
        }
        return h(NTag, { type: types[row.status], size: 'small' }, () => COMMENT_STATUS[row.status])
      },
    },
    {
      title: '时间',
      key: 'createdAt',
      width: 120,
      render: (row) => formatDate(row.createdAt),
    },
    {
      title: '操作',
      key: 'actions',
      width: 200,
      render: (row) =>
        h(NSpace, null, () => [
          row.status === 'PENDING' &&
            h(NButton, { size: 'small', type: 'success', onClick: () => handleApprove(row.id) }, () => '通过'),
          row.status === 'PENDING' &&
            h(NButton, { size: 'small', type: 'warning', onClick: () => handleReject(row.id) }, () => '拒绝'),
          h(
            NPopconfirm,
            { onPositiveClick: () => handleDelete(row.id) },
            {
              trigger: () => h(NButton, { size: 'small', type: 'error' }, () => '删除'),
              default: () => '确定删除该评论？',
            }
          ),
        ]),
    },
  ]

  async function loadData() {
    loading.value = true
    try {
      const res = (await commentApi.admin.getAll({
        page: pagination.value.page - 1,
        size: pagination.value.pageSize,
        status: statusFilter.value || undefined,
      })) as unknown as { data: PageResult<CommentDTO> }
      comments.value = res.data.content
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

  function handleStatusChange() {
    pagination.value.page = 1
    loadData()
  }

  async function handleApprove(id: number) {
    try {
      await commentApi.admin.approve(id)
      message.success('审核通过')
      loadData()
    } catch (err) {
      message.error('操作失败')
    }
  }

  async function handleReject(id: number) {
    try {
      await commentApi.admin.reject(id)
      message.success('已拒绝')
      loadData()
    } catch (err) {
      message.error('操作失败')
    }
  }

  async function handleDelete(id: number) {
    try {
      await commentApi.admin.delete(id)
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
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-bold">评论管理</h1>
      <NSelect
        v-model:value="statusFilter"
        :options="statusOptions"
        style="width: 120px"
        @update:value="handleStatusChange"
      />
    </div>

    <NCard>
      <NDataTable
        :columns="columns"
        :data="comments"
        :loading="loading"
        :pagination="pagination"
        :row-key="(row: CommentDTO) => row.id"
        @update:page="handlePageChange"
        @update:page-size="handlePageSizeChange"
      />
    </NCard>
  </div>
</template>
