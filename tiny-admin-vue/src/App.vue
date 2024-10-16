<script lang="ts" setup>
import {useDark} from "@vueuse/core";
import {theme} from "ant-design-vue";

const isDark = useDark()
</script>

<template>
  <a-config-provider
      :theme="{
        // token: {
        //   colorPrimary: '#00b96b',
        //   colorLink: '#00b96b',
        // },
          algorithm: isDark ? theme.darkAlgorithm : theme.defaultAlgorithm,
      }"
  >
    <Page>
      <router-view/>
    </Page>
  </a-config-provider>
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Open+Sans:ital,wght@0,300..800;1,300..800&display=swap');

* {
  font-family: "Open Sans", sans-serif;
  font-optical-sizing: auto;
  font-weight: 400;
  font-style: normal;
  font-variation-settings: "wdth" 100;
}
</style>