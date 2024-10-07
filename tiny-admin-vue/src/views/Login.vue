<template>
  <div class="flex items-center justify-center h-screen p-4">
    <div
        class="border dark:border-gray-600 bg-white dark:bg-gray-800 shadow-sm hover:shadow-lg w-full max-w-lg px-6 py-12 rounded-md"
    >
      <h1
          class="sm:text-3xl text-2xl font-bold mb-8 text-center dark:text-gray-200"
      >
        Tiny Admin
      </h1>
      <a-form
          :label-col="{ span: 3 }"
          :model="loginForm"
          :rules="{}"
          auto-complete="off"
          class="grid sm:gap-4 items-center grid-cols-1"
          size="large"
      >
        <a-form-item :label="$t('用户名')">
          <a-input v-model:value="loginForm.username"/>
        </a-form-item>
        <a-form-item :label="$t('密码')">
          <a-input v-model:value="loginForm.password" type="password"/>
        </a-form-item>
        <a-form-item class="w-full max-sm:mt-4 sm:w-[88%] ml-auto">
          <a-button
              :loading="loginLoading"
              block
              type="primary"
              @click="handleLogin"
          >{{ $t('登录') }}
          </a-button
          >
        </a-form-item>
      </a-form>
    </div>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {useUserStore} from '@/store'
import {message} from 'ant-design-vue'
import router from '@/router'

const loginForm = ref({
  username: '',
  password: '',
})
const loginLoading = ref(false)
const handleLogin = () => {
  loginLoading.value = true
  const userStore = useUserStore()
  userStore
      .login(loginForm.value)
      .then(() => {
        router.push('/home')
        message.success(t('登录成功！'))
      })
      .catch((error) => {
        console.log(error)
        message.error(error)
      })
      .finally(() => {
        loginLoading.value = false
      })
}
</script>
