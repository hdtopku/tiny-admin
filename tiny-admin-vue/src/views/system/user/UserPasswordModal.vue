<script lang="ts" setup>
import {message} from 'ant-design-vue'

import {Rule} from 'ant-design-vue/es/form'
import {updatePassword} from '@/api/system/user.ts'

const passwordModalVisible = ref(false)
const passwordItem = ref({
  id: '',
  password: '',
})
const username = ref('')
const passwordRules: Record<string, Rule[]> = reactive({
  password: [
    {
      required: true,
      message: 'Please enter a new password',
      trigger: ['blur'],
      whitespace: false,
    },
    {
      min: 6,
      max: 20,
      message: 'Password length must be between 6 and 20 characters',
      trigger: ['change', 'blur'],
    },
  ],
})
const passwordFormRef = ref()
const handleChangePassword = () => {
  passwordFormRef.value.validate().then(() => {
    updatePassword({...passwordItem.value}).then(() => {
      passwordModalVisible.value = false
      message.success('Password updated successfully')
    })
  })
}
defineExpose({
  openModal: (item: any = {}) => {
    passwordModalVisible.value = true
    passwordItem.value = {id: item.key, password: item.password}
    username.value = item.username
  },
})
</script>

<template>
  <a-modal
      v-model:open="passwordModalVisible"
      :cancel-text="'Cancel'"
      :ok-text="'Submit'"
      :title="'Change password for ' + username"
      @cancel="() => (passwordModalVisible = false)"
      @ok="handleChangePassword"
  >
    <a-form
        ref="passwordFormRef"
        :model="passwordItem"
        :rules="passwordRules"
        @keydown.enter="handleChangePassword"
    >
      <a-form-item name="password" required>
        <a-input
            v-model:value="passwordItem.password"
            :placeholder="'Please enter a new password'"
            allow-clear
            autocomplete="off"
        ></a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
