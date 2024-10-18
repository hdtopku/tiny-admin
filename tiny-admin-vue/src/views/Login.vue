<template>
  <div
      class="dark:bg-gray-800 bg-gray-100 dark:text-gray-200 text-gray-800 duration-300 flex max-sm:pt-4 items-start sm:items-center justify-center h-screen"
  >
    <div
        class="w-11/12 max-w-3xl grid grid-cols-1 max-sm:grid-rows-3 sm:grid-cols-3 md:grid-cols-2 gap-4"
    >
      <div class="drawing"></div>
      <div
          class="max-sm:row-span-2 max-md:col-span-2 dark:bg-gray-800 bg-gray-100 border overflow-hidden dark:shadow-blue-500/30 shadow-sm hover:shadow-lg rounded-md"
      >
        <div class="text-center">
          <HeadLink class="my-4 mr-6"></HeadLink>
          <h1 class="w-full text-3xl sm:text-2xl font-bold text-center">
            Tiny Admin
          </h1>
        </div>
        <a-form
            ref="formRef"
            :model="loginForm"
            :rules="rules"
            auto-complete="off"
            class="grid sm:gap-4 items-center grid-cols-1 m-6"
            size="large"
        >
          <a-form-item name="username">
            <a-input
                v-model:value="loginForm.username"
                :placeholder="$t('用户名')"
                allow-clear
            >
              <template #prefix>
                <UserOutlined/>
              </template>
            </a-input>
          </a-form-item>
          <a-form-item name="password">
            <a-input
                v-model:value="loginForm.password"
                :placeholder="$t('密码')"
                allow-clear
                type="password"
            >
              <template #prefix>
                <LockOutlined/>
              </template>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-button
                :loading="loginLoading"
                block
                type="primary"
                @click="handleLogin"
            >
              <span class="text-md tracking-widest">
                {{ $t('登录') }}
              </span>
            </a-button>
          </a-form-item>
        </a-form>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import {t} from '@/utils/i18n.ts'
import {LockOutlined, UserOutlined} from '@ant-design/icons-vue'
import {useUserStore} from '@/store'
import {message} from 'ant-design-vue'
import router from '@/router'

const loginForm = ref({
  username: '',
  password: '',
})
const rules = {
  username: [
    {required: true, message: t('请输入用户名'), trigger: ['blur', 'change']},
  ],

  password: [
    {required: true, message: t('请输入密码'), trigger: ['blur', 'change']},
  ],
}
const formRef = ref()
const loginLoading = ref(false)
const handleLogin = () => {
  formRef.value.validate().then(() => {
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
  })
}
</script>
<style lang="less" scoped>
.drawing {
  background: url('/login_bg.svg') no-repeat center center;
  background-size: contain;
}
</style>
