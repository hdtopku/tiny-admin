<script lang="ts" setup>

const a = ref(1)
const b = reactive({'name': 'John'})
</script>

<template>
  <h1 class="text-4xl font-bold text-center">Welcome to my website!</h1>
  <a-button type="primary">Primary Button</a-button>
  {{ a }}
  {{ b }}
</template>

<style scoped>

</style>
