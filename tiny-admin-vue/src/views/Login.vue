<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'

import {useUserStore} from '@/store'
import {message} from 'ant-design-vue'
import router from '@/router'
import {GithubOutlined, ReadOutlined} from '@ant-design/icons-vue'

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

<template>
  <div class="dark:bg-gray-800 bg-gray-100 duration-300 flex items-center justify-center h-screen p-4 login">
    <div
        class="border dark:shadow-blue-500/30 shadow-sm hover:shadow-lg w-full max-w-lg px-6 pt-6 pb-12 rounded-md"
    >
      <HeadLink></HeadLink>
      <h1
          class="sm:text-3xl text-2xl font-bold mt-4 mb-8 text-center dark:text-gray-200 bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500"
      >
        Tiny Admin
      </h1>
      <a-form
          :label-col="{ span: 5 }"
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

<style scoped>
</style>
