<script lang="ts" setup>
import {useDark} from "@vueuse/core";

const isDark = useDark()
</script>

<template>
  <Page>
    <!--  <a-config-provider-->
    <!--      :theme="{-->
    <!--      algorithm: isDark ? theme.darkAlgorithm : theme.defaultAlgorithm,-->
    <!--    }"-->
    <!--  >-->
    <router-view/>
    <!--  </a-config-provider>-->
  </Page>
</template>

<style scoped>
</style>