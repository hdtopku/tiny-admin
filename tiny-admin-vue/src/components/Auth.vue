<script lang="ts" setup>
const props = defineProps({
  hasPermission: {
    type: String || [String],
    default: '',
  }
})
</script>

<template>
  <span v-permission="props.hasPermission">
    <slot></slot>
  </span>
  <a-tooltip title="暂无该权限，请联系管理员">
    <slot></slot>
  </a-tooltip>
</template>

<style scoped>

</style>