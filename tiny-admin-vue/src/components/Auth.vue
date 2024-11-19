<template>
  <span v-permission="props.hasPermission">
    <slot></slot>
  </span>
  <a-tooltip :title="'No permission, please contact the administrator'">
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
