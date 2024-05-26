<script lang="ts" setup>
import {theme} from "ant-design-vue";
import {useDark} from "@vueuse/core";

const isDark = useDark()
</script>

<template>
  <a-config-provider
      :theme="{
      algorithm: isDark ? theme.darkAlgorithm : theme.defaultAlgorithm,
    }"
  >
    <router-view v-slot="{ Component, route }">
      <transition mode="out-in" name="fade">
        <div :key="route.path">
          <component :is="Component"></component>
        </div>
      </transition>
    </router-view>
  </a-config-provider>
</template>
