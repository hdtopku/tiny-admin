<script lang="ts" setup>

import {useDark, useTitle} from "@vueuse/core";

const isDark = useDark()

useTitle(() => isDark.value ? '🌙 Good evening!' : '☀️ Good morning!')
</script>

<template>
  <h1 class="text-4xl font-bold text-center">你好，Welcome to my website!</h1>
  <a-button type="primary">Primary Button</a-button>
</template>

<style scoped>

</style>
