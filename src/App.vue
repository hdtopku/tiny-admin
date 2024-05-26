<script lang="ts" setup>
import {theme} from "ant-design-vue";
import {useDark} from "@vueuse/core";

const isDark = useDark()
</script>

<template>
  <transition mode="out-in" name="fade">
    <a-config-provider
        :theme="{
      algorithm: isDark ? theme.darkAlgorithm : theme.defaultAlgorithm,
    }"
    >
      <router-view/>
    </a-config-provider>
  </transition>
</template>

<style scoped>

</style>
