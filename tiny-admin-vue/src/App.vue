<script lang="ts" setup>
import {useDark} from "@vueuse/core";
import {theme} from "ant-design-vue";

const isDark = useDark()
</script>

<template>
  <Page>
    <a-config-provider
        :theme="{
        // token: {
        //   colorPrimary: '#00b96b',
        //   colorLink: '#00b96b',
        // },
          algorithm: isDark ? theme.darkAlgorithm : theme.defaultAlgorithm,
      }"
    >
      <router-view/>
    </a-config-provider>
  </Page>
</template>