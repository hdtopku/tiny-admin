<script lang="ts" setup>
const {text, length} = defineProps({
  text: {type: String, default: ''},
  length: {type: Number, default: 20},
})
</script>

<template>
  <span v-if="text?.length">
    <span v-if="text.length <= length">{{ text }}</span>
    <a-tooltip v-else :arrow="false">
      <template #title>
        <span>{{ text }}</span>
      </template>
      <span>{{ text?.substring(0, length) }}...</span>
    </a-tooltip>
  </span>
</template>

<style scoped>

</style>