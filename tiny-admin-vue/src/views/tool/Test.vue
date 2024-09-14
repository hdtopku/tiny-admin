<script lang="ts" setup>
// import ChatWindow from "@/views/tool/ChatWindow.vue";
import useGlobal from "@/hooks/useGlobal.ts";

const {$bus} = useGlobal()
$bus.emit('newMessage')

</script>


<template>
  <div class="relative">
<!--    <ChatWindow/>-->
  </div>
</template>
