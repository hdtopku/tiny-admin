<template>
  <div
      class="max-sm:pt-4 sm:items-center items-start dark:bg-gray-800 dark:text-gray-200 bg-gray-100 text-gray-800 flex justify-center duration-300 h-screen"
  >
    <div
        class="grid-cols-1 max-sm:grid-rows-3 sm:grid-cols-3 md:grid-cols-2 w-11/12 max-w-4xl grid gap-4"
    >
      <div class="drawing"></div>
      <div class="max-sm:row-span-2 max-md:col-span-2 ">
        <HeadLink class="mb-2 mr-6"></HeadLink>
        <div
            class="dark:shadow-blue-500/30 hover:shadow-xl shadow-md border overflow-hidden rounded-xl p-4"
        >
          <div>
            <div class="text-lg text-center">
            <span
                class="inline-block italic animate__animated animate__lightSpeedInLeft animate__slower animate__delay-1s animate__repeat-3 tracking-tight">
            Welcome to
              </span>
              <span class="text-3xl font-bold tracking-wide ml-2">Tiny Admin</span>
            </div>
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
                  allow-clear autocomplete>
                <template #prefix>
                  <UserOutlined/>
                </template>
              </a-input>
            </a-form-item>
            <a-form-item name="password">
              <a-input-password
                  v-model:value="loginForm.password"
                  :placeholder="$t('密码')"
                  allow-clear
                  autocomplete="current-password"
                  type="password"
              >
                <template #prefix>
                  <LockOutlined/>
                </template>
              </a-input-password>
            </a-form-item>
            <a-form-item>
              <a-button
                  :class="loginError ? 'animate__animated animate__headShake': ''"
                  :loading="loginLoading"
                  block
                  class="hover:scale-[102%] duration-500"
                  type="primary"
                  @click="handleLogin"
              >
              <span :class="loginLoading ? ' animate__animated animate__slideOutRight animate__infinite animate__slower' : ''"
                    class="text-md tracking-widest">
                {{ $t('登录') }}
              </span>
              </a-button>
            </a-form-item>
          </a-form>
        </div>
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
const loginError = ref(false)
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
        .catch(() => {
          loginError.value = true
        })
        .finally(() => {
          loginLoading.value = false
          setTimeout(() => {
            loginError.value = false
          }, 600)
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
