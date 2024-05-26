<script lang="ts" setup>

import {useDark, useTitle} from "@vueuse/core";

const isDark = useDark()

useTitle(() => isDark.value ? '🌙 Good evening!' : '☀️ Good morning!')
</script>

<template>
  <h1 class="text-4xl font-bold text-center">Welcome to Tiny Admin!</h1>
</template>
