<template>
  <span v-permission="props.hasPermission">
    <slot></slot>
  </span>
  <a-tooltip :title="$t('暂无该权限，请联系管理员')">
    <slot></slot>
  </a-tooltip>
</template>
<script lang="ts" setup>
const props = defineProps({
  hasPermission: {
    type: String || [String],
    default: '',
  },
})
</script>
