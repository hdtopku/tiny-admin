<script lang="ts" setup>
import DarkToggle from "@/components/DarkToggle.vue";

const loginForm = ref({
  username: "",
  password: "",
});
</script>

<template>
  <DarkToggle/>
  <div class="flex items-center justify-center h-screen p-4 ">
    <div
        class=" border dark:border-gray-600 bg-white dark:bg-gray-800 shadow-sm hover:shadow-lg rounded-md w-full max-w-lg px-6 py-12 rounded-md">
      <h1 class="sm:text-3xl text-2xl font-bold mb-8 text-center dark:text-gray-200">Tiny Admin</h1>
      <a-form :label-col="{ span: 4 }" auto-complete="off" class="grid sm:gap-4 items-center grid-cols-1" size="large">
        <a-form-item label="用户名">
          <a-input v-model="loginForm.username"/>
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model="loginForm.password" type="password"/>
        </a-form-item>
        <a-form-item class="w-full max-sm:mt-4 sm:w-10/12 ml-auto">
          <a-button block type="primary" @click="handleSubmit">登录</a-button>
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>
