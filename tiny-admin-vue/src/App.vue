<script lang="ts" setup>
import {useDark} from "@vueuse/core";

const isDark = useDark()
</script>

<template>
  <!--  <a-config-provider-->
  <!--      :theme="{-->
  <!--      algorithm: isDark ? theme.darkAlgorithm : theme.defaultAlgorithm,-->
  <!--    }"-->
  <!--  >-->
    <router-view/>
  <!--  </a-config-provider>-->
</template>

<style scoped>
</style>