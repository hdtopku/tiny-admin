<script lang="ts" setup>
import websocketClient from "@/utils/websocket"

websocketClient.activate()

</script>

<template>
  <div>
    <slot></slot>
  </div>
</template>

<style scoped>

</style>