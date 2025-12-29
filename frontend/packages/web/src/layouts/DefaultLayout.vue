<script setup lang="ts">
  import TheHeader from '@/components/common/TheHeader.vue'
  import TheFooter from '@/components/common/TheFooter.vue'
</script>

<template>
  <div class="min-h-screen flex flex-col bg-white dark:bg-gray-900">
    <TheHeader />
    <main class="flex-1 container mx-auto px-4 py-8 max-w-4xl">
      <RouterView />
    </main>
    <TheFooter />
  </div>
</template>
